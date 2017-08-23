package com.jdkgroup.pms.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.constant.AppConstant;
import com.jdkgroup.database.RealmController;
import com.jdkgroup.dialog.SpinnerDialog;
import com.jdkgroup.models.CountryModel;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.utils.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class RegisterUserActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.appEdtUserName)
    AppCompatEditText appEdtUserName;
    @BindView(R.id.appEdtEmail)
    AppCompatEditText appEdtEmail;
    @BindView(R.id.appEdtPassword)
    AppCompatEditText appEdtPassword;
    @BindView(R.id.appIvPasswordShow)
    AppCompatImageView appIvPasswordShow;
    @BindView(R.id.appEdtConfirmPassword)
    AppCompatEditText appEdtConfirmPassword;
    @BindView(R.id.appIvConfirmPasswordShow)
    AppCompatImageView appIvConfirmPasswordShow;
    @BindView(R.id.appEdtMobile)
    AppCompatEditText appEdtMobile;
    @BindView(R.id.appEdtCountry)
    AppCompatEditText appEdtCountry;
    @BindView(R.id.appBtnRegisterUser)
    AppCompatButton appBtnRegisterUser;

    private Realm realm;
    private RealmController realmController;

    CountryModel selectedCountry = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        hideSoftKeyboard();

        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        realmController = new RealmController(realm);

        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_register_user));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolBar.setNavigationOnClickListener(arrow -> finish());

        appBtnRegisterUser.setOnClickListener(this);
        appEdtCountry.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appBtnRegisterUser:
                String username, email, password, confirmpassword, mobile;
                username = appEdtUserName.getText().toString().trim();
                email = appEdtEmail.getText().toString().trim();
                password = appEdtPassword.getText().toString().trim();
                confirmpassword = appEdtConfirmPassword.getText().toString().trim();
                mobile = appEdtMobile.getText().toString().trim();

                if (isValidation(username, email, password, confirmpassword, mobile) == true) {
                    hideSoftKeyboard();
                    Bundle bundle = new Bundle();
                    bundle.putInt(AppConstant.BUNDLE_CLASSNAME, AppConstant.RESULTCODE_REGISTER_USER);
                    launchParcelable(VerifyOTPActivity.class, bundle);
                }
                break;

            case R.id.appEdtCountry:
                hideSoftKeyboard();
                openCountryDialog();
                break;
        }
    }

    private boolean isValidation(final String username, final String email, final String password, final String confirmpassword, final String mobile) {

        if (TextUtils.isEmpty(username)) {
            AppUtils.showSnakBar(coordinatorLayout, getString(R.string.msg_empty_username));
            return false;
        } else if (TextUtils.isEmpty(email)) {
            AppUtils.showSnakBar(coordinatorLayout, getString(R.string.msg_empty_email));
            return false;
        } else if (TextUtils.isEmpty(password)) {
            AppUtils.showSnakBar(coordinatorLayout, getString(R.string.msg_empty_password));
            return false;
        } else if (TextUtils.isEmpty(confirmpassword)) {
            AppUtils.showSnakBar(coordinatorLayout, getString(R.string.msg_empty_confirm_password));
            return false;
        } else if (TextUtils.isEmpty(mobile)) {
            AppUtils.showSnakBar(coordinatorLayout, getString(R.string.msg_empty_mobile));
            return false;
        }
        return true;
    }

    public void openCountryDialog() {
        List<CountryModel> alCountryModel = realmController.getAllData();

        if (alCountryModel.size() > 0) {
            SpinnerDialog sd = new SpinnerDialog(this, AppUtils.getStringFromId(this, R.string.title_country), new SpinnerDialog.OnItemClick() {

                @Override
                public void selectedItem(Object object) {
                    selectedCountry = (CountryModel) object;
                    appEdtCountry.setText(selectedCountry.getName());
                }
            }, alCountryModel);
            sd.show();
        } else {
            AppUtils.showToastById(this, R.string.no_data);
        }
    }

}