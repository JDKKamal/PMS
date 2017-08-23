package com.jdkgroup.pms.adapter;

/**
 * Created by kamlesh on 8/15/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.jdkgroup.models.CategoryModel;
import com.jdkgroup.models.ReviewsListModel;
import com.jdkgroup.pms.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListAdapter.ViewHolder> {

    private List<ReviewsListModel> alReviewsList;
    private ItemListener listener;

    public ReviewsListAdapter(List<ReviewsListModel> alReviewsList, ItemListener listener) {
        this.alReviewsList = alReviewsList;
        this.listener = listener;
    }

    public void setListener(ItemListener listener) {
        listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_reviews_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(alReviewsList.get(position));
    }

    @Override
    public int getItemCount() {
        return alReviewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ReviewsListModel item;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        public void setData(ReviewsListModel item) {
            this.item = item;

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }

    public interface ItemListener {
        void onUpdateAtReviewList(View view, int position, ReviewsListModel reviewsListModel, boolean isSelect);
    }
}