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

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.appEdtCurrentPassword)
    AppCompatEditText appEdtCurrentPassword;
    @BindView(R.id.appEdtNewPassword)
    AppCompatEditText appEdtNewPassword;
    @BindView(R.id.appEdtNewConfirmPassword)
    AppCompatEditText appEdtNewConfirmPassword;
    @BindView(R.id.appBtnChangePassword)
    AppCompatButton appBtnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        hideSoftKeyboard();

        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_change_password));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolBar.setNavigationOnClickListener(arrow -> finish());
        appBtnChangePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appBtnChangePassword:
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstant.BUNDLE_CLASSNAME, AppConstant.RESULTCODE_REGISTER_USER);
                launchParcelable(VerifyOTPActivity.class, bundle);
                break;
        }
    }
}