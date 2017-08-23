package com.jdkgroup.pms.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.baseclasses.BaseFragment;
import com.jdkgroup.models.AddToCardModel;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.adapter.AddToCardAdapter;
import com.jdkgroup.pms.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BuyActivity extends BaseActivity implements View.OnClickListener, AddToCardAdapter.OnCartItemChange {

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.appTvGrandTotal)
    AppCompatTextView appTvGrandTotal;
    @BindView(R.id.rlNoData)
    RelativeLayout rlNoData;
    @BindView(R.id.rlPresentData)
    RelativeLayout rlPresentData;
    @BindView(R.id.appBtnPayment)
    AppCompatButton appBtnPayment;

    private AddToCardAdapter addToCardAdapter;
    private List<AddToCardModel> alAddToCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        hideSoftKeyboard();

        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_buy));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        getGrandTotal();
        addToCardAdapter = new AddToCardAdapter(getActivity(), getDataAddToCart());
        recyclerView.setAdapter(addToCardAdapter);
        addToCardAdapter.setClickListener(this);

        toolBar.setNavigationOnClickListener(arrow -> finish());
        appBtnPayment.setOnClickListener(this);

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
            case R.id.appBtnPayment:
                if (appTvGrandTotal.getText().toString().equalsIgnoreCase("₹0.00")) {
                    AppUtils.showToast(getActivity(), "Add to cart not item selected.");
                } else {
                    launch(PaymentActivity.class);
                }
                break;
        }
    }
}
