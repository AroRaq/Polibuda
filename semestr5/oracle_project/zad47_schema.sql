ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD';
ALTER SESSION SET ddl_lock_timeout=1;

create or replace type O_KOT as OBJECT (
    imie            VARCHAR2(15),
    plec            VARCHAR2(1),
    pseudo          VARCHAR2(15),
    funkcja         VARCHAR(15),
    szef            ref O_KOT,
    w_stadku_od     DATE,
    przydzial_myszy NUMBER(3),
    myszy_extra     NUMBER(3),
    map member function compare return VARCHAR2,
    member function dochod return NUMBER,
    member function rok_dolaczenia return VARCHAR2
);

create or replace type body O_KOT is
    map member function compare return VARCHAR2 is
    begin
        return pseudo;
    end;

    member function dochod return number is
    begin
        return nvl(myszy_extra, 0) + przydzial_myszy;
    end;

    member function rok_dolaczenia return VARCHAR2 is
    begin
        return to_char(extract(year from w_stadku_od));
    end;
end;

create or replace type O_PLEBS as OBJECT
(
    id_pleb NUMBER,
    kot ref O_KOT,
    map member function compare return number
);

create or replace type body O_PLEBS is
    map member function compare return number is
    begin
        return id_pleb;
    end;
end;

create or replace type O_ELITA as OBJECT
(
    id_elity NUMBER,
    kot ref O_KOT,
    sluga ref O_PLEBS,
    map member function compare return NUMBER,
    member function ma_sluge return BOOLEAN,
    member procedure przypisz_sluge(slug ref O_PLEBS)
);

create or replace type body O_ELITA is
    map member function compare return NUMBER is
    begin
        return id_elity;
    end;

    member function ma_sluge return BOOLEAN is
    begin
        return sluga is not null;
    end;

    member procedure przypisz_sluge(slug ref O_PLEBS) is
    begin
        sluga := slug;
    end;
end;


create or replace type O_KONTO as OBJECT
(
    id_konta            NUMBER,
    data_wprowadzenia   DATE,
    data_usuniecia      DATE,
    wlasciciel          ref O_ELITA,
    map member function compare return NUMBER,
    member function ma_mysz return BOOLEAN,
    member procedure wprowadz_mysz,
    member procedure usun_mysz
);

create or replace type body O_KONTO is
    map member function compare return NUMBER is
    begin
        return id_konta;
    end;

    member function ma_mysz return BOOLEAN is
    begin
        return (data_wprowadzenia is not null and data_usuniecia is null)
            or (data_wprowadzenia is not null and data_usuniecia < data_wprowadzenia);
    end;

    member procedure wprowadz_mysz is
    begin
        data_wprowadzenia := SYSDATE;
    end;

    member procedure usun_mysz is
    begin
        data_usuniecia := SYSDATE;
    end;
end;

create table T_KOT1 of O_KOT
(constraint kot_psd_pk1 primary key (pseudo));

create table T_PLEBS of O_PLEBS
(constraint plb_idp_pk primary key (id_pleb));

create table T_ELITA of O_ELITA
(constraint elt_ide_pk primary key (id_elity));

create table T_KONTO of O_KONTO
(CONSTRAINT kon_idw_pk PRIMARY KEY (id_konta));

create or replace trigger TRG_PLEBS_INSERT
before insert or update on T_PLEBS for each row
declare
    ile NUMBER;
    pragma autonomous_transaction;
begin
    select count(deref(P.kot).pseudo) into ile from T_PLEBS P where deref(P.kot).pseudo = deref(:NEW.kot).pseudo;
    if ile > 0 then raise_application_error(-20001, 'Kot jest już wpisany do plebsu!');
    end if;

    select count(deref(E.kot).pseudo) into ile from T_ELITA E where deref(E.kot).pseudo = deref(:NEW.kot).pseudo;
    if ile > 0 then raise_application_error(-20001, 'Kot jest już wpisany do elity!');
    end if;
    DBMS_OUTPUT.PUT_LINE('TRIGGER_PLEBS');
end;

create or replace trigger TRG_ELITA_INSERT
before insert or update on T_ELITA for each row
declare
    ile NUMBER;
    pragma autonomous_transaction;
begin
    select count(deref(P.kot).pseudo) into ile from T_PLEBS P where deref(P.kot).pseudo = deref(:NEW.kot).pseudo;
    if ile > 0 then raise_application_error(-20001, 'Kot jest już wpisany do plebsu!');
    end if;

    select count(deref(E.kot).pseudo) into ile from T_ELITA E where deref(E.kot).pseudo = deref(:NEW.kot).pseudo;
    if ile > 0 then raise_application_error(-20001, 'Kot jest już wpisany do elity!');
    end if;
    DBMS_OUTPUT.PUT_LINE('TRIGGER_ELITA');
end;


drop table T_KONTO;
drop table T_ELITA;
drop table T_PLEBS;
drop table T_KOT1;

drop type O_KONTO;
drop type O_ELITA;
drop type O_PLEBS;
drop type O_KOT;


