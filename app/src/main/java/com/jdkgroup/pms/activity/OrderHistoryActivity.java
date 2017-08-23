package com.jdkgroup.pms.activity;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.models.AddToCardModel;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.adapter.OrderHistoryAdapter;
import com.jdkgroup.pms.utils.AppUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderHistoryActivity extends BaseActivity implements View.OnClickListener, CompactCalendarView.CompactCalendarViewListener {

    @Nullable
    @BindView(R.id.toolBar)
    public Toolbar toolBar;
    @BindView(R.id.compactCalendarView)
    CompactCalendarView compactCalendarView;
    @BindView(R.id.appIvPrevious)
    AppCompatImageView appIvPrevious;
    @BindView(R.id.appIvNext)
    AppCompatImageView appIvNext;
    @BindView(R.id.appTvYear)
    AppCompatTextView appTvYear;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM-yyyy", Locale.getDefault());

    private OrderHistoryAdapter orderHistoryAdapter;
    private List<AddToCardModel> alAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        hideSoftKeyboard();

        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_order_history));

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        init();
        setAdapter();

        compactCalendarView.setListener(this);
        appIvPrevious.setOnClickListener(this);
        appIvNext.setOnClickListener(this);

        toolBar.setNavigationOnClickListener(arrow -> finish());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item;
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        item = (MenuItem) menu.findItem(R.id.action_search_new_screen);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_feedback:
                launch(FeedbackActivity.class);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        addEvents("Tue Aug 14 2017");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setAdapter();
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
        orderHistoryAdapter = new OrderHistoryAdapter(getActivity(), getDataAddToCart());
        recyclerView.setAdapter(orderHistoryAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        appTvYear.setText(dateFormatForMonth.format(new Date()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appIvPrevious:
                compactCalendarView.showPreviousMonth();
                break;

            case R.id.appIvNext:
                compactCalendarView.showNextMonth();
                break;
        }
    }

    public void addEvents(String timeMilliseconds) {
        try {
            Event event = new Event(Color.argb(255, 255, 255, 0), AppUtils.getDateTimeInMilliseconds(timeMilliseconds), "Payment is android teams.");
            List<Event> addEvent = new ArrayList<>();
            addEvent.add(event);
            compactCalendarView.addEvents(addEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDayClick(Date dateClicked) {
        List<Event> alEvent = compactCalendarView.getEvents(dateClicked);
        if (alEvent != null) {
            for (Event event : alEvent) {
                AppUtils.showToast(getActivity(), event.getData() + "");
            }
        }
    }

    @Override
    public void onMonthScroll(Date firstDayOfNewMonth) {
        appTvYear.setText(dateFormatForMonth.format(firstDayOfNewMonth));
    }
}