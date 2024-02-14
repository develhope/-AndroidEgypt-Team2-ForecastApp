package co.develhope.meteoapp.ui.today.view_model

import co.develhope.meteoapp.data.domain.DailyDataLocal

data class DailyDataUiModel(
    val dailyDataLocal: DailyDataLocal?= DailyDataLocal(),
    val isProgressbarVisible:Boolean = false
)