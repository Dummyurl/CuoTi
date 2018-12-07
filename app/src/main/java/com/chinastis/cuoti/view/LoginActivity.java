package com.chinastis.cuoti.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chinastis.cuoti.MainActivity;
import com.chinastis.cuoti.R;
import com.chinastis.cuoti.presenter.LoginPresenter;
import com.chinastis.cuoti.util.Constant;
import com.chinastis.cuoti.view.interfaces.LoginViewInterface;
import com.chinastis.cuoti.widget.MyProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends AppCompatActivity implements LoginViewInterface {

    @BindView(R.id.name_login)
    EditText nameLogin;
    @BindView(R.id.password_login)
    EditText passwordLogin;
    @BindView(R.id.remember_pwd)
    CheckBox rememberPwd;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.version_login)
    TextView versionLogin;


    private LoginPresenter loginPresenter;
    private SharedPreferences sp;
    private MyProgressDialog progressDialog;
    private String account;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this);

        ButterKnife.bind(this);
        initEvent();

        initAccount();


    }

    /**
     * desc：初始化事件
     */
    private void initEvent() {

        progressDialog = new MyProgressDialog(this);
        progressDialog.setMessage("正在登录,请稍后...");
        progressDialog.setCancelable(false);


        rememberPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                isRememberPwd = isChecked;
            }
        });
    }

    /**
     * desc：初始化帐号信息
     */
    private void initAccount() {
        versionLogin.setText("版本号:"+getVersionName());
        sp = getSharedPreferences("sp", MODE_PRIVATE);
        nameLogin.setText(sp.getString("account", ""));
        passwordLogin.setText(sp.getString("password", ""));

        if (!nameLogin.getText().toString().equals("")) {
            rememberPwd.setChecked(true);
        }
    }

    /**
     * desc:保存帐号信息
     */
    private void saveAccount() {
        JPushInterface.setAlias(getApplicationContext(),0,Constant.USER);
        account = nameLogin.getText().toString();
        password = passwordLogin.getText().toString();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("account", account);
        editor.putString("password", password);
        editor.putString("userId",Constant.USER_ID);
        editor.putBoolean("isLogin",true);
        editor.apply();
    }


    @Override
    public void loginComplete(String result) {
        progressDialog.dismiss();
        Log.e("MENG", "login ---- result：" + result);

        try {
            JSONObject jsonObject = new JSONObject(result);
            String state = jsonObject.getString("success");
            if (state.equals("true")) {
                Constant.USER = nameLogin.getText().toString();


                JSONObject data = jsonObject.getJSONObject("data");
                Constant.USER_ID = data.getString("id");

                saveAccount();

                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));

                this.finish();

            } else {
                Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
        }


    }

    @OnClick(R.id.login_button)
    public void onViewClicked() {

                account = nameLogin.getText().toString();
                password = passwordLogin.getText().toString();

                if (account.equals("") || password.equals("")) {
                    Toast.makeText(this, "请填写帐号或密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, String> params = new HashMap<>();
                params.put("userName", account);
                params.put("passWord", password);

                loginPresenter.doLogin(params);
                progressDialog.setMessage("正在登录,请稍后...");
                progressDialog.show();

    }


    public String getVersionName() {
        String versionCode = "";
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            versionCode = info.versionName;
        } catch (Exception e) {

        }
        return versionCode;
    }

    public void register(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
    }
}
