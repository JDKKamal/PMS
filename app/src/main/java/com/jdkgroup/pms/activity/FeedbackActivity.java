package com.jdkgroup.pms.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.pms.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        hideSoftKeyboard();

        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_feedback));

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolBar.setNavigationOnClickListener(arrow -> finish());
    }


    @Override
    public void onClick(View view) {

    }
}