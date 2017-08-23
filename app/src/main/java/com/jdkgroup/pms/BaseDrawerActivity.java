package com.jdkgroup.pms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.constant.AppConstant;
import com.jdkgroup.customviews.FontTypeface;
import com.jdkgroup.pms.activity.ChangePasswordActivity;
import com.jdkgroup.pms.activity.LoginActivity;
import com.jdkgroup.pms.activity.OrderHistoryActivity;
import com.jdkgroup.pms.activity.ProfileEditActivity;
import com.jdkgroup.pms.activity.SettingActivity;
import com.jdkgroup.pms.utils.AppUtils;

import butterknife.BindView;

public class BaseDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, FragmentManager.OnBackStackChangedListener {

    @BindView (R.id.drawer_layout)
    public DrawerLayout drawerLayout;
    @BindView (R.id.navigationView)
    NavigationView navigationView;
    @BindView (R.id.toolBar)
    Toolbar toolBar;

    public AppCompatImageView appIvProfile;
    public AppCompatTextView appTvName, appTvEmail;
    private int openDrawer = 1, closeDrawer = 0;

    private String className = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        hideSoftKeyboard();
        bindViews();
        setupHeader();

        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);

        actionBarDrawerToggle(drawerLayout, toolBar);

        fontSetNavigationMenu(navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        appIvProfile.setBackgroundResource(R.mipmap.ic_profile);

        AppUtils.lauchFramgentActivity(getActivity(), AppConstant.LAUNCHHOME);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                if (f != null) {
                    updateTitleAndDrawer(f);
                }
            }
        });


        appIvProfile.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeDrawerLeft(openDrawer);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupHeader() {
        View headerView = navigationView.getHeaderView(0);
        appIvProfile = (AppCompatImageView) headerView.findViewById(R.id.appIvProfile);
        appTvName = (AppCompatTextView) headerView.findViewById(R.id.appTvName);
        appTvEmail = (AppCompatTextView) headerView.findViewById(R.id.appTvEmail);

        headerView.findViewById(R.id.appIvProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGlobalMenuHeaderClick(v);
            }
        });
    }

    public void onGlobalMenuHeaderClick(final View v) {
        closeDrawerLeft(closeDrawer);
    }

    private void closeDrawerLeft(int isDrawer) {
        switch (isDrawer) {
            case 0:
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case 1:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
    }

    @SuppressWarnings ("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        launchNavigationMenu(item.getItemId());
        closeDrawerLeft(closeDrawer);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appIvProfile:
                closeDrawerLeft(closeDrawer);
                launch(ProfileEditActivity.class);
                break;
        }
    }

    private void launchNavigationMenu(final int id) {
        switch (id) {
            case R.id.nav_home:
                AppUtils.lauchFramgentActivity(getActivity(), AppConstant.LAUNCHHOME);
                break;

            case R.id.nav_add_to_card:
                AppUtils.lauchFramgentActivity(getActivity(), AppConstant.LAUNCHADDTOCAT);
                break;

            case R.id.nav_purchase_items:
                AppUtils.lauchFramgentActivity(getActivity(), AppConstant.LAUNCHPURCHASEITEMS);
                break;

            case R.id.nav_order_history:
                launch(OrderHistoryActivity.class);
                break;

            case R.id.nav_change_password:
                launch(ChangePasswordActivity.class);
                break;

            case R.id.nav_about_us:
                //launch(.class);
                break;

            case R.id.nav_settings:
                launch(SettingActivity.class);
                break;

            case R.id.nav_notifications:
                AppUtils.lauchFramgentActivity(getActivity(), AppConstant.LAUNCHNOTIFICATIONSLIST);
                break;

            case R.id.nav_help:
                //launch(HelpActivity.class);
                break;

            case R.id.nav_logout:
                launch(LoginActivity.class);
                break;
        }
    }

    private void fontSetNavigationMenu(NavigationView navigationView) {
        FontTypeface fontTypeface = new FontTypeface(this);
        Typeface typeface = fontTypeface.getTypefaceAndroid();

        MenuItem item;

        item = navigationView.getMenu().findItem(R.id.nav_home);
        fontSetNavigationMenu(item, typeface);

        item = navigationView.getMenu().findItem(R.id.nav_home);
        fontSetNavigationMenu(item, typeface);

        item = navigationView.getMenu().findItem(R.id.nav_add_to_card);
        fontSetNavigationMenu(item, typeface);

        item = navigationView.getMenu().findItem(R.id.nav_order_history);
        fontSetNavigationMenu(item, typeface);

        item = navigationView.getMenu().findItem(R.id.nav_purchase_items);
        fontSetNavigationMenu(item, typeface);

        item = navigationView.getMenu().findItem(R.id.nav_change_password);
        fontSetNavigationMenu(item, typeface);

        item = navigationView.getMenu().findItem(R.id.nav_about_us);
        fontSetNavigationMenu(item, typeface);

        item = navigationView.getMenu().findItem(R.id.nav_settings);
        fontSetNavigationMenu(item, typeface);

        item = navigationView.getMenu().findItem(R.id.nav_notifications);
        fontSetNavigationMenu(item, typeface);

        item = navigationView.getMenu().findItem(R.id.nav_help);
        fontSetNavigationMenu(item, typeface);

        item = navigationView.getMenu().findItem(R.id.nav_logout);
        fontSetNavigationMenu(item, typeface);
    }

    @Override
    public void onBackPressed() {
        if (className.equals("HomeFragment")) {
            AppUtils.appExist(getActivity());
        } else {
            super.onBackPressed();
        }
    }

    private void updateTitleAndDrawer(Fragment fragment) {
        String fragClassName = fragment.getClass().getName();
        className = fragClassName.replace("com.jdkgroup.pms.fragment.", "");
        toolBarSetTitle(className);
    }

    public void toolBarSetTitle(String str) {
        switch (str) {
            case "HomeFragment":
                navigationView.getMenu().getItem(0).setChecked(true);
                toolBar.setTitle(getString(R.string.app_name));
                break;

            case "AddToCartFragment":
                navigationView.getMenu().getItem(1).setChecked(true);
                toolBar.setTitle(getString(R.string.action_add_to_cart));
                break;

            case "PurchaseItemsTab":
                navigationView.getMenu().getItem(3).setChecked(true);
                toolBar.setTitle(getString(R.string.action_purchase_items));
                break;

            case "NotificaitonsFragment":
                navigationView.getMenu().getItem(7).setChecked(true);
                toolBar.setTitle(getString(R.string.action_notifications));
                break;
        }
    }

    @Override
    public void onBackStackChanged() {
        super.onBackPressed();
    }

   /* private void clearBackStack() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        }
    }*/
}
