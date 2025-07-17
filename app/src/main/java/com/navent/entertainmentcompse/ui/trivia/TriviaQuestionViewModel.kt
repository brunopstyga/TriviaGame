package com.navent.entertainmentcompse.ui.trivia

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.navent.entertainmentcompse.data.GameRepository
import com.navent.entertainmentcompse.model.TriviaQuestion
import com.navent.entertainmentcompse.ui.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TriviaQuestionViewModel@Inject constructor(
    private val gameRepository: GameRepository)  : ViewModel() {

    var isLoading = mutableStateOf(false)

    private var _triviaQuestions = MutableLiveData<List<TriviaQuestion>>()
    val triviaQuestions: LiveData<List<TriviaQuestion>> = _triviaQuestions

    fun getDataTriviaQuestion(category: String,type: String,difficulty: String) {
        viewModelScope.launch {
            isLoading.value = true
            Timber.tag("getDataTriviaQuestion").d("ENTRO POR ACA")
            val result = gameRepository.getTriviaQuestions(category,10,type,difficulty)

            if (result is Resource.Success) {
                _triviaQuestions.postValue(result.data ?: emptyList())
            } else {
                _triviaQuestions.postValue(emptyList())
            }
            isLoading.value = false
        }
    }
}