-- INSERT ALL
BEGIN
    INSERT INTO T_KOT1 VALUES('MRUCZEK','M','TYGRYS','SZEFUNIO',NULL,'2002-01-01',103,33   );
    INSERT INTO T_KOT1 VALUES('MICKA','D','LOLA','MILUSIA', (select ref(K) from T_KOT1 K where K.pseudo = 'TYGRYS') ,'2009-10-14',25,47     );
    INSERT INTO T_KOT1 VALUES('CHYTRY','M','BOLEK','DZIELCZY',(select ref(K) from T_KOT1 K where K.pseudo = 'TYGRYS'),'2002-05-05',50,NULL);
    INSERT INTO T_KOT1 VALUES('KOREK','M','ZOMBI','BANDZIOR',(select ref(K) from T_KOT1 K where K.pseudo = 'TYGRYS'),'2004-03-16',75,13   );
    INSERT INTO T_KOT1 VALUES('BOLEK','M','LYSY','BANDZIOR',(select ref(K) from T_KOT1 K where K.pseudo = 'TYGRYS'),'2006-08-15',72,21    );
    INSERT INTO T_KOT1 VALUES('RUDA','D','MALA','MILUSIA',(select ref(K) from T_KOT1 K where K.pseudo = 'TYGRYS'),'2006-09-17',22,42      );
    INSERT INTO T_KOT1 VALUES('PUCEK','M','RAFA','LOWCZY',(select ref(K) from T_KOT1 K where K.pseudo = 'TYGRYS'),'2006-10-15',65,NULL    );
    INSERT INTO T_KOT1 VALUES('SONIA','D','PUSZYSTA','MILUSIA',(select ref(K) from T_KOT1 K where K.pseudo = 'ZOMBI'),'2010-11-18',20,35  );
    INSERT INTO T_KOT1 VALUES('PUNIA','D','KURKA','LOWCZY',(select ref(K) from T_KOT1 K where K.pseudo = 'ZOMBI'),'2008-01-01',61,NULL    );
    INSERT INTO T_KOT1 VALUES('JACEK','M','PLACEK','LOWCZY',(select ref(K) from T_KOT1 K where K.pseudo = 'LYSY'),'2008-12-01',67,NULL    );
    INSERT INTO T_KOT1 VALUES('BARI','M','RURA','LAPACZ',(select ref(K) from T_KOT1 K where K.pseudo = 'LYSY'),'2009-09-01',56,NULL       );
    INSERT INTO T_KOT1 VALUES('ZUZIA','D','SZYBKA','LOWCZY',(select ref(K) from T_KOT1 K where K.pseudo = 'LYSY'),'2006-07-21',65,NULL    );
    INSERT INTO T_KOT1 VALUES('BELA','D','LASKA','MILUSIA',(select ref(K) from T_KOT1 K where K.pseudo = 'LYSY'),'2008-02-01',24,28       );
    INSERT INTO T_KOT1 VALUES('KSAWERY','M','MAN','LAPACZ',(select ref(K) from T_KOT1 K where K.pseudo = 'RAFA'),'2008-07-12',51,NULL     );
    INSERT INTO T_KOT1 VALUES('LATKA','D','UCHO','KOT',(select ref(K) from T_KOT1 K where K.pseudo = 'RAFA'),'2011-01-01',40,NULL         );
    INSERT INTO T_KOT1 VALUES('DUDEK','M','MALY','KOT',(select ref(K) from T_KOT1 K where K.pseudo = 'RAFA'),'2011-05-15',40,NULL         );
    INSERT INTO T_KOT1 VALUES('MELA','D','DAMA','LAPACZ',(select ref(K) from T_KOT1 K where K.pseudo = 'RAFA'),'2008-11-01',51,NULL       );
    INSERT INTO T_KOT1 VALUES('LUCEK','M','ZERO','KOT',(select ref(K) from T_KOT1 K where K.pseudo = 'KURKA'),'2010-03-01',43,NULL        );
END;

alter table T_PLEBS disable all triggers;
alter table T_ELITA disable all triggers;
alter table T_KOT1 disable all triggers;

rollback;

SELECT * FROM V$SESSION WHERE STATUS = 'ACTIVE';
SELECT * FROM V$OPEN_CURSOR WHERE SID = --the id

insert into T_PLEBS
    select O_PLEBS(rownum, ref(kot))
    from T_KOT1 kot
    where kot.funkcja = 'KOT' or kot.funkcja = 'LOWCZY';

insert into T_ELITA
    select O_ELITA(rownum, ref(kot), null)
    from T_KOT1 kot
    where kot.funkcja in ('SZEFUNIO', 'MILUSIA', 'DZIELCZY');

delete from T_KOT1 where 1=1;
delete from T_PLEBS where 1=1;
delete from T_ELITA where 1=1;

select * from T_KOT1;
select id_pleb, deref(kot) from t_plebs;
select id_elity, deref(kot) from t_elita;
select * from t_konto;

declare
    el ref O_ELITA;
    sl ref O_PLEBS;
begin
    select ref(P) into sl from T_PLEBS P where deref(P.KOT).pseudo = 'ZERO';
    update T_ELITA set SLUGA = sl where deref(KOT).pseudo in ('TYGRYS', 'BOLEK');
end;

select deref(deref(T.SLUGA).KOT).PSEUDO PSEUDO_SLUGI, COUNT(*) ILE_PANOW from T_ELITA T
group by T.SLUGA;
