package com.chinastis.cuoti;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chinastis.cuoti.adapter.ClassListAdapter;
import com.chinastis.cuoti.bean.QuesClass;
import com.chinastis.cuoti.database.dao.QuestionDao;
import com.chinastis.cuoti.util.ActivityManager;
import com.chinastis.cuoti.util.Constant;
import com.chinastis.cuoti.view.CameraActivity;
import com.chinastis.cuoti.view.PlanActivity;
import com.chinastis.cuoti.view.SelfActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.class_list_main)
    RecyclerView classListMain;
    @BindView(R.id.plan_main)
    TextView planMain;
    @BindView(R.id.capture_main)
    TextView captureMain;
    @BindView(R.id.me_main)
    TextView meMain;
    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    private List<QuesClass> classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActivityManager.getManager().addActivity(this);

//        initData();


    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        classes = new ArrayList<>();

        QuesClass quesClass = new QuesClass();
        quesClass.setClassName("语文");
        quesClass.setQesNum(String.valueOf(getClassQuesCount("语文")));
        quesClass.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_yuwen));
        classes.add(quesClass);

        quesClass = new QuesClass();
        quesClass.setClassName("数学");
        quesClass.setQesNum(String.valueOf(getClassQuesCount("数学")));
        quesClass.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_shuxue));
        classes.add(quesClass);

        quesClass = new QuesClass();
        quesClass.setClassName("英语");
        quesClass.setQesNum(String.valueOf(getClassQuesCount("英语")));
        quesClass.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_yingyu));
        classes.add(quesClass);

        quesClass = new QuesClass();
        quesClass.setClassName("政治");
        quesClass.setQesNum(String.valueOf(getClassQuesCount("政治")));
        quesClass.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_zhengzhi));
        classes.add(quesClass);

        quesClass = new QuesClass();
        quesClass.setClassName("历史");
        quesClass.setQesNum(String.valueOf(getClassQuesCount("历史")));
        quesClass.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_lishi));
        classes.add(quesClass);

        quesClass = new QuesClass();
        quesClass.setClassName("地理");
        quesClass.setQesNum(String.valueOf(getClassQuesCount("地理")));
        quesClass.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_dili));
        classes.add(quesClass);

        quesClass = new QuesClass();
        quesClass.setClassName("生物");
        quesClass.setQesNum(String.valueOf(getClassQuesCount("生物")));
        quesClass.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_shengwu));
        classes.add(quesClass);

        quesClass = new QuesClass();
        quesClass.setClassName("物理");
        quesClass.setQesNum(String.valueOf(getClassQuesCount("物理")));
        quesClass.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_wuli));
        classes.add(quesClass);

        quesClass = new QuesClass();
        quesClass.setClassName("化学");
        quesClass.setQesNum(String.valueOf(getClassQuesCount("化学")));
        quesClass.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_huaxue));
        classes.add(quesClass);

//        quesClass = new QuesClass();
//        quesClass.setClassName("科学");
//        quesClass.setQesNum(String.valueOf(getClassQuesCount("科学")));
//        quesClass.setIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
//        classes.add(quesClass);

        classListMain.setLayoutManager(new GridLayoutManager(this, 3));
        ClassListAdapter adapter = new ClassListAdapter(this, classes);
        classListMain.setAdapter(adapter);


    }


    private int getClassQuesCount(String className) {

        return MyApp.getDaoSession().getQuestionDao()
                .queryBuilder()
                .where(QuestionDao.Properties.Ques_class.eq(className))
                .where(QuestionDao.Properties.Ueser.eq(Constant.USER_ID))
                .list()
                .size();

    }

    @OnClick({R.id.plan_main, R.id.capture_main, R.id.me_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.plan_main:
                Intent planIntent = new Intent(MainActivity.this, PlanActivity.class);
                planIntent.putExtra(Constant.TITLE, "复习计划");
                startActivity(planIntent);
                break;
            case R.id.capture_main:
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
                break;
            case R.id.me_main:
                startActivity(new Intent(MainActivity.this, SelfActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getManager().removeActivity(this);
    }
}
