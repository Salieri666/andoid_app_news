package ru.example.andoid_app_news.samples

import ru.example.andoid_app_news.model.ui.News

object NewsSamples {
    fun getNews() = listOf(
            News(1,"","Desc1","Title1", "Date1","","Source1"),
            News(2,"","Desc2","Title2", "Date2","","Source2"),
            News(3,"","Desc3","Title3", "Date3","","Source3"),
            News(4,"","Desc1","Title1", "Date1","","Source1"),
            News(5,"","Desc2","Title2", "Date2","","Source2"),
            News(6,"","Desc3","Title3", "Date3","","Source3")
    )
}