package com.chinastis.cuoti.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chinastis.cuoti.R;
import com.chinastis.cuoti.util.Constant;

public class PlanActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_plan);

        initEvent();


    }

    private void initEvent() {
        setEndIcon(getResources().getDrawable(R.drawable.icon_plan_setting));
        setEndIconClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanActivity.this,PlanSettingActivity.class);
                intent.putExtra(Constant.TITLE,"计划设置");
                startActivity(intent);
            }
        });
    }
}
