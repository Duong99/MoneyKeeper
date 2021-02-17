package vn.com.nghiemduong.moneykeeper.ui.dialog.attention;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import vn.com.nghiemduong.moneykeeper.R;

/**
 * -  dialog hiển thị chú ý có bao gồm báo cáo không
 * - @created_by nxduong on 17/2/2021
 **/
public class AttentionReportDialog extends Dialog implements View.OnClickListener {
    private TextView tvContentAttention;
    private IOnClickAttentionReportDialog mIOnClickDialogAttention;

    public AttentionReportDialog(@NonNull Context context, IOnClickAttentionReportDialog
            iOnClickAttentionReportDialog) {
        super(context);
        this.mIOnClickDialogAttention = iOnClickAttentionReportDialog;
        setContentView(R.layout.dialog_attention);
        init();
    }

    // Khởi tạo / Ánh xạ view
    private void init() {
        tvContentAttention = findViewById(R.id.tvContentAttention);
        tvContentAttention.setText(getContext().getResources()
                .getString(R.string.attention_report));

        TextView tvNoAttention = findViewById(R.id.tvNoAttention);
        tvNoAttention.setOnClickListener(this);

        TextView tvYesAttention = findViewById(R.id.tvYesAttention);
        tvYesAttention.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNoAttention:
                mIOnClickDialogAttention.onNoReport();
                dismiss();
                break;

            case R.id.tvYesAttention:
                mIOnClickDialogAttention.onYesReport();
                dismiss();
                break;
        }
    }

    public interface IOnClickAttentionReportDialog {
        void onNoReport();

        void onYesReport();
    }
}
