package ru.example.andoid_app_news.service.rss

import org.xmlpull.v1.XmlPullParser
import ru.example.andoid_app_news.model.ui.News
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class LentaRssParser : AbstractRssParser() {

    override fun readItem(parser: XmlPullParser): News {
        parser.require(XmlPullParser.START_TAG, ns, "item")
        var title: String? = null
        var description: String? = null
        var link: String? = null
        var pubDate: String? = null
        var img: String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "title" -> title = readFromTag(parser, "title")
                "description" -> description = readFromTag(parser, "description")
                "link" -> link = readFromTag(parser, "link")
                "pubDate" -> pubDate = readFromTag(parser, "pubDate")
                "enclosure" -> img = readFromTagAttribute(parser, "enclosure", "url")
                else -> skip(parser)
            }
        }

        val sdf = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z").withLocale(Locale.ENGLISH)
        val sourceDate = ZonedDateTime.parse(pubDate, sdf)

        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss").withZone(TimeZone.getDefault().toZoneId()).withLocale(Locale.ENGLISH)
        val timeString = sourceDate.format(formatter)

        return News(null, link?: UUID.randomUUID().toString(), title, description, timeString, img, "Lenta.ru", sourceDate.toInstant().toEpochMilli())
    }
}