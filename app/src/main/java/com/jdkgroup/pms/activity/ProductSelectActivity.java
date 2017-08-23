package com.jdkgroup.pms.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.constant.AppConstant;
import com.jdkgroup.customviews.discreteseekbar.DiscreteSeekBar;
import com.jdkgroup.models.ProductSelectModel;
import com.jdkgroup.pms.BaseDrawerActivity;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.adapter.HomeAdapter;
import com.jdkgroup.pms.adapter.ProductSelectAdapter;
import com.jdkgroup.pms.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductSelectActivity extends BaseActivity implements View.OnClickListener, ProductSelectAdapter.OnSelectItemChange {

    @BindView (R.id.toolBar)
    Toolbar toolBar;
    @BindView (R.id.rlNoData)
    RelativeLayout rlNoData;
    @BindView (R.id.rlPresentData)
    RelativeLayout rlPresentData;
    @BindView (R.id.discreteSeekBar)
    DiscreteSeekBar discreteSeekBar;
    @BindView (R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView (R.id.appTvSelectProductTotal)
    AppCompatTextView appTvSelectProductTotal;
    @BindView (R.id.appBtnBuy)
    AppCompatButton appBtnBuy;
    @BindView (R.id.appEdtFilter)
    AppCompatEditText appEdtFilter;

    private List<ProductSelectModel> alCategoryModel;
    private ProductSelectAdapter homeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_select);

        hideSoftKeyboard();
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        init();
        initMultipleOfSeekBar();

        appEdtFilter.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence query, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {
                query = query.toString().toLowerCase();

                final List<ProductSelectModel> filteredModelList = filter(getCategory(), query.toString());
                homeAdapter.setFilter(filteredModelList);
            }
        });


        toolBar.setNavigationOnClickListener(arrow -> finish());
        appBtnBuy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appBtnBuy:
                launch(BuyActivity.class);
                break;
        }
    }

    private void init() {
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_product));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        homeAdapter = new ProductSelectAdapter(getActivity(), getCategory());
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.setClickListener(this);

    }

    //TODO FILTER CATEGORY
    private List<ProductSelectModel> filter(List<ProductSelectModel> alCategory, String query) {
        query = query.toLowerCase();

        final List<ProductSelectModel> filteredModelList = new ArrayList<>();
        for (ProductSelectModel category : alCategory) {
            final String text = category.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(category);
            }
        }
        return filteredModelList;
    }

    public List<ProductSelectModel> getCategory() {
        alCategoryModel = new ArrayList<>();
        alCategoryModel.add(new ProductSelectModel(0, "Mobile", true));
        alCategoryModel.add(new ProductSelectModel(1, "Mobile", false));
        alCategoryModel.add(new ProductSelectModel(2, "TV", false));
        alCategoryModel.add(new ProductSelectModel(3, "Laptop", false));
        alCategoryModel.add(new ProductSelectModel(4, "Mouse", false));
        alCategoryModel.add(new ProductSelectModel(5, "Cloth", false));
        alCategoryModel.add(new ProductSelectModel(6, "Bag", false));
        alCategoryModel.add(new ProductSelectModel(7, "Car", false));
        alCategoryModel.add(new ProductSelectModel(8, "Bike", false));

        return alCategoryModel;
    }

    @Override
    public void onUpdateAt(int position, boolean flagSelect) {
        ProductSelectModel categoryModel =  alCategoryModel.get(position);
        alCategoryModel.set(position, new ProductSelectModel(categoryModel.getId(), categoryModel.getName(), flagSelect));
        getSelectTotal();
    }

    @Override
    public void OnDetailsAt(View view, int position) {
        launch(ProductDetailsActivity.class);
    }

    public void getSelectTotal() {
        int selectCategoryTotal = 0;

        for (ProductSelectModel categoryModel : alCategoryModel) {
            if (categoryModel.isSelect() == true) {
                selectCategoryTotal += +1;
            }
        }
        appTvSelectProductTotal.setText(getString(R.string.select_items) + " " + selectCategoryTotal + "");
    }


    private void initMultipleOfSeekBar() {
        discreteSeekBar.setMin(0);
        discreteSeekBar.setMax(100);  //result should be in multiple of 100
        discreteSeekBar.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
            @Override
            public int transform(int value) {
                return value * 100;
            }
        });

        discreteSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                AppUtils.showToast(getActivity(), (value * 100) + "");
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
    }
}