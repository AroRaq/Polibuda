ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD';

-- zadanie 17
select K.PSEUDO "POLUJE W POLU",
       K.PRZYDZIAL_MYSZY,
       B.NAZWA  "BANDA"
from KOCURY K
    inner join BANDY B on K.NR_BANDY = B.NR_BANDY
where K.PRZYDZIAL_MYSZY > 50
    and B.TEREN in ('POLE', 'CALOSC')
order by K.PRZYDZIAL_MYSZY desc;

-- zadanie 18
select K.IMIE, K.W_STADKU_OD
from KOCURY K
    inner join KOCURY JACEK on K.W_STADKU_OD < JACEK.W_STADKU_OD and JACEK.IMIE = 'JACEK'
order by K.W_STADKU_OD desc;

-- zadanie 19a
select K.IMIE,
       K.FUNKCJA,
       SZEF1.IMIE "SZEF1",
       SZEF2.IMIE "SZEF2",
       SZEF3.IMIE "SZEF3"
from KOCURY K
    left join KOCURY SZEF1 on K.SZEF = SZEF1.PSEUDO
    left join KOCURY SZEF2 on SZEF1.SZEF = SZEF2.PSEUDO
    left join KOCURY SZEF3 on SZEF2.SZEF = SZEF3.PSEUDO
where K.FUNKCJA in ('KOT', 'MILUSIA');

-- zadanie 19b
select *
from (
    select connect_by_root IMIE "IMIE",
           connect_by_root FUNKCJA "FUNKCJA",
           level LVL,
           IMIE "IMIE_SZEFA"
    from KOCURY
    connect by prior SZEF = PSEUDO
    start with FUNKCJA in ('KOT', 'MILUSIA')
    )
pivot
(max("IMIE_SZEFA") for LVL in (2 SZEF1, 3 SZEF2, 4 SZEF3));

-- zadanie 19c
select IMIE, FUNKCJA, SUBSTR(SZEFOWIE, 15) "IMIONA KOLEJNYCH SZEFOW"
from (select CONNECT_BY_ROOT K.IMIE "IMIE",
             CONNECT_BY_ROOT K.FUNKCJA "FUNKCJA",
             SYS_CONNECT_BY_PATH(RPAD(K.IMIE, 10), '| ') "SZEFOWIE",
             CONNECT_BY_ISLEAF "IS_LEAF"
      from KOCURY K
      start with K.FUNKCJA in ('KOT', 'MILUSIA')
      connect by prior K.SZEF = K.PSEUDO)
where IS_LEAF = 1;

-- zadanie 20
select K.IMIE "IMIE KOTKI",
       B.NAZWA "NAZWA BANDY",
       WK.IMIE_WROGA,
       W.STOPIEN_WROGOSCI "OCENA WROGA",
       WK.DATA_INCYDENTU
from KOCURY K
    inner join WROGOWIE_KOCUROW WK on K.PSEUDO = WK.PSEUDO
    left join BANDY B on K.NR_BANDY = B.NR_BANDY
    inner join WROGOWIE W on WK.IMIE_WROGA = W.IMIE_WROGA
where K.PLEC = 'D'
    and WK.DATA_INCYDENTU > date '2007-01-01';

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
       count(WK.IMIE_WROGA) "Liczba wrogow"
from KOCURY K
    inner join WROGOWIE_KOCUROW WK on K.PSEUDO = WK.PSEUDO
group by K.PSEUDO, K.FUNKCJA
having count(WK.IMIE_WROGA) > 1;

-- zadanie 23
select * from (
        select K.IMIE, (K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA) * 12 "DAWKA_ROCZNA", 'Powyzej 864' "DAWKA"
        from KOCURY K
        where K.MYSZY_EXTRA is not null
            and (K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA) * 12 > 864
    union
        select K.IMIE, (K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA) * 12 "DAWKA_ROCZNA", '864' "DAWKA"
        from KOCURY K
        where K.MYSZY_EXTRA is not null
            and (K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA) * 12 = 864
    union
        select K.IMIE, (K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA) * 12 "DAWKA_ROCZNA", 'Ponizej 864' "DAWKA"
        from KOCURY K
        where K.MYSZY_EXTRA is not null
            and (K.PRZYDZIAL_MYSZY + K.MYSZY_EXTRA) * 12 < 864
    )
order by DAWKA_ROCZNA desc;

-- zadanie 24a
select B.NR_BANDY, B.NAZWA, B.TEREN
from BANDY B
    left join KOCURY K on B.NR_BANDY = K.NR_BANDY
