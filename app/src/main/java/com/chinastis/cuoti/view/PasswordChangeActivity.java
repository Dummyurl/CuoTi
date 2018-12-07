package com.chinastis.cuoti.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.chinastis.cuoti.R;
import com.chinastis.cuoti.bean.Msg;
import com.chinastis.cuoti.retrofit.RetrofitClient;
import com.chinastis.cuoti.retrofit.RetrofitService;
import com.chinastis.cuoti.util.Constant;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PasswordChangeActivity extends BaseActivity {

    @BindView(R.id.password_old_password)
    EditText passwordOldPassword;
    @BindView(R.id.password_password)
    EditText passwordPassword;
    @BindView(R.id.password_password2)
    EditText passwordPassword2;
    @BindView(R.id.change_password)
    Button changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_password_change);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.change_password)
    public void onViewClicked() {
        if(!passwordPassword.getText().toString().endsWith(passwordPassword2.getText().toString())) {
            showToast("两次密码输入不一致");
            return;
        }

        Map<String ,String> params = new HashMap<>();
            params.put("userId", Constant.USER_ID);
        params.put("newPwd",passwordPassword.getText().toString());
//        params.put("oldPwd",passwordOldPassword.getText().toString());

        Retrofit retrofit = RetrofitClient.getGsonRetrofitInstance();

        retrofit.create(RetrofitService.class)
                .changePassword(params)
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
                        PasswordChangeActivity.this.finish();
                    }
                });

    }

}
