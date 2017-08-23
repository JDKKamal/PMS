package com.jdkgroup.pms.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.customviews.dragndrop.OnStartDragListener;
import com.jdkgroup.customviews.dragndrop.SimpleItemTouchHelperCallback;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.adapter.OrderAdapter;
import com.jdkgroup.models.AddToCardModel;
import com.jdkgroup.pms.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity implements OnStartDragListener, OrderAdapter.ICallback, View.OnClickListener {

    @BindView (R.id.toolBar)
    Toolbar toolBar;
    @BindView (R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView (R.id.appTvPayment)
    AppCompatButton appTvPayment;

    private ItemTouchHelper mItemTouchHelper;

    private OrderAdapter orderAdapter;
    private List<AddToCardModel> alAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        hideSoftKeyboard();
        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_order));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        init();
        toolBar.setNavigationOnClickListener(arrow -> finish());
        appTvPayment.setOnClickListener(this);
    }

    private void init() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setAdapter();

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(this, orderAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
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

    private void setAdapter() {
        orderAdapter = new OrderAdapter(getActivity(), this, getDataAddToCart(), this);
        recyclerView.setAdapter(orderAdapter);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
        mItemTouchHelper.startSwipe(viewHolder);
    }

    @Override
    public void onItemPositionChange(int fromPosition, int toPosition) {
        AppUtils.showToast(getActivity(), "Item is moved from " + fromPosition + " to " + toPosition);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appTvPayment:
                launch(PaymentActivity.class);
                break;
        }
    }
}
