package io.sophi.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.sophi.app.models.ContentData
import io.sophi.app.ui.theme.AppTheme


@Composable
fun HomeScreen(
    contents: List<ContentData>,
    onNavigateToContent: (String) -> Unit,
    onNavigateToTestSuite: () -> Unit
) {

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Button( modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally)
                .padding(innerPadding),
                onClick = { onNavigateToTestSuite() }) {
                Text(text = "Run Test Suite")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.dp)
                    .background(MaterialTheme.colorScheme.onPrimary)
            )
            LazyColumn {
                contents.forEach { content ->
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                                .clickable { onNavigateToContent(content.id) },
                        ) {
                            ContentCard(content = content)
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val data = listOf(
        ContentData(
            headline = "Sample Headline",
            plainText = "This is a sample plain text for preview purposes.",
            byline = "By Author"
        ),
        ContentData(
            headline = "Sample Headline",
            plainText = "This is a sample plain text for preview purposes.",
            byline = "By Author"
        )
    )
    AppTheme {
        HomeScreen(data,
            onNavigateToContent = {},
            ) {}
    }
}