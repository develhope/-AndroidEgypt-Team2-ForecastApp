package co.develhope.meteoapp.ui.tomorrow.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.data.domain.DailyDataLocal
import co.develhope.meteoapp.network.WeatherRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TomorrowViewModel() : ViewModel() {
    private val tomorrowWeatherRepo = WeatherRepo()
    private val _result: MutableLiveData<DailyDataLocal?> = MutableLiveData()
    var result: LiveData<DailyDataLocal?> = _result
    private val _showProgress: MutableLiveData<Boolean> = MutableLiveData()
    var showProgress: LiveData<Boolean> = _showProgress


    fun isLoading(showProgress:Boolean) {
        _showProgress.value = showProgress
    }


    fun getDaily(
        lat: Double,
        lon: Double,
        startDate: String,
        endDate: String
    ) {
        viewModelScope.launch(IO) {
            val response = tomorrowWeatherRepo.getWeather(lat, lon, startDate, endDate)
            if (response != null) {
                _result.postValue(response)
                Log.i("TAG", "getDaily: $response")
            } else {
                Log.e("TAG", "getDaily: Couldn't achieve network call. (Tomorrow Screen) ")
            }
        }

    }
}