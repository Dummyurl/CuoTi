package com.chinastis.cuoti.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.chinastis.cuoti.R;
import com.chinastis.cuoti.bean.Msg;
import com.chinastis.cuoti.retrofit.RetrofitClient;
import com.chinastis.cuoti.retrofit.RetrofitService;
import com.chinastis.cuoti.util.Constant;
import com.chinastis.cuoti.view.RegisterActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xianglong on 2018/12/7.
 */

public class NumberPickerDialog extends Dialog {
    @BindView(R.id.picker_num)
    NumberPicker pickerNum;
    @BindView(R.id.cancel_num)
    Button cancelNum;
    @BindView(R.id.confirm_num)
    Button confirmNum;
    private Context mContext;

    private int num = 1;

    public NumberPickerDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public NumberPickerDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_number_picker);
        ButterKnife.bind(this);
        initNumberPicker();


    }

    private void initNumberPicker() {
        //设置最大值
        pickerNum.setMaxValue(100);
        //设置最小值
        pickerNum.setMinValue(1);
        //设置当前值
        pickerNum.setValue(1);
        //设置不可编辑
        pickerNum.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        //设置滑动监听
        pickerNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                num = newVal;
            }
        });


    }

    @OnClick({R.id.cancel_num, R.id.confirm_num})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel_num:
                this.dismiss();
                break;
            case R.id.confirm_num:

                Map<String,String> form = new HashMap<>();
                form.put("interval",String.valueOf(num));
                form.put("user_id", Constant.USER_ID);

                Retrofit retrofit = RetrofitClient.getGsonRetrofitInstance();

                retrofit.create(RetrofitService.class)
                        .setNotice(form)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())

                        .subscribe(new Subscriber<Msg>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(mContext,"网络错误，请稍后再试",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(Msg msg) {
                                if(msg.getSuccess().equals("false")) {
                                    Toast.makeText(mContext,msg.getMessage(),Toast.LENGTH_SHORT).show();

                                    return;
                                }
                                Toast.makeText(mContext,msg.getMessage(),Toast.LENGTH_SHORT).show();

                                NumberPickerDialog.this.dismiss();
                            }
                        });


                break;
        }
    }
}
