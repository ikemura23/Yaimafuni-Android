package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.Header;
import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.Row;

public class TimeTableViewModel {
    public ObservableField<Header> header = new ObservableField<>();
    public ObservableArrayList<Row> rows = new ObservableArrayList<>();

    public ObservableField<Header> getHeader() {
        return header;
    }

    public void setHeader(ObservableField<Header> header) {
        this.header = header;
    }

    public ObservableArrayList<Row> getRows() {
        return rows;
    }

    public void setRows(ObservableArrayList<Row> rows) {
        this.rows = rows;
    }
}
