package com.jdkgroup.pms.adapter;

/**
 * Created by kamlesh on 8/15/2017.
 */

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.jdkgroup.models.CategoryModel;
import com.jdkgroup.models.NotificationsModel;
import com.jdkgroup.pms.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.ViewHolder> {

    private Activity activity;
    private List<NotificationsModel> alNotifications;

    public NotificationsListAdapter( Activity activity, List<NotificationsModel> alNotifications) {
        this.activity = activity;
        this.alNotifications = alNotifications;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_notifications_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationsModel notificationsModel = alNotifications.get(position);

    }

    @Override
    public int getItemCount() {
        return alNotifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.appTvNotificationId)
        AppCompatTextView appTvNotificationId;

        public CategoryModel item;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void setData(CategoryModel item) {
            this.item = item;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }


}