where K.PSEUDO is null;

-- zadanie 24b
select B.NR_BANDY, B.NAZWA, B.TEREN
from BANDY B
    left join KOCURY K on B.NR_BANDY = K.NR_BANDY
group by B.NR_BANDY, B.NAZWA, B.TEREN
having count(K.PSEUDO) = 0;

-- zadanie 25
select K.IMIE,
       K.FUNKCJA,
       K.PRZYDZIAL_MYSZY
from KOCURY K
where K.PRZYDZIAL_MYSZY >= (select NAJWIEKSZY_PRZYDZIAL
                            from
                                (select 3*K.PRZYDZIAL_MYSZY "NAJWIEKSZY_PRZYDZIAL"
                                from KOCURY K
                                    inner join BANDY B on K.NR_BANDY = B.NR_BANDY and B.TEREN in ('SAD', 'CALOSC')
                                where K.FUNKCJA = 'MILUSIA'
                                order by K.PRZYDZIAL_MYSZY DESC)
                            where ROWNUM = 1);

-- zadanie 26
with AVERAGE as
    (select K.FUNKCJA,
            ROUND(AVG(K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0))) "SREDNI_PRZYDZIAL"
     from KOCURY K
     where K.FUNKCJA != 'SZEFUNIO'
     group by K.FUNKCJA)
select *
from AVERAGE A
where A.SREDNI_PRZYDZIAL >= ALL(select SREDNI_PRZYDZIAL from AVERAGE)or
      A.SREDNI_PRZYDZIAL <= ALL(select SREDNI_PRZYDZIAL from AVERAGE);

-- zadanie 27a
select K.PSEUDO,
       K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) "PRZYDZIAL"
from KOCURY K
where :N > (select count( distinct K2.PRZYDZIAL_MYSZY + NVL(K2.MYSZY_EXTRA, 0)) "ILE"
            from KOCURY K2
            where K2.PRZYDZIAL_MYSZY + NVL(K2.MYSZY_EXTRA, 0) > K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0))
order by K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) desc;

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
select K.PSEUDO,
       K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) "PRZYDZIAL"
from KOCURY K
    left join KOCURY K2 on K2.PRZYDZIAL_MYSZY + NVL(K2.MYSZY_EXTRA, 0) > K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0)
group by K.PSEUDO, K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0)
having count(distinct K2.PRZYDZIAL_MYSZY + NVL(K2.MYSZY_EXTRA, 0)) < :N
order by K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) desc;

-- zadanie 27d
select PSEUDO,
       PRZYDZIAL
from (select PSEUDO,
             K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) "PRZYDZIAL",
             dense_rank() over (order by PRZYDZIAL_MYSZY + NVL(MYSZY_EXTRA, 0) desc) "POZ"
      from KOCURY K)
where POZ <= :N;

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
select max(K.IMIE) "IMIE",
       K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) "ZJADA",
       K.NR_BANDY,
       AVG(K2.PRZYDZIAL_MYSZY + NVL(K2.MYSZY_EXTRA, 0)) "SREDNIA_BANDY"
from KOCURY K
    inner join KOCURY K2 on K.NR_BANDY = K2.NR_BANDY
where K.PLEC = 'M'
group by K.PSEUDO, K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0), K.NR_BANDY
having K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) <= AVG(K2.PRZYDZIAL_MYSZY + NVL(K2.MYSZY_EXTRA, 0));

-- zadanie 29b
select K.IMIE,
       K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) "ZJADA",
       K.NR_BANDY,
       X.AV "SREDNIA_BANDY"
from KOCURY K
    inner join (select NR_BANDY,
                       avg(PRZYDZIAL_MYSZY + NVL(MYSZY_EXTRA, 0)) "AV"
                from KOCURY group by NR_BANDY
        ) X
        on K.NR_BANDY = X.NR_BANDY and K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) <= X.AV
where K.PLEC = 'M';

-- zadanie 29c
select K.IMIE,
       K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) "ZJADA",
       K.NR_BANDY,
       (select avg(PRZYDZIAL_MYSZY + NVL(MYSZY_EXTRA, 0)) from KOCURY where NR_BANDY = K.NR_BANDY) "SREDNIA_BANDY"
from KOCURY K
where K.PLEC = 'M' and K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) <=
                           (select avg(PRZYDZIAL_MYSZY + NVL(MYSZY_EXTRA, 0))
                            from KOCURY
                            where NR_BANDY = K.NR_BANDY);

