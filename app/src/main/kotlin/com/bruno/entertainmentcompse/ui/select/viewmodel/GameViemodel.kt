package com.bruno.entertainmentcompse.ui.select.viewmodel
import com.bruno.entertainmentcompse.BaseViewModel
import com.bruno.entertainmentcompse.di.IoDispatcher
import com.bruno.entertainmentcompse.domain.usecase.GetCategoriesUseCase
import com.bruno.entertainmentcompse.domain.usecase.GetDataUseCase
import com.bruno.entertainmentcompse.ui.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getDataUseCase: GetDataUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel(){

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    fun updateState(update: CategoryUiState.() -> CategoryUiState) {
        _uiState.update { it.update() }
    }

    fun startGame(): Boolean {
        val state = _uiState.value
        return state.selectedCategory != null &&
                !state.selectedDifficulty.isNullOrBlank() &&
                (state.selectedAmount ?: 0) > 0
    }

    fun resetGame() {
        _uiState.update {
            it.copy(
                triviaQuestions = emptyList(),
                selectedDifficulty = null,
                selectedType = null,
                selectedAmount = null,
                selectedCategory = null,
                gameFinished = false
            )
        }
    }

    fun getDataCategories() {
        launchSafe(dispatcher = dispatcher) {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getCategoriesUseCase()) {
                is Resource.Success -> {
                    val categories = result.data?.triviaCategory ?: emptyList()
                    _uiState.update {
                        it.copy(
                            categories = categories,
                            selectedCategory = categories.firstOrNull()
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(categories = emptyList(), error = result.message) }
                }
                else -> {}
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun getTrivia(amount: Int, categoryId: Int) {
        launchSafe(dispatcher = Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getDataUseCase(amount.toString(), categoryId)) {
                is Resource.Success -> {
                    val questions = result.data ?: emptyList()

                    _uiState.update {
                        it.copy(
                            triviaQuestions = questions,
                            showGame = questions.isNotEmpty(),
                            gameFinished = false
                        )
                    }
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        triviaQuestions = emptyList(),
                        showGame = false,
                        error = result.message
                    ) }
                else -> {}
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onPlayClicked() {
        val isValid = startGame()

        if (!isValid) {
            updateState { copy(showDialog = true) }
            return
        }

        val state = uiState.value
        getTrivia(
            amount = state.selectedAmount ?: 10,
            categoryId = state.selectedCategory?.id ?: 0
        )
    }

    fun onCategoryScreenEntered() {
        getDataCategories()
    }

    override fun onError(exception: Throwable) {
        _uiState.update { it.copy(isLoading = false, error = exception.message ?: "Unexpected error") }
    }

    fun onGameFinished() {
        updateState {
            copy(
                showGame = false,
                gameFinished = true
            )
        }
    }

    fun dismissDialog() {
        updateState { copy(showDialog = false) }
    }


}