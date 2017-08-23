package com.jdkgroup.pms.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.jdkgroup.models.CompanyListModel;
import com.jdkgroup.pms.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.ViewHolder> {

    private List<CompanyListModel> alCompanyList;
    private ItemListener listener;

    public CompanyListAdapter(List<CompanyListModel> alCompanyList, ItemListener listener) {
        this.alCompanyList = alCompanyList;
        this.listener = listener;
    }

    public void setListener(ItemListener listener) {
        listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_product_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(alCompanyList.get(position));

        holder.appCbCompany.setOnCheckedChangeListener(null);
        holder.appCbCompany.setChecked(alCompanyList.get(position).isSelect());


    }

    @Override
    public int getItemCount() {
        return alCompanyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.appCbCompany)
        CheckBox appCbCompany;

        public CompanyListModel item;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            appCbCompany.setOnClickListener(this);
        }

        public void setData(CompanyListModel item) {
            this.item = item;
            appCbCompany.setText(item.getName());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.appCbCompany:
                    if (appCbCompany.isChecked() == true) {
                        listener.onUpdateAt(view, getAdapterPosition(), alCompanyList.get(getAdapterPosition()), true);
                    } else {
                        listener.onUpdateAt(view, getAdapterPosition(), alCompanyList.get(getAdapterPosition()), false);
                    }
                    break;
            }
        }
    }

    public interface ItemListener {
        void onUpdateAt(View view, int position, CompanyListModel companyListModel, boolean isSelect);
    }
}