
package com.ikmr.banbara23.yaeyama_liner_checker.front.status.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.core.Base;
import com.ikmr.banbara23.yaeyama_liner_checker.model.Liner;

/**
 * 一覧アダプター
 */
public class StatusListAdapter extends ArrayAdapter<Liner> {

    private final LayoutInflater mInflater;

    public StatusListAdapter() {
        super(Base.getContext(), 0);
        mInflater = LayoutInflater.from(Base.getContext());
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        if (view == null) {
            view = mInflater.inflate(R.layout.fragment_liner_list_view, parent, false);
        }
        ImageView statusIconImage = (ImageView) view.findViewById(R.id.view_status_list_status_icon_image);
        TextView portText = (TextView) view.findViewById(R.id.view_status_list_port_text);
        TextView descriptionText = (TextView) view.findViewById(R.id.view_status_list_description_text);

        Liner liner = getItem(position);
        // 港
        portText.setText(liner.getPort().getPort());
        // ステータス
        int imageResource = 0;
        switch (liner.getStatus()) {
            case NORMAL:
                imageResource = R.drawable.ic_status_normal;
                break;
            case CANCEL:
                imageResource = R.drawable.ic_status_cancel;
                break;
            case CAUTION:
                imageResource = R.drawable.ic_status_cation;
                break;
            case SUSPEND:
                imageResource = R.drawable.ic_status_cancel;
        }
        statusIconImage.setImageResource(imageResource);
        descriptionText.setText(liner.getText());
        return view;
    }
}
