package com.chinastis.cuoti.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinastis.cuoti.R;
import com.chinastis.cuoti.util.ActivityManager;
import com.chinastis.cuoti.util.Constant;

/********************************************
 * 文件名称: BaseActivity.java
 * 系统名称:
 * 模块名称: 基础界面
 * 软件版权: 苏州伽利工程技术有限公司
 * 功能说明: 基础界面
 * 系统版本: v1.0
 * 开发人员: 孟祥龙
 * 开发时间: 2017/8/16 上午11:57
 * 审核人员:
 * 相关文档:
 * 修改记录:
 *********************************************/
public class BaseActivity extends AppCompatActivity {

    private Intent fromIntent;
    private FrameLayout contentView;
    private TextView titleText;
    private String title;
    private ImageView iconEnd;
    private TextView textEnd;
    private TextView lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(0x80000000,0x80000000);
        setContentView(R.layout.activity_base);

        fromIntent = getIntent();
        contentView = (FrameLayout) findViewById(R.id.content_base);
        lock = (TextView) findViewById(R.id.lock);

        initToolbar();

        ActivityManager.getManager().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        ActivityManager.getManager().removeActivity(this);
        super.onDestroy();

    }

    /**
     * desc: 初始化界面顶部工具栏
     */
    private void initToolbar() {
        titleText = (TextView) findViewById(R.id.title_base);
        title = fromIntent.getStringExtra(Constant.TITLE);
        titleText.setText(title == null ? "错题本" : title);

        iconEnd = (ImageView) findViewById(R.id.navigation_icon_end);
        textEnd = (TextView) findViewById(R.id.navigation_text_end);

        ImageView navigationIcon = (ImageView) findViewById(R.id.navigation_icon_base);
        navigationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BaseActivity.this.finish();
                onBackPressed();
            }
        });

    }

    /**
     * desc: 添加内容view
     * @param view 主体view的id
     */
    public void addContentView(int view) {
        View localView = LayoutInflater.from(this).inflate(view, null);
        this.contentView.removeAllViews();
        this.contentView.addView(localView, new LinearLayout.LayoutParams(-1, -1));
    }

    /**
     * desc: 吐司
     * @param message 吐司信息
     */
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    /**
     * desc: 对话框提示
     * @param message 提示信息
     */
    public void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("确定",null);
        builder.create().show();
    }

    /**
     * desc: 设置标题
     * @param title 标题
     */
    public void setTitle(String title) {
        titleText.setText(title);
    }

    public String getTitleText() {
        return title;
    }

    /**
     * 设置副图标
     * @param icon 图标
     */
    public void setEndIcon(Drawable icon) {
        iconEnd.setVisibility(View.VISIBLE);
        iconEnd.setImageDrawable(icon);
    }

    public void setEndIconClickEvent(View.OnClickListener listener) {
        iconEnd.setOnClickListener(listener);
    }

    public ImageView getIconEnd() {
        return iconEnd;
    }

    /**
     * 设置副标题
     * @param title 标题
     */
    public void setEndText(String title) {
        textEnd.setVisibility(View.VISIBLE);
        textEnd.setText(title);
    }

    public void setEndTextClickEvent(View.OnClickListener listener) {
        textEnd.setOnClickListener(listener);
    }

    /**
     * 获取SP存储
     * @return SharedPreferences
     */
    public SharedPreferences getMySp() {
        return getSharedPreferences("sp", MODE_PRIVATE);
    }

    /**
     * 锁定界面
     * @param isLock
     */
    public void lockTop(boolean isLock){
        if (isLock) {
            lock.setVisibility(View.VISIBLE);
        } else {
            lock.setVisibility(View.GONE);
        }

    }

}
