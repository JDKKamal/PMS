package com.jdkgroup.pms.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.constant.AppConstant;
import com.jdkgroup.database.RealmController;
import com.jdkgroup.models.CountryModel;
import com.jdkgroup.pms.BaseDrawerActivity;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.utils.AppUtils;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView (R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView (R.id.appEdtEmail)
    AppCompatEditText appEdtEmail;
    @BindView (R.id.appEdtPassword)
    AppCompatEditText appEdtPassword;
    @BindView (R.id.appIvPasswordShow)
    AppCompatImageView appIvPasswordShow;
    @BindView (R.id.appTvForgotPassword)
    AppCompatTextView appTvForgotPassword;
    @BindView (R.id.appBtnLogin)
    AppCompatButton appBtnLogin;
    @BindView (R.id.appTvRegister)
    AppCompatTextView appTvRegister;
    @BindView (R.id.appIvTwitter)
    AppCompatImageView appIvTwitter;
    @BindView (R.id.appIvFacebook)
    AppCompatImageView appIvFacebook;

    private boolean flagPasswordShow;

    private Realm realm;
    private RealmController realmController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hideSoftKeyboard();

        ButterKnife.bind(this);
        pusherRealTimeMessageInit(); //TODO NOTIFICATION  (NOTIFICATION SENT FROM DEBUG CONSOLE https://dashboard.pusher.com/apps/386101/console/realtime_messages)

        realm = Realm.getDefaultInstance();
        realmController = new RealmController(realm);
        flagPasswordShow = true;

        try {
            //realmController.deleteAllCountryData();
            String UUIDCurrentTimeMillis = System.currentTimeMillis() + "-" + UUID.randomUUID().toString();
            String jsonCountry = AppUtils.readFileFromAssets(getActivity(), AppConstant.READ_FILE_JSON_COUNTRY, AppConstant.EXTENSION_JSON);

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            List<CountryModel> alCountryModel = gson.fromJson(jsonCountry, new TypeToken<List<CountryModel>>() {
            }.getType());

            CountryModel countryModel;
            for (CountryModel country : alCountryModel) {
                countryModel = new CountryModel(UUIDCurrentTimeMillis, country.getName(), country.getCode());
                realmController.addData(countryModel);
            }
        } catch (Exception ex) {
            AppUtils.showSnakBar(coordinatorLayout, getString(R.string.no_data));
        }

        appBtnLogin.setOnClickListener(this);
        appTvRegister.setOnClickListener(this);
        appIvPasswordShow.setOnClickListener(this);
        appTvForgotPassword.setOnClickListener(this);
    }

    private void isPasswordShow(AppCompatEditText appCompatEditText) {
        if (flagPasswordShow == true) {
            flagPasswordShow = false;
            appCompatEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else if (flagPasswordShow == false) {
            flagPasswordShow = true;
            appCompatEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            appCompatEditText.setSelection(appCompatEditText.length());
        }
    }

    private boolean isValidation(final String email, final String password) {
        if (TextUtils.isEmpty(email)) {
            AppUtils.showSnakBar(coordinatorLayout, getString(R.string.msg_empty_email));
            return false;
        } else if (TextUtils.isEmpty(password)) {
            AppUtils.showSnakBar(coordinatorLayout, getString(R.string.msg_empty_password));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appBtnLogin:
                String email, password;
                email = appEdtEmail.getText().toString().trim();
                password = appEdtPassword.getText().toString().trim();

                if (isValidation(email, password) == true) {
                    appEdtEmail.setText(null);
                    appEdtPassword.setText(null);
                    launch(BaseDrawerActivity.class);
                }
                break;

            case R.id.appTvRegister:
                appEdtPassword.setText(null);
                launch(RegisterUserActivity.class);
                break;

            case R.id.appTvForgotPassword:
                appEdtPassword.setText(null);
                launch(ForgotPasswordActivity.class);
                break;

            case R.id.appIvPasswordShow:
                isPasswordShow(appEdtPassword);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AppUtils.appExist(getActivity());
    }
}