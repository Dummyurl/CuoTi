package com.chinastis.cuoti.widget;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


import java.io.File;
import java.io.IOException;
import java.util.List;

/********************************************
 * 文件名称: MyCameraView.java
 * 系统名称: 动态巡查系统
 * 模块名称: 自定义相机类
 * 软件版权: 苏州伽利工程技术有限公司
 * 功能说明: 实现自定义拍照功能，放大缩小，点击聚焦
 * 系统版本: v1.0
 * 开发人员: 孟祥龙
 * 开发时间: 2017/8/21 上午9:38:52
 * 审核人员:
 * 相关文档:
 * 修改记录:
 *********************************************/
public class MyCameraView implements SurfaceHolder.Callback ,View.OnTouchListener {

    private Context mContext;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private Camera mCamera;

    private CameraListener listener;
    private float startPoint;
    private boolean isZooming = false ;
    private int mOrientation;
    private OrientationEventListener mOrEventListener;

    private MediaRecorder recorder;
    private boolean isRecording;
    private int rotation = 90;

    public MyCameraView(Context context, SurfaceView surfaceView) {

        this.mContext = context;
        this.mSurfaceView = surfaceView;
        this.mSurfaceView.setOnTouchListener(this);
        this.mHolder = mSurfaceView.getHolder();
        this.mHolder.addCallback(this);
        this.mHolder.setKeepScreenOn(true);

    }

    private void setCamera() {
        mCamera = Camera.open();

        listener.cameraInit(mCamera);
    }

    public Camera getCamera() {
        return mCamera;
    }

    public void setCameraListener (CameraListener cameraListener)
    {
        this.listener = cameraListener;
    }

    /**
     * 拍照
     */
    public void capture() {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                mCamera.stopPreview();
                mOrEventListener.disable();
                if(listener != null) {
                    listener.pictureTaken(data);
                }
            }
        });
    }

    /**
     * 开始预览
     */
    public void startPreview(){
        if(mCamera!=null) {
            mCamera.startPreview();
            mOrEventListener.enable();
        }
    }

    /**
     * 实现相机自动聚焦
     */
    private void focus() {
        listener.startFocus();
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if(listener != null) {
                    listener.autoFocusSuccess();
                }
                mCamera.cancelAutoFocus();
            }
        });
    }

    /**
     * 相机预览界面缩放
     */
    private void setZoom(int scale) {
        Camera.Parameters parameters = mCamera.getParameters();

        if(parameters.isZoomSupported()){
            parameters.setZoom(scale);
            mCamera.setParameters(parameters);

        }
    }

    /**
     * SurfaceHolder 回调函数
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setCamera();
        if(mCamera != null) {
            try {
                setCameraParameters();
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
                startOrientationChangeListener();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        if(recorder != null) {
            stopRecord();
        }
    }

    /**
     * 相机界面的触摸回调函数，实现放大缩小，聚焦功能
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(event.getPointerCount() > 1 ){
                    startPoint = distance(event);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if(event.getPointerCount() > 1 ){
                    float endPoint = distance(event);
                    int scale = (int)((endPoint - startPoint)/10);
                    int zoom = scale+mCamera.getParameters().getZoom();
                    if (zoom<=mCamera.getParameters().getMaxZoom() && zoom>=0) {
                        if(isZooming) {
                            setZoom(zoom);
                            listener.zooming(zoom);
                        }
                    }
                    startPoint = endPoint;
                    isZooming =true;

                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
                isZooming = false;
                if(event.getPointerCount()<2) {
                    focus();
                }
                break;
        }
        return true;
    }

    /**
     * 计算两个手指间的距离
     */
    private float distance(MotionEvent event) {

        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);

        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 启动屏幕朝向改变监听函数 用于在屏幕横竖屏切换时改变保存的图片的方向
     */
    private void startOrientationChangeListener() {
        mOrEventListener = new OrientationEventListener(mContext, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int rotation) {
                if (((rotation >= 0) && (rotation <= 45)) || (rotation > 315)) {
                    rotation = 0;
                } else if ((rotation > 45) && (rotation <= 135)) {
                    rotation = 90;
                } else if ((rotation > 135) && (rotation <= 225)) {
                    rotation = 180;
                } else if ((rotation > 225) && (rotation <= 315)) {
                    rotation = 270;
                } else {
                    rotation = 0;
                }
                if (rotation == mOrientation)
                    return;
                mOrientation = rotation;
                updateCameraOrientation(mOrientation);
            }
        };
        if (mOrEventListener.canDetectOrientation()) {
            mOrEventListener.enable();
        } else {
            mOrEventListener.disable();
        }
    }

    /**
     * desc 更新所拍摄图片的方向
     * @param orientation 旋转角度
     */
    private void updateCameraOrientation(int orientation) {
        if(mCamera == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        rotation = 90 + orientation == 360 ? 0 : 90+orientation;

        Log.e("MENG","oritation:"+orientation+"----rotation:"+rotation);

        if (rotation !=360 && rotation<360) {
            parameters.setRotation(rotation);
            mCamera.setParameters(parameters);
        } else {
            Log.e("MENG","360");
        }



    }

    /**
     * 设置照相机参数
     */
    private void setCameraParameters() {

        int width = 0;
        int height = 0;

        Camera.Parameters parameters = mCamera.getParameters();

        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setJpegQuality(100);
        parameters.setJpegThumbnailQuality(100);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.setRotation(90);

        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();

        if (sizeList.size() > 1) {
            for (Camera.Size cur : sizeList) {

                if (cur.width >= width && cur.height >= height) {
                    width = cur.width;
                    height = cur.height;
                }
            }
            parameters.setPictureSize(width,height);
        }
        mCamera.setParameters(parameters);
    }

    public void destroy() {
        mOrEventListener.disable();
        mOrEventListener = null;
    }

    public void initRecorder(String fileName) {
//        Camera.Parameters parameters = mCamera.getParameters();
//        parameters.setRotation(mOrientation-90);
//        mCamera.setParameters(parameters);

        mCamera.unlock();
        mOrEventListener.disable();
        isRecording = true;
        recorder = new MediaRecorder();

        recorder.reset();
        recorder.setCamera(mCamera);
        recorder.setOrientationHint(rotation);

        Log.e("MENG","ori:"+rotation);



        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        File videoFile = new File(fileName);
        if(!videoFile.exists()) {
            try {
                videoFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        recorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));

        recorder.setMaxDuration(600000);
        recorder.setOutputFile(videoFile.getAbsolutePath());
        recorder.setPreviewDisplay(mHolder.getSurface());

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            isRecording = false;
        }

    }

    public void startRecord(String fileName) {
        initRecorder(fileName);
        recorder.start();
    }

    //停止录像
    public void stopRecord(){
        if(recorder !=null){
            if(isRecording){
                recorder.stop();
                recorder.reset();
                recorder.release();
                recorder =null;
                isRecording = false;

                mCamera.stopPreview();

            }
        }
    }

    public interface CameraListener {

        void pictureTaken(byte[] data);

        void startFocus();

        void autoFocusSuccess();

        void zooming(int zoom);

        void cameraInit(Camera camera);


    }
}
