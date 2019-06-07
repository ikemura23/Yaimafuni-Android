package com.ikmr.banbara23.yaeyama_liner_checker.ui.portlist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus

class PortListViewModel : ViewModel() {
    val items = MutableLiveData<List<PortStatus>>()
}
