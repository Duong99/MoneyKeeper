package vn.com.nghiemduong.moneykeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.com.nghiemduong.moneykeeper.R;
import vn.com.nghiemduong.moneykeeper.data.model.Contact;
import vn.com.nghiemduong.moneykeeper.utils.AppUtils;

/**
 * - @created_by nxduong on 31/1/2021
 **/
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<Contact> mListContacts;
    private ArrayList<Contact> mListContactsFiltered;
    private IOnClickContact mOnClickContact;
    private CustomFilter mCustomFilter;

    public ContactAdapter(Context mContext, ArrayList<Contact> mListContacts,
                          IOnClickContact onClickContact) {
        this.mContext = mContext;
        this.mListContacts = mListContacts;
        this.mListContactsFiltered = mListContacts;
        this.mOnClickContact = onClickContact;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_rcv_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = mListContacts.get(position);
        if (contact != null) {
            holder.tvContactName.setText(contact.getContactName());
            holder.tvDefaultContactImage.setText(contact.getContactName().substring(0, 1));

            // Set màu cho ảnh contact
            Random r = new Random();
            int n = r.nextInt(5);
            switch (n) {
                case 0:
                    holder.tvDefaultContactImage.setBackgroundColor(mContext.getResources()
                            .getColor(R.color.contact_color_blue));
                    break;
                case 1:
                    holder.tvDefaultContactImage.setBackgroundColor(mContext.getResources()
                            .getColor(R.color.contact_color_violet));
                    break;
                case 2:
                    holder.tvDefaultContactImage.setBackgroundColor(mContext.getResources()
                            .getColor(R.color.contact_color_pink));
                    break;
                case 3:
                    holder.tvDefaultContactImage.setBackgroundColor(mContext.getResources()
                            .getColor(R.color.contact_color_orange));
                    break;
                case 4:
                    holder.tvDefaultContactImage.setBackgroundColor(mContext.getResources()
                            .getColor(R.color.contact_color_yellow));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListContacts.size();
    }

    @Override
    public Filter getFilter() {
        if (mCustomFilter == null) {
            mCustomFilter = new CustomFilter();
        }
        return mCustomFilter;
    }

    /**
     * -  Lớp bộ lọc contact, tìm kiếm contact
     * - @created_by nxduong on
     **/
    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<Contact> filters = new ArrayList<>();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                for (int i = 0; i < mListContactsFiltered.size(); i++) {
                    // kiểm tra tên hoặc số điện thoại trùng
                    if (mListContactsFiltered.get(i).getContactName().toUpperCase().contains(constraint)
                            || mListContactsFiltered.get(i).getContactPhone().toUpperCase().contains(constraint)) {
                        Contact contact = new Contact(mListContactsFiltered.get(i).getContactId(),
                                mListContactsFiltered.get(i).getContactName(),
                                mListContactsFiltered.get(i).getContactPhone());
                        filters.add(contact);
                    }
                }
            }
            results.values = filters;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mListContacts = (ArrayList<Contact>) results.values;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDefaultContactImage, tvContactName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDefaultContactImage = itemView.findViewById(R.id.tvDefaultContactImage);
            tvContactName = itemView.findViewById(R.id.tvContactName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnClickContact.onClickContact(mListContacts.get(position));
                        }
                    } catch (Exception e) {
                        AppUtils.handlerException(e);
                    }
                }
            });
        }
    }

    public interface IOnClickContact {
        void onClickContact(Contact contact);
    }
}
