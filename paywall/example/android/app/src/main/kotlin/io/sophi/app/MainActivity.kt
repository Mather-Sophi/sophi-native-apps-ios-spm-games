package io.sophi.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.sophi.app.decision.DeciderProvider
import io.sophi.app.repository.ContentRepository
import io.sophi.app.ui.theme.AppTheme
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Content(val id: String)

@Serializable
object TestSuite

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigator(innerPadding)
                }
            }
        }
    }
}

@Composable
fun Navigator(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    val contentRepository = ContentRepository()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            val contentRepository = ContentRepository()
            val data = contentRepository.getAllContent()
            HomeScreen(
                contents = data,
                onNavigateToContent = { contentId -> navController.navigate(Content(contentId)) },
                onNavigateToTestSuite = { navController.navigate(TestSuite) }
            )
        }
        composable<Content> { backStackEntry ->
            val selectedContent: Content = backStackEntry.toRoute()
            val content = contentRepository.getOneById(selectedContent.id)
            val decider = DeciderProvider.decider
            val decision = runBlocking {
                val decision = decider.decide(
                    contentId = selectedContent.id,
                    assignedGroup = "control",
                    contentProperties = null,
                    userProperties = null
                )
                return@runBlocking decision
            }
            if (content != null) {
                ContentView(content = content, decision = decision)
            }
        }
        composable<TestSuite> {
            TestSuiteView()
        }
    }
}