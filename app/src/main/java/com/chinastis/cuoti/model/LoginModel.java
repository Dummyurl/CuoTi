package com.chinastis.cuoti.model;



import com.chinastis.cuoti.listener.LoginListener;
import com.chinastis.cuoti.retrofit.RetrofitClient;
import com.chinastis.cuoti.retrofit.RetrofitService;

import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/********************************************
 * 文件名称: LoginModel.java
 * 系统名称:
 * 模块名称: 登录model模块
 * 软件版权: 苏州伽利工程技术有限公司
 * 功能说明: 登录业务
 * 系统版本: v1.0
 * 开发人员: 孟祥龙
 * 开发时间: 2017/8/15 下午3:01
 * 审核人员:
 * 相关文档:
 * 修改记录:
 *********************************************/

public class LoginModel {

    /**
     * 系统登录
     * @param form 登录参数表单
     * @param loginListener 登录监听器
     */
    public void loginAction(Map<String ,String > form, final LoginListener loginListener) {

        RetrofitClient.getRetrofitInstance()
                .create(RetrofitService.class)
                .login(form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginListener.loginFailure("internet");
                    }

                    @Override
                    public void onNext(String s) {
                        loginListener.loginSuccess(s);
                    }
                });

    }

}
