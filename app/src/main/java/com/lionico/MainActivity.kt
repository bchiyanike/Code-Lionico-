// MainActivity hosts the app's fragments and navigation
package com.lionico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.lionico.ui.theme.LionicoTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity is the entry point of the app.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionicoTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "dashboardFragment")
            }
        }
    }
}
