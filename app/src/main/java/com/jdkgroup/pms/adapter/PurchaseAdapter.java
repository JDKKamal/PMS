package com.jdkgroup.pms.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jdkgroup.models.CategoryModel;
import com.jdkgroup.pms.R;
import com.jdkgroup.models.PurchaseModel;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {
    private ItemClickListener clickListener;
    private Activity activity;
    private List<PurchaseModel> alPurchase;

    public PurchaseAdapter(Activity activity, List<PurchaseModel> alPurchase) {
        this.activity = activity;
        this.alPurchase = alPurchase;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_purchase, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        PurchaseModel purchaseModel = alPurchase.get(position);

        viewHolder.appTvCompanyName.setText(String.valueOf(purchaseModel.getCouriercellno()));
    }

    @Override
    public int getItemCount() {
        return (null != alPurchase ? alPurchase.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.appTvCompanyName)
        AppCompatTextView appTvCompanyName;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            appTvCompanyName.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            if (clickListener != null) {
                if (view.getId() == R.id.appTvCompanyName) {
                    clickListener.onClick(view, getAdapterPosition());
                }
            }
        }
    }

    public void setFilter(List<PurchaseModel> alFilter) {
        alPurchase = new ArrayList<>();
        alPurchase.addAll(alFilter);
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }
}

