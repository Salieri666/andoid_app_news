package ru.example.andoid_app_news

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ru.example.andoid_app_news.model.data.News
import ru.example.andoid_app_news.model.entity.BookmarkEntity
import ru.example.andoid_app_news.rss.LentaRssParser
import ru.example.andoid_app_news.samples.NewsSamples

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner::class)
class TestModels {
    @Test
    fun testConvertModels() {
        val entity = BookmarkEntity(1, "url", "title", "description", "url_img","source",1651081264000,1651081264000)
        val news = News(1, "url", "title", "description", "27 Apr 2022 21:41:04", "url_img", "source", 1651081264000)

        val convertedNews = News.toNews(entity)
        val convertedEntity = News.toEntity(news, 1651081264000)

        assertEquals(entity.id, convertedNews.id)
        assertEquals(entity.sourceDate, convertedNews.sourceDate)
        assertEquals("27 Apr 2022 21:41:04", convertedNews.date)

        assertEquals(news.id, convertedEntity.id)
        assertEquals(news.sourceDate, convertedEntity.sourceDate)
    }

    @Test
    fun testLentaNewsParser() {
        val newsSamples = NewsSamples()
        val news = LentaRssParser().parse(newsSamples.lentaNewsString.byteInputStream()).items

        Assert.assertNotNull(news)
        assertEquals(1, news?.size)
        assertEquals("Lenta.ru", news?.get(0)?.source)
    }
}