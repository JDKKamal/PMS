package com.jdkgroup.pms.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.constant.AppConstant;
import com.jdkgroup.models.CategoryModel;
import com.jdkgroup.models.CompanyListModel;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.adapter.CompanyListAdapter;
import com.jdkgroup.pms.adapter.ProductListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListActivity extends BaseActivity implements View.OnClickListener, ProductListAdapter.ItemListener, CompanyListAdapter.ItemListener {

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.rlCompany)
    RelativeLayout rlCompany;
    @BindView(R.id.rlProduct)
    RelativeLayout rlProduct;
    @BindView(R.id.appBtnSubmit)
    AppCompatButton appBtnSubmit;

    private BottomSheetBehavior behavior;
    private BottomSheetDialog mBottomSheetDialog;
    private ProductListAdapter productListAdapter;
    private List<CategoryModel> alSelectCategoryModel;
    private CompanyListAdapter companyListAdapter;
    private List<CompanyListModel> alCompanyListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        alCompanyListModel = new ArrayList<>();
        alCompanyListModel.add(new CompanyListModel(1, "Company 1", false));
        alCompanyListModel.add(new CompanyListModel(2, "Company 2", false));
        alCompanyListModel.add(new CompanyListModel(3, "Company 3", false));
        alCompanyListModel.add(new CompanyListModel(4, "Company 4", false));

        hideSoftKeyboard();
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_product));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        alSelectCategoryModel = getParcelable(AppConstant.BUNDLE_PARCELER_CLASSNAME);

        View bottomSheet = findViewById(R.id.bottomSheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });

        toolBar.setNavigationOnClickListener(arrow -> finish());
        rlCompany.setOnClickListener(this);
        rlProduct.setOnClickListener(this);
        appBtnSubmit.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showBottomSheetDialogProductList() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_layout_product_list, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        AppCompatImageView appIvSelect = (AppCompatImageView) view.findViewById(R.id.appIvSelect);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productListAdapter = new ProductListAdapter(alSelectCategoryModel, this);
        recyclerView.setAdapter(productListAdapter);

        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });

        appIvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomSheetDialog != null) {
                    mBottomSheetDialog.dismiss();
                }
            }
        });
    }

    private void showBottomSheetDialogCompanyList() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_layout_product_list, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        AppCompatImageView appIvSelect = (AppCompatImageView) view.findViewById(R.id.appIvSelect);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        companyListAdapter = new CompanyListAdapter(alCompanyListModel, this);
        recyclerView.setAdapter(companyListAdapter);

        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });

        appIvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomSheetDialog != null) {
                    mBottomSheetDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onUpdateAt(View view, int position, CategoryModel categoryModel, boolean isSelect) {
        alSelectCategoryModel.set(position, new CategoryModel(categoryModel.getId(), categoryModel.getName(), isSelect));
        productListAdapter.notifyDataSetChanged();
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlCompany:
                showBottomSheetDialogCompanyList();
                break;

            case R.id.rlProduct:
                showBottomSheetDialogProductList();
                break;

            case R.id.appBtnSubmit:
                launch(ProductSelectActivity.class);
                break;
        }
    }

    @Override
    public void onUpdateAt(View view, int position, CompanyListModel companyListModel, boolean isSelect) {
        alCompanyListModel.set(position, new CompanyListModel(companyListModel.getId(), companyListModel.getName(), isSelect));
        companyListAdapter.notifyDataSetChanged();
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
}