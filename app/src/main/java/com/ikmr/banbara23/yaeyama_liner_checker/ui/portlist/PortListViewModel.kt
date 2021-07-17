package com.ikmr.banbara23.yaeyama_liner_checker.ui.portlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.ikemura.shared.model.statusdetail.PortStatus

class PortListViewModel : ViewModel() {
    val items = MutableLiveData<List<PortStatus>>()
}
