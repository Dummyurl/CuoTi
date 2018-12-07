package com.chinastis.cuoti.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.chinastis.cuoti.R;
import com.chinastis.cuoti.util.ActivityManager;
import com.chinastis.cuoti.util.Constant;
import com.chinastis.cuoti.widget.NumberPickerDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SelfActivity extends BaseActivity {

    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.profile)
    TextView profile;
    @BindView(R.id.change_password)
    TextView changePassword;
    @BindView(R.id.help_setting)
    TextView helpSetting;
    @BindView(R.id.exit_setting)
    Button exitSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.profile_image, R.id.profile, R.id.change_password, R.id.help_setting, R.id.exit_setting})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.profile_image:

                break;
            case R.id.profile:
                intent.setClass(this,ProfileActivity.class);
                intent.putExtra(Constant.TITLE,"修改个人资料");
                startActivity(intent);
                break;
            case R.id.change_password:
                intent.setClass(this,PasswordChangeActivity.class);
                intent.putExtra(Constant.TITLE,"修改密码");
                startActivity(intent);
                break;
            case R.id.help_setting:
                NumberPickerDialog dialog = new NumberPickerDialog(this);
                dialog.show();
                break;
            case R.id.exit_setting:
                SharedPreferences sp = getSharedPreferences("sp",MODE_PRIVATE);
                SharedPreferences.Editor editor =sp.edit();
                editor.putBoolean("isLogin",false);
                editor.apply();

                intent.setClass(this,LoginActivity.class);
                startActivity(intent);
                ActivityManager.getManager().removeAllActivities();
                this.finish();
                break;
        }
    }
}
