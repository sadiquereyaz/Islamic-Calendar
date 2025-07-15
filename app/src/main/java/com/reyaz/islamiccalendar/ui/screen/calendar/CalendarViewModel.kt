package com.reyaz.islamiccalendar.ui.screen.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reyaz.islamiccalendar.domain.repository.CalendarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "CALENDAR_VIEW_MODEL"

class CalendarViewModel(
    private val calendarRepository: CalendarRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<CalendarUiState>(CalendarUiState.Loading)
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    init {
        observeCalendar(month = null, year = null)
    }

    private fun observeCalendar(month: Int?, year: Int?) {
        viewModelScope.launch {
            Log.d(TAG, "observeCalendar called with month: $month, year: $year")
            try {
                _uiState.value = CalendarUiState.Loading
                calendarRepository.observeCalendar(month, year).collect { result ->
                    result.fold(
                        onSuccess = { completeCalendar ->
                            _uiState.value = CalendarUiState.Success(data = completeCalendar)
//                            Log.d(TAG, "Index of today is: ${completeCalendar.currentHijriMonthDayIndex}")
                            Log.d(TAG, "observeCalendar month: ${completeCalendar.hijriMonth}, year: ${completeCalendar.hijriYear}")
                        },
                        onFailure = { e ->
                            _uiState.value = CalendarUiState.Error(e.message ?: "Unknown error")
                        }
                    )
                }
            } catch (e: Exception){
                Log.e(TAG, "observeCalendar error", e)
            }
        }
    }

    fun changeMonth(changeBy: Int) {
        if (uiState.value is CalendarUiState.Success) {
            val targetMonth = (_uiState.value as CalendarUiState.Success).data.hijriMonth + changeBy
            val targetYear = (_uiState.value as CalendarUiState.Success).data.hijriYear

//            Log.d(TAG, "changeMonth year before modifying $targetYear")

            if (targetMonth > 12) observeCalendar(
                month = 1,
                year = targetYear + 1
            ) else if (targetMonth < 1) observeCalendar(
                month = 12,
                year = targetYear - 1
            )
            else observeCalendar(month = targetMonth, year = targetYear)
//            Log.d(TAG, "changeMonth called with targetMonth: $targetMonth, targetYear: $targetYear")

        }
    }

    fun onCellClick(cellIndex: Int) {
        _uiState.update { currentState ->
            when (currentState) {
                is CalendarUiState.Success -> {
                    currentState.copy(selectedIndex = cellIndex)
                }

                else -> currentState // Ignore clicks if not in Success state
            }
        }
    }

}