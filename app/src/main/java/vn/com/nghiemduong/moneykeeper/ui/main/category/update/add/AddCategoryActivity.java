package vn.com.nghiemduong.moneykeeper.ui.main.category.update.add;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.ui.base.BaseActivity;
import vn.com.nghiemduong.moneykeeper.ui.main.category.icon.IconCategoryActivity;

/**
 * -
 * Màn hình cho phép thêm, sửa, xóa hạng mục (category)
 * <p>
 * - @created_by nxduong on 7/2/2021
 **/

public class AddCategoryActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivIconCategoryAdd, ivIconCategoryParentAdd;
    private EditText etNameCategoryAdd, etExplainCategoryAdd;
    private LinearLayout llDeleteCategory, llSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        init();
    }

    /**
     * Khởi tạo ánh xạ view
     *
     * @created_by nxduong on 7/2/2021
     */
    private void init() {
        ivIconCategoryAdd = findViewById(R.id.ivIconCategoryAdd);
        ivIconCategoryAdd.setOnClickListener(this);

        ivIconCategoryParentAdd = findViewById(R.id.ivIconCategoryParentAdd);
        ivIconCategoryParentAdd.setOnClickListener(this);

        llDeleteCategory = findViewById(R.id.llDelete);
        llDeleteCategory.setOnClickListener(this);

        llSave = findViewById(R.id.llSave);
        llSave.setOnClickListener(this);

        etNameCategoryAdd = findViewById(R.id.etNameCategoryAdd);
        etExplainCategoryAdd = findViewById(R.id.etExplain);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivIconCategoryAdd:
                Intent intent = new Intent(this, IconCategoryActivity.class);
                startActivity(intent);
                break;

            case R.id.ivIconCategoryParentAdd:
                Intent intentParent = new Intent(this, IconCategoryActivity.class);
                startActivity(intentParent);
                break;

            case R.id.llDelete:
                break;

            case R.id.llSave:

                break;
        }
    }
}