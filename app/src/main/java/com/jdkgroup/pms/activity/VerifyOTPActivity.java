package com.jdkgroup.pms.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.constant.AppConstant;
import com.jdkgroup.customviews.VerificationAction;
import com.jdkgroup.customviews.VerificationCodeEditText;
import com.jdkgroup.pms.BaseDrawerActivity;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.utils.AppUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerifyOTPActivity extends BaseActivity implements View.OnClickListener, VerificationAction.OnVerificationCodeChangedListener {

    @BindView (R.id.toolBar)
    Toolbar toolBar;
    @BindView (R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView (R.id.verificationCodeEditText)
    VerificationCodeEditText verificationCodeEditText;
    @BindView (R.id.appBtnVerifyOTPResend)
    AppCompatButton appBtnVerifyOTPResend;
    @BindView (R.id.appBtnOTPVerify)
    AppCompatButton appBtnOTPVerify;
    @BindView (R.id.appTvTimer)
    AppCompatTextView appTvTimer;

    private int resultCode;
    private CountDownTimer countDownTimer;
    int noOfMinutes = 5 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        init();
        initBundle();

        setCountDownTimer(noOfMinutes);

        toolBar.setNavigationOnClickListener(arrow -> finish());

        appBtnVerifyOTPResend.setOnClickListener(this);
        appBtnOTPVerify.setOnClickListener(this);
        verificationCodeEditText.setOnVerificationCodeChangedListener(this);
    }

    public void init() {
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_otp));

        //TODO TOOLBAR SET
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void initBundle() {
        //TODO BUNDLE DATA GET (RegisterUserActivity, ForgotPasswordActivity, ChangePasswordActivity)
        Bundle bundle = getIntent().getExtras().getBundle(AppConstant.BUNDLE);
        if (bundle != null) {
            resultCode = bundle.getInt(AppConstant.BUNDLE_CLASSNAME);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item;
        getMenuInflater().inflate(R.menu.setting_menu, menu);

        item = (MenuItem) menu.findItem(R.id.action_feedback);
        item.setVisible(false);
        item = (MenuItem) menu.findItem(R.id.action_search_new_screen);
        item.setVisible(false);

        return true;
    }

    @Override
    public void onVerCodeChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void onInputCompleted(CharSequence s) {
        AppUtils.showToast(getActivity(), s.toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appBtnVerifyOTPResend:
                verificationCodeEditText.setText(null);
                countDownTimer.cancel();
                countDownTimer = null;
                setCountDownTimer(noOfMinutes);
                break;
            case R.id.appBtnOTPVerify:
                AppUtils.showSnakBar(coordinatorLayout, getString(R.string.msg_otp_verify_success));
                verificationCodeEditText.setText(null);
                getResultCode();
                break;
        }
    }

    public void getResultCode() {
        launch(BaseDrawerActivity.class);
        finish();
    }

    private void setCountDownTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                appBtnVerifyOTPResend.setEnabled(false);
                long millis = millisUntilFinished;
                String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                appTvTimer.setText(hms);
            }

            public void onFinish() {
                appBtnVerifyOTPResend.setEnabled(true);
                appTvTimer.setText(R.string.timer);
            }
        }.start();

    }
}