package com.jdkgroup.pms.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdkgroup.baseclasses.BaseFragment;
import com.jdkgroup.models.CategoryModel;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.adapter.PurchaseAdapter;
import com.jdkgroup.models.PurchaseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PurchaseItemsFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView (R.id.appEdtFilter)
    AppCompatEditText appEdtFilter;

    private PurchaseAdapter purchaseAdapter;
    private LinearLayoutManager linearLayoutManager;

    public PurchaseItemsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_items, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        purchaseAdapter = new PurchaseAdapter(getActivity(), getDataPurchase());
        recyclerView.setAdapter(purchaseAdapter);

        appEdtFilter.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence query, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {
                query = query.toString().toLowerCase();

                final List<PurchaseModel> filteredModelList = filter(getDataPurchase(), query.toString());
                purchaseAdapter.setFilter(filteredModelList);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    private void init()
    {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private List<PurchaseModel> getDataPurchase()
    {
        List<PurchaseModel> alPurchase = new ArrayList<>();
        for(int i=0; i<40; i++)
        {
            alPurchase.add(new PurchaseModel("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"));
        }
        return alPurchase;
    }

    //TODO FILTER CATEGORY
    private List<PurchaseModel> filter(List<PurchaseModel> alCategory, String query) {
        query = query.toLowerCase();

        final List<PurchaseModel> filteredModelList = new ArrayList<>();
        for (PurchaseModel category : alCategory) {
            final String text = category.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(category);
            }
        }
        return filteredModelList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}