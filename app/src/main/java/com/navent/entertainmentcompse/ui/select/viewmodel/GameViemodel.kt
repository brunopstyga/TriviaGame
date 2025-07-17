package com.navent.entertainmentcompse.ui.select.viewmodel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.navent.entertainmentcompse.data.GameRepository
import com.navent.entertainmentcompse.di.MainDispatcher
import com.navent.entertainmentcompse.model.Category
import com.navent.entertainmentcompse.model.TriviaQuestion
import com.navent.entertainmentcompse.model.TriviaResponse
import com.navent.entertainmentcompse.ui.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    @MainDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    var isLoading = mutableStateOf(false)

    private val _triviaQuestions = MutableLiveData<List<TriviaQuestion>>()
    val triviaQuestions: LiveData<List<TriviaQuestion>> = _triviaQuestions

    private var _gameCategories = MutableLiveData<List<Category>>()
    val gameCategories: LiveData<List<Category>> = _gameCategories

    private val _difficulty = MutableLiveData<String?>()
    val difficulty: LiveData<String?> = _difficulty

    private val _type = MutableLiveData<String?>()
    val type: LiveData<String?> = _type

    private val _selectedAmount = MutableLiveData<Int?>()
    val selectedAmount: LiveData<Int?> = _selectedAmount

    private var _selectedCategoryId = MutableLiveData<Category?>(null)
    val selectedCategory: LiveData<Category?> = _selectedCategoryId

    private val _gameFinished = MutableLiveData(false)
    val gameFinished: LiveData<Boolean> = _gameFinished

    fun setSelectedDifficulty(selectedDifficulty: String) {
        _difficulty.value = selectedDifficulty
    }

    fun setSelectedType(selectedType: String) {
        _type.value = selectedType
    }

    fun setSelectedAmount(amount: Int) {
        _selectedAmount.value = amount
    }

    fun startGame(): Boolean {
        val categorySelected = _selectedCategoryId.value != null
        val difficultySelected = !_difficulty.value.isNullOrBlank()
         val amount = _selectedAmount.value ?: 0
        return categorySelected && difficultySelected && amount > 0
    }

    fun getDataCategories() {
        viewModelScope.launch(dispatcher) {
            isLoading.value = true
            val categ = gameRepository.getCategories()
            if (categ is Resource.Success) {
                val categories = categ.data?.triviaCategory ?: emptyList()
                categories.forEach {
                    setSelectedCategory(Category(it.id, it.name))
                }
                _gameCategories.postValue(categories)
            } else {
                _gameCategories.postValue(emptyList())
            }
            isLoading.value = false
        }
    }

    fun setGameFinished(finished: Boolean) {
        _gameFinished.value = finished
    }

    fun resetGame() {
        _triviaQuestions.value = emptyList()

        _difficulty.value = null
        _type.value = null
        _selectedAmount.value = null
        _selectedCategoryId.value = null

        _gameFinished.value = false
    }


    fun getTrivia(amount: Int, categoryId: Int) {
        viewModelScope.launch(dispatcher) {
            isLoading.value = true

            val result = gameRepository.getData(amount = amount.toString(), categoryId = categoryId)
            Timber.tag("getTrivia").d("los datos getTrivia: ${result.data}")
            when (result) {
                is Resource.Success -> {
                    val triviaResponse = result.data ?: emptyList()
                    _triviaQuestions.postValue(triviaResponse)
                }

                is Resource.Error -> {
                    Timber.e("getTrivia", "Error: ${result.message}")
                    _triviaQuestions.postValue(emptyList())
                }

                is Resource.Loading -> {}
            }

            isLoading.value = false
        }
    }

    fun setSelectedCategory(category: Category) {
        _selectedCategoryId.value = category
    }

    fun getSelectedCategoryName(): String {
        return _selectedCategoryId.value?.name ?: "Sin nombre"
    }
}