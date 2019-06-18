package com.arkadr.tvgallery

import android.net.Uri

abstract class PhotoList {
    companion object {
        val shibes: ArrayList<Photo> by lazy {
            arrayListOf(
                Photo(Uri.parse("https://i.redd.it/ch6esq28bf921.jpg"), "shibe1"),
                Photo(Uri.parse("https://i.redd.it/2l48nwrdbpl21.jpg"), "shibe2"),
                Photo(Uri.parse("https://i.redd.it/eic4vj804y811.jpg"), "shibe3"),
                Photo(Uri.parse("https://i.redd.it/by4w30hirbh21.jpg"), "shibe4"),
                Photo(Uri.parse("https://i.redd.it/w3p8g6ru0oq21.jpg"), "shibe5"),
                Photo(Uri.parse("https://i.redd.it/vho46o1z8rc11.jpg"), "shibe7"),
                Photo(Uri.parse("https://i.redd.it/srrnmwwuple21.jpg"), "shibe8"),
                Photo(Uri.parse("https://i.redd.it/1rprgi7xtj911.jpg"), "shibe9"),
                Photo(Uri.parse("https://i.redd.it/k2h71rehuyl01.jpg"), "shibe10"),
                Photo(Uri.parse("https://i.redd.it/e1n8tu8e9j621.jpg"), "shibe11")
            )
        }
        val cates: ArrayList<Photo> by lazy {
            arrayListOf(
                Photo(Uri.parse("https://i.redd.it/nap97qc4ilh21.jpg"), "cate1"),
                Photo(Uri.parse("https://i.redd.it/rnku4kwar0d01.jpg"), "cate2"),
                Photo(
                    Uri.parse("https://external-preview.redd.it/wtA92LBm58-JGjbLsvAvj-Eves8-1ldCvQUwx3pufkA.jpg?auto=webp&s=a90e8ea558862168ec300d494857921609e3df45"),
                    "cate3"
                ),
                Photo(Uri.parse("https://i.redd.it/67ihts1joh621.jpg"), "cate4"),
                Photo(
                    Uri.parse("https://external-preview.redd.it/F9Xj_raG3xCNzo3Id-f6fbxOT8NLIxKJkckyx9FsDf0.jpg?auto=webp&s=b2644c2bcf846dc9a61b2d1130f4f60e261e9c45"),
                    "cate5"
                ),
                Photo(Uri.parse("https://i.redd.it/t76tzci3t5y11.jpg"), "cate7"),
                Photo(Uri.parse("https://i.redd.it/at8wvk2rizzx.jpg"), "cate8"),
                Photo(Uri.parse("https://i.redd.it/5ga1n0uer1q11.jpg"), "cate9"),
                Photo(Uri.parse("https://i.redd.it/rttwja64k5e21.jpg"), "cate10"),
                Photo(Uri.parse("https://i.redd.it/e3utgzi1aqt01.jpg"), "cate11"),
                Photo(Uri.parse("https://i.redd.it/3hf744b0zco11.jpg"), "cate12")
            )
        }

        val parrots: ArrayList<Photo> by lazy {
            arrayListOf(
                Photo(Uri.parse("https://i.redd.it/p0hyle45i7o11.jpg"), "parrot1"),
                Photo(Uri.parse("https://i.redd.it/iky3q5box5211.jpg"), "parrot2"),
                Photo(Uri.parse("https://i.redd.it/y3idzhuv9ye11.jpg"), "parrot4"),
                Photo(Uri.parse("https://i.redd.it/3mo54xm0zah21.jpg"), "parrot5"),
                Photo(Uri.parse("https://i.redd.it/b6f4kgsbc4i21.png"), "parrot6"))

        }
    }
}