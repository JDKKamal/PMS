package com.jdkgroup.baseclasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.jdkgroup.constant.AppConstant;
import com.jdkgroup.customviews.CustomTypefaceSpan;
import com.jdkgroup.pms.R;
import com.jdkgroup.pms.utils.AppUtils;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import java.io.Serializable;
import java.util.HashMap;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.TextView;

import org.parceler.Parcels;

public class BaseActivity extends AppCompatActivity {

    public Toolbar toolBar;

    public ProgressBar progressToolbar;
    private Dialog progressDialog;
    private Intent intent;
    private HashMap<String, String> params;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();
    }

    protected void bindViews() {
        ButterKnife.bind(this);
        setupToolbar();
    }

    protected void setContentViewWithoutInject(int layoutResId) {
        super.setContentView(layoutResId);
    }

    protected void setupToolbar() {
        if (toolBar != null) {
            setSupportActionBar(toolBar);
            toolBar.setNavigationIcon(R.drawable.ic_drawer);
        }
    }

    protected void fontSetNavigationMenu(MenuItem item, Typeface font) {
        SpannableString mNewTitle = new SpannableString(item.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font, 16), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        item.setTitle(mNewTitle);
    }

    protected void actionBarDrawerToggle(DrawerLayout drawerLayout, Toolbar toolBar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    protected Toolbar getToolbar() {
        return toolBar;
    }

    protected void toolBarSetFont(final Toolbar toolBar) {
        for (int i = 0; i < toolBar.getChildCount(); i++) {
            View view = toolBar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                Typeface titleFont = Typeface.createFromAsset(getApplicationContext().getAssets(), AppConstant.FONT_AILERON_SEMIBOLD);
                if (tv.getText().equals(toolBar.getTitle())) {
                    tv.setTypeface(titleFont);
                    break;
                }
            }
        }
    }

    protected void hideSoftKeyboard() {
        try {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getApplicationWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public HashMap<String, String> getDefaultParameter() {
        params = new HashMap<>();
        return params;
    }

    public HashMap<String, String> getDefaultParamWithIdAndToken() {
        params = getDefaultParameter();
        return params;
    }

    public void showProgressDialog(final boolean show) {
        //Show Progress bar here
        if (show) {
            showProgressDialog();
        } else {
            hideProgressDialog();
        }
    }

    public void showProgressToolBar(final boolean show, final View view) {
        if (show) {
            progressToolbar.setVisibility(View.VISIBLE);
            if (view != null)
                view.setVisibility(View.GONE);

        } else {
            progressToolbar.setVisibility(View.GONE);
            if (view != null)
                view.setVisibility(View.VISIBLE);
        }
    }

    protected final void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new Dialog(this);
        } else {
            return;
        }
        View view = LayoutInflater.from(this).inflate(R.layout.app_loading_dialog, null, false);

        AppCompatImageView appIvProgressBar = (AppCompatImageView) view.findViewById(R.id.appIvProgressBar);
        Animation a1 = AnimationUtils.loadAnimation(this, R.anim.progress_anim);
        a1.setDuration(1500);
        appIvProgressBar.startAnimation(a1);

        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(view);
        Window window = progressDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent));
        }
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    /**
     * hide progress bar
     */
    protected final void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    /*
    public void showProgress() {
        customProgressbar = new CustomProgressbar(this);
        if (customProgressbar != null && customProgressbar.isShowing()) {
            customProgressbar.hide();
        }
        customProgressbar.show(false);
        customProgressbar.isShowing();
    }

    public void hideProgress() {
        if (customProgressbar != null && customProgressbar.isShowing()) {
            customProgressbar.hide();
        }
        customProgressbar = null;
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    protected void attachBaseContext(final Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onAuthenticationFailure(final String message) {

    }

    public boolean hasInternetWithoutMessage() {
        if (AppUtils.hasInternetConnection(this)) {
            return true;
        } else {
            return false;
        }
    }

    /* TODO LAUNCH ACTIVITY/FRAGMENT */
    protected void launch(final Class<?> classType, final Serializable data) {
        intent = new Intent(this, classType);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    protected void launchParcelable(final Class<?> classType, final Bundle data) {
        intent = new Intent(this, classType);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(AppConstant.BUNDLE, data);
        startActivity(intent);
    }

    protected void launch(final Class<?> classType, final int flags) {
        intent = new Intent(this, classType);
        intent.addFlags(flags);
        startActivity(intent);
    }

    protected void launch(final Class<?> classType) {
        intent = new Intent(this, classType);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    //ACTIVITY CLEAR TO LAUNCH NEW
    protected void launchClear(final Class<?> classType) {
        intent = new Intent(this, classType);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    protected void launchClearParcelable(final Class<?> classType, final Bundle bundle) {
        intent = new Intent(this, classType);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    /* TODO LAUNCH ACTIVITY/FRAGMENT ANIMATION*/
    public void AnimationAppCompactActivity(final Intent intent, boolean reverseAnimate, final boolean animate, final int requestCode, final int animationtype) {
        SwitchAnimationAppCompactActivity(intent, reverseAnimate, animate, requestCode, animationtype);
    }

    private void SwitchAnimationAppCompactActivity(final Intent intent, boolean reverseAnimate, final boolean animate, final int requestCode, final int animationtype) {
        switch (animationtype) {
            case 1: //startActivity
                startActivity(intent);
                if (animate)
                    overridePendingTransition(R.anim.animation_slide_from_right, R.anim.animation_slide_to_left);
                break;

            case 2: //startActivityForResult
                startActivityForResult(intent, requestCode);
                if (animate)
                    overridePendingTransition(R.anim.animation_slide_from_right, R.anim.animation_slide_to_left);
                break;

            case 3: //reverse animation
                finish();
                if (animate)
                    overridePendingTransition(R.anim.animation_slide_from_left, R.anim.animation_slide_to_right);
                break;

            case 4: //finish
                finish();
                if ((animate == true) && (reverseAnimate == true))
                    overridePendingTransition(R.anim.animation_slide_from_left, R.anim.animation_slide_to_right);
                else if ((animate == true) && (reverseAnimate == false))
                    overridePendingTransition(R.anim.animation_slide_from_left, R.anim.animation_slide_to_right);
                break;

            case 5: //startActivity
                startActivity(intent);
                if ((animate == true) && (reverseAnimate == true))
                    overridePendingTransition(R.anim.animation_slide_from_left, R.anim.animation_slide_to_right);
                else if ((animate == true) && (reverseAnimate == false))
                    overridePendingTransition(R.anim.animation_slide_from_right, R.anim.animation_slide_to_left);
                break;
        }
    }

    protected void intentOpenBrowser(final String url) {
        if (AppUtils.hasInternet(getActivity())) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } else {
            AppUtils.showToast(getActivity(), getString(R.string.no_internet_message));
        }
    }

    public InputFilter decimalPointAfterBeforeAmount(final int maxDigitsBeforeDecimalPoint, final int maxDigitsAfterDecimalPoint) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source.subSequence(start, end).toString());
                if (!builder.toString().matches("(([1-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?")) {
                    if (source.length() == 0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }
                return null;
            }
        };

        return filter;
    }

    protected <T> T getParcelable(String bundleName) {
        return Parcels.unwrap(getActivity().getIntent().getParcelableExtra(bundleName));
    }

    protected void pusherRealTimeMessageInit() {
        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");

        Pusher pusher = new Pusher("886d7aaef31603216409", options);
        pusher.connect();

        Channel channel = pusher.subscribe("my-channel");

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                AppUtils.loge("PUSHER Connection " +  change.getCurrentState() + " form " + change.getPreviousState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                AppUtils.loge("PUSHER ERROR " + " There was a problem connecting!");
            }
        }, ConnectionState.ALL);

        channel.bind("my-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                AppUtils.loge("PUSHER EVENT " + eventName + " - " + data);
            }
        });
    }
}

