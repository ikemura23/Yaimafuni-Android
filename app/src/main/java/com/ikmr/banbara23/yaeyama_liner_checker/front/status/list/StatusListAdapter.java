
package com.ikmr.banbara23.yaeyama_liner_checker.front.status.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
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
        ImageView statusIconImage = (ImageView) view.findViewById(R.id.view_status_list_status_icon_image);
        TextView portText = (TextView) view.findViewById(R.id.view_status_list_port_text);
        TextView descriptionText = (TextView) view.findViewById(R.id.view_status_list_description_text);

        PortStatus portStatus = getItem(position);
        if (portStatus != null) {

            portText.setText(portStatus.getPortName());
            int imageResource = 0;
            switch (portStatus.getStatus().getCode()) {
                case "normal":
                    imageResource = R.drawable.ic_status_normal;
                    break;
                case "cancel":
                    imageResource = R.drawable.ic_status_cancel;
                    break;
                case "cation":
                    imageResource = R.drawable.ic_status_cation;
                    break;
                case "suspend":
                    imageResource = R.drawable.ic_status_cancel;
            }
            statusIconImage.setImageResource(imageResource);

            if (TextUtils.isEmpty(portStatus.getComment())) {
                descriptionText.setText(portStatus.getStatus().getText());
            } else {
                descriptionText.setText(portStatus.getComment());
            }
        }
        return view;
    }
}
