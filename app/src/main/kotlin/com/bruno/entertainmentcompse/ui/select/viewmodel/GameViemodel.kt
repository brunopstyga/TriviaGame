package com.bruno.entertainmentcompse.ui.select.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno.entertainmentcompse.di.MainDispatcher
import com.bruno.entertainmentcompse.model.Category
import com.bruno.entertainmentcompse.model.GetCategoriesUseCase
import com.bruno.entertainmentcompse.model.GetDataUseCase
import com.bruno.entertainmentcompse.ui.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getDataUseCase: GetDataUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    fun setSelectedCategory(category: Category) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun setSelectedDifficulty(difficulty: String) {
        _uiState.update { it.copy(selectedDifficulty = difficulty) }
    }

    fun setSelectedType(type: String) {
        _uiState.update { it.copy(selectedType = type) }
    }

    fun setSelectedAmount(amount: Int) {
        _uiState.update { it.copy(selectedAmount = amount) }
    }


    fun setGameFinished(finished: Boolean) {
        _uiState.update { it.copy(gameFinished = finished) }
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
        viewModelScope.launch(dispatcher) {
            _uiState.update { it.copy(isLoading = true) }

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
                    _uiState.update { it.copy(categories = emptyList()) }
                }
                else -> {}
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun getTrivia(amount: Int, categoryId: Int) {
        viewModelScope.launch(dispatcher) {
            _uiState.update { it.copy(isLoading = true) }

            when (val result = getDataUseCase(amount.toString(), categoryId)) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(triviaQuestions = result.data ?: emptyList())
                    }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(triviaQuestions = emptyList()) }
                }
                else -> {}
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun getSelectedCategoryName(): String {
        return _uiState.value.selectedCategory?.name ?: "Sin nombre"
    }
}