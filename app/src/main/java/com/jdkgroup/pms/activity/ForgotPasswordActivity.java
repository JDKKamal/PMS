package com.jdkgroup.pms.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.constant.AppConstant;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {

    @BindView (R.id.toolBar)
    Toolbar toolBar;
    @BindView (R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView (R.id.appEdtEmail)
    AppCompatEditText appEdtEmail;
    @BindView (R.id.appBtnForgotPassword)
    AppCompatButton appBtnForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        hideSoftKeyboard();

        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_forgot_password));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolBar.setNavigationOnClickListener(arrow -> finish());
        appBtnForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appBtnForgotPassword:
                String email = appEdtEmail.getText().toString().trim();
                if (isValidation(email) == true) {
                    appEdtEmail.setText(null);
                    Bundle bundle = new Bundle();
                    bundle.putInt(AppConstant.BUNDLE_CLASSNAME, AppConstant.RESULTCODE_FORGOT_PASSWORD);
                    launchParcelable(VerifyOTPActivity.class, bundle);
                }
                break;
        }
    }

    private boolean isValidation(final String email) {
        if (TextUtils.isEmpty(email)) {
            AppUtils.showSnakBar(coordinatorLayout, getString(R.string.msg_empty_email));
            return false;
        }
        return true;
    }
}