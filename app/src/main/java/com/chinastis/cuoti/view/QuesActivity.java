package com.chinastis.cuoti.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chinastis.cuoti.MyApp;
import com.chinastis.cuoti.R;
import com.chinastis.cuoti.adapter.QuesContentAdapter;
import com.chinastis.cuoti.bean.Question;
import com.chinastis.cuoti.database.dao.QuestionDao;
import com.chinastis.cuoti.util.Constant;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuesActivity extends BaseActivity {

    @BindView(R.id.understand_ques)
    TextView understandQues;
    @BindView(R.id.review_ques)
    TextView reviewQues;
    @BindView(R.id.ques_images)
    RecyclerView quesImages;
    @BindView(R.id.ans_images)
    RecyclerView ansImages;
    @BindView(R.id.tip)
    TextView tip;

    private String quesId;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_ques);
        ButterKnife.bind(this);

        quesId = getIntent().getStringExtra("quesId");

        setEndIcon(getResources().getDrawable(R.drawable.icon_edit));
        setEndIconClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuesActivity.this,SaveActivity.class);
                intent.putExtra(Constant.TITLE,"错题编辑");
                intent.putExtra("quesId",quesId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {

        question = MyApp.getDaoSession().getQuestionDao()
                .queryBuilder()
                .where(QuestionDao.Properties.Ques_id.eq(quesId))
                .unique();

        if (question!=null) {
            understandQues.setText(question.getUnderstand());
            reviewQues.setText("复习" + question.getReviewTime() + "次");
            tip.setText(question.getDate()+" 错题来自："+question.getQues_from());
        }

        File quesImage = new File(Constant.PATH+quesId+File.separator+"ques");
        final File[] quesFiles = quesImage.listFiles();
        QuesContentAdapter quesAdapter = new QuesContentAdapter(this,quesFiles);
        quesImages.setLayoutManager(new LinearLayoutManager(this,0,false));
        quesImages.setAdapter(quesAdapter);
        quesImages.setNestedScrollingEnabled(false);

        File ansImage = new File(Constant.PATH+quesId+File.separator+"ans");
        final File[] ansFiles = ansImage.listFiles();
        QuesContentAdapter ansAdapter = new QuesContentAdapter(this,ansFiles);
        ansImages.setLayoutManager(new LinearLayoutManager(this,0,false));
        ansImages.setAdapter(ansAdapter);
        ansImages.setNestedScrollingEnabled(false);


    }
}
