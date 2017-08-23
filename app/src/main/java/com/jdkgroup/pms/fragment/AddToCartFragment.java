package com.jdkgroup.pms.fragment;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jdkgroup.baseclasses.BaseFragment;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.activity.OrderActivity;
import com.jdkgroup.pms.adapter.AddToCardAdapter;
import com.jdkgroup.models.AddToCardModel;
import com.jdkgroup.pms.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddToCartFragment extends BaseFragment implements View.OnClickListener, AddToCardAdapter.OnCartItemChange {

    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.appTvGrandTotal)
    AppCompatTextView appTvGrandTotal;
    @BindView(R.id.rlNoData)
    RelativeLayout rlNoData;
    @BindView(R.id.rlPresentData)
    RelativeLayout rlPresentData;
    @BindView(R.id.appTvOrder)
    AppCompatButton appTvOrder;

    private AddToCardAdapter addToCardAdapter;
    private List<AddToCardModel> alAddToCart;

    public AddToCartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_to_cart, container, false);

        //String strtext = getArguments().getString("name");
        //AppUtils.showToast(getActivity(), strtext);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getGrandTotal();
        addToCardAdapter = new AddToCardAdapter(getActivity(), getDataAddToCart());
        recyclerView.setAdapter(addToCardAdapter);
        addToCardAdapter.setClickListener(this);

        appTvOrder.setOnClickListener(this);
    }

    private List<AddToCardModel> getDataAddToCart() {
        alAddToCart = new ArrayList<>();
        alAddToCart.add(new AddToCardModel("1", "Flip1", "Shirt", 10, 1, 1, 10, 1, 10, false));
        alAddToCart.add(new AddToCardModel("2", "Flip2", "Shirt", 10, 1, 0, 20, 1, 20, false));
        alAddToCart.add(new AddToCardModel("3", "Flip3", "Shirt", 10, 1, 0, 30, 1, 30, false));
        alAddToCart.add(new AddToCardModel("4", "Flip4", "Shirt", 10, 1, 0, 40, 1, 40, false));
        alAddToCart.add(new AddToCardModel("6", "Flip5", "Shirt", 10, 1, 0, 50, 1, 50, false));
        alAddToCart.add(new AddToCardModel("7", "Flip6", "Shirt", 10, 1, 0, 60, 1, 60, false));
        alAddToCart.add(new AddToCardModel("8", "Flip7", "Shirt", 10, 1, 0, 70, 1, 70, false));
        alAddToCart.add(new AddToCardModel("9", "Flip8", "Shirt", 10, 1, 1, 80, 1, 80, false));
        return alAddToCart;
    }

    @Override
    public void onRemoveAt(final View view, final int itemPosition) {
        addToCardAdapter.removeAt(itemPosition);
    }

    @Override
    public void onUpdateAt(View view, int position, int quantity, AddToCardModel addToCardModel, boolean isSelect) {
        if (isSelect == false && quantity == 0) {
            addToCardAdapter.updateAt(position, new AddToCardModel(addToCardModel.getId(), addToCardModel.getCompanyname(), addToCardModel.getTitle(), addToCardModel.getDiscount(), addToCardModel.getQuantity(), addToCardModel.getIsaddtocart(), addToCardModel.getPrice(), addToCardModel.getDiscountprice(), addToCardModel.getTotalprice(), isSelect));
        } else if (isSelect == true && quantity != 0) {
            addToCardAdapter.updateAt(position, new AddToCardModel(addToCardModel.getId(), addToCardModel.getCompanyname(), addToCardModel.getTitle(), addToCardModel.getDiscount(), quantity, addToCardModel.getIsaddtocart(), addToCardModel.getPrice(), addToCardModel.getDiscountprice(), quantity * addToCardModel.getPrice(), isSelect));
        }
    }

    @Override
    public void onGrandTotal(double grandtotal) {
        appTvGrandTotal.setText(AppUtils.getStringFromId(getActivity(), R.string.sy_rupee) + "" + String.format("%.2f", grandtotal));
    }

    @Override
    public void onPresentItems(int totalitmes) {
        if (totalitmes == 0) {
            isNoData(0);
        } else {
            isNoData(1);
        }
    }

    public void getGrandTotal() {
        double grandtotal = 0;
        for (AddToCardModel addToCardModel : getDataAddToCart()) {
            if (addToCardModel.isSelected() == true && addToCardModel.getQuantity() != 0) {
                grandtotal += addToCardModel.getQuantity() * addToCardModel.getPrice();
            }
            appTvGrandTotal.setText(AppUtils.getStringFromId(getActivity(), R.string.sy_rupee) + "" + String.format("%.2f", grandtotal));
        }
    }

    public void isNoData(final int isData) {
        if (isData == 0) {
            rlPresentData.setVisibility(View.GONE);
            rlNoData.setVisibility(View.VISIBLE);
        } else if (isData == 1) {
            rlPresentData.setVisibility(View.VISIBLE);
            rlNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appTvOrder:
                if (appTvGrandTotal.getText().toString().equalsIgnoreCase("₹0.00")) {
                    AppUtils.showToast(getActivity(), "Add to cart not item selected.");
                } else {
                    launch(OrderActivity.class);
                }
                break;
        }
    }
}
