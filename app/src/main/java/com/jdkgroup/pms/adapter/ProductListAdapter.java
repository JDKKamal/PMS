package com.jdkgroup.pms.adapter;

/**
 * Created by kamlesh on 8/15/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.jdkgroup.models.CategoryModel;
import com.jdkgroup.pms.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<CategoryModel> alCategory;
    private ItemListener listener;

    public ProductListAdapter(List<CategoryModel> alCategory, ItemListener listener) {
        this.alCategory = alCategory;
        this.listener = listener;
    }

    public void setListener(ItemListener listener) {
        listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_product_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(alCategory.get(position));

        holder.appCbCompany.setOnCheckedChangeListener(null);
        holder.appCbCompany.setChecked(alCategory.get(position).isSelect());


    }

    @Override
    public int getItemCount() {
        return alCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.appCbCompany)
        CheckBox appCbCompany;

        public CategoryModel item;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            appCbCompany.setOnClickListener(this);
        }

        public void setData(CategoryModel item) {
            this.item = item;
            appCbCompany.setText(item.getName());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.appCbCompany:
                    if (appCbCompany.isChecked() == true) {
                        listener.onUpdateAt(view, getAdapterPosition(), alCategory.get(getAdapterPosition()), true);
                    } else {
                        listener.onUpdateAt(view, getAdapterPosition(), alCategory.get(getAdapterPosition()), false);
                    }
                    break;
            }
        }
    }

    public interface ItemListener {
        void onUpdateAt(View view, int position, CategoryModel categoryModel, boolean isSelect);
    }
}