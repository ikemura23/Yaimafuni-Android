package com.yaeyama.linerchecker.ui.portstatusdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yaeyama.linerchecker.ui.portstatusdetail.component.PortMainStatus
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme
import com.yaeyama_liner_checker.domain.statusdetail.Status

/**
 * どこからも使われていない？
 */
@Composable
fun PortStatusDetailScreen(
    portName: String,
    status: Status,
    statusDescription: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column {
            // PortMainStatus()
            PortMainStatus(
                portName = portName,
                status = status,
                statusDescription = statusDescription,
            )
        }
    }
}

@Preview
@Composable
private fun PortStatusDetailScreenPreview() {
    YaimafuniAndroidTheme {
        PortStatusDetailScreen(
            portName = "portName",
            status = Status(code = "normal", text = "text"),
            statusDescription = "statusDescription",
        )
    }
}
