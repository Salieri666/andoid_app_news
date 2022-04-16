package ru.example.andoid_app_news.samples

import ru.example.andoid_app_news.model.ui.News

object NewsSamples {
    fun getNews() = listOf(
            News(1,"1","Title1","Desc1", "Date1","","Source1", 1),
            News(2,"2","Title2","Desc2", "Date2","","Source2",2),
            News(3,"3","Title3","Desc3", "Date3","","Source3",3),
            News(4,"4","Title4","Desc4", "Date1","","Source1",4),
            News(5,"5","Title5","Desc5", "Date2","","Source2",5),
            News(6,"6","Title6","Desc6", "Date3","","Source3",6)
    )
}