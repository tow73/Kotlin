package ddwu.com.mobile.adaptereventtest

class WeatherDao {
    val weathers = ArrayList<WeatherDto>()

    init {
        weathers.add (WeatherDto("No", "하월곡동", "서울시 성북구", "좋음"))
    }
}