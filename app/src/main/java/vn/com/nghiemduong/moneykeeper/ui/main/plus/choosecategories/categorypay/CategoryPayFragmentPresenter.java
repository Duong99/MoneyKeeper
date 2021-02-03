package vn.com.nghiemduong.moneykeeper.ui.main.plus.choosecategories.categorypay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import vn.com.nghiemduong.moneykeeper.data.model.Category;
import vn.com.nghiemduong.moneykeeper.data.model.SubCategory;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 28/1/2021
 **/
public class CategoryPayFragmentPresenter implements CategoryPayFragmentMvpPresenter {
    public CategoryPayFragmentMvpView mCategoryPayFragmentMvpView;
    private Context mContext;

    public CategoryPayFragmentPresenter(CategoryPayFragmentMvpView categoryPayFragmentMvpView,
                                        Context context) {
        this.mCategoryPayFragmentMvpView = categoryPayFragmentMvpView;
        this.mContext = context;
    }

    /**
     * Hàm đọc danh sách hạng mục chi từ trong file json rồi trả về sang view
     *
     * @return categories
     * @created_by nxduong on 28/1/2021
     */
    @Override
    public void getListCategoryPay() {
        ArrayList<Category> categories = new ArrayList<>();
        String jsonFileString = getAssetJsonData(mContext);

        try {
            String imageFile, title, subImageFile, subTitle;
            JSONArray jsonArray = new JSONArray(jsonFileString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = new JSONObject(jsonArray.get(i).toString());
                imageFile = object.getString("Image");

                title = object.getString("Title");

                ArrayList<SubCategory> subCategories = new ArrayList<>();

                JSONArray subArray = new JSONArray(object.getJSONArray("Sub").toString());
                for (int j = 0; j < subArray.length(); j++) {
                    JSONObject objectSub = new JSONObject(subArray.get(j).toString());
                    subImageFile = objectSub.getString("SubPicture");

                    subTitle = objectSub.getString("SubTitle");

                    subCategories.add(new SubCategory(subImageFile, subTitle));
                }
                categories.add(new Category(imageFile, title, subCategories));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mCategoryPayFragmentMvpView.resultCategoryPay(categories);
    }

    // Hàm đọc file json từ assets @return json
    private String getAssetJsonData(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("CategoryJson.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
