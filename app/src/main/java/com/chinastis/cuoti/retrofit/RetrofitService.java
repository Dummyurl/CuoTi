package com.chinastis.cuoti.retrofit;

import com.chinastis.cuoti.bean.Msg;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/********************************************
 * 文件名称: RetrofitService.java
 * 系统名称: 芜湖巡查系统app
 * 模块名称: retrofit网络框架
 * 软件版权: 苏州伽利工程技术有限公司
 * 功能说明: retrofit服务接口
 * 系统版本: v1.0
 * 开发人员: 孟祥龙
 * 开发时间: 2017/8/8 下午3:29
 * 审核人员:
 * 相关文档:
 * 修改记录:
 *********************************************/

public interface RetrofitService {
    //登录
    @FormUrlEncoded
    @POST("android/login.do ")
    Observable<String> login(@FieldMap Map<String, String> form);

    @FormUrlEncoded
    @POST("android/register.do ")//注册
    Observable<Msg> registe(@FieldMap Map<String, String> form);

    @FormUrlEncoded
    @POST("android/updatePassword.do")//修改密码
    Observable<Msg> changePassword(@FieldMap Map<String, String> form);

    @FormUrlEncoded
    @POST("android/modifyUser.do")//修改个人资料
    Observable<Msg> changeProfile(@FieldMap Map<String, String> form);


    @POST("android/newProblem.do")//新增错题
    Observable<Msg> uploadQues(@Body MultipartBody multipartBody);

    @FormUrlEncoded
    @POST("android/updateInterval.do")//增加提醒
    Observable<Msg> setNotice(@FieldMap Map<String, String> form);


    @FormUrlEncoded
    @POST("android/delete.do")//删除错题
    Observable<Msg> deleteQuestionById(@FieldMap Map<String, String> form);

    @FormUrlEncoded
    @POST("android/initPaper.do")//提交组卷打印
    Observable<Msg> printQuestion(@FieldMap Map<String, String> form);



    @POST("mobile/trace.do")
    Observable<String> postLocation(@Query("pId") String id, @Query("devId") String device, @Query("traceX") String x, @Query("traceY") String y);


    @POST("mobile/keyLine.do")
    Observable<String> postImportantLocation(@Query("pId") String id,
                                             @Query("devId") String device,
                                             @Query("traceX") String x,
                                             @Query("traceY") String y,
                                             @Query("groupId") String groupId,
                                             @Query("qyName") String qyName,
                                             @Query("userNames") String userNames);







}