-- zadanie 30
select K.IMIE,
       K.W_STADKU_OD,
       '<=== NAJMLODSZY STAZEM W BANDZIE ' || B.NAZWA " "
from KOCURY K
    inner join BANDY B on K.NR_BANDY = B.NR_BANDY
where K.W_STADKU_OD <= all(select W_STADKU_OD from KOCURY where NR_BANDY = K.NR_BANDY)
union
select K.IMIE,
       K.W_STADKU_OD,
       '<=== NAJSTARSZY STAZEM W BANDZIE ' || B.NAZWA " "
from KOCURY K
    inner join BANDY B on K.NR_BANDY = B.NR_BANDY
where K.W_STADKU_OD >= all(select W_STADKU_OD from KOCURY where NR_BANDY = K.NR_BANDY)
union
select K.IMIE,
       K.W_STADKU_OD,
       ' ' " "
from KOCURY K
    inner join BANDY B on K.NR_BANDY = B.NR_BANDY
where K.W_STADKU_OD > any(select W_STADKU_OD from KOCURY where NR_BANDY = K.NR_BANDY) and
      K.W_STADKU_OD < any(select W_STADKU_OD from KOCURY where NR_BANDY = K.NR_BANDY);

-- zadanie 31
select K.PSEUDO,
       K.IMIE,
       K.FUNKCJA,
       K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0) "ZJADA",
       'OD ' || V.MIN_SPOZ || ' DO ' || V.MAX_SPOZ "GRANICE_SPOZYCIA",
       K.W_STADKU_OD
from KOCURY K
    inner join BANDY B on K.NR_BANDY = B.NR_BANDY
    inner join VW_INFO_BANDY V on V.NAZWA_BANDY = B.NAZWA
where K.PSEUDO = :PSEUDO;

-- zadanie 32
select X.PSEUDO, X.PLEC, X.PRZYDZIAL_MYSZY, X.MYSZY_EXTRA
from (
    select K.PSEUDO, K.PLEC, K.PRZYDZIAL_MYSZY, NVL(K.MYSZY_EXTRA, 0) "MYSZY_EXTRA",
           rank() over (partition by K.NR_BANDY order by K.W_STADKU_OD) "rank"
    from KOCURY K
        inner join BANDY B on K.NR_BANDY = B.NR_BANDY
    where B.NAZWA in ('CZARNI RYCERZE', 'LACIACI MYSLIWI')
) X
where X."rank" < 4;

update KOCURY K
set K.PRZYDZIAL_MYSZY = K.PRZYDZIAL_MYSZY + decode(K.PLEC, 'M', 10,
                                                           'D', 0.1*(select min(PRZYDZIAL_MYSZY) from KOCURY)),
    K.MYSZY_EXTRA = NVL(K.MYSZY_EXTRA, 0) + 0.15*(select avg(NVL(MYSZY_EXTRA, 0)) from KOCURY K2 where K.NR_BANDY = K2.NR_BANDY)
where K.PSEUDO in (select PSEUDO
    from (
         select K.PSEUDO,
                dense_rank() over (partition by K.NR_BANDY order by K.W_STADKU_OD) "rank"
         from KOCURY K
            inner join BANDY B on K.NR_BANDY = B.NR_BANDY
         where B.NAZWA in ('CZARNI RYCERZE', 'LACIACI MYSLIWI')
     )
    where "rank" < 4);

rollback;

-- zadanie 33a
select decode(PLEC, 'Kotka', ' ', NAZWA) "NAZWA", PLEC, ILE,
       SZEFUNIO, BANDZIOR, LOWCZY, LAPACZ, KOT, MILUSIA, DZIELCZY, SUMA
from (
    select 1 "ORDER",
           B.NAZWA "NAZWA",
           decode(K.PLEC, 'D', 'Kotka', 'Kocur') "PLEC",
           to_char(count(pseudo)) "ILE",
           to_char(sum(decode(FUNKCJA, 'SZEFUNIO', PRZYDZIAL_MYSZY + NVL(MYSZY_EXTRA, 0), 0))) "SZEFUNIO",
           to_char(sum(decode(FUNKCJA, 'BANDZIOR', PRZYDZIAL_MYSZY + NVL(MYSZY_EXTRA, 0), 0))) "BANDZIOR",
           to_char(sum(decode(FUNKCJA, 'LOWCZY'  , PRZYDZIAL_MYSZY + NVL(MYSZY_EXTRA, 0), 0))) "LOWCZY",
           to_char(sum(decode(FUNKCJA, 'LAPACZ'  , PRZYDZIAL_MYSZY + NVL(MYSZY_EXTRA, 0), 0))) "LAPACZ",
           to_char(sum(decode(FUNKCJA, 'KOT'     , PRZYDZIAL_MYSZY + NVL(MYSZY_EXTRA, 0), 0))) "KOT",
           to_char(sum(decode(FUNKCJA, 'MILUSIA' , PRZYDZIAL_MYSZY + NVL(MYSZY_EXTRA, 0), 0))) "MILUSIA",
           to_char(sum(decode(FUNKCJA, 'DZIELCZY', PRZYDZIAL_MYSZY + NVL(MYSZY_EXTRA, 0), 0))) "DZIELCZY",
           to_char(sum(PRZYDZIAL_MYSZY + NVL(MYSZY_EXTRA, 0))) "SUMA"
    from KOCURY K
        inner join BANDY B on K.NR_BANDY = B.NR_BANDY
    group by B.NAZWA, K.PLEC
    union
    select 2 "ORDER", 'z ------', '-----', '---', '---------', '---------', '---------', '---------', '---------', '---------', '---------', '---------'
    from dual
    union
    select 3 "ORDER", 'ZJADA RAZEM' "NAZWA", ' ' "PLEC", ' ' "ILE",
           to_char(sum(decode(K.FUNKCJA, 'SZEFUNIO', K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0), 0))) "SZEFUNIO",
           to_char(sum(decode(K.FUNKCJA, 'BANDZIOR', K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0), 0))) "BANDZIOR",
           to_char(sum(decode(K.FUNKCJA, 'LOWCZY'  , K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0), 0))) "LOWCZY",
           to_char(sum(decode(K.FUNKCJA, 'LAPACZ'  , K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0), 0))) "LAPACZ",
           to_char(sum(decode(K.FUNKCJA, 'KOT'     , K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0), 0))) "KOT",
           to_char(sum(decode(K.FUNKCJA, 'MILUSIA' , K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0), 0))) "MILUSIA",
           to_char(sum(decode(K.FUNKCJA, 'DZIELCZY', K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0), 0))) "DZIELCZY",
           to_char(sum(K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA,0))) suma
    from KOCURY K
    )
order by "ORDER", ROWNUM;

-- zadanie 33b
select * from (
    select B.NAZWA, K.FUNKCJA, decode(K.PLEC, 'D', 'KOTKA', 'M', 'KOCOR') "PLEC",
           K.PRZYDZIAL_MYSZY + NVL(K.MYSZY_EXTRA, 0)  "PRZYDZIAL"
    from BANDY B
        inner join KOCURY K on B.NR_BANDY = K.NR_BANDY
    union
    select B.NAZWA, 'SUMA' "SUMA", decode(K.PLEC, 'D', 'KOTKA', 'M', 'KOCOR') "PLEC",
           sum(PRZYDZIAL_MYSZY + NVL(MYSZY_EXTRA, 0)) "PRZYDZIAL"
    from BANDY B
        inner join KOCURY K on B.NR_BANDY = K.NR_BANDY
    group by B.NAZWA, K.PLEC
    union
    select 'Zjada razem ' "NAZWA", K.FUNKCJA, ' ' "PLEC",
           sum(NVL(PRZYDZIAL_MYSZY, 0) + NVL(MYSZY_EXTRA, 0)) "PRZYDZIAL"
    from BANDY B
        inner join KOCURY K on B.NR_BANDY = K.NR_BANDY
    group by K.FUNKCJA
    union
    select 'Zjada razem ' "NAZWA", 'SUMA' "FUNKCJA", ' ' "PLEC",
           sum(NVL(K.PRZYDZIAL_MYSZY, 0) + NVL(K.MYSZY_EXTRA, 0)) "PRZYDZIAL"
    from KOCURY K
    union
    select B.NAZWA, 'ILE' "FUNKCJA", decode(K.PLEC, 'D', 'KOTKA', 'M', 'KOCOR') "PLEC", count(K.PSEUDO) "PRZYDZIAL"
    from KOCURY K
        inner join BANDY B on K.NR_BANDY = B.NR_BANDY
    group by B.NAZWA, K.PLEC
)
pivot (
    sum(PRZYDZIAL)
    for FUNKCJA in ('ILE', 'SZEFUNIO', 'BANDZIOR', 'LOWCZY', 'LAPACZ', 'KOT', 'MILUSIA', 'DZIELCZY', 'SUMA')
      )
order by NAZWA;