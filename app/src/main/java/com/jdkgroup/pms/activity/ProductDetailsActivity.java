package com.jdkgroup.pms.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.models.ReviewsListModel;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.adapter.ProductDetailImageSlidingAdapter;
import com.jdkgroup.pms.adapter.ReviewsListAdapter;
import com.jdkgroup.pms.utils.AppUtils;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsActivity extends BaseActivity implements View.OnClickListener, ReviewsListAdapter.ItemListener{

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.circlePageIndicator)
    CirclePageIndicator circlePageIndicator;
    @BindView(R.id.appTvReviews)
    AppCompatTextView appTvReviews;

    private BottomSheetBehavior behavior;
    private BottomSheetDialog mBottomSheetDialog;

    private ReviewsListAdapter reviewsListAdapter;

    private int[] viewpagerimage = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private ProductDetailImageSlidingAdapter productDetailImageSlidingAdapter;
    private PageIndicator pageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_sliding);
        AppUtils.hideKeyboard(getActivity());
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_product_details));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolBar.setNavigationOnClickListener(arrow -> finish());

        productDetailImageSlidingAdapter = new ProductDetailImageSlidingAdapter(getApplicationContext(), viewpagerimage);
        viewPager.setAdapter(productDetailImageSlidingAdapter);
        pageIndicator = circlePageIndicator;
        circlePageIndicator.setViewPager(viewPager);

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

        appTvReviews.setOnClickListener(this);
    }

    private void showBottomLayoutReviewsList() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_layout_reviews, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        AppCompatTextView appTvCancel = (AppCompatTextView) view.findViewById(R.id.appTvCancel);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewsListAdapter = new ReviewsListAdapter(getReviewsList(), this);
        recyclerView.setAdapter(reviewsListAdapter);

        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });

        appTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomSheetDialog != null) {
                    mBottomSheetDialog.dismiss();
                }
            }
        });
    }

    private List<ReviewsListModel> getReviewsList()
    {
        List<ReviewsListModel> alReviewsListModel = new ArrayList<>();
        alReviewsListModel.add(new ReviewsListModel("1"));

        return alReviewsListModel;
    }

    @Override
    public void onUpdateAtReviewList(View view, int position, ReviewsListModel reviewsListModel, boolean isSelect) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.appTvReviews:
                showBottomLayoutReviewsList();
                break;
        }
    }
}