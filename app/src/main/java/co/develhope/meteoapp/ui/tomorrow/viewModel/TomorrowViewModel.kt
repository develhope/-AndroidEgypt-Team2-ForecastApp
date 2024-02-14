package co.develhope.meteoapp.ui.tomorrow.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.data.domain.DailyDataLocal
import co.develhope.meteoapp.network.WeatherRepo
import co.develhope.meteoapp.ui.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TomorrowViewModel() : ViewModel() {
    private val tomorrowWeatherRepo = WeatherRepo()
    private val _result: MutableLiveData<DataState<DailyDataLocal>?> = MutableLiveData()
    var result: LiveData<DataState<DailyDataLocal>?> = _result


    fun getTomorrowWeather(
        lat: Double,
        lon: Double,
        startDate: String,
        endDate: String
    ) {
        viewModelScope.launch {

            _result.value = DataState.Loading(true)

            val response = tomorrowWeatherRepo.getWeather(lat, lon, startDate, endDate)
            try {
                if (response != null) {
                    _result.postValue(DataState.Success(response))
                }
            } catch (e: Exception) {
                _result.value = DataState.Failure(e)
            }

        }
    }

}