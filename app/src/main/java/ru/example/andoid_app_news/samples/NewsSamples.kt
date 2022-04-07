package ru.example.andoid_app_news.samples

import ru.example.andoid_app_news.model.News

object NewsSamples {
    fun getNews() = listOf(
            News("","Desc1","Title1", "Date1","","Source1"),
            News("","Desc2","Title2", "Date2","","Source2"),
            News("","Desc3","Title3", "Date3","","Source3"),
            News("","Desc1","Title1", "Date1","","Source1"),
            News("","Desc2","Title2", "Date2","","Source2"),
            News("","Desc3","Title3", "Date3","","Source3")
    )
}