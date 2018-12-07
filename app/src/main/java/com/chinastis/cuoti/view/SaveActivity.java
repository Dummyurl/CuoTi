package com.chinastis.cuoti.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.chinastis.cuoti.MyApp;
import com.chinastis.cuoti.R;
import com.chinastis.cuoti.adapter.ButtonSelectAdapter;
import com.chinastis.cuoti.adapter.ImageListAdapter;
import com.chinastis.cuoti.bean.Msg;
import com.chinastis.cuoti.bean.Question;
import com.chinastis.cuoti.database.dao.QuestionDao;
import com.chinastis.cuoti.retrofit.RetrofitClient;
import com.chinastis.cuoti.retrofit.RetrofitService;
import com.chinastis.cuoti.util.Constant;
import com.chinastis.cuoti.util.DateUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SaveActivity extends BaseActivity {

    @BindView(R.id.ques_image_list)
    RecyclerView quesImageList;
    @BindView(R.id.ans_image_list)
    RecyclerView ansImageList;
    @BindView(R.id.class_list_save)
    RecyclerView classListSave;
    @BindView(R.id.understand_list_save)
    RecyclerView understandListSave;
    @BindView(R.id.ques_from_edit)
    EditText quesFromEdit;
    @BindView(R.id.tip_edit)
    EditText tipEdit;
    @BindView(R.id.save_button)
    Button saveButton;

    private String quesId;
    private Question question;
    private ButtonSelectAdapter unAdapter;
    private ButtonSelectAdapter classSelectAdapter;

    private boolean isNew = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_save);
        ButterKnife.bind(this);

        quesId = getIntent().getStringExtra("quesId");

        initData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        File quesImage = new File(Constant.PATH + quesId + File.separator + "ques");
        final File[] quesFiles = quesImage.listFiles();
        ImageListAdapter quesAdapter = new ImageListAdapter(this, quesFiles);
        quesImageList.setLayoutManager(new LinearLayoutManager(this, 0, false));
        quesImageList.setAdapter(quesAdapter);
        quesImageList.setNestedScrollingEnabled(false);

        quesAdapter.setAddListener(new ImageListAdapter.AddImageListener() {
            @Override
            public void addItemClicked() {
                Intent intent = new Intent(SaveActivity.this, CameraActivity.class);
                intent.putExtra("quesId", quesId);
                intent.putExtra("isQues", true);
                startActivity(intent);
            }
        });

        quesAdapter.setDeleteImageListener(new ImageListAdapter.DeleteImageListener() {
            @Override
            public void deleteItemClicked(int position) {
                File file = quesFiles[position];
                file.delete();
                SaveActivity.this.onResume();
            }
        });


        File ansImage = new File(Constant.PATH + quesId + File.separator + "ans");
        final File[] ansFiles = ansImage.listFiles();
        ImageListAdapter ansAdapter = new ImageListAdapter(this, ansFiles);
        ansImageList.setLayoutManager(new LinearLayoutManager(this, 0, false));
        ansImageList.setAdapter(ansAdapter);
        ansImageList.setNestedScrollingEnabled(false);


        ansAdapter.setAddListener(new ImageListAdapter.AddImageListener() {
            @Override
            public void addItemClicked() {
                Intent intent = new Intent(SaveActivity.this, CameraActivity.class);
                intent.putExtra("quesId", quesId);
                intent.putExtra("isQues", false);
                startActivity(intent);

            }
        });

        ansAdapter.setDeleteImageListener(new ImageListAdapter.DeleteImageListener() {
            @Override
            public void deleteItemClicked(int position) {
                File file = ansFiles[position];
                file.delete();
                SaveActivity.this.onResume();
            }
        });
    }

    private void initData() {

        List<String> classes = new ArrayList<>();
        String[] classArray = getResources().getStringArray(R.array.classes);
        Collections.addAll(classes, classArray);

        classSelectAdapter = new ButtonSelectAdapter(classes, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        classListSave.setLayoutManager(gridLayoutManager);
        classListSave.setAdapter(classSelectAdapter);


        List<String> understandLevel = new ArrayList<>();
        understandLevel.add("不懂");
        understandLevel.add("略懂");
        understandLevel.add("基本懂");
        understandLevel.add("完全懂");
        unAdapter = new ButtonSelectAdapter(understandLevel, this);

        gridLayoutManager = new GridLayoutManager(this, 4);
        understandListSave.setLayoutManager(gridLayoutManager);
        understandListSave.setAdapter(unAdapter);
        question = MyApp.getDaoSession().getQuestionDao()
                .queryBuilder()
                .where(QuestionDao.Properties.Ques_id.eq(quesId))
                .unique();

        if (question != null) {


            quesFromEdit.setText(question.getQues_from());
            tipEdit.setText(question.getTip());

            classSelectAdapter.setSelectedItem(question.getQues_class());
            classSelectAdapter.notifyDataSetChanged();


            unAdapter.setSelectedItem(question.getUnderstand());
            unAdapter.notifyDataSetChanged();
        }

    }

    @OnClick(R.id.save_button)
    public void onViewClicked() {
        Question saveQuestion;
        if (question != null) {
            saveQuestion = question;
        } else {
            saveQuestion = new Question();
        }

        if (classSelectAdapter.getSelectedItem() == null
                || classSelectAdapter.getSelectedItem().equals("")) {
            showToast("请选择学科");
            return;
        }

        if (unAdapter.getSelectedItem() == null
                || unAdapter.getSelectedItem().equals("")) {
            showToast("请选择掌握程度");
            return;
        }

        if (quesFromEdit.getText().toString().equals("")) {
            showToast("请填写错题来源");
            return;
        }

        saveQuestion.setDate(DateUtil.getCurrentTime());
        saveQuestion.setQues_id(quesId);
        saveQuestion.setUnderstand(unAdapter.getSelectedItem());
        saveQuestion.setQues_class(classSelectAdapter.getSelectedItem());
        saveQuestion.setQues_from(quesFromEdit.getText().toString());
        saveQuestion.setTip(tipEdit.getText().toString());
        saveQuestion.setReviewTime("0");
        saveQuestion.setRightTime("0");
        saveQuestion.setWrongTime("0");
        saveQuestion.setUeser(Constant.USER_ID);

        MyApp.getDaoSession().getQuestionDao()
                .insertOrReplace(saveQuestion);


        uploadQues(saveQuestion);
//        upload2(saveQuestion);

        try {

        } catch (Exception e) {

        }


        showToast("保存成功");
//        this.finish();

    }

    private void uploadQues(Question question) {
        Retrofit retrofit = RetrofitClient.getGsonRetrofitInstance();

        MultipartBody.Builder builder = new MultipartBody.Builder();

        builder.addFormDataPart("id", question.getQues_id());
        builder.addFormDataPart("userId", Constant.USER_ID);
        Log.e("MENG","userId:"+Constant.USER_ID);
        builder.addFormDataPart("subject", Constant.classMap == null?"语文":
                Constant.classMap.get(question.getQues_class()));
        builder.addFormDataPart("degree", Constant.understandMap == null?"略懂":
                Constant.understandMap.get(question.getUnderstand()));
        builder.addFormDataPart("source", question.getQues_from());
        builder.addFormDataPart("remark", question.getTip());
        builder.addFormDataPart("grade", "");


        File quesImage = new File(Constant.PATH + quesId + File.separator + "ques");
        final File[] quesFiles = quesImage.listFiles();

        builder = filesToMultipartBody(quesFiles, builder, "questionPhotos");

        File ansImage = new File(Constant.PATH + quesId + File.separator + "ans");
        File[] ansFiles = ansImage.listFiles();

        builder = filesToMultipartBody(ansFiles, builder, "answerPhotos");

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();

        retrofit.create(RetrofitService.class)
                .uploadQues(multipartBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Msg>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MENG", "e:" + e);
                        showToast("网络错误，请稍后再试");
                    }

                    @Override
                    public void onNext(Msg msg) {
                        if (msg.getSuccess().equals("false")) {
                            showToast(msg.getMessage());
                            return;
                        }
                        showToast(msg.getMessage());

                    }
                });

    }

    public MultipartBody.Builder filesToMultipartBody(File[] files, MultipartBody.Builder builder, String key) {

        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
            builder.addFormDataPart(key, file.getName(), requestBody);
        }

        return builder;
    }



}
