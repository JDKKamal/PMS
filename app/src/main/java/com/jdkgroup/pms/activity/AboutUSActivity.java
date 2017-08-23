package com.jdkgroup.pms.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.pms.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUSActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_about_us));

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolBar.setNavigationOnClickListener(arrow -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item;
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        item = (MenuItem) menu.findItem(R.id.action_search_new_screen);
        item.setVisible(false);
        item = (MenuItem) menu.findItem(R.id.action_feedback);
        item.setVisible(false);
        return true;
    }
}