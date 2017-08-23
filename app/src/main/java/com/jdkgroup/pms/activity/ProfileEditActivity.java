package com.jdkgroup.pms.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.constant.AppConstant;
import com.jdkgroup.pms.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileEditActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.appEdtUserName)
    AppCompatEditText appEdtUserName;
    @BindView(R.id.appBtnVerifyEmail)
    AppCompatButton appBtnVerifyEmail;
    @BindView(R.id.appBtnVerifyMobile)
    AppCompatButton appBtnVerifyMobile;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        hideSoftKeyboard();

        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_profile_edit));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolBar.setNavigationOnClickListener(arrow -> finish());
        appBtnVerifyEmail.setOnClickListener(this);
        appBtnVerifyMobile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appBtnVerifyEmail:
                bundle = new Bundle();
                bundle.putInt(AppConstant.BUNDLE_CLASSNAME, AppConstant.RESULTCODE_CHANGE_PASSWORD_MOBILE);
                launchParcelable(VerifyOTPActivity.class, bundle);
                break;

            case R.id.appBtnVerifyMobile:
                bundle = new Bundle();
                bundle.putInt(AppConstant.BUNDLE_CLASSNAME, AppConstant.RESULTCODE_CHANGE_PASSWORD_EMAIL);
                launchParcelable(VerifyOTPActivity.class, bundle);
                break;
        }
    }
}