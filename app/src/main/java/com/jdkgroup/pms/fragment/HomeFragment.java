package com.jdkgroup.pms.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jdkgroup.baseclasses.BaseFragment;
import com.jdkgroup.constant.AppConstant;
import com.jdkgroup.models.CategoryModel;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.activity.ProductListActivity;
import com.jdkgroup.pms.adapter.HomeAdapter;
import com.jdkgroup.pms.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment implements View.OnClickListener, HomeAdapter.OnSelectItemChange {

    Unbinder unbinder;
    @BindView (R.id.rlNoData)
    RelativeLayout rlNoData;
    @BindView (R.id.rlPresentData)
    RelativeLayout rlPresentData;
    @BindView (R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView (R.id.appTvSelectCategoryTotal)
    AppCompatTextView appTvSelectCategoryTotal;
    @BindView (R.id.appBtnSubmit)
    AppCompatButton appBtnSubmit;
    @BindView (R.id.appEdtFilter)
    AppCompatEditText appEdtFilter;
    @BindView (R.id.appIvSearch)
    AppCompatImageView appIvSearch;

    private List<CategoryModel> alCategoryModel;
    private HomeAdapter homeAdapter;
    boolean flagFilterClose = false;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);

        appIvSearch.setOnClickListener(this);
        appBtnSubmit.setOnClickListener(this);
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

        if (getCategory().isEmpty()) {
            isNoData(0);
        } else {
            isNoData(1);
        }

        homeAdapter = new HomeAdapter(getActivity(), getCategory());
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.setClickListener(this);

        getSelectTotal();
    }

    public List<CategoryModel> getCategory() {
        alCategoryModel = new ArrayList<>();
        alCategoryModel.add(new CategoryModel(0, "Mobile", true));
        alCategoryModel.add(new CategoryModel(1, "Mobile", false));
        alCategoryModel.add(new CategoryModel(2, "TV", false));
        alCategoryModel.add(new CategoryModel(3, "Laptop", false));
        alCategoryModel.add(new CategoryModel(4, "Mouse", false));
        alCategoryModel.add(new CategoryModel(5, "Cloth", false));
        alCategoryModel.add(new CategoryModel(6, "Bag", false));
        alCategoryModel.add(new CategoryModel(7, "Car", false));
        alCategoryModel.add(new CategoryModel(8, "Bike", false));

        return alCategoryModel;
    }

    @Override
    public void onUpdateAt(int position, boolean flagSelect) {
        CategoryModel categoryModel = alCategoryModel.get(position);
        alCategoryModel.set(position, new CategoryModel(categoryModel.getId(), categoryModel.getName(), flagSelect));
        getSelectTotal();
    }

    //TODO FILTER CATEGORY
    private List<CategoryModel> filter(List<CategoryModel> alCategory, String query) {
        query = query.toLowerCase();

        final List<CategoryModel> filteredModelList = new ArrayList<>();
        for (CategoryModel category : alCategory) {
            final String text = category.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(category);
            }
        }
        return filteredModelList;
    }

    public void getSelectTotal() {
        int selectCategoryTotal = 0;

        for (CategoryModel categoryModel : alCategoryModel) {
            if (categoryModel.isSelect() == true) {
                selectCategoryTotal += +1;
            }
        }
        appTvSelectCategoryTotal.setText(getString(R.string.select_items) + " " + selectCategoryTotal + "");
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
            case R.id.appBtnSubmit:

                List<CategoryModel> alSelectCategoryModel = new ArrayList<>();
                for (CategoryModel categoryModel : alCategoryModel) {
                    if (categoryModel.isSelect() == true) {
                        alSelectCategoryModel.add(new CategoryModel(categoryModel.getId(), categoryModel.getName()));
                    }
                }
                sentParcelableListLaunchClear(ProductListActivity.class, AppConstant.BUNDLE_PARCELER_CLASSNAME, alSelectCategoryModel);
                break;
            case R.id.appIvSearch:
                String filter = appEdtFilter.getText().toString();
                if (flagFilterClose == false) {
                    if (Validation(filter) == true) {
                        flagFilterClose = true;
                        appIvSearch.setImageResource(R.drawable.ic_close);
                    }
                } else if (flagFilterClose == true) {
                    flagFilterClose = false;
                    appIvSearch.setImageResource(R.drawable.ic_search);
                    appEdtFilter.setText(null);
                    filter = "";
                }
                AppUtils.hideKeyboard(getActivity());
                final List<CategoryModel> filteredModelList = filter(getCategory(), filter);
                homeAdapter.setFilter(filteredModelList);
                break;
        }
    }

    private boolean Validation(String filter) {
        if (TextUtils.isEmpty(filter)) {
            return false;
        }
        return true;
    }
}