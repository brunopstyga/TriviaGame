package com.bruno.entertainmentcompse.ui.trivia

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno.entertainmentcompse.data.GameRepository
import com.bruno.entertainmentcompse.model.TriviaQuestion
import com.bruno.entertainmentcompse.ui.Resource
import com.bruno.entertainmentcompse.ui.select.viewmodel.CategoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TriviaQuestionViewModel@Inject constructor(
    private val gameRepository: GameRepository)  : ViewModel() {

    private val _uiState = MutableStateFlow(TriviaQuestionUIState())
    val uiState: StateFlow<TriviaQuestionUIState> = _uiState.asStateFlow()


    fun getDataTriviaQuestion(category: String, type: String, difficulty: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            Timber.tag("getDataTriviaQuestion").d("ENTRO POR ACA")

            when (val result = gameRepository.getTriviaQuestions(category, 10, type, difficulty)) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            triviaQuestions = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            triviaQuestions = emptyList(),
                            isLoading = false,
                            error = result.message ?: "Unknown error"
                        )
                    }
                }

                is Resource.Loading -> TODO()
            }
        }
    }

}


