package co.develhope.meteoapp.ui.search.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.data.domain.SearchDataLocal
import co.develhope.meteoapp.network.SearchRepo
import co.develhope.meteoapp.ui.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepo: SearchRepo) : ViewModel() {
    private val _state: MutableStateFlow<DataState<SearchDataLocal>> =
        MutableStateFlow(DataState.Loading(false))
    val state: StateFlow<DataState<SearchDataLocal>> = _state
    fun getPlaces(place: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                DataState.Loading(true)
            }
            try {
                val response = _state.updateAndGet {
                    DataState.Success(searchRepo.getSearch(place)!!)
                }
                (response as DataState.Success).data.forEach {
                    Log.d(
                        "DATA", "${it.admin1},${it.name}, ${it.latitude}, ${it.longitude} "
                    )
                }

            } catch (e: Exception) {
                Log.e("ERROR", "network error ")
                _state.update {
                    DataState.Failure(e)
                }
            }
        }
    }
}