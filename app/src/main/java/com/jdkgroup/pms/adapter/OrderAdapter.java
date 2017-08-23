package com.jdkgroup.pms.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdkgroup.customviews.dragndrop.ItemTouchHelperAdapter;
import com.jdkgroup.customviews.dragndrop.ItemTouchHelperViewHolder;
import com.jdkgroup.customviews.dragndrop.OnStartDragListener;
import com.jdkgroup.pms.R;
import com.jdkgroup.models.AddToCardModel;
import com.jdkgroup.pms.utils.AppUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {

    private List<AddToCardModel> alAddToCart;
    private ArrayList<AddToCardModel> itemDataPendingRemoval;
    private static Context activity;
    private final OnStartDragListener mDragStartListener;

    public interface ICallback {
        void onItemPositionChange(int fromPosition, int toPosition);
    }

    private ICallback listener;

    public OrderAdapter(Activity activity, OnStartDragListener dragStartListener, List<AddToCardModel> alAddToCart, ICallback listener) {
        this.activity = activity;
        mDragStartListener = dragStartListener;
        this.alAddToCart = alAddToCart;
        this.listener = listener;

        itemDataPendingRemoval = new ArrayList<>();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_order, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        final AddToCardModel addToCardModel = alAddToCart.get(position);
        if (itemDataPendingRemoval.contains(addToCardModel)) {
        } else {
            holder.appTvCompanyName.setText(addToCardModel.getCompanyname());
            holder.appTvCompanyName.setText(String.valueOf(addToCardModel.getCompanyname()));
            holder.appTvProductName.setText(String.valueOf(addToCardModel.getTitle()));
            holder.appTvPrice.setText(AppUtils.getStringFromId(activity, R.string.sy_rupee) + "" + String.format("%.2f", addToCardModel.getPrice()));
            holder.appTvTotalPrice.setText(AppUtils.getStringFromId(activity, R.string.sy_rupee) + "" + String.format("%.2f", addToCardModel.getTotalprice()));
        }
    }

    @Override
    public int getItemCount() {
        return alAddToCart.size();
    }

    public void pendingRemoval(final int position, int swipeDir) {
        final AddToCardModel addToCardModel = alAddToCart.get(position);

        if (!itemDataPendingRemoval.contains(addToCardModel)) {
            undoPreviousPositionsIfAny(position);
            itemDataPendingRemoval.add(addToCardModel);
            notifyItemChanged(position);
        }
    }

    private void undoPreviousPositionsIfAny(int position) {
        for (int i = 0; i < alAddToCart.size(); i++) {

            if (i != position) {
                if (itemDataPendingRemoval.contains(alAddToCart.get(i))) {
                    itemDataPendingRemoval.remove(alAddToCart.get(i));
                    notifyItemChanged(i);
                }
            }
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(alAddToCart, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);

        return true;
    }

    public void remove(int position) {
        final AddToCardModel addToCardModel = alAddToCart.get(position);
        if (itemDataPendingRemoval.contains(addToCardModel)) {
            itemDataPendingRemoval.remove(addToCardModel);
        }
        if (alAddToCart.contains(addToCardModel)) {
            alAddToCart.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean isPendingRemoval(int position) {
        AddToCardModel addToCardModel = alAddToCart.get(position);
        return itemDataPendingRemoval.contains(addToCardModel);
    }

    @Override
    public void onDrop(int fromPosition, int toPosition) {
        listener.onItemPositionChange(fromPosition, toPosition);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        @BindView(R.id.appTvCompanyName)
        AppCompatTextView appTvCompanyName;
        @BindView(R.id.appTvProductName)
        AppCompatTextView appTvProductName;
        @BindView(R.id.appTvPrice)
        AppCompatTextView appTvPrice;
        @BindView(R.id.appTvTotalPrice)
        AppCompatTextView appTvTotalPrice;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorDragSelect));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorDropSelect));
        }
    }
}
