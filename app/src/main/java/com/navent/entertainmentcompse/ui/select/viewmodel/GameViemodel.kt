package com.navent.entertainmentcompse.ui.select.viewmodel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.navent.entertainmentcompse.data.GameRepository
import com.navent.entertainmentcompse.model.Category
import com.navent.entertainmentcompse.ui.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {

    var isLoading = mutableStateOf(false)

    private var _gameCategories = MutableLiveData<List<Category>>()
    val gameCategories: LiveData<List<Category>> = _gameCategories

    private val _difficulty = MutableLiveData<String?>()
    val difficulty: LiveData<String?> = _difficulty

    private val _type = MutableLiveData<String?>()
    val type: LiveData<String?> = _type

    private var _selectedCategoryId = MutableLiveData<Category?>(null)
    val selectedCategory: LiveData<Category?> = _selectedCategoryId

    fun setSelectedDifficulty(selectedDifficulty: String) {
        _difficulty.value = selectedDifficulty
    }

    fun setSelectedType(selectedType: String) {
        _type.value = selectedType
    }

    fun startGame(): Boolean {
        val categorySelected = _selectedCategoryId.value != null
        val difficultySelected = !_difficulty.value.isNullOrBlank()

        return categorySelected && difficultySelected
    }

    fun getDataCategories() {
        viewModelScope.launch {
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

    fun setSelectedCategory(category: Category) {
        _selectedCategoryId.value = category
    }

    fun getSelectedCategoryName(): String {
        return _selectedCategoryId.value?.name ?: "Sin nombre"
    }

}