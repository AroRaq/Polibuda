ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD';

-- zadanie 1
select wk.IMIE_WROGA WROG, wk.OPIS_INCYDENTU PRZEWINA
from WROGOWIE_KOCUROW wk
where EXTRACT(YEAR FROM wk.DATA_INCYDENTU) = 2009;

-- zadanie 2
select koc.IMIE, koc.FUNKCJA, koc.W_STADKU_OD "Z NAMI OD"
from KOCURY koc
where koc.PLEC = 'D'
    and koc.W_STADKU_OD between '2005-09-01' and '2007-07-31';

-- zadanie 3
select wr.IMIE_WROGA WROG, wr.GATUNEK, wr.STOPIEN_WROGOSCI "STOPIEN WROGOSCI"
from WROGOWIE wr
where wr.LAPOWKA is null
order by wr.STOPIEN_WROGOSCI;

-- zadanie 4
select koc.IMIE || ' zwany ' || koc.PSEUDO || '(fun. ' || koc.FUNKCJA || ') lowi myszki w bandzie' || koc.NR_BANDY || ' od ' || koc.W_STADKU_OD
from KOCURY koc
where koc.PLEC = 'M'
order by koc.W_STADKU_OD desc, koc.PSEUDO;

-- zadanie 5
select koc.PSEUDO, regexp_replace(regexp_replace(koc.PSEUDO, 'A', '#', 1, 1), 'L', '%', 1, 1) "PO WYMIANIE A NA # ORAZ l NA %"
from KOCURY koc
where koc.PSEUDO LIKE '%A%' and koc.PSEUDO LIKE '%L%';

-- zadanie 6
select koc.IMIE,
       koc.W_STADKU_OD "W STADKU",
       ROUND(CASE WHEN SYSDATE > ADD_MONTHS(koc.W_STADKU_OD, 6) THEN 10/11*koc.PRZYDZIAL_MYSZY
            ELSE koc.PRZYDZIAL_MYSZY END) "ZJADAL",
        ADD_MONTHS(koc.W_STADKU_OD, 6) "PODWYZKA",
        koc.PRZYDZIAL_MYSZY "ZJADA"
from KOCURY koc
where extract(month from koc.W_STADKU_OD) between 3 and 9;

-- zadanie 7
select koc.IMIE,
       koc.PRZYDZIAL_MYSZY*3 "MYSZY KWARTALNIE",
       NVL(koc.MYSZY_EXTRA*3, 0) "KWARTALNE DODATKI"
from KOCURY koc
where koc.PRZYDZIAL_MYSZY > 2*NVL(koc.MYSZY_EXTRA, 0);

-- zadanie 8
select koc.IMIE,
       CASE WHEN (koc.PRZYDZIAL_MYSZY + NVL(koc.MYSZY_EXTRA, 0))*12 = 660 THEN 'Limit'
            WHEN (koc.PRZYDZIAL_MYSZY + NVL(koc.MYSZY_EXTRA, 0))*12 < 660 THEN 'Ponizej 660'
            ELSE TO_CHAR((koc.PRZYDZIAL_MYSZY + NVL(koc.MYSZY_EXTRA, 0))*12)
        END "ZJADA ROCZNIE"
from KOCURY koc;

-- zadanie 9
select koc.PSEUDO,
       koc.W_STADKU_OD,
       case when next_day(last_day(:CURRDATE) - 7, 'Wednesday') >= :CURRDATE and extract(day from koc.W_STADKU_OD) <= 15
            then next_day(last_day(:CURRDATE) - 7, 'Wednesday')
            else next_day(last_day(add_months(:CURRDATE, 1)) - 7, 'Wednesday')
       end "WYPLATA"
from KOCURY koc
order by koc.W_STADKU_OD;

-- zadanie 10
select koc.PSEUDO, case
    when count(koc.PSEUDO) = 1 then 'Unikalny'
    else 'nieunikalny' end "UNIKALNOSC"
from KOCURY koc
where koc.PSEUDO is not null
group by koc.PSEUDO;

select koc.SZEF, case
    when count(koc.SZEF) = 1 then 'Unikalny'
    else 'nieunikalny' end "UNIKALNOSC"
from KOCURY koc
where koc.SZEF is not null
group by koc.SZEF;


-- zadanie 11
select wk.PSEUDO, count(wk.PSEUDO) "LICZBA WROGOW"
from WROGOWIE_KOCUROW wk
group by wk.PSEUDO
having count(wk.PSEUDO) > 1;

-- zadanie 12
select 'Liczba kotow = ' || count(koc.PSEUDO) || ' lowi jako ' || koc.FUNKCJA ||
       ' i zjada max. ' || max(koc.PRZYDZIAL_MYSZY + nvl(koc.MYSZY_EXTRA, 0)) || ' myszy miesiecznie.' "MAX PRZYDZIAL MYSZY"
from KOCURY koc
where koc.PLEC = 'D' and koc.FUNKCJA <> 'SZEFUNIO'
group by koc.FUNKCJA
having max(koc.PRZYDZIAL_MYSZY + nvl(koc.MYSZY_EXTRA, 0)) > 50;

-- zadanie 13
select koc.NR_BANDY,
       koc.PLEC,
       min(koc.PRZYDZIAL_MYSZY) "Minimalny przydzial"
from KOCURY koc
group by koc.NR_BANDY, koc.PLEC;

-- zadanie 14
select level "POZIOM", koc.PSEUDO, koc.FUNKCJA, koc.NR_BANDY
from KOCURY koc
where koc.PLEC = 'M'
connect by prior koc.PSEUDO = koc.SZEF
start with koc.FUNKCJA = 'BANDZIOR';

-- zadanie 15
select rpad('===>', (level-1)*4, '===>') || to_char(level-1) || '         ' || koc.IMIE "HIERARCHIA",
       nvl(koc.SZEF, 'Sam sobie panem') "PSEUDO SZEFA",
       koc.FUNKCJA
from KOCURY koc
where koc.MYSZY_EXTRA is not null
connect by prior koc.PSEUDO = koc.SZEF
start with koc.SZEF is null;

-- zadanie 16
select lpad('    ', (level-1)*4, '    ') || koc.PSEUDO "DROGA SLUZBOWA"
from KOCURY koc
connect by prior koc.SZEF = koc.PSEUDO
start with koc.PLEC = 'M' and sysdate - koc.W_STADKU_OD > 365 * 10 and koc.MYSZY_EXTRA is null;