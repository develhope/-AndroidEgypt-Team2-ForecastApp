package co.develhope.meteoapp.ui.home.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.data.domain.WeeklyDataLocal
import co.develhope.meteoapp.network.WeatherRepo
import co.develhope.meteoapp.ui.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: WeatherRepo) : ViewModel() {
    private val _result: MutableLiveData<DataState<WeeklyDataLocal>> = MutableLiveData()
    var result: LiveData<DataState<WeeklyDataLocal>> = _result
    private val _navigationCommand: MutableSharedFlow<Boolean> = MutableSharedFlow()
    var navigationCommand: SharedFlow<Boolean> = _navigationCommand

    fun getWeekly(lat: Double, lon: Double) {
        viewModelScope.launch {
            _result.value = DataState.Loading(true)
            val response = repo.getHomeWeather(lat, lon)
            try {
                if (response != null) {
                    _result.postValue(DataState.Success(response))
                }
            } catch (e: Exception) {
                _result.value = DataState.Failure(e)
                _navigationCommand.emit(true)
            }
        }
    }
}