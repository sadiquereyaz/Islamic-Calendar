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
    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    init {
        observeCalendar()
    }

    private fun observeCalendar() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            calendarRepository.observeCalendar(uiState.value.month, uiState.value.year).collect { result ->
                if (result.isSuccess) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            calendarDays = result.getOrNull()
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.exceptionOrNull()?.message
                        )
                    }
                }
            }
        }
    }
}