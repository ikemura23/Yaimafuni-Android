
package com.ikmr.banbara23.yaeyama_liner_checker.front.status.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;

/**
 * 詳細トップのカスタムビュー
 */
public class StatusDetailTopView extends FrameLayout {

    // Bind View ---------------------------------------
    ImageView mViewStatusDetailTopImage;

    TextView mStatusText;

    TextView mUpdateText;

    TextView mCommentText;

    public StatusDetailTopView(Context context) {
        super(context);
    }

    public StatusDetailTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_status_detail_top, this);
    }

    /**
     * ステータスの設定
     *
     * @param liner 運航情報
     */
//    public void bindStatus(Liner liner) {
//        if (liner == null) {
//            return;
//        }
//        setStatusIcon(liner.getStatus());
//        setStatusText(liner.getText());
//    }

//    /**
//     * 運航状況
//     *
//     * @param status
//     */
//    private void setStatusIcon(Status status) {
//        if (status == null) {
//            return;
//        }
//        int imageResource = 0;
//        switch (status) {
//            case NORMAL:
//                imageResource = R.drawable.ic_status_normal;
//                break;
//            case CANCEL:
//                imageResource = R.drawable.ic_status_cancel;
//                break;
//            case CAUTION:
//                imageResource = R.drawable.ic_status_cation;
//                break;
//            case SUSPEND:
//                imageResource = R.drawable.ic_status_cancel;
//        }
//        mViewStatusDetailTopImage.setImageResource(imageResource);
//    }
//
//    private void setStatusText(String value) {
//        if (value == null) {
//            return;
//        }
//        mStatusText.setText(StringUtils.trimAll(value));
//    }

    /**
     * 更新日時
     *
     * @param update
     */
    public void setUpdateText(String update) {
        if (update == null) {
            mUpdateText.setVisibility(GONE);
            return;
        }
        mUpdateText.setText(update.trim());
    }

    /**
     * コメント
     *
     * @param comment
     */
    public void setCommentText(String comment) {
        if (comment == null) {
            mCommentText.setVisibility(GONE);
            return;
        }
        mCommentText.setVisibility(VISIBLE);
        mCommentText.setText(comment.trim());
    }
}
