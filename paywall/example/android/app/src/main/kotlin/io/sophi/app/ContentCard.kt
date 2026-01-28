package io.sophi.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.sophi.app.models.ContentData
import io.sophi.app.ui.theme.AppTheme

@Composable
fun ContentCard(content: ContentData, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .background(Color(1f, 1f, 1f, 1f))
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(64.dp)
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = content.headline,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = content.plainText,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = content.byline,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(top = 6.dp)
                    .align(Alignment.End)
            )
        }
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ContentCardPreview() {
    AppTheme {
        ContentCard(
            content = ContentData(
                headline = "Sample Headline for Content Card",
                plainText = "This is a sample plain text to demonstrate the content card layout in Jetpack Compose.",
                byline = "By Author Name"
            )
        )
    }
}