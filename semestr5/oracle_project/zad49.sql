ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD';

--UTWORZ TABELE MYSZY
begin
    execute immediate 'create table MYSZY
    (
        NR_MYSZY NUMBER
            constraint msz_nrm_pk primary key,
        LOWCA VARCHAR2(15)
            constraint msz_low_fk references KOCURY(PSEUDO),
        ZJADACZ VARCHAR2(15)
            constraint msz_zjd_fk references KOCURY(PSEUDO),
        WAGA_MYSZY NUMBER(2),
        DATA_ZLOWIENIA DATE,
        DATA_WYDANIA DATE
    )';
end;

drop table myszy;

create or replace function OSTATNIA_SRODA(data DATE) return DATE is
begin
    return next_day(last_day(data) - 7, 'Wednesday');
end;

create or replace function LOSOWA_DATA_W_MIESIACU(data DATE) return DATE is
    START_DATE DATE := trunc(data) - (to_number(to_char(data,'DD')) - 1);
    END_DATE DATE := add_months(trunc(data) - (to_number(to_char(data,'DD')) - 1), 1) - 1;
begin
    return start_date + trunc(dbms_random.value(0,(end_date-start_date+1)));
end;

-- WYPELNIJ DANE OD 2004
declare
    CURR_DATE DATE DEFAULT '2004-01-01';
    NAST_WYPLATA DATE;
    ILE_MYSZY NUMBER;
    ILE_KOTOW NUMBER;
    MOC_KOTA NUMBER;
    type td is table of MYSZY%ROWTYPE INDEX BY SIMPLE_INTEGER;
--     type tz is table of NUMBER;
    DO_WPISANIA td;
--     DO_ZJEDZENIA tz;
    ID_MYSZY NUMBER DEFAULT 1;
begin
    DBMS_RANDOM.SEED(100);
    while CURR_DATE < SYSDATE - 1
    loop
        NAST_WYPLATA := OSTATNIA_SRODA(CURR_DATE);

        select sum(K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0)), count(pseudo)
        into ILE_MYSZY, ILE_KOTOW
        from KOCURY K
        where K.W_STADKU_OD < NAST_WYPLATA;

        MOC_KOTA := CEIL(ILE_MYSZY/ILE_KOTOW);

        for kot in (select * from kocury where W_STADKU_OD < NAST_WYPLATA)
        loop
            for nr in 1..MOC_KOTA
            loop
                DO_WPISANIA(ID_MYSZY).DATA_WYDANIA := NAST_WYPLATA;
                DO_WPISANIA(ID_MYSZY).LOWCA := kot.PSEUDO;
                DO_WPISANIA(ID_MYSZY).ZJADACZ := kot.PSEUDO;
                DO_WPISANIA(ID_MYSZY).NR_MYSZY := ID_MYSZY;
                DO_WPISANIA(ID_MYSZY).DATA_ZLOWIENIA := LOSOWA_DATA_W_MIESIACU(CURR_DATE);
                DO_WPISANIA(ID_MYSZY).WAGA_MYSZY := DBMS_RANDOM.VALUE(15, 45);
                ID_MYSZY := ID_MYSZY + 1;
            end loop;
        end loop;

--         select ID_MYSZY bulk collect into DO_ZJEDZENIA from table(DO_WPISANIA) where ZJADACZ is null order by DATA_ZLOWIENIA;
--
--         for kot in (select * from kocury where W_STADKU_OD < NAST_WYPLATA)
--         loop
--             update DO_WPISANIA set ZJADACZ = kot.PSEUDO where ID_MYSZY in (select * from DO_ZJEDZENIA);
--         end loop;

        CURR_DATE := ADD_MONTHS(CURR_DATE, 1);
    end loop;
    forall nr in 1..ID_MYSZY-1
    insert into MYSZY values DO_WPISANIA(nr);
    DO_WPISANIA.DELETE();

    DBMS_OUTPUT.PUT_LINE('KONIEC');
end;

select count(*) from MYSZY order by NR_MYSZY;
select * from MYSZY order by DATA_ZLOWIENIA desc;

delete from MYSZY where 1=1;

--UTWORZ KONTA
declare
    table_name varchar2(21);
