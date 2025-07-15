package com.reyaz.islamiccalendar.ui.screen.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reyaz.islamiccalendar.domain.repository.CalendarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CalendarViewModel(
    private val calendarRepository: CalendarRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<CalendarUiState>(CalendarUiState.Loading)
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    init {
        observeCalendar(month = null, year = null)
    }

    fun observeCalendar(month: Int?, year: Int?) {
        viewModelScope.launch {
            _uiState.value = CalendarUiState.Loading
            calendarRepository.observeCalendar(month, year).collect { result ->
                result.fold(
                    onSuccess = { completeCalendar ->
                        _uiState.value = CalendarUiState.Success(data = completeCalendar)
                    },
                    onFailure = { e ->
                        _uiState.value = CalendarUiState.Error(e.message ?: "Unknown error")
                    }
                )
            }
        }
    }

    fun changeMonth(changeBy: Int) {
        if (uiState.value is CalendarUiState.Success) {
            val targetMonth = (_uiState.value as CalendarUiState.Success).data.hijriMonth + changeBy
            val targetYear = (_uiState.value as CalendarUiState.Success).data.hijriYear

            if (targetMonth > 12) observeCalendar(
                month = 1,
                year = targetYear + 1
            ) else if (targetMonth < 1) observeCalendar(
                month = 12,
                year = targetYear - 1
            )
            else observeCalendar(month = targetMonth, year = null)
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