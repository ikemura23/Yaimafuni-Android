package com.ikmr.banbara23.yaeyama_liner_checker.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ikmr.banbara23.yaeyama_liner_checker.model.top.TopPort
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
abstract class DashBoardViewModel : ViewModel() {
    abstract val uiState: LiveData<TopPort>
}
