package com.chinastis.cuoti.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;

import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.chinastis.cuoti.R;
import com.chinastis.cuoti.util.Constant;
import com.chinastis.cuoti.util.DateUtil;
import com.chinastis.cuoti.widget.MyCameraView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Timer;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/********************************************
 * 文件名称: CameraActivity.java
 * 系统名称:
 * 模块名称: 任务核查
 * 软件版权: 苏州伽利工程技术有限公司
 * 功能说明: 自定义拍照界面
 * 系统版本: v1.0
 * 开发人员: 孟祥龙
 * 开发时间: 2017/8/23 上午11:30
 * 审核人员:
 * 相关文档:
 * 修改记录:
 *********************************************/

public class CameraActivity extends Activity implements MyCameraView.CameraListener {
    @BindView(R.id.back_camera)
    TextView backCamera;
    @BindView(R.id.top_layout_camera)
    FrameLayout topLayoutCamera;
    @BindView(R.id.surface_camera)
    SurfaceView surfaceCamera;
    @BindView(R.id.shutter_camera)
    ImageView shutterCamera;
    @BindView(R.id.yes_camera)
    ImageView yesCamera;
    @BindView(R.id.no_camera)
    ImageView noCamera;
    @BindView(R.id.bottom_layout_camera)
    RelativeLayout bottomLayoutCamera;
    @BindView(R.id.bottom_bar_camera)
    FrameLayout bottomBarCamera;
    @BindView(R.id.focus_camera)
    TextView focusCamera;


    private String quesId;
    private String imageQuesPath;
    private String imageAnsPath;
    private byte[] imageData;
    private MyCameraView myCameraView;

    private boolean isRecording;

    private boolean isQuse;
    private String imageFileName;
    private boolean isNew;


    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ButterKnife.bind(this);
        isRecording = false;
        isQuse = getIntent().getBooleanExtra("isQues", true);
        quesId = getIntent().getStringExtra("quesId");
        if (quesId == null || quesId.equals("")) {
            quesId = getQuesId();
            isNew = true;
        }


        imageQuesPath = Constant.PATH
                + quesId + File.separator + "ques" + File.separator;

        imageAnsPath = Constant.PATH
                + quesId + File.separator + "ans" + File.separator;


        initView();

    }


    /**
     * 初始化View
     */
    private void initView() {
        myCameraView = new MyCameraView(this, surfaceCamera);
        myCameraView.setCameraListener(this);

    }

    /**
     * 初始化View点击事件
     */
    @OnClick({R.id.back_camera,
            R.id.shutter_camera,
            R.id.yes_camera,
            R.id.no_camera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_camera:
                if (isRecording) {
                    Toast.makeText(this, "正在录制视频...", Toast.LENGTH_SHORT).show();
                    return;
                }
                CameraActivity.this.finish();
                break;

            case R.id.shutter_camera:

                myCameraView.capture();
                shutterCamera.setEnabled(false);

                break;

            case R.id.yes_camera:
                bottomBarCamera.setVisibility(View.GONE);

                if (isNew) {
                    saveImage(imageAnsPath + quesId + "0.jpg");
                    saveImage(imageQuesPath + quesId + "0.jpg");
                } else {
                    if (isQuse) {
                        File quesDir = new File(imageQuesPath);
                        int fileCount = quesDir.listFiles().length;
                        imageFileName = imageQuesPath + quesId + (fileCount + 1) + ".jpg";
                        saveImage(imageQuesPath + quesId + (fileCount + 1) + ".jpg");
                    } else {
                        File quesDir = new File(imageAnsPath);
                        int fileCount = quesDir.listFiles().length;
                        imageFileName = imageAnsPath + quesId + (fileCount + 1) + ".jpg";
                        saveImage(imageAnsPath + quesId + (fileCount + 1) + ".jpg");
                    }
                }

//                compressImage(imagePath,8);
                Intent cropIntent = new Intent(CameraActivity.this, CropActivity.class);
                cropIntent.putExtra("imagePath", imageFileName);
                cropIntent.putExtra("quesId", quesId);
                cropIntent.putExtra("isNew", isNew);
                cropIntent.putExtra("isQues", isQuse);
                startActivity(cropIntent);

                this.finish();
                break;

            case R.id.no_camera:
                myCameraView.startPreview();
                bottomBarCamera.setVisibility(View.GONE);
                shutterCamera.setEnabled(true);
                break;

        }
    }


    /**
     * 保存图片
     */
    private void saveImage(String filePath) {
        if (filePath == null) {
            return;
        }

        File file = new File(filePath);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(imageData);
            fos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 以下camera操作的回调方法，如照片数据获取，聚焦完成
     */
    @Override
    public void pictureTaken(byte[] data) {
        imageData = data;
        bottomBarCamera.setVisibility(View.VISIBLE);


    }

    @Override
    public void startFocus() {
        focusCamera.setEnabled(true);
        focusCamera.setVisibility(View.VISIBLE);
    }

    @Override
    public void autoFocusSuccess() {
        focusCamera.setEnabled(false);
        focusCamera.startAnimation(AnimationUtils.loadAnimation(this, R.anim.camera_focus));

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                focusCamera.setVisibility(View.GONE);
            }
        }, 500);
    }

    @Override
    public void zooming(int zoom) {
    }

    @Override
    public void cameraInit(Camera camera) {
        setCameraDisplayOrientation(this, 0, camera);

    }


    @Override
    protected void onDestroy() {
        myCameraView.destroy();


        super.onDestroy();
    }


    /**
     * desc：相机预览方向配置
     */
    public void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();

        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }


//    private File getMediaFile() {
//        File newFile = new File(videoPath);
//        if (!newFile.getParentFile().exists()) {
//            newFile.getParentFile().mkdirs();
//        }
//        return newFile;
//    }


    public String addZeroUtil(int number) {
        if (number < 10) {
            StringBuffer numString = new StringBuffer();
            return numString.append("0").append(number).toString();
        }

        return String.valueOf(number);
    }


    public String getQuesId() {
        return DateUtil.getCurrentTime2();
    }
}
