create table PLEBS (
    id_pleb NUMBER
        CONSTRAINT plb_idp_pk1 PRIMARY KEY,
    pseudo VARCHAR2(15)
        CONSTRAINT plb_psd_fk1 REFERENCES KOCURY(PSEUDO)
);

create table ELITY (
    id_elity NUMBER
        CONSTRAINT elt_ide_pk1 PRIMARY KEY,
    pseudo VARCHAR2(15)
        CONSTRAINT elt_psd_fk1 REFERENCES KOCURY(PSEUDO),
    sluga NUMBER
        CONSTRAINT elt_slg_fk1 REFERENCES PLEBS(id_pleb)
);

create table KONTA (
    id_konta NUMBER
        CONSTRAINT knt_idk_pk1 PRIMARY KEY,
    wlasciciel NUMBER
        CONSTRAINT knt_wla_fk1 REFERENCES ELITY(id_elity),
    data_wprowadzenia DATE,
    data_usuniecia DATE
);

create or replace force view VW_KOCURY of O_KOT
with object identifier (pseudo) as
select IMIE, PLEC, PSEUDO, FUNKCJA, MAKE_REF(VW_KOCURY, SZEF), W_STADKU_OD, PRZYDZIAL_MYSZY, MYSZY_EXTRA
from KOCURY K;

create or replace view VW_PLEBS of O_PLEBS
with object identifier (id_pleb) as
select id_pleb, MAKE_REF(VW_KOCURY, pseudo)
from PLEBS;

create or replace view VW_ELITY of O_ELITA
with object identifier (id_elity) as
select id_elity, MAKE_REF(VW_KOCURY, pseudo), MAKE_REF(VW_PLEBS, sluga)
from ELITY;

create or replace view VW_KONTA of O_KONTO
with object identifier (id_konta) as
select id_konta, data_wprowadzenia, data_usuniecia, MAKE_REF(VW_ELITY, wlasciciel)
from KONTA;


insert into PLEBS
    select ROWNUM, K.PSEUDO
    from KOCURY K
    where K.funkcja = 'KOT' or K.funkcja = 'LOWCZY';
    -- SELECT * FROM DUAL;

insert into ELITY
    select rownum, K.PSEUDO, null
    from KOCURY K
    where K.funkcja in ('SZEFUNIO', 'MILUSIA', 'DZIELCZY');

select * from VW_ELITY;




-- zadanie 23

select
       kot.imie,
       rocznie.WARTOSC as "DAWKA ROCZNA",
       case when rocznie.WARTOSC < 864 then 'PONIZEJ 864'
            when rocznie.WARTOSC > 864 then 'POWYZEJ 864'
            else '864' end DAWKA
from VW_KOCURY kot
    join (select k.PSEUDO, value(k).dochod() * 12 "WARTOSC" from VW_KOCURY k) rocznie
        on rocznie.PSEUDO = kot.PSEUDO
where kot.MYSZY_EXTRA is not null
order by rocznie.WARTOSC desc;

-- zadanie 28
with DOLACZENIA as
    (select value(k).rok_dolaczenia() "ROK", count(PSEUDO) "LICZBA"
    from VW_KOCURY K
    group by value(k).rok_dolaczenia()),
SREDNIA as
    (select avg(LICZBA) "VAL" from DOLACZENIA)
select ROK, LICZBA
from (
        select D.ROK, D.LICZBA, RANK() OVER (ORDER BY D.LICZBA - S.VAL) "POZ"
        from DOLACZENIA D, SREDNIA S
        where D.LICZBA - S.VAL > 0
    union
        select D.ROK, D.LICZBA, RANK() OVER (ORDER BY D.LICZBA - S.VAL DESC) "POZ"
        from DOLACZENIA D, SREDNIA S
        where D.LICZBA - S.VAL < 0
    union
        select 'SREDNIA' "ROK", S.VAL, 1 "POZ" from SREDNIA S
    )
where POZ = 1
order by LICZBA;





-- ZADANIE 37
DECLARE
    CURSOR KURSOR IS
    SELECT ROWNUM "NR",
        PSEUDONIM "PSEUDONIM",
        ZJADA "ZJADA"
    FROM (
        SELECT K.PSEUDO "PSEUDONIM",
            value(K).dochod() "ZJADA"
        FROM VW_KOCURY K
        ORDER BY value(k).dochod() DESC)
    WHERE ROWNUM < 6;

BEGIN
    DBMS_OUTPUT.PUT_LINE('NR  PSEUDONIM  ZJADA');
    DBMS_OUTPUT.PUT_LINE('--------------------');
    FOR KOT IN KURSOR
    LOOP
        DBMS_OUTPUT.PUT_LINE(KOT.NR || '   ' || RPAD(KOT.PSEUDONIM, 10) || ' ' || KOT.ZJADA);
    END LOOP;
END;
/



-- ZADANIE 38
DECLARE
    CURSOR KURSOR IS
        SELECT ref(k) r
        FROM VW_KOCURY K
        WHERE K.FUNKCJA IN ('KOT', 'MILUSIA');
    AKTUALNY_KOT      O_KOT;
    IMIE              VARCHAR2(15);
    COL_WIDTH         NUMBER DEFAULT 15;
    ILE_SZEFOW        NUMBER DEFAULT &ILE_SZEFOW;
BEGIN
    DBMS_OUTPUT.PUT(RPAD('IMIE ', COL_WIDTH));
    FOR COUNTER IN 1..ILE_SZEFOW
    LOOP
        DBMS_OUTPUT.PUT(RPAD('|  SZEF ' || COUNTER, COL_WIDTH));
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE(RPAD('-', COL_WIDTH*(ILE_SZEFOW+1), '-'));
    FOR KOT IN KURSOR
    LOOP
        SELECT deref(kot.r) into AKTUALNY_KOT from dual;
        DBMS_OUTPUT.PUT(RPAD(AKTUALNY_KOT.IMIE, COL_WIDTH));
        FOR COUNTER IN 1..ILE_SZEFOW
        LOOP
            IF AKTUALNY_KOT.SZEF IS NULL THEN
                DBMS_OUTPUT.PUT(RPAD('|  ', COL_WIDTH));

            ELSE
                select deref(AKTUALNY_KOT.SZEF) into AKTUALNY_KOT from dual;
                DBMS_OUTPUT.PUT(RPAD('|  ' || AKTUALNY_KOT.IMIE, COL_WIDTH));
            END IF;
        END LOOP;
        DBMS_OUTPUT.PUT_LINE('');
    END LOOP;
END;
/
