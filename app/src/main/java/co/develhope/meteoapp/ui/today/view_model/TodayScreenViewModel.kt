package co.develhope.meteoapp.ui.today.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.network.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class TodayScreenViewModel @Inject constructor(
    private val repo: WeatherRepo,
) : ViewModel() {
    private val _todayScreenState = MutableStateFlow(DailyDataUiModel())
    val todayScreenState: StateFlow<DailyDataUiModel> = _todayScreenState

    fun fetchTodayWeather(
        lat: Double,
        lon: Double,
        startDate: String,
        endDate: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _todayScreenState.update {
                    it.copy(
                        isProgressbarVisible = true
                    )
                }
                _todayScreenState.update {it.copy(
                    dailyDataLocal = repo.getWeather(
                        lat=lat,
                        lon=lon,
                        startDate=startDate,
                        endDate=endDate
                    ),
                    isProgressbarVisible = false
                )
                }
            } catch (_: CancellationException) {
                _todayScreenState.update {
                    it.copy(
                        isProgressbarVisible = false
                    )
                }
            } catch (ex: Exception) {
                _todayScreenState.update {
                    it.copy(
                        dailyDataLocal = null,
                        isProgressbarVisible = false
                    )
                }
            }
        }
    }
}