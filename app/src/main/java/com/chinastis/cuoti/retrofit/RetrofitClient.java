package com.chinastis.cuoti.retrofit;

/**
 * Created by xianglong on 2018/12/5.
 */


import com.chinastis.cuoti.util.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/********************************************
 * 文件名称: RetrofitClient.java
 * 系统名称: 芜湖巡查系统app
 * 模块名称: retrofit网络框架
 * 软件版权: 苏州伽利工程技术有限公司
 * 功能说明: retrofit客户端，获取retrofit单例
 * 系统版本: v1.0
 * 开发人员: 孟祥龙
 * 开发时间: 2017/8/8 下午3:26
 * 审核人员:
 * 相关文档:
 * 修改记录:
 *********************************************/

public class RetrofitClient {

    private static Retrofit mRetrofit;
    private static Retrofit gsonRetrofit;

    /**
     * desc 获取Retrofit实例
     * @return Retrofit实例
     */
    public static Retrofit getRetrofitInstance() {
        if (mRetrofit == null) {
            synchronized (RetrofitClient.class) {
                if(mRetrofit == null){
                    mRetrofit = new Retrofit.Builder()
                            .baseUrl(Constant.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(getClient()).build();
                }
            }

        }
        return mRetrofit;
    }

    /**
     * desc 获取Retrofit实例
     * @return Retrofit实例
     */
    public static Retrofit getGsonRetrofitInstance() {
        if (gsonRetrofit == null) {
            synchronized (RetrofitClient.class) {
                if(gsonRetrofit == null){
                    gsonRetrofit = new Retrofit.Builder()
                            .baseUrl(Constant.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(getClient()).build();
                }
            }

        }
        return gsonRetrofit;
    }

    /**
     * desc:设置超时时间
     * @return OkHttpClient
     */
    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
    }
}
