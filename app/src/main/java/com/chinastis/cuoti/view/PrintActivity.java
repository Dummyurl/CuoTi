package com.chinastis.cuoti.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chinastis.cuoti.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.quit_print)
    public void onViewClicked() {
        this.finish();
    }
}
