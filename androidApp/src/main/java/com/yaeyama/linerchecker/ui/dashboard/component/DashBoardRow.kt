package com.yaeyama.linerchecker.ui.dashboard.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.statusdetail.Company
import com.yaeyama.linerchecker.domain.top.Ports
import com.yaeyama.linerchecker.ui.dashboard.FakeDashBoardDataProvider
import com.yaeyama.linerchecker.ui.theme.YaimafuniAndroidTheme

@Composable
fun DashBoardRow(
    port: Ports,
    onRowClick: (Ports) -> Unit,
) {
    Box(
        modifier = Modifier
            // clickable は子要素のセマンティクスをまとめるため、スクリーンリーダーでは1行が1項目として読み上げられる
            .clickable(
                onClickLabel = stringResource(R.string.dashboard_row_click_label),
                onClick = { onRowClick(port) },
            )
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = port.anei.portName,
            modifier = Modifier.align(Alignment.CenterStart),
        )
        DashBoardRowItem(
            modifier = Modifier.align(Alignment.Center),
            companyName = Company.ANEI.fullName,
            status = port.anei.status,
        )
        DashBoardRowItem(
            modifier = Modifier.align(Alignment.CenterEnd),
            companyName = stringResource(R.string.company_ykf_short),
            // 画面上は略称のため、読み上げでは正式名称を使う
            companyAccessibilityName = Company.YKF.fullName,
            status = port.ykf.status,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DashBoardRowPreview() {
    YaimafuniAndroidTheme {
        DashBoardRow(
            FakeDashBoardDataProvider.dummyPort1,
        ) {}
    }
}
