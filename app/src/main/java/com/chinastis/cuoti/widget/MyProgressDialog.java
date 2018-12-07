package com.chinastis.cuoti.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.chinastis.cuoti.R;

/********************************************
 * 文件名称: MyProgressDialog.java
 * 系统名称:
 * 模块名称: 自定义控件
 * 软件版权: 苏州伽利工程技术有限公司
 * 功能说明: 自定义进度对话框
 * 系统版本: v1.0
 * 开发人员: 孟祥龙
 * 开发时间: 2017/8/17 下午1:36
 * 审核人员:
 * 相关文档:
 * 修改记录:
 *********************************************/

public class MyProgressDialog extends Dialog {

    private String message;
    private TextView messageText;

    public MyProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_my_progress);

        messageText = (TextView) findViewById(R.id.message_progress);
        messageText.setText(message);

    }

    public void setMessage(String message) {
        this.message = message;

    }

    @Override
    public void show() {
        if (messageText!=null)
            messageText.setText(message);
        super.show();
    }
}