begin
    for kot in (select pseudo from KOCURY)
    loop
        table_name := 'KONTO_' || kot.PSEUDO;
        execute immediate 'create table ' || table_name || ' ( ' ||
                            'DATA_ZLOWIENIA DATE, ' ||
                            'WAGA_MYSZY NUMBER)';
    end loop;
end;

--USUN KONTA
begin
    for tab in (select table_name from user_tables where TABLE_NAME like 'KONTO_%')
    loop
        execute immediate 'drop table ' || tab.TABLE_NAME;
    end loop;
end;

create or replace procedure przyjmij_myszy(pseudo KOCURY.PSEUDO%TYPE, data DATE) is
    type konto_kota_typ is RECORD(DATA_ZLOWIENIA DATE, WAGA_MYSZY NUMBER);
    type konto_kota_tab is TABLE OF konto_kota_typ index by BINARY_INTEGER;
    konto_kota konto_kota_tab;

    type td is TABLE OF MYSZY%ROWTYPE index by SIMPLE_INTEGER;
    do_wpisania td;

    id_myszy NUMBER;
    sql1 VARCHAR2(1000);
begin
    sql1 := 'select * from KONTO_' || pseudo ||
                      ' where to_char(DATA_ZLOWIENIA, ''MM-DD'') = to_char(date ''' || data || ''', ''MM-DD'')';
    DBMS_OUTPUT.PUT_LINE(sql1);
    execute immediate sql1 bulk collect into konto_kota;

    select nvl(max(NR_MYSZY), 0)+1 into id_myszy from MYSZY;
    for i in  1..konto_kota.COUNT
    loop
        do_wpisania(i).NR_MYSZY := id_myszy;
        do_wpisania(i).WAGA_MYSZY := konto_kota(i).WAGA_MYSZY;
        do_wpisania(i).DATA_ZLOWIENIA := konto_kota(i).DATA_ZLOWIENIA;
        do_wpisania(i).LOWCA := pseudo;
        do_wpisania(i).DATA_WYDANIA := null;
        id_myszy := id_myszy + 1;
    end loop;

    forall i in 1..konto_kota.COUNT
    insert into MYSZY values do_wpisania(i);

    execute immediate 'delete from KONTO_' || pseudo ||
                      ' where to_char(DATA_ZLOWIENIA, ''MM-DD'') = to_char(date ''' || data || ''', ''MM-DD'')';
end;

delete from MYSZY where 1=1;

begin
    for i in 1..100
    loop
        insert into KONTO_TYGRYS VALUES(SYSDATE, 20);
    end loop;
end;

select * from KONTO_TYGRYS;

begin
    PRZYJMIJ_MYSZY('TYGRYS', SYSDATE);
end;

select * from MYSZY;

create or replace procedure wyplata is
    data_wyplaty DATE := OSTATNIA_SRODA(SYSDATE);
    type td is TABLE OF MYSZY%ROWTYPE index by binary_integer;
    wyplacane_myszy td;
    nr_myszy NUMBER := 1;
begin
    select * bulk collect into wyplacane_myszy from MYSZY where DATA_WYDANIA is null;
    for kot in (
        select * from KOCURY
        start with szef is null
                   connect by prior pseudo = szef
        order by level)
    loop
        for i in 1..kot.PRZYDZIAL_MYSZY + nvl(kot.MYSZY_EXTRA, 0)
        loop
            exit when nr_myszy > wyplacane_myszy.COUNT;
            wyplacane_myszy(nr_myszy).LOWCA := kot.PSEUDO;
            wyplacane_myszy(nr_myszy).DATA_WYDANIA := data_wyplaty;
            nr_myszy := nr_myszy + 1;
        end loop;
    end loop;
    forall i in 1..nr_myszy-1
    update MYSZY SET LOWCA = wyplacane_myszy(i).LOWCA, DATA_WYDANIA = wyplacane_myszy(i).DATA_WYDANIA
    where NR_MYSZY = wyplacane_myszy(i).NR_MYSZY;
end;

delete from myszy where 1=1;

begin
    for i in 1..10000
    loop
        insert into MYSZY VALUES(i, null, null, 20, '2020-01-01', null);
    end loop;
    wyplata();
end;

select * from myszy order by LOWCA;