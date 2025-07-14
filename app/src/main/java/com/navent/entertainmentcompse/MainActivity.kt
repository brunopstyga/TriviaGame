    package com.navent.entertainmentcompse

    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.enableEdgeToEdge
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Scaffold
    import androidx.compose.ui.Modifier
    import androidx.navigation.NavHostController
    import androidx.navigation.compose.rememberNavController
    import com.navent.entertainmentcompse.navigation.NavigationGraph
    import com.navent.entertainmentcompse.ui.theme.EntertainmentCompseTheme
    import dagger.hilt.android.AndroidEntryPoint

    @AndroidEntryPoint
    class MainActivity : ComponentActivity() {

        private lateinit var navController: NavHostController

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContent {
                EntertainmentCompseTheme {
                    Scaffold { padding ->
                        navController = rememberNavController()
                        NavigationGraph(
                            modifier = Modifier.padding(padding),
                            navController = navController
                        )
                    }
                }
            }
        }
    }

