package com.jdkgroup.pms.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jdkgroup.models.ProductSelectModel;
import com.jdkgroup.pms.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductSelectAdapter extends RecyclerView.Adapter<ProductSelectAdapter.ViewHolder> {

    private Activity activity;
    private List<ProductSelectModel> alCategoryModel;
    private List<Integer> alSelectCategoryModel;
    private SparseBooleanArray selectedItems;

    private OnSelectItemChange OnSelectItemChange;

    public ProductSelectAdapter(Activity activity, List<ProductSelectModel> alCategoryModel) {
        this.activity = activity;
        this.alCategoryModel = alCategoryModel;

        alSelectCategoryModel = new ArrayList<>();
        selectedItems = new SparseBooleanArray();

        for (ProductSelectModel categoryModel : alCategoryModel) {
            if (categoryModel.isSelect() == true) {
                alSelectCategoryModel.add(categoryModel.getId());
                selectedItems.put(categoryModel.getId(), true);
            }
        }
    }

    public void setClickListener(OnSelectItemChange OnSelectItemChange) {
        this.OnSelectItemChange = OnSelectItemChange;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_product_select, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ProductSelectModel CategoryModel = alCategoryModel.get(position);
        viewHolder.appTvProductName.setText(CategoryModel.getName());

        viewHolder.appIvAddToCartSelect.setImageResource(R.drawable.ic_check_box_select1);

        if (getSelectedItems().contains(CategoryModel.getId())) {
            viewHolder.appIvAddToCartSelect.setImageResource(R.drawable.ic_check_box_select);
        } else {
            viewHolder.appIvAddToCartSelect.setImageResource(R.drawable.ic_check_box_select1);
        }
    }

    @Override
    public int getItemCount() {
        return (null != alCategoryModel ? alCategoryModel.size() : 0);
    }

    public void setFilter(List<ProductSelectModel> alFilter) {
        alCategoryModel = new ArrayList<>();
        alCategoryModel.addAll(alFilter);
        notifyDataSetChanged();
    }

    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    public void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
            OnSelectItemChange.onUpdateAt(position, false);
        } else {
            selectedItems.put(position, true);
            OnSelectItemChange.onUpdateAt(position, true);
        }
        notifyItemChanged(position);
    }

    public void clearSelection() {
        List<Integer> selection = getSelectedItems();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    public void selectCategoryModel(int id) {
        boolean check = containsCategoryModelID(alSelectCategoryModel, id); //Check CategoryModel Id
        if (check == true) {
            //Remove
            Iterator<Integer> iterator = alSelectCategoryModel.iterator();
            while (iterator.hasNext()) {
                Integer CategoryModeldelete = iterator.next();
                if (CategoryModeldelete == id) {
                    iterator.remove();
                }
            }
        } else {
            //Add
            alSelectCategoryModel.add(id);
        }
    }

    public boolean containsCategoryModelID(List<Integer> listCategoryModel, int id) {
        for (Integer categoryId : listCategoryModel) {
            if (categoryId != null && categoryId == id) {
                return true;
            }
        }
        return false;
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.appTvCompanyName)
        AppCompatTextView appTvCompanyName;
        @BindView(R.id.appTvDetails)
        AppCompatTextView appTvDetails;
        @BindView(R.id.appTvProductName)
        AppCompatTextView appTvProductName;
        @BindView(R.id.appTvPrice)
        AppCompatTextView appTvPrice;
        @BindView(R.id.appTvProductId)
        AppCompatTextView appTvProductId;
        @BindView (R.id.appllAddToCartSelect)
        LinearLayout appllAddToCartSelect;
        @BindView (R.id.appIvAddToCartSelect)
        AppCompatImageView appIvAddToCartSelect;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            appllAddToCartSelect.setOnClickListener(this);
            appTvDetails.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.appllAddToCartSelect:
                    ProductSelectModel CategoryModel = alCategoryModel.get(getAdapterPosition());
                    toggleSelection(CategoryModel.getId());
                    selectCategoryModel(CategoryModel.getId());

                    if (getSelectedItems().contains(alCategoryModel.get(getAdapterPosition()).getId())) {
                        appIvAddToCartSelect.setImageResource(R.drawable.ic_check_box_select);
                    } else {
                        appIvAddToCartSelect.setImageResource(R.drawable.ic_check_box_select1);
                    }
                    break;

                case R.id.appTvDetails:
                    OnSelectItemChange.OnDetailsAt(view, getAdapterPosition());
                    break;
            }
        }
    }

    public interface OnSelectItemChange {
        void onUpdateAt(int position, boolean flagSelect);
        void OnDetailsAt(View view, int position);
    }
}
