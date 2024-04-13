package ddwu.com.mobile.networktest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import javax.net.ssl.HttpsURLConnection


class NetworkManager(val context: Context) {
    private val TAG = "NetworkManager"

    fun downloadText(url: String) : String? {
        var receivedContents : String? = null
        var iStream : InputStream? = null
        var conn : HttpURLConnection? = null
//        var conn : HttpsURLConnection? = null

        try {
            val url : URL = URL(url)
            conn = url.openConnection() as HttpURLConnection       // 서버 연결 설정 – MalformedURLException
//            conn = url.openConnection() as HttpsURLConnection       // 서버 연결 설정 – MalformedURLException

            conn.readTimeout = 5000                                 // 읽기 타임아웃 지정 - SocketTimeoutException
            conn.connectTimeout = 5000                              // 연결 타임아웃 지정 - SocketTimeoutException
            conn.doInput = true                                     // 서버 응답 지정 – default
            conn.requestMethod = "GET"                              // 연결 방식 지정 - or POST
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=EUC-KR")
            // 전송 형식 지정  json 일 경우 application/json 으로 변경

//            conn.connect()                                          // 통신 링크 열기 – 트래픽 발생
            val responseCode = conn.responseCode                    // 서버 전송 및 응답 결과 수신

            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw IOException("Http Error Code: $responseCode")
            }

            iStream = conn.inputStream                          // 응답 결과 스트림 확인
            receivedContents = readStreamToString (iStream)         // stream 처리 함수를 구현한 후 사용

        } catch (e: Exception) {        // MalformedURLException, IOExceptionl, SocketTimeoutException 등 처리 필요
            Log.d(TAG, e.message!!)
        } finally {
            if (iStream != null) { try { iStream.close()} catch (e: IOException) { Log.d(TAG, e.message!!) } }
            if (conn != null) conn.disconnect()
        }
        return receivedContents
    }


    fun downloadImage(url : String) : Bitmap? {
        var receivedContents : Bitmap? = null
        var iStream : InputStream? = null
        var conn : HttpURLConnection? = null

        // Image Download 구현
        try {
            val url : URL = URL(url)
            conn = url.openConnection() as HttpURLConnection       // 서버 연결 설정 – MalformedURLException
//            conn = url.openConnection() as HttpsURLConnection       // 서버 연결 설정 – MalformedURLException

            conn.readTimeout = 5000                                 // 읽기 타임아웃 지정 - SocketTimeoutException
            conn.connectTimeout = 5000                              // 연결 타임아웃 지정 - SocketTimeoutException
            conn.doInput = true                                     // 서버 응답 지정 – default
            conn.requestMethod = "GET"                              // 연결 방식 지정 - or POST
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=EUC-KR")
            // 전송 형식 지정  json 일 경우 application/json 으로 변경

//            conn.connect()                                          // 통신 링크 열기 – 트래픽 발생
            val responseCode = conn.responseCode                    // 서버 전송 및 응답 결과 수신

            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw IOException("Http Error Code: $responseCode")
            }

            iStream = conn.inputStream                          // 응답 결과 스트림 확인
            receivedContents = readStreamToImage (iStream)      // stream 처리 함수를 구현한 후 사용

        } catch (e: Exception) {        // MalformedURLException, IOExceptionl, SocketTimeoutException 등 처리 필요
            Log.d(TAG, e.message!!)
        } finally {
            if (iStream != null) { try { iStream.close()} catch (e: IOException) { Log.d(TAG, e.message!!) } }
            if (conn != null) conn.disconnect()
        }

        return receivedContents
    }


    fun sendPostData(url: String) : String? {
        var receivedContents : String? = null
        var iStream : InputStream? = null
        var conn : HttpsURLConnection? = null

        // POST 요청 구현
        try {
            val url : URL = URL(url)
//            conn = url.openConnection() as HttpURLConnection       // 서버 연결 설정 – MalformedURLException
            conn = url.openConnection() as HttpsURLConnection       // 서버 연결 설정 – MalformedURLException

            conn.readTimeout = 5000                                 // 읽기 타임아웃 지정 - SocketTimeoutException
            conn.connectTimeout = 5000                              // 연결 타임아웃 지정 - SocketTimeoutException
            conn.doInput = true                                     // 서버 응답 지정 – default
            conn.requestMethod = "POST"                              // 연결 방식 지정 - or POST
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=EUC-KR")
            // 전송 형식 지정  json 일 경우 application/json 으로 변경
            val params1 = "user=somsom"
            val params2 = "&dept=computer"
            val outStreamWriter : OutputStreamWriter = OutputStreamWriter(conn.outputStream, "UTF-8")
            val writer : BufferedWriter = BufferedWriter(outStreamWriter)
            writer.write(params1)
            writer.write(params2)
            writer.flush()

            val responseCode = conn.responseCode                    // 서버 전송 및 응답 결과 수신

            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw IOException("Http Error Code: $responseCode")
            }

            iStream = conn.inputStream                          // 응답 결과 스트림 확인
            receivedContents = readStreamToString (iStream)         // stream 처리 함수를 구현한 후 사용

        } catch (e: Exception) {        // MalformedURLException, IOExceptionl, SocketTimeoutException 등 처리 필요
            Log.d(TAG, e.message!!)
        } finally {
            if (iStream != null) { try { iStream.close()} catch (e: IOException) { Log.d(TAG, e.message!!) } }
            if (conn != null) conn.disconnect()
        }
        return receivedContents

        return receivedContents
    }


    // InputStream 을 String 으로 변환
    private fun readStreamToString(iStream : InputStream?) : String {
        val resultBuilder = StringBuilder()

        val inputStreamReader = InputStreamReader(iStream)
        val bufferedReader = BufferedReader(inputStreamReader)

        var readLine : String? = bufferedReader.readLine()
        while (readLine != null) {
            resultBuilder.append(readLine + System.lineSeparator())
            readLine = bufferedReader.readLine()
        }

        bufferedReader.close()
        return resultBuilder.toString()
    }

    // InputStream 을 Bitmap 으로 변환
    private fun readStreamToImage(iStream: InputStream?) : Bitmap {
        val bitmap = BitmapFactory.decodeStream(iStream)
        return bitmap
    }

}