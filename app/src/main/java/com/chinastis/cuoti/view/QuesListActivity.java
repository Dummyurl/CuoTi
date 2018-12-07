package com.chinastis.cuoti.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinastis.cuoti.MyApp;
import com.chinastis.cuoti.R;
import com.chinastis.cuoti.adapter.QuesListAdapter;
import com.chinastis.cuoti.bean.Msg;
import com.chinastis.cuoti.bean.Question;
import com.chinastis.cuoti.database.dao.QuestionDao;
import com.chinastis.cuoti.retrofit.RetrofitClient;
import com.chinastis.cuoti.retrofit.RetrofitService;
import com.chinastis.cuoti.util.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QuesListActivity extends BaseActivity {

    @BindView(R.id.ques_list)
    RecyclerView quesList;
    @BindView(R.id.tool_layout)
    LinearLayout toolLayout;
    @BindView(R.id.delete_count_list)
    TextView deleteCountList;
    @BindView(R.id.delete_button_list)
    Button deleteButtonList;
    @BindView(R.id.delete_layout)
    LinearLayout deleteLayout;
    @BindView(R.id.print_count_list)
    TextView printCountList;
    @BindView(R.id.print_button_list)
    Button printButtonList;
    @BindView(R.id.print_layout)
    LinearLayout printLayout;

    private List<Question> questions;
    private String className;
    private QuesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_ques_list);
        ButterKnife.bind(this);
        className = getIntent().getStringExtra(Constant.TITLE);


        initEvent();
    }

    private void initEvent() {
        setEndIcon(getResources().getDrawable(R.drawable.icon_ques_delete));
        setEndIconClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPrintOrDeleteLayout(true);

            }
        });
    }

    public void refreshSelectedNum(int num) {
        deleteCountList.setText("已选中" + num + "项");
        printCountList.setText("已选中" + num + "项");

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        questions = MyApp.getDaoSession()
                .getQuestionDao()
                .queryBuilder()
                .where(QuestionDao.Properties.Ques_class.eq(className))
                .where(QuestionDao.Properties.Ueser.eq(Constant.USER_ID))
                .list();

        adapter = new QuesListAdapter(questions, this);

        quesList.setLayoutManager(new LinearLayoutManager(this));
        quesList.setAdapter(adapter);

        quesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("MENG", "scroll state:" + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        hidePrintAndDeleteLayout();
    }

    @OnClick({R.id.add_ques, R.id.print_ques, R.id.delete_button_list,
            R.id.delete_cancel_list, R.id.print_cancel_list,R.id.print_button_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete_cancel_list:
                hidePrintAndDeleteLayout();
                break;
            case R.id.print_cancel_list:
                hidePrintAndDeleteLayout();
                break;
            case R.id.add_ques:
                Intent intent = new Intent(QuesListActivity.this, CameraActivity.class);
                startActivity(intent);
                break;
            case R.id.print_ques:
                showPrintOrDeleteLayout(false);
                break;
            case R.id.print_button_list:
                printQuestions();
                break;
            case R.id.delete_button_list:
                deleteQuestions();

                break;
        }
    }

    /**
     * 组卷打印
     */
    private void printQuestions() {
        if (adapter.getCheckedItem().size() == 0) {
            showToast("尚未选中需打印的目标");
            return;
        }

        Retrofit retrofit = RetrofitClient.getGsonRetrofitInstance();
        Map<String, String> form = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        List<String> printIds = adapter.getCheckedItem();

        for (int i = 0; i<printIds.size(); i++) {
            sb.append(printIds.get(i));
            if (i < printIds.size() - 1) {
                sb.append(",");
            }
        }
        Log.e("MENG","print items:"+sb.toString());

        form.put("problems", sb.toString());
        form.put("subject", Constant.classMap.get(className));
        form.put("userId", Constant.USER_ID);

        retrofit.create(RetrofitService.class)
                .printQuestion(form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Msg>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("网络错误，请稍后再试");
                        Log.e("MENG","e:"+e);
                    }

                    @Override
                    public void onNext(Msg msg) {
                        if(msg.getSuccess().equals("false")) {
                            showToast(msg.getMessage());
                            return;
                        }
                        showToast(msg.getMessage());

                        Log.e("MENG","print result data:"+msg.getData());

                        startActivity(new Intent(QuesListActivity.this,PrintActivity.class));

                    }
                });
    }

    /**
     * 删除操作
    */
    private void deleteQuestions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定删除？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                List<String> quesIds = adapter.getCheckedItem();
                for (String id : quesIds) {
                    Question question = MyApp.getDaoSession().getQuestionDao()
                            .queryBuilder()
                            .where(QuestionDao.Properties.Ques_id.eq(id))
                            .unique();
                    MyApp.getDaoSession().getQuestionDao()
                            .delete(question);

                    deleteOnServer(id);
                }

                showToast("删除完成！");

                QuesListActivity.this.onResume();


                toolLayout.setVisibility(View.VISIBLE);
                adapter.setCheckBoxShow(false);


            }
        });


        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

    /**
     * 后台端删除数据
     * @param quesId 错题编号
     */
    private void deleteOnServer(String quesId) {
        Retrofit retrofit = RetrofitClient.getGsonRetrofitInstance();

        Map<String, String> form = new HashMap<>();
        form.put("id", quesId);

        retrofit.create(RetrofitService.class)
                .deleteQuestionById(form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Subscriber<Msg>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        showToast("网络错误，请稍后再试");
                    }

                    @Override
                    public void onNext(Msg msg) {
//                        if(msg.getSuccess().equals("false")) {
//                            showToast(msg.getMessage());
//                            return;
//                        }
//                        showToast(msg.getMessage());
                    }
                });

    }

    /**
     * 控制视图显示，显示打印组件 或者 显示删除组件
     * @param isDeleteLayout 是否显示删除组件
     */
    public void showPrintOrDeleteLayout(boolean isDeleteLayout) {
        if (isDeleteLayout) {
            deleteLayout.setVisibility(View.VISIBLE);
            printLayout.setVisibility(View.GONE);
        } else {
            deleteLayout.setVisibility(View.GONE);
            printLayout.setVisibility(View.VISIBLE);
        }

        toolLayout.setVisibility(View.GONE);
        adapter.setCheckBoxShow(true);
    }

    /**
     * 隐藏打印、删除组件
     */
    public void hidePrintAndDeleteLayout() {
        deleteLayout.setVisibility(View.GONE);
        printLayout.setVisibility(View.GONE);
        toolLayout.setVisibility(View.VISIBLE);
        adapter.setCheckBoxShow(false);
    }

}
