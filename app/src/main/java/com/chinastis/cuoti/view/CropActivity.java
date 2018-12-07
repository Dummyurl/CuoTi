package com.chinastis.cuoti.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.chinastis.cuoti.R;
import com.chinastis.cuoti.util.Constant;
import com.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CropActivity extends AppCompatActivity {

    @BindView(R.id.image_crop)
    CropImageView imageCrop;
    private String imagePath;
    private String quesId;
    private boolean isNew;
    private boolean isQues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        ButterKnife.bind(this);


        imagePath = getIntent().getStringExtra("imagePath");
        quesId = getIntent().getStringExtra("quesId");
        isNew = getIntent().getBooleanExtra("isNew",true);
        isQues =  getIntent().getBooleanExtra("isQues",true);

        if (isNew) {
            imageCrop.setImageBitmap(BitmapFactory.decodeFile(Constant.PATH
                    + quesId+File.separator+"ques"+File.separator+quesId+"0.jpg"));
        } else {
            if (imagePath != null && !"".equals(imagePath)) {
                imageCrop.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            }
        }
    }

    @OnClick(R.id.confirm_crop)
    public void onViewClicked() {
//        imageCrop.setImageBitmap(imageCrop.getCroppedImage());

        FileOutputStream fos = null;


        try {

            if (isNew) {
                fos = new FileOutputStream(new File(Constant.PATH
                        + quesId+File.separator+"ques"+File.separator+quesId+"0.jpg"));
                imageCrop.getCroppedImage().compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();

                fos = new FileOutputStream(new File(Constant.PATH
                        + quesId+File.separator+"ans"+File.separator+quesId+"0.jpg"));
                imageCrop.getCroppedImage().compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                Intent intent = new Intent(this,SaveActivity.class);
                intent.putExtra("quesId",quesId);
                intent.putExtra(Constant.TITLE,"错题编辑");
                startActivity(intent);

            } else {

                fos = new FileOutputStream(new File(imagePath));
                imageCrop.getCroppedImage().compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
            }


            this.finish();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
