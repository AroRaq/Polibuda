ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD';

-- zadanie 17
select koc.PSEUDO "POLUJE W POLU",
       koc.PRZYDZIAL_MYSZY,
       ban.NAZWA "BANDA"
from KOCURY koc
    inner join BANDY ban on koc.NR_BANDY = ban.NR_BANDY
where koc.PRZYDZIAL_MYSZY > 50
    and (ban.TEREN = 'POLE' or ban.TEREN = 'CALOSC')
order by koc.PRZYDZIAL_MYSZY desc;

-- zadanie 18
select koc.IMIE, koc.W_STADKU_OD
from KOCURY koc
    inner join KOCURY jacek on koc.W_STADKU_OD < jacek.W_STADKU_OD and jacek.IMIE = 'JACEK';

-- zadanie 19a
select koc.IMIE,
       koc.FUNKCJA,
       szef1.IMIE "SZEF1",
       szef2.IMIE "SZEF2",
       szef3.IMIE "SZEF3"
from KOCURY koc
    left join KOCURY szef1 on koc.SZEF = szef1.PSEUDO
    left join KOCURY szef2 on szef1.SZEF = szef2.PSEUDO
    left join KOCURY szef3 on szef2.SZEF = szef3.PSEUDO
where koc.FUNKCJA = 'KOT' or koc.FUNKCJA = 'MILUSIA';

-- zadanie 19b

-- zadanie 19c

-- zadanie 20
select koc.IMIE "IMIE KOTKI",
       ban.NAZWA "NAZWA BANDY",
       wk.IMIE_WROGA,
       wrog.STOPIEN_WROGOSCI "OCENA WROGA",
       wk.DATA_INCYDENTU
from KOCURY koc
    inner join WROGOWIE_KOCUROW wk on koc.PSEUDO = wk.PSEUDO
    left join BANDY ban on koc.NR_BANDY = ban.NR_BANDY
    inner join WROGOWIE wrog on wk.IMIE_WROGA = wrog.IMIE_WROGA
where koc.PLEC = 'D'
    and wk.DATA_INCYDENTU > date '2007-01-01';

-- zadanie 21
select B.NAZWA,
       COUNT(DISTINCT k.PSEUDO) "KOTY Z WROGAMI"
from KOCURY K
    inner join WROGOWIE_KOCUROW WK on K.PSEUDO = WK.PSEUDO
    right join BANDY B on K.NR_BANDY = B.NR_BANDY
group by B.NR_BANDY, B.NAZWA;

-- zadanie 22
select k.FUNKCJA,
       K.PSEUDO,
       COUNT(wk.IMIE_WROGA) "Liczba wrogow"
from KOCURY K
    inner join WROGOWIE_KOCUROW WK on K.PSEUDO = WK.PSEUDO
group by K.PSEUDO, K.FUNKCJA
having COUNT(wk.IMIE_WROGA) > 1;

-- zadanie 23 TODO
(
    select K.IMIE, (K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA)*12 "DAWKA ROCZNA", 'Powyzej 864' "DAWKA"
    from KOCURY K
    where K.MYSZY_EXTRA is not null and (K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA)*12 > 864
--     order by K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA desc
)
union
(
    select K.IMIE, (K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA)*12 "DAWKA ROCZNA", '864' "DAWKA"
    from KOCURY K
    where K.MYSZY_EXTRA is not null and (K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA)*12 = 864
--     order by (K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA)*12 desc
)
union
(
    select K.IMIE, (K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA)*12 "DAWKA ROCZNA", 'Ponizej 864' "DAWKA"
    from KOCURY K
    where K.MYSZY_EXTRA is not null and (K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA)*12 < 864
--     order by (K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA)*12 desc
);

-- zadanie 24a
select B.NR_BANDY, B.NAZWA, B.TEREN
from BANDY B
    left join KOCURY K on B.NR_BANDY = K.NR_BANDY
group by B.NR_BANDY, B.NAZWA, B.TEREN
having COUNT(K.PSEUDO) = 0;

-- zadanie 24b
select B.NR_BANDY, B.NAZWA, B.TEREN
from BANDY B
    left join KOCURY K on B.NR_BANDY = K.NR_BANDY
where K.PSEUDO is null;

-- zadanie 25 TODO
select K.IMIE,
       K.FUNKCJA,
       K.PRZYDZIAL_MYSZY
from KOCURY K
where K.PRZYDZIAL_MYSZY >= (select NAJWIEKSZY_PRZYDZIAL "NAJWIEKSZY_PRZYDZIAL"
                            from
                                (select 3*K.PRZYDZIAL_MYSZY "NAJWIEKSZY_PRZYDZIAL"
                                from KOCURY K
                                    inner join BANDY B on K.NR_BANDY = B.NR_BANDY and B.TEREN = 'SAD'
                                where K.FUNKCJA = 'MILUSIA'
                                order by K.PRZYDZIAL_MYSZY DESC)
                            where ROWNUM = 1);

-- zadanie 26
with average as
    (select K.FUNKCJA,
           ROUND(AVG(K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0))) "SREDNI_PRZYDZIAL"
     from KOCURY K
     where K.FUNKCJA != 'SZEFUNIO'
     group by K.FUNKCJA)
select *
from average av
where av.SREDNI_PRZYDZIAL >= ALL(select SREDNI_PRZYDZIAL from average)
    or av.SREDNI_PRZYDZIAL <= ALL(select SREDNI_PRZYDZIAL from average);

-- zadanie 27a

-- zadanie 27b
select K.PSEUDO,
       K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) "PRZYDZIAL"
from KOCURY K
where K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) IN
    (select * from
        (select distinct K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) "PRZYDZIAL"
        from KOCURY K
        order by K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) desc)
    where ROWNUM <= :N);

-- zadanie 27c

-- zadanie 27d

-- zadanie 28
with DOLACZENIA as
    (select to_char(extract(year from K.W_STADKU_OD)) "ROK", count(PSEUDO) "LICZBA"
    from KOCURY K
    group by extract(year from K.W_STADKU_OD)),
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

-- zadanie 29a


-- zadanie 29b
with KOCURY2 as (
    select K.IMIE, K.NR_BANDY, K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) "ZJADA", K.PLEC
    from KOCURY K
)
select K.IMIE,
       K.ZJADA,
       K.NR_BANDY,
       X.AV "SREDNIA_BANDY"
from KOCURY2 K
    inner join (select NR_BANDY, avg(ZJADA) "AV" from KOCURY2 group by NR_BANDY) X
        on K.NR_BANDY = X.NR_BANDY and K.ZJADA <= X.AV
where K.PLEC = 'M';

-- zadanie 29c
with KOCURY2 as (
    select K.IMIE, K.NR_BANDY, K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) "ZJADA", K.PLEC
    from KOCURY K
)
select K.IMIE,
       K.ZJADA,
       K.NR_BANDY,
       (select avg(ZJADA) from KOCURY2 where NR_BANDY = K.NR_BANDY) "SREDNIA_BANDY"
from KOCURY2 K
where K.PLEC = 'M' and K.ZJADA <= (select avg(ZJADA) from KOCURY2 where NR_BANDY = K.NR_BANDY);
