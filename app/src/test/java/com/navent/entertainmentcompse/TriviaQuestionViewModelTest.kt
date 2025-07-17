package com.navent.entertainmentcompse

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.navent.entertainmentcompse.Util.getOrAwaitValue
import com.navent.entertainmentcompse.data.GameRepository
import com.navent.entertainmentcompse.model.TriviaQuestion
import com.navent.entertainmentcompse.ui.Resource
import com.navent.entertainmentcompse.ui.trivia.TriviaQuestionViewModel
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*

@ExperimentalCoroutinesApi
class TriviaQuestionViewModelTest {

    /**
     * Rule to execute LiveData updates instantly and synchronously in tests.
     * This is required for LiveData assertions to work properly.
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Coroutine test dispatcher for controlling coroutine execution in unit tests
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: GameRepository
    private lateinit var viewModel: TriviaQuestionViewModel

    /**
     * Sets up the test environment before each test.
     * Replaces the main dispatcher with a test dispatcher and initializes the ViewModel with a mocked repository.
     */
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = TriviaQuestionViewModel(repository)
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
     * Then: The triviaQuestions LiveData should be updated with that list and isLoading should be false.
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
            repository.getTriviaQuestions("9", 10, "multiple", "easy")
        } returns Resource.Success(mockQuestions)

        viewModel.getDataTriviaQuestion("9", "multiple", "easy")

        // Advances coroutine scheduler to execute all pending coroutines
        testScheduler.advanceUntilIdle()

        val trivia = viewModel.triviaQuestions.getOrAwaitValue()
        assertEquals(1, trivia.size)
        assertEquals("What is H2O?", trivia[0].question)
        assertEquals(false, viewModel.isLoading.value)
    }

    /**
     * Test that getDataTriviaQuestion sets an empty list when repository returns an error.
     *
     * Given: A mocked error response from the repository.
     * When: getDataTriviaQuestion is called.
     * Then: The triviaQuestions LiveData should be an empty list and isLoading should be false.
     */
    @Test
    fun `getDataTriviaQuestion sets empty list on error`() = runTest {
        coEvery {
            repository.getTriviaQuestions(any(), any(), any(), any())
        } returns Resource.Error("error")

        viewModel.getDataTriviaQuestion("9", "multiple", "easy")

        testScheduler.advanceUntilIdle()

        val trivia = viewModel.triviaQuestions.getOrAwaitValue()
        assertEquals(0, trivia.size)
        assertEquals(false, viewModel.isLoading.value)
    }
}

