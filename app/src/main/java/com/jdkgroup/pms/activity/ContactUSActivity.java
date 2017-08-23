package com.jdkgroup.pms.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.pms.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactUSActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_contact_us));

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolBar.setNavigationOnClickListener(arrow -> finish());
    }
}