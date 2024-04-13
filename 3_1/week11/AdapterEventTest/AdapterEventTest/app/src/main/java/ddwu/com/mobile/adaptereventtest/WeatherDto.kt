package ddwu.com.mobile.adaptereventtest

class WeatherDto (val no: String, val dong: String, val loc: String, val weather: String) {
    override fun toString() = "$no ($dong, $loc) $weather"
}