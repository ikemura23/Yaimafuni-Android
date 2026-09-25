package com.yaeyama.linerchecker.ui.portstatusdetail.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yaeyama.linerchecker.R
import com.yaeyama.linerchecker.domain.statusdetail.Company
import com.yaeyama.linerchecker.domain.statusdetail.RouteOperators
import com.yaeyama.linerchecker.ui.common.compose.BackNavigationTopAppBar
import com.yaeyama.linerchecker.ui.main.compose.MainScaffold
import com.yaeyama.linerchecker.ui.portstatusdetail.PortStatusDetailScreen
import com.yaeyama.linerchecker.ui.portstatusdetail.PortStatusDetailViewModel
import com.yaeyama.linerchecker.ui.theme.AppBackgroundColor

/**
 * PortStatusDetailActivity用のCompose実装
 * TabLayoutをComposeのTabRowに置き換え、ViewPager2も不要にした統合版
 */
@Composable
fun PortStatusDetailScreen(
    portCode: String,
    portName: String,
    viewModel: PortStatusDetailViewModel,
    onBackPressed: () -> Unit,
) {
    // タブの状態管理（画面回転などの構成変更後も選択中のタブを保持する）
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    // 航路を運航している会社ごとにタブを表示する
    val companies = remember(portCode) { RouteOperators.companiesOf(portCode) }
    val selectedCompany = companies.getOrElse(selectedTabIndex) { companies.first() }

    MainScaffold(
        topBar = {
            BackNavigationTopAppBar(
                title = portName,
                onBackPressed = onBackPressed,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            // Composeタブ
            TabRow(
                selectedTabIndex = selectedTabIndex,
                contentColor = colorResource(id = R.color.colorPrimary),
                containerColor = AppBackgroundColor,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        color = colorResource(id = R.color.orange3),
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    )
                },
                modifier = Modifier.padding(0.dp),
            ) {
                companies.forEachIndexed { index, company ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = stringResource(company.tabTitleRes),
                                color = Color.White,
                            )
                        },
                    )
                }
            }

            // 選択されたタブの内容を表示
            PortStatusDetailTabContent(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                portCode = portCode,
                company = selectedCompany,
                viewModel = viewModel,
            )
        }
    }
}

@Composable
private fun PortStatusDetailTabContent(
    modifier: Modifier = Modifier,
    portCode: String,
    company: Company,
    viewModel: PortStatusDetailViewModel,
) {
    PortStatusDetailScreen(
        modifier = modifier,
        company = company,
        portCode = portCode,
        viewModel = viewModel,
    )
}

/** タブに表示する会社名 */
private val Company.tabTitleRes: Int
    get() = when (this) {
        Company.ANEI -> R.string.tab_annei
        Company.YKF -> R.string.tab_ykf
    }
