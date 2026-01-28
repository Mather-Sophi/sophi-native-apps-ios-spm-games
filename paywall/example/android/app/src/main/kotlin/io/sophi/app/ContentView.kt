package io.sophi.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import io.sophi.paywall.enums.WallType
import io.sophi.paywall.enums.WallVisibility
import io.sophi.paywall.models.Experiment
import io.sophi.paywall.models.Outcome
import io.sophi.paywall.models.WallDecision

@Composable
fun ContentView(content: ContentData, decision: WallDecision) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
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
            Text(
                text = content.headline,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium),
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = content.byline,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(top = 6.dp)
                    .align(Alignment.End)
            )
            Text(
                text = content.plainText,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp)
            )

        }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(Modifier.weight(1f))
        Column(
            modifier = Modifier
                .background(Color(0.6f, 0.6f, 0.6f, 0.96f))
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .height(500.dp)
        ) {
            Text(
                "Paywall Decision",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp)
            )
            Column {
                if (decision.outcome.wallType != null) {
                    Text("Wall Type: ${decision.outcome.wallType} (Code: ${decision.outcome.wallTypeCode})")
                }
                Text("Wall Visibility: ${decision.outcome.wallVisibility} (Code: ${decision.outcome.wallVisibilityCode})")
                Text("Trace: ${decision.trace}")
                Text("Context: ${decision.context}")
                Text("Inputs: ${decision.inputs}")
                Text("Experiment Code: ${decision.experimentsCode}")
                Text("Search Params: ${decision.searchParams}")
                Text("User Properties: ${decision.userProperties}")
                Text("Content Properties: ${decision.contentProperties}")
                Text("Paywall Score: ${decision.paywallScore}")

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    val data = ContentData(
        headline = "Sample Headline",
        plainText = "This is a sample plain text for preview purposes. It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).",
        byline = "By Author"
    )

    val decision = WallDecision(
        id = "97132ed6-42ae-41bc-b7e9-aa36b5b0d9de",
        createdAt = "2024-06-01T12:00:00Z",
        trace = "t004010d004999",
        context = "DA04AA02AB01AC01BA02BB01BC01AD02AE01BD02BE01BF01AF00BG00CA20CB18CC17CD03CE02CF01CG00",
        inputs = "HA:18|HB:1|FA:00|DC:01|DE:01|EA:Mac OS|EC:Edge|DD:12|DB:America/Toronto|GC:direct",
        outcome = Outcome(
            wallType = WallType.PAYWALL,
            wallVisibility = WallVisibility.ALWAYS
        ),
        searchParams = "modelTrace=d004999&experiment.id=pw%3Aarticle%3Atest1_ab&experiment.assignedGroup=variant&content.id=123&visitor.type=anon&user.sevenDayPageViewsByArticleWithPaywall=3&device.os=android",
        experimentsCode = "control"
    )
    AppTheme {
        ContentView(data, decision = decision)
    }
}