package com.chinastis.cuoti.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.chinastis.cuoti.R;
import com.chinastis.cuoti.bean.Msg;
import com.chinastis.cuoti.retrofit.RetrofitClient;
import com.chinastis.cuoti.retrofit.RetrofitService;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.name_profile)
    EditText nameProfile;
    @BindView(R.id.password_profile)
    EditText passwordProfile;
    @BindView(R.id.password2_profile)
    EditText password2Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register)
    public void onViewClicked() {
        if (passwordProfile.getText().toString().equals("") || password2Profile.getText().toString().equals("")) {
            showToast("请输入密码");
            return;
        }

        if(!passwordProfile.getText().toString().equals(password2Profile.getText().toString())) {
            showToast("两次输入的密码不一致");
            return;
        }

        if (nameProfile.getText().toString().equals("")) {
            showToast("请输入用户名");
            return;
        }

        HashMap<String,String> profileMess = new HashMap<>();

        profileMess.put("userName",nameProfile.getText().toString());
        profileMess.put("userId",nameProfile.getText().toString());
        profileMess.put("pwd",passwordProfile.getText().toString());

        RetrofitClient.getGsonRetrofitInstance()
                .create(RetrofitService.class)
                .registe(profileMess)
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
                        showToast(msg.getMessage());
                        RegisterActivity.this.finish();
                    }
                });

    }
}
