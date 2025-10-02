package com.bruno.entertainmentcompse.ui.trivia

import com.bruno.entertainmentcompse.BaseViewModel
import com.bruno.entertainmentcompse.model.GetTriviaQuestionsUseCase
import com.bruno.entertainmentcompse.ui.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TriviaQuestionViewModel@Inject constructor(
    private val getTriviaQuestionsUseCase: GetTriviaQuestionsUseCase)  : BaseViewModel() {

    private val _uiState = MutableStateFlow(TriviaQuestionUIState())
    val uiState: StateFlow<TriviaQuestionUIState> = _uiState.asStateFlow()



    fun getDataTriviaQuestion(category: String, type: String, difficulty: String) {
        launchSafe(dispatcher = Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true, error = null) }

            Timber.tag("TriviaQuestion").d("Fetching trivia questions...")

            when (val result = getTriviaQuestionsUseCase(category, 10, type, difficulty)) {
                is Resource.Success -> _uiState.update {
                    it.copy(
                        triviaQuestions = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> _uiState.update {
                    it.copy(
                        triviaQuestions = emptyList(),
                        isLoading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
                else -> {}
            }
        }
    }

    override fun onError(exception: Throwable) {
        _uiState.update { it.copy(isLoading = false, error = exception.message ?: "Unexpected error") }
    }

}


