package com.ikmr.banbara23.yaeyama_liner_checker.front.status.list;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.common.StatusHelper;
import com.ikmr.banbara23.yaeyama_liner_checker.model.PortStatus;

/**
 * 一覧アダプター
 */
public class StatusListAdapter extends ArrayAdapter<PortStatus> {

    private final LayoutInflater mInflater;

    public StatusListAdapter(Context context) {
        super(context, 0);
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        if (view == null) {
            view = mInflater.inflate(R.layout.fragment_liner_list_view, parent, false);
        }
        TextView portText = (TextView) view.findViewById(R.id.view_status_list_port_text);
        TextView descriptionText = (TextView) view.findViewById(R.id.view_status_list_description_text);
        TextView statusText = (TextView) view.findViewById(R.id.view_status_list_status);

        PortStatus portStatus = getItem(position);
        // ステータス文字
        portText.setText(portStatus.getPortName());
        statusText.setText(portStatus.getStatus().getText());
        // 背景色
        statusText.setBackgroundColor(StatusHelper.INSTANCE.getStatusColor(portStatus.getStatus().getCode()));
        // コメント
        if (TextUtils.isEmpty(portStatus.getComment())) {
            descriptionText.setVisibility(View.GONE);
        }
        descriptionText.setText(portStatus.getComment());
        return view;
    }
}
