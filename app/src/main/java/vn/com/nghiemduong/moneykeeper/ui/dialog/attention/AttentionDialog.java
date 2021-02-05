package vn.com.nghiemduong.moneykeeper.ui.dialog.attention;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import vn.com.nghiemduong.moneykeeper.R;

/**
 * Dialog hiển thị các chú ý
 * - @created_by nxduong on 2/2/2021
 **/
public class AttentionDialog extends Dialog implements View.OnClickListener {

    public final static int ATTENTION_DELETE_ACCOUNT = 657;
    public final static int ATTENTION_DELETE_DATA = 658;

    private TextView tvContentAttention;
    private TextView tvNoAttention, tvYesAttention;
    private IOnClickAttentionDialog mIOnClickDialogAttention;
    private int mKeyAttention;

    public AttentionDialog(@NonNull Context context,
                           IOnClickAttentionDialog onClickDialogAttention, int keyAttention) {
        super(context);

        setContentView(R.layout.dialog_attention);
        this.mIOnClickDialogAttention = onClickDialogAttention;
        this.mKeyAttention = keyAttention;
        init();
        setContentTextView();
    }

    /**
     * - Hàm setText cho tvContentAttention phù hợp với từng keyAttention được gửi tới
     * - @created_by nxduong on 2/2/2021
     **/

    private void setContentTextView() {
        switch (mKeyAttention) {
            case ATTENTION_DELETE_ACCOUNT:
                String message = getContext().getString(R.string.attention_delete_account_1)
                        + "<font color='red'>" + getContext().getString(R.string.attention_delete_account_2)
                        + "<b>" + getContext().getString(R.string.attention_delete_account_3) + "</b></font>"
                        + getContext().getString(R.string.attention_delete_account_4);
                tvContentAttention.setText(Html.fromHtml(message));
                break;

            case ATTENTION_DELETE_DATA:
                tvContentAttention.setText(getContext().getResources()
                        .getString(R.string.attention_delete_data));
                break;
        }
    }

    // Khởi tạo / Ánh xạ view
    private void init() {
        tvContentAttention = findViewById(R.id.tvContentAttention);

        tvNoAttention = findViewById(R.id.tvNoAttention);
        tvNoAttention.setOnClickListener(this);

        tvYesAttention = findViewById(R.id.tvYesAttention);
        tvYesAttention.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNoAttention:
                dismiss();
                break;

            case R.id.tvYesAttention:
                mIOnClickDialogAttention.onClickYesDelete();
                dismiss();
                break;
        }
    }

    public interface IOnClickAttentionDialog {
        void onClickYesDelete();
    }
}
