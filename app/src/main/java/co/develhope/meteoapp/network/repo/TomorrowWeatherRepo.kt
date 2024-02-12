//package co.develhope.meteoapp.network.repo
//
//import co.develhope.meteoapp.data.domain.DailyDataLocal
//import co.develhope.meteoapp.data.dto.toDailyDataLocal
//import co.develhope.meteoapp.network.WeatherService
//import java.text.SimpleDateFormat
//
//class TomorrowWeatherRepo(private val apiService: WeatherService) {
//    private val dailyData =
//        "temperature_2m,relativehumidity_2m,apparent_temperature,precipitation_probability,rain,weathercode,cloudcover,windspeed_10m,winddirection_10m,uv_index,is_day"
//
//    suspend fun getTomorrowWeather(
//        lat: Double,
//        lon: Double,
//        startDate: String,
//        endDate: String
//    ): DailyDataLocal? {
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
//        val startDateFormatted = dateFormat.format(dateFormat.parse(startDate))
//        val endDateFormatted = dateFormat.format(dateFormat.parse(endDate))
//
//        val response = apiService.getDaily(
//            lat,
//            lon,
//            dailyData,
//            "UTC",
//            startDateFormatted,
//            endDateFormatted
//        )
//
//        return response.toDailyDataLocal()
//    }
//}