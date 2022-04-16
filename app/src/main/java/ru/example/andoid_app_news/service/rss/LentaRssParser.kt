package ru.example.andoid_app_news.service.rss

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import ru.example.andoid_app_news.model.data.Channel
import ru.example.andoid_app_news.model.ui.News
import java.io.IOException
import java.io.InputStream
import java.util.*

class LentaRssParser {
    private val ns: String? = null

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): Channel {
        var channel: Channel=Channel()
        var items: List<News>
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            channel = readRss(parser)
        }

        return channel
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun readRss(parser: XmlPullParser): Channel {
        var channel: Channel = Channel()

        parser.require(XmlPullParser.START_TAG, ns, "rss")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }

            if (parser.name == "channel") {
                channel = readChannel(parser)
            } else {
                skip(parser)
            }
        }
        return channel
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readChannel(parser: XmlPullParser): Channel {
        parser.require(XmlPullParser.START_TAG, ns, "channel")
        var language: String? = null
        var title: String? = null
        var description: String? = null
        var link: String? = null
        val items = mutableListOf<News>()

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "language" -> language = readFromTag(parser, "language")
                "title" -> title = readFromTag(parser, "title")
                "description" -> description = readFromTag(parser, "description")
                "link" -> link = readFromTag(parser, "link")
                "item" -> items.add(readItem(parser))
                else -> skip(parser)
            }
        }
        return Channel(language, title, description, link, items)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readItem(parser: XmlPullParser): News {
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

        return News(null, link?: UUID.randomUUID().toString(), title, description, pubDate, img, "Lenta.ru", 0)
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readFromTag(parser: XmlPullParser, name: String?): String {
        parser.require(XmlPullParser.START_TAG, ns, name)
        val summary = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, name)
        return summary
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readFromTagAttribute(parser: XmlPullParser, name: String?, nameAttribute: String): String {
        parser.require(XmlPullParser.START_TAG, ns, name)
        val summary = readProperty(parser, nameAttribute)
        parser.require(XmlPullParser.END_TAG, ns, name)
        return summary
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readProperty(parser: XmlPullParser, attribute: String): String {
        var result = ""
        result = parser.getAttributeValue(null, attribute)
        parser.nextTag()
        return result
    }
}