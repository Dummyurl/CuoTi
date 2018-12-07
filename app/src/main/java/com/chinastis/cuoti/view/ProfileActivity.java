package com.chinastis.cuoti.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.chinastis.cuoti.MyApp;
import com.chinastis.cuoti.R;
import com.chinastis.cuoti.bean.Msg;
import com.chinastis.cuoti.bean.User;
import com.chinastis.cuoti.database.dao.UserDao;
import com.chinastis.cuoti.retrofit.RetrofitClient;
import com.chinastis.cuoti.retrofit.RetrofitService;
import com.chinastis.cuoti.util.Constant;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.mobile_profile)
    EditText mobileProfile;
    @BindView(R.id.phone_profile)
    EditText phoneProfile;
    @BindView(R.id.qq_profile)
    EditText qqProfile;
    @BindView(R.id.mail_profile)
    EditText mailProfile;
    @BindView(R.id.school_profile)
    EditText schoolProfile;
    @BindView(R.id.grade_profile)
    EditText gradeProfile;
    @BindView(R.id.class_profile)
    EditText classProfile;
    @BindView(R.id.year_profile)
    EditText yearProfile;
    @BindView(R.id.confirm_profile)
    Button confirmProfile;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        user = MyApp.getDaoSession().getUserDao()
                .queryBuilder()
                .where(UserDao.Properties.UserId.eq(Constant.USER_ID))
                .unique();
        if (user !=null) {
            qqProfile.setText(user.getQq());
            mobileProfile.setText(user.getMobile());
            phoneProfile.setText(user.getPhone());
            mailProfile.setText(user.getMail());
            schoolProfile.setText(user.getSchool());
            gradeProfile.setText(user.getGrade());
            classProfile.setText(user.getClassNum());
            yearProfile.setText(user.getYear());
        }

    }

    @OnClick(R.id.confirm_profile)
    public void onViewClicked() {
        Map<String,String> form = new HashMap<>();
        form.put("id",Constant.USER_ID);
        form.put("name",Constant.USER);
        form.put("phone",mobileProfile.getText().toString());
        form.put("telephone",phoneProfile.getText().toString());
        form.put("qq",qqProfile.getText().toString());
        form.put("schoolYear",yearProfile.getText().toString());
        form.put("classNumber",classProfile.getText().toString());
        form.put("mail",mailProfile.getText().toString());
        form.put("schoolName",schoolProfile.getText().toString());
        form.put("classLevel",gradeProfile.getText().toString());

        RetrofitClient.getGsonRetrofitInstance()
                .create(RetrofitService.class)
                .changeProfile(form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Subscriber<Msg>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("网络错误，请稍后再试");
                    }

                    @Override
                    public void onNext(Msg msg) {
                        if(msg.getSuccess().equals("false")) {
                            showToast(msg.getMessage());
                            return;
                        }

                        User userUpdate;
                        if (user!=null) {
                            userUpdate = user;
                        } else {
                            userUpdate = new User();
                        }
                        userUpdate.setUserId(Constant.USER_ID);
                        userUpdate.setName(Constant.USER);
                        userUpdate.setMobile(mobileProfile.getText().toString());
                        userUpdate.setPhone(phoneProfile.getText().toString());
                        userUpdate.setQq(qqProfile.getText().toString());
                        userUpdate.setYear(yearProfile.getText().toString());
                        userUpdate.setClassNum(classProfile.getText().toString());
                        userUpdate.setMail(mailProfile.getText().toString());
                        userUpdate.setSchool(schoolProfile.getText().toString());
                        userUpdate.setGrade(gradeProfile.getText().toString());
                        MyApp.getDaoSession().getUserDao().insertOrReplace(userUpdate);

                        showToast(msg.getMessage());
                        ProfileActivity.this.finish();
                    }
                });
    }
}
