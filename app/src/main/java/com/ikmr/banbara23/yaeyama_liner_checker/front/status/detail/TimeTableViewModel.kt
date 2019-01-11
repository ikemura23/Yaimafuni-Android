package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField

import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.Header
import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.Row

class TimeTableViewModel {
    var header = ObservableField<Header>()
    var rows = ObservableArrayList<Row>()
    var canShow = ObservableBoolean()
}
