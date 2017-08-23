package com.jdkgroup.pms.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.constant.AppConstant;
import com.jdkgroup.models.Person;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.adapter.PaymentAdapter;
import com.jdkgroup.pms.utils.AppUtils;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends BaseActivity implements View.OnClickListener, PaymentAdapter.ItemClickListener {

    @BindView (R.id.toolBar)
    Toolbar toolBar;
    @BindView (R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView (R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView (R.id.llUserAnotherDebitCard)
    LinearLayout llUserAnotherDebitCard;
    @BindView (R.id.cvDebitCardShow)
    CardView cvDebitCardShow;
    @BindView (R.id.appBtnPayment)
    AppCompatButton appBtnPayment;
    @BindView (R.id.appIvSelectAnotherCard)
    AppCompatImageView appIvSelectAnotherCard;

    private ArrayList<Person> mPersonList;
    private PaymentAdapter paymentAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String jsonAlreadyAccount = "";
    private int flagPayment;

    //PAYPAL
    private final int PAYPAL_REQUEST_CODE = 123;
    private PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(AppConstant.PAYPAL_CLIENT_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        hideSoftKeyboard();

        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_payment));

        flagPayment = 0;

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolBar.setNavigationOnClickListener(arrow -> finish());
        llUserAnotherDebitCard.setOnClickListener(this);
        appBtnPayment.setOnClickListener(this);

        cvDebitCardShow.setVisibility(View.GONE);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        setupPersonList();

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }

    private void setupPersonList() {
        mPersonList = new ArrayList<>();
        mPersonList.clear();
        for (int i = 0; i < 5; i++) {
            Person person = new Person("Person " + i, "Desgination " + i, "Address " + i);
            mPersonList.add(person);
        }
        paymentAdapter = new PaymentAdapter(mPersonList, this);
        paymentAdapter.setClickListener(this);
        recyclerView.setAdapter(paymentAdapter);
    }


    @Override
    public void onUpdateAt(View view, int position) {
        appIvSelectAnotherCard.setImageResource(R.drawable.ic_circle_unselect);
        hideSoftKeyboard();
        flagPayment = 2;
        jsonAlreadyAccount = "";
        cvDebitCardShow.setVisibility(View.GONE);
        paymentAdapter.setSelected(position, 1);
    }

    @Override
    public void onUpdateAtCVV(int position, String cvv) {
        jsonAlreadyAccount = String.valueOf(position);
    }

    //TODO PAYPAL PAYMENT
    private void makePayment() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(100)), "USD", "Tip Payment", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation paymentConfirmation = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (paymentConfirmation != null) {
                    String paymentDetails = null;
                    try {
                        paymentDetails = paymentConfirmation.toJSONObject().toString(4);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                AppUtils.showToast(getActivity(), "Payment Successful");
                //TODO LAUNCH ACTIVITY OR FRAGMENT HERE

            } else if (resultCode == Activity.RESULT_CANCELED) {
                AppUtils.showToast(getActivity(), "Payment Cancel");
            } else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
                AppUtils.showToast(getActivity(), "Payment Error");

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llUserAnotherDebitCard:
                hideSoftKeyboard();
                flagPayment = 1;
                jsonAlreadyAccount = "";
                paymentAdapter.setSelected(0, 0);
                cvDebitCardShow.setVisibility(View.VISIBLE);
                appIvSelectAnotherCard.setImageResource(R.drawable.ic_circle_select);
                break;

            case R.id.appBtnPayment:
                if (flagPayment == 0) {

                } else if (flagPayment == 1) {


                } else if (flagPayment == 2) {
                    //TODO ALREADY ACCOUNT NO. AVAILABLE
                    if (jsonAlreadyAccount.isEmpty()) {
                        AppUtils.showToast(getActivity(), "Valid CVV");
                    } else {
                        AppUtils.showToast(getActivity(), jsonAlreadyAccount);
                        //PAYPAL CALL
                        makePayment();
                    }
                }
                break;
        }
    }

}