package ddwu.com.mobile.naveropenapitest.network

import android.util.Xml
import ddwu.com.mobile.naveropenapitest.data.Book
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class   NaverBookParser {
    private val ns: String? = null

    companion object {
        val FAULT_RESULT = "faultResult"
        val ITEM_TAG = "item"
        val TITLE_TAG = "title"
        val AUTHOR_TAG = "author"
        val PUBDATE_TAG = "pubdate"
    }

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream?) : List<Book> {

        inputStream.use { inputStream ->
            val parser : XmlPullParser = Xml.newPullParser()

            /*Parser 의 동작 정의, next() 호출 전 반드시 호출 필요*/
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)

            /* Paring 대상이 되는 inputStream 설정 */
            parser.setInput(inputStream, null)

            /*Parsing 대상 태그의 상위 태그까지 이동*/
            while (parser.name != "channel") {
                parser.next()
            }

            return readBookList(parser)
        }
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun readBookList(parser: XmlPullParser) : List<Book> {
        val books = mutableListOf<Book>()

        parser.require(XmlPullParser.START_TAG, ns, "channel")
        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == ITEM_TAG) {
                books.add( readBookItem(parser) )
            } else {
                skip(parser)
            }
        }

        return books
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun readBookItem(parser: XmlPullParser) : Book {
        var title : String? = null
        var author : String? = null
        var pubdate : Int? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                TITLE_TAG -> title = readTextInTag(parser, TITLE_TAG)
                AUTHOR_TAG -> author = readTextInTag(parser, AUTHOR_TAG)
                PUBDATE_TAG -> pubdate = readTextInTag(parser, PUBDATE_TAG).toInt()
                else -> skip(parser)
            }
        }
        return Book(title, author, pubdate)
    }



    @Throws(IOException::class, XmlPullParserException::class)
    private fun readTextInTag (parser: XmlPullParser, tag: String): String {
        parser.require(XmlPullParser.START_TAG, ns, tag)
        var text = ""
        if (parser.next() == XmlPullParser.TEXT) {
            text = parser.text
            parser.nextTag()
        }
        parser.require(XmlPullParser.END_TAG, ns, tag)
        return text
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
}