package com.bruno.entertainmentcompse

import com.bruno.entertainmentcompse.data.GameRepository
import com.bruno.entertainmentcompse.model.GetTriviaQuestionsUseCase
import com.bruno.entertainmentcompse.model.TriviaQuestion
import com.bruno.entertainmentcompse.ui.Resource
import com.bruno.entertainmentcompse.ui.trivia.TriviaQuestionViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TriviaQuestionViewModelTest {

    // Coroutine test dispatcher for controlling coroutine execution in unit tests
    private val testDispatcher = StandardTestDispatcher()


    private lateinit var getTriviaQuestionsUseCase: GetTriviaQuestionsUseCase
    private lateinit var viewModel: TriviaQuestionViewModel

    /**
     * Sets up the test environment before each test.
     * Replaces the main dispatcher with a test dispatcher and initializes the ViewModel with a mocked repository.
     */
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getTriviaQuestionsUseCase = mockk(relaxed = true)
        viewModel = TriviaQuestionViewModel(getTriviaQuestionsUseCase)
    }

    /**
     * Cleans up after each test by resetting the main dispatcher.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Test that getDataTriviaQuestion successfully sets triviaQuestions LiveData with data from repository.
     *
     * Given: A mocked successful response from the repository containing one trivia question.
     * When: getDataTriviaQuestion is called.
     * Then: The triviaQuestions UI state (StateFlow) should be updated with that list and isLoading should be false.
     */
    @Test
    fun `getDataTriviaQuestion sets triviaQuestions on success`() = runTest {
        val mockQuestions = listOf(
            TriviaQuestion(
                id = 1,
                category = "Science",
                type = "multiple",
                difficulty = "easy",
                question = "What is H2O?",
                createdBy = "Admin",
                correctAnswer = "Water",
                incorrectAnswers = listOf("Oxygen", "Hydrogen", "Helium")
            )
        )

        coEvery {
            getTriviaQuestionsUseCase("9", 10, "multiple", "easy")
        } returns Resource.Success(mockQuestions)

        viewModel.getDataTriviaQuestion("9", "multiple", "easy")

        // Advances coroutine scheduler to execute all pending coroutines
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(1, state.triviaQuestions.size)
        assertEquals("What is H2O?", state.triviaQuestions[0].question)
        assertEquals(false, state.isLoading)
    }

    /**
     * Test that getDataTriviaQuestion sets an empty list when repository returns an error.
     *
     * Given: A mocked error response from the repository.
     * When: getDataTriviaQuestion is called.
     * Then: The triviaQuestions in the UI state (StateFlow) should be an empty list and isLoading should be false.
    */
    @Test
    fun `getDataTriviaQuestion sets empty list on error`() = runTest {
        coEvery {
            getTriviaQuestionsUseCase(any(), any(), any(), any())
        } returns Resource.Error("error")

        viewModel.getDataTriviaQuestion("9", "multiple", "easy")

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.triviaQuestions.isEmpty())
        assertEquals(false, state.isLoading)
    }
}

