package io.sophi.app

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.sophi.app.test.TestSuiteManager
import io.sophi.app.test.Dimensions
import io.sophi.app.test.TestResult
import kotlinx.coroutines.launch

@Composable
fun TestSuiteView() {
    var results by remember { mutableStateOf<List<TestResult>>(emptyList()) }
    var isRunning by remember { mutableStateOf(false) }
    var totalExecutionTime by remember { mutableStateOf(0L) }
    val scope = rememberCoroutineScope()
    var dimensionsTestCase by remember { mutableStateOf<Dimensions?>(null) }

    LaunchedEffect(Unit) {
        dimensionsTestCase = Dimensions()
        TestSuiteManager.registerTest(dimensionsTestCase!!)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Test Suite", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isRunning = true
                scope.launch {
                    val startTime = System.currentTimeMillis()
                    // Get individual test results from Dimensions
                    results = dimensionsTestCase?.getIndividualResults() ?: emptyList()
                    totalExecutionTime = System.currentTimeMillis() - startTime
                    isRunning = false
                }
            },
            enabled = !isRunning,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isRunning) "Running..." else "Run All Tests")
        }

        if (results.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))

            val passedCount = results.count { it.passed }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "Test Results: $passedCount/${results.size} passed",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "Total execution time: ${totalExecutionTime}ms",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn {
                items(results) { result ->
                    TestResultCard(result)
                }
            }
        }
    }
}

@Composable
fun TestResultCard(result: TestResult) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(
            containerColor = if (result.passed) {
                Color.Green.copy(alpha = 0.1f)
            } else {
                Color.Red.copy(alpha = 0.1f)
            }
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    result.testName,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    if (result.passed) "✓ PASSED" else "✗ FAILED",
                    color = if (result.passed) Color.Green else Color.Red,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Text(
                "Execution time: ${result.executionTimeMs}ms",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            result.error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Error: $it",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red
                )
            }

            if (isExpanded && result.debugInfo != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        result.debugInfo,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(8.dp),
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Click to collapse",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else if (result.debugInfo != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Click to view debug info",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestSuiteViewPreview() {
    TestSuiteView()
}

@Preview(showBackground = true)
@Composable
fun TestResultCardPreview() {
    Column(modifier = Modifier.padding(8.dp)) {
        TestResultCard(
            result = TestResult(
                testName = "User Dimensions Test",
                passed = true,
                executionTimeMs = 245
            )
        )
        TestResultCard(
            result = TestResult(
                testName = "Device Dimensions Test",
                passed = false,
                error = "Assertion failed: expected 10, got 5",
                executionTimeMs = 180
            )
        )
    }
}

