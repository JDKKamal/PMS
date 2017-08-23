package com.jdkgroup.pms.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.jdkgroup.pms.R;
import com.jdkgroup.models.AddToCardModel;
import com.jdkgroup.pms.utils.AppUtils;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddToCardAdapter extends RecyclerView.Adapter<AddToCardAdapter.ViewHolder> {

    private Activity activity;
    private List<AddToCardModel> alAddToCart;
    private OnCartItemChange onCartItemChange;

    public AddToCardAdapter(Activity activity, List<AddToCardModel> alAddToCart) {
        this.activity = activity;
        this.alAddToCart = alAddToCart;
    }

    public void setClickListener(OnCartItemChange onCartItemChange) {
        this.onCartItemChange = onCartItemChange;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_add_to_cart, parent, false);
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
        holder.appEdtQuantity.setText(String.valueOf(addToCardModel.getQuantity()));

        holder.appCbAddToCard.setOnCheckedChangeListener(null);
        holder.appCbAddToCard.setChecked(alAddToCart.get(position).isSelected());

        if (addToCardModel.isSelected() == true) {
            holder.appCbAddToCard.setTextColor(ContextCompat.getColor(activity, R.color.colorCheckBoxActive));
        }
    }

    @Override
    public int getItemCount() {
        return alAddToCart.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.appTvCompanyName)
        AppCompatTextView appTvCompanyName;
        @BindView(R.id.appTvProductName)
        AppCompatTextView appTvProductName;
        @BindView(R.id.appTvPrice)
        AppCompatTextView appTvPrice;
        @BindView(R.id.appTvDelete)
        AppCompatTextView appTvDelete;
        @BindView(R.id.appIvMinus)
        AppCompatImageView appIvMinus;
        @BindView(R.id.appIvAdd)
        AppCompatImageView appIvAdd;
        @BindView(R.id.appTvTotalPrice)
        AppCompatTextView appTvTotalPrice;
        @BindView(R.id.appEdtQuantity)
        AppCompatEditText appEdtQuantity;
        @BindView(R.id.appIvImagePurchase)
        AppCompatImageView appIvImagePurchase;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.appCbAddToCard)
        AppCompatCheckBox appCbAddToCard;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            appEdtQuantity.setEnabled(false);
            appIvMinus.setOnClickListener(this);
            appTvDelete.setOnClickListener(this);
            appIvAdd.setOnClickListener(this);
            appCbAddToCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.appTvDelete:
                    onCartItemChange.onRemoveAt(view, getAdapterPosition());
                    onCartItemChange.onPresentItems(alAddToCart.size());
                    getGrandTotal();
                    break;

                case R.id.appCbAddToCard:
                    if (appCbAddToCard.isChecked() == true) {
                        onCartItemChange.onUpdateAt(view, getAdapterPosition(), alAddToCart.get(getAdapterPosition()).getQuantity(), alAddToCart.get(getAdapterPosition()), true);
                        getGrandTotal();
                    } else {
                        onCartItemChange.onUpdateAt(view, getAdapterPosition(), 0, alAddToCart.get(getAdapterPosition()), false);
                        getGrandTotal();
                    }
                    break;

                case R.id.appIvAdd:
                    if (appCbAddToCard.isChecked() == true) {
                        int qty = 0;
                        String strQty = appEdtQuantity.getText().toString();
                        try {
                            qty = Integer.parseInt(strQty);
                        } catch (Exception e) {
                        }
                        qty++;
                        appEdtQuantity.setText(qty + "");
                        onCartItemChange.onUpdateAt(view, getAdapterPosition(), qty, alAddToCart.get(getAdapterPosition()), true);
                        getGrandTotal();
                    } else {
                        AppUtils.showToast(activity, activity.getString(R.string.msg_order_not_select));
                    }
                    break;

                case R.id.appIvMinus:
                    if (appCbAddToCard.isChecked() == true) {
                        String strQty = appEdtQuantity.getText().toString();
                        int qty = 1;
                        try {
                            qty = Integer.parseInt(strQty);
                            if (qty > 1)
                                qty--;
                        } catch (Exception e) {
                        }
                        appEdtQuantity.setText(qty + "");
                        onCartItemChange.onUpdateAt(view, getAdapterPosition(), qty, alAddToCart.get(getAdapterPosition()), true);
                        getGrandTotal();
                    } else {
                        AppUtils.showToast(activity, activity.getString(R.string.msg_order_not_select));
                    }
                    break;
            }
        }
    }

    public interface OnCartItemChange {
        void onRemoveAt(View view, int position);
        void onUpdateAt(View view, int position, int quantity, AddToCardModel addToCardModel, boolean isSelect);
        void onGrandTotal(double grandtotal);
        void onPresentItems(int totalitmes);
    }

    public void removeAt(int itemPosition) {
        alAddToCart.remove(itemPosition);
        notifyItemChanged(itemPosition);
        notifyDataSetChanged();
    }

    public void updateAt(int itemPosition, AddToCardModel addToCardModel) {
        this.alAddToCart.set(itemPosition, addToCardModel);
        notifyItemChanged(itemPosition);
        notifyDataSetChanged();
    }

    public void getGrandTotal() {
        double grandtotal = 0;
        for (AddToCardModel addToCardModel : alAddToCart) {
            if (addToCardModel.isSelected() == true && addToCardModel.getQuantity() != 0) {
                grandtotal += addToCardModel.getQuantity() * addToCardModel.getPrice();
            }
        }
        onCartItemChange.onGrandTotal(grandtotal);
    }
}