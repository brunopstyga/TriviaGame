app Trivia Game

Trivia Game is an Android application developed using Kotlin and Jetpack Compose. It allows users to play a customizable trivia game with multiple-choice questions, including correct and incorrect answers. Users can select category, difficulty, type, and number of questions. At the end of the game, they can review their performance and start a new round.

Main Features

- Trivia game with multiple categories  
- Custom configuration: category, difficulty, question type, and number of questions  
- Displays questions with multiple-choice answers and validates user selection  
- Option to restart the game after completion  
- Dynamic data fetching from a public API (`OpenTDB`)  
- MVVM architecture with reactive state management using `StateFlow`  
- Modern and fully declarative UI built with `Jetpack Compose`  
- Dependency injection using `Hilt`  
- Unit testing support with `JUnit`, `MockK`, `Robolectric`, and `coroutines-test`

  Test Classes

- `TriviaQuestionViewModelTest`: tests question loading from the API and UI state  
- `GameViewModelTest`: validates game setup logic, configuration, and category loading  

### How to Run Tests

**From Android Studio:**
- Right-click on the test package and select **Run Tests**

Running Tests

- `TriviaQuestionViewModelTest`: prueba la carga de preguntas desde la API y el estado de la UI.
- `GameViewModelTest`: valida la lógica para iniciar el juego, configurar preguntas y cargar categorías.

From command line:
```bash
./gradlew testDebugUnitTest

Tecnologías de test usadas:

JUnit

MockK

kotlinx-coroutines-test

Robolectric

Tecnologías utilizadas:

Kotlin

Jetpack Compose

Retrofit + Gson + OkHttp

Kotlin Coroutines + StateFlow

Hilt (Dagger)

Timber

HTML decode util (Html.fromHtml)

Arquitectura MVVM

Dependencias destacadas:

Jetpack Compose

Hilt para DI

Retrofit, Gson, OkHttp

MockK, Mockito, Robolectric, JUnit

Coroutines Test, StateFlow, LiveData, Timber

Data is obtained from the OpenTDB public API:

Configuration:
https://opentdb.com/api_config.php

Example question:
https://opentdb.com/api.php?amount=10&category=11&difficulty=medium&type=multiple

Screens Structure
GameScreen

Allows users to select category, difficulty, question type, and number of questions. Includes the "Play" button.

TriviaQuestionScreen

Displays questions one by one. Users can answer and see if their answer is correct or incorrect. At the end, they can restart the game.

Architecture
MVVM (Model-View-ViewModel)

UI 100% declarative with Jetpack Compose.

State handled with StateFlow, compatible with collectAsState() in Compose.

Using functional operators (map, combine, filter) to transform state.

Contact
Phone number: +54 9 341 586-3212

Email: bpstyga@gmail.com

LinkedIn: Bruno Pstyga

 GitHub: @brunopstyga

 Documentation API: OpenTDB

