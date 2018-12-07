package com.chinastis.cuoti.presenter;


import com.chinastis.cuoti.listener.LoginListener;
import com.chinastis.cuoti.model.LoginModel;
import com.chinastis.cuoti.view.interfaces.LoginViewInterface;

import java.util.Map;

/********************************************
 * 文件名称: LoginPresenter.java
 * 系统名称:
 * 模块名称: 登录控制模块
 * 软件版权: 苏州伽利工程技术有限公司
 * 功能说明: 登录功能的协调
 * 系统版本: v1.0
 * 开发人员: 孟祥龙
 * 开发时间: 2017/8/15 下午3:01
 * 审核人员:
 * 相关文档:
 * 修改记录:
 *********************************************/

public class LoginPresenter implements LoginListener {

    private LoginViewInterface loginViewInterface;
    private LoginModel loginModel;

    public LoginPresenter(LoginViewInterface loginViewInterface) {
        this.loginViewInterface = loginViewInterface;
        loginModel = new LoginModel();
    }

    /**
     * desc:登录model开始登录
     */
    public void doLogin(Map<String ,String> form) {
        loginModel.loginAction(form,this);
    }

    /**
     * 登录结果回调
     * @param result 登录结果
     */
    @Override
    public void loginSuccess(String result) {
        loginViewInterface.loginComplete(result);
    }

    @Override
    public void loginFailure(String result) {
        loginViewInterface.loginComplete(result);
    }
}
