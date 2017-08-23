package com.jdkgroup.pms.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jdkgroup.baseclasses.BaseFragment;
import com.jdkgroup.models.NotificationsModel;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.adapter.NotificationsListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NotificaitonsFragment extends BaseFragment{

    Unbinder unbinder;
    @BindView(R.id.rlNoData)
    RelativeLayout rlNoData;
    @BindView(R.id.rlPresentData)
    RelativeLayout rlPresentData;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<NotificationsModel> alNotificationsModel;
    private NotificationsListAdapter notificationsListAdapter;

    public NotificaitonsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications_list, null);
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

        if (getNotifications().isEmpty()) {
            isNoData(0);
        } else {
            isNoData(1);
        }

        notificationsListAdapter = new NotificationsListAdapter(getActivity(), getNotifications());
        recyclerView.setAdapter(notificationsListAdapter);
    }

    public List<NotificationsModel> getNotifications() {
        alNotificationsModel = new ArrayList<>();
        alNotificationsModel.add(new NotificationsModel("1"));

        return alNotificationsModel;
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


}