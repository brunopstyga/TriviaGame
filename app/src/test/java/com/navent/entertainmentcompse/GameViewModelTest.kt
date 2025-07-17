package com.navent.entertainmentcompse

import android.os.Looper
import com.navent.entertainmentcompse.data.GameRepository
import com.navent.entertainmentcompse.model.Category
import com.navent.entertainmentcompse.model.CategoryTrivia
import com.navent.entertainmentcompse.model.TriviaQuestion
import com.navent.entertainmentcompse.ui.Resource
import com.navent.entertainmentcompse.ui.select.viewmodel.GameViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class GameViewModelTest {

    private lateinit var viewModel: GameViewModel
    private lateinit var gameRepository: GameRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        gameRepository = mockk(relaxed = true)
        viewModel = GameViewModel(gameRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Test that startGame returns false if the category is not selected.
     *
     * The game requires a selected category, difficulty, and question amount > 0 to start.
     * This test omits the category to ensure validation fails correctly.
     */
    @Test
    fun `startGame returns false when category is not selected`() {
        viewModel.setSelectedDifficulty("easy")
        viewModel.setSelectedAmount(10)

        val result = viewModel.startGame()
        assertFalse(result)
    }

    /**
     * Test that getTrivia sets the triviaQuestions LiveData correctly when repository returns data.
     *
     * Mocks a successful fetch of trivia questions from the repository.
     * Then verifies the LiveData holds the expected question list.
     */
    @Test
    fun `getTrivia sets triviaQuestions on success`() = runTest {
        val mockQuestions = listOf(
            TriviaQuestion(
                id = 1,
                category = "Science",
                type = "multiple",
                difficulty = "easy",
                question = "What is the boiling point of water?",
                createdBy = "Admin",
                correctAnswer = "100°C",
                incorrectAnswers = listOf("90°C", "110°C", "120°C")
            ),
            TriviaQuestion(
                id = 2,
                category = "History",
                type = "multiple",
                difficulty = "medium",
                question = "Who was the first president of the United States?",
                createdBy = "Admin",
                correctAnswer = "George Washington",
                incorrectAnswers = listOf("Thomas Jefferson", "John Adams", "Benjamin Franklin")
            )
        )

        coEvery { gameRepository.getData("10", 9) } returns Resource.Success(mockQuestions)

        viewModel.getTrivia(10, 9)
        testDispatcher.scheduler.advanceUntilIdle()
        shadowOf(Looper.getMainLooper()).idle()

        val state = viewModel.uiState.value
        val result = state.triviaQuestions
        assertEquals(2, result.size)
        assertEquals("What is the boiling point of water?", result[0].question)
    }

    /**
     * Test that getTrivia sets an empty list in triviaQuestions LiveData when repository fails.
     *
     * Mocks a failure from the repository and ensures ViewModel clears the question list.
     */
    @Test
    fun `getTrivia sets empty list on error`() = runTest {
        coEvery { gameRepository.getData("10", 9) } returns Resource.Error("Some error")

        viewModel.getTrivia(10, 9)
        testDispatcher.scheduler.advanceUntilIdle()
        shadowOf(Looper.getMainLooper()).idle()


        val state = viewModel.uiState.value
        val result = state.triviaQuestions
        assertTrue(result.isEmpty())
    }

    /**
     * Test that startGame returns false if the difficulty is not selected.
     *
     * The game cannot start if any required input is missing.
     * This test checks behavior when the difficulty is not set.
     */
    @Test
    fun `startGame returns false when difficulty is not selected`() {
        viewModel.setSelectedCategory(Category(9, "General Knowledge"))
        viewModel.setSelectedAmount(10)

        val result = viewModel.startGame()
        assertFalse(result)
    }

    /**
     * Test that startGame returns false if the selected amount is zero.
     *
     * The question amount must be a positive number.
     */
    @Test
    fun `startGame returns false when amount is zero`() {
        viewModel.setSelectedCategory(Category(9, "General Knowledge"))
        viewModel.setSelectedDifficulty("easy")
        viewModel.setSelectedAmount(0)

        val result = viewModel.startGame()
        assertFalse(result)
    }

    /**
     * Test that startGame returns true when all required fields are correctly set.
     *
     * This test validates the happy path where the game should be allowed to start.
     */
    @Test
    fun `startGame returns true when all fields are set correctly`() {
        viewModel.setSelectedCategory(Category(9, "General Knowledge"))
        viewModel.setSelectedDifficulty("easy")
        viewModel.setSelectedAmount(10)

        val result = viewModel.startGame()
        assertTrue(result)
    }

    /**
     * Test that getDataCategories updates gameCategories LiveData when fetch is successful.
     *
     * Mocks a valid response from the repository and verifies the ViewModel updates the list.
     */
    @Test
    fun `getDataCategories updates gameCategories on success`() = runTest {
        val mockCategoryList = listOf(Category(9, "General"), Category(10, "Books"))
        val mockCategoryTrivia = CategoryTrivia(triviaCategory = mockCategoryList)

        coEvery { gameRepository.getCategories() } returns Resource.Success(mockCategoryTrivia)

        viewModel.getDataCategories()
        testDispatcher.scheduler.advanceUntilIdle()
        shadowOf(Looper.getMainLooper()).idle()

        val state = viewModel.uiState.value
        val result = state.categories
        assertEquals(2, result.size)
        assertEquals("General", result[0].name)
    }

    /**
     * Test that getDataCategories sets an empty list in gameCategories on error.
     *
     * Ensures that if the repository fails to fetch categories,
     * the ViewModel clears any existing data and does not crash.
     */
    @Test
    fun `getDataCategories sets empty list on error`() = runTest {
        coEvery { gameRepository.getCategories() } returns Resource.Error("Failed")

        viewModel.getDataCategories()
        testDispatcher.scheduler.advanceUntilIdle()
        shadowOf(Looper.getMainLooper()).idle()

        val state = viewModel.uiState.value
        val result = state.categories
        assertTrue(result.isEmpty())
    }
}

