app Trivia Game

Trivia Game es una aplicación Android desarrollada en Kotlin y Jetpack Compose que permite a los usuarios jugar un juego de preguntas personalizables, con respuestas correctas e incorrectas. Los usuarios pueden seleccionar la categoría, dificultad, tipo y cantidad de preguntas. Al finalizar, pueden revisar su desempeño e iniciar una nueva partida.

Características principales

Juego de preguntas tipo trivia con múltiples categorías.
- Configuración personalizada: categoría, dificultad, tipo y número de preguntas.
- Muestra preguntas con opciones y valida la respuesta seleccionada.
- Opción de reiniciar el juego una vez finalizado.
- Obtención dinámica de datos desde una API pública (`OpenTDB`).
- Arquitectura basada en `MVVM` con `StateFlow` para manejo de estado reactivo.
- Interfaz moderna y 100% declarativa con `Jetpack Compose`.
- Inyección de dependencias usando `Hilt`.
- Soporte de test unitarios con `JUnit`, `MockK`, `Robolectric`, `coroutines-test`.

Cómo correr los tests

- `TriviaQuestionViewModelTest`: prueba la carga de preguntas desde la API y el estado de la UI.
- `GameViewModelTest`: valida la lógica para iniciar el juego, configurar preguntas y cargar categorías.

Para ejecutar los tests:

Desde Android Studio:
- Haz clic derecho sobre el paquete de pruebas y selecciona **Run Tests**.

Desde línea de comandos:
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

Los datos se obtienen de la API pública de OpenTDB:

Configuración:
https://opentdb.com/api_config.php

Preguntas (ejemplo):
https://opentdb.com/api.php?amount=10&category=11&difficulty=medium&type=multiple

Estructura de pantallas
GameScreen
Permite seleccionar categoría, dificultad, tipo de pregunta y cantidad. Contiene el botón "Jugar".

TriviaQuestionScreen
Muestra las preguntas una por una. Permite responder y muestra si la respuesta fue correcta o incorrecta. Al finalizar, se puede reiniciar.

Arquitectura y patrón de diseño
MVVM (Model-View-ViewModel)

UI 100% declarativa con Jetpack Compose.

Estado manejado con StateFlow, compatible con collectAsState() en Compose.

Uso de operadores funcionales (map, combine, filter) para transformar estado.

Contacto
Celular: +54 9 341 586-3212

Email: bpstyga@gmail.com

LinkedIn: Bruno Pstyga

 GitHub: @brunopstyga

 Documentación API: OpenTDB

