package com.ikmr.banbara23.yaeyama_liner_checker.common;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.TimeTableRowBinding;
import com.ikmr.banbara23.yaeyama_liner_checker.model.time_table.Row;

import java.util.List;

public class BindAdapter {
    @BindingAdapter("srcCompat")
    public static void srcCompat(ImageView view, Drawable drawable) {
        view.setImageDrawable(drawable);
    }

    @BindingAdapter({"inflateData"})
    public static void inflateData(LinearLayout layout, List<Row> data) {
        LayoutInflater inflater = LayoutInflater.from(layout.getContext());
        for (Row row : data) {
            TimeTableRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.time_table_row, layout, true);
            binding.setRow(row);
        }
    }
}
