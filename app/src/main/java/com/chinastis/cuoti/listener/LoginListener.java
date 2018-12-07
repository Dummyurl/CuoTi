package com.chinastis.cuoti.listener;

/********************************************
 * 文件名称: LoginListener.java
 * 系统名称:
 * 模块名称: 监听器
 * 软件版权: 苏州伽利工程技术有限公司
 * 功能说明: 监听登录操作
 * 系统版本: v1.0
 * 开发人员: 孟祥龙
 * 开发时间: 2017/8/15 下午3:04
 * 审核人员:
 * 相关文档:
 * 修改记录:
 *********************************************/

public interface LoginListener {
    void loginSuccess(String result);
    void loginFailure(String result);
}
