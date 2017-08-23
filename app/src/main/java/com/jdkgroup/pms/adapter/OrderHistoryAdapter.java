package com.jdkgroup.pms.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jdkgroup.models.AddToCardModel;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.utils.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    private Activity activity;
    private List<AddToCardModel> alAddToCart;

    public OrderHistoryAdapter(Activity activity, List<AddToCardModel> alAddToCart) {
        this.activity = activity;
        this.alAddToCart = alAddToCart;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_order_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        final AddToCardModel addToCardModel = alAddToCart.get(position);
        holder.appTvCompanyName.setText(String.valueOf(addToCardModel.getCompanyname()));
        holder.appTvProductName.setText(String.valueOf(addToCardModel.getTitle()));
        holder.appTvPrice.setText(AppUtils.getStringFromId(activity, R.string.sy_rupee) + "" + String.format("%.2f", addToCardModel.getPrice()));
        holder.appTvTotalPrice.setText(AppUtils.getStringFromId(activity, R.string.sy_rupee) + "" + String.format("%.2f", addToCardModel.getTotalprice()));
        holder.appTvItems.setText(activity.getString(R.string.items) + " " + String.valueOf(addToCardModel.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return alAddToCart.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.appTvCompanyName)
        AppCompatTextView appTvCompanyName;
        @BindView(R.id.appTvProductName)
        AppCompatTextView appTvProductName;
        @BindView(R.id.appTvPrice)
        AppCompatTextView appTvPrice;
        @BindView(R.id.appTvDetails)
        AppCompatTextView appTvDetails;
        @BindView(R.id.appTvTotalPrice)
        AppCompatTextView appTvTotalPrice;
        @BindView(R.id.appTvOrderID)
        AppCompatTextView appTvOrderID;
        @BindView(R.id.appTvStatus)
        AppCompatTextView appTvStatus;
        @BindView(R.id.appIvImagePurchase)
        AppCompatImageView appIvImagePurchase;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.appTvItems)
        AppCompatTextView appTvItems;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
}