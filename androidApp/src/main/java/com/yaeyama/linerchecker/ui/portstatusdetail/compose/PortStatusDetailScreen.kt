package com.yaeyama.linerchecker.ui.portstatusdetail.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.yaeyama.linerchecker.ui.common.compose.BackNavigationTopAppBar
import com.yaeyama.linerchecker.ui.portstatusdetail.PortStatusDetailScreen
import com.yaeyama.linerchecker.ui.portstatusdetail.PortStatusDetailViewModel
import com.yaeyama_liner_checker.domain.statusdetail.Company

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
    // タブの状態管理
    var selectedTabIndex by remember { mutableStateOf(0) }
    
    // タブタイトルを定義 (波照間港は安栄観光のみなので1つ、他は2つ)
    val tabTitles = if (portCode == "hateruma") {
        listOf(R.string.tab_annei)
    } else {
        listOf(R.string.tab_annei, R.string.tab_ykf)
    }
    
    // 選択されたタブに応じてCompanyを取得
    val selectedCompany = when (selectedTabIndex) {
        0 -> Company.ANEI
        1 -> Company.YKF
        else -> Company.ANEI
    }
    
    Scaffold(
        topBar = {
            BackNavigationTopAppBar(
                title = portName,
                onBackPressed = onBackPressed
            )
        },
        backgroundColor = Color.Transparent,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Composeタブ
            TabRow(
                selectedTabIndex = selectedTabIndex,
                backgroundColor = colorResource(id = R.color.colorPrimary),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        color = colorResource(id = R.color.orange3),
                        modifier = Modifier.padding(0.dp)
                    )
                },
                modifier = Modifier.padding(0.dp)
            ) {
                tabTitles.forEachIndexed { index, titleRes ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = stringResource(titleRes),
                                color = Color.White
                            )
                        }
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
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun PortStatusDetailTabContent(
    modifier: Modifier = Modifier,
    portCode: String,
    company: Company,
    viewModel: PortStatusDetailViewModel
) {
    PortStatusDetailScreen(
        modifier = modifier,
        company = company,
        portCode = portCode,
        viewModel = viewModel
    )
}