package com.bupt.ZigbeeResolution.http;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpControl {
    static private Cookie ck;
    private String host = "10.108.218.64";
    private static String session;
    public static String id;
    public String deviceToken;

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private static final MediaType js = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            .build();

    public void httplogin() throws IOException {
        RequestBody loginBody = RequestBody.create(js, "{\"userName\":\"Gantch@bupt.edu.cn\",\"passwd\":\"password\"}");

        final Request requestRegister = new Request.Builder().url("http://10.108.218.64/api/user/login").header("Accept", "text/plain, */*; q=0.01").addHeader("Connection", "keep-alive").addHeader("Content-Type", "application/json; charset=UTF-8").post(loginBody).build();

        Response response = mOkHttpClient.newCall(requestRegister).execute();
        if (response.isSuccessful()) {
            Headers headers = response.headers();
            ck = cookieStore.get(host).get(0);

            String sessionStr = ck.toString();
            session = sessionStr.substring(0, sessionStr.indexOf(";"));

            System.out.println("Login ck is :" + ck);
            System.out.println("Login session is :" + session);
        }
    }
 /*
    创建新设备的post请求
     */

    public String httpcreate(String devicename,String gatewayName, String type, String model) throws Exception{

        //请求体
        JSONObject obj = new JSONObject();
        obj.put("name",devicename);
        //obj.put("lifetime", "NaN");
        obj.put("tenantId",2);
        if(!gatewayName.equals("0")) {
            obj.put("parentDeviceId", gatewayName);
        }
        obj.put("manufacture","Gantch");
        obj.put("deviceType", type);
        obj.put("model",model);

        RequestBody bodyCreate = RequestBody.create(js, obj.toString());

        //创建一个Request Request是OkHttp中访问的请求，Builder是辅助类。Response即OkHttp中的响应。
        Request requestCreate = new Request.Builder()
                .url("http://47.105.120.203:30080/api/v1/deviceaccess/device")
                .post(bodyCreate)
                .addHeader("Accept","application/json, text/plain, */*")
//                .addHeader("Accept","text/plain, */*, q=0.01")
                .addHeader("Connection","keep-alive")
                .addHeader("Content-Type","application/json;charset=UTF-8")
                //.addHeader("Cookie",session.toString())
                .build();
            //得到一个call对象
        Response response = mOkHttpClient.newCall(requestCreate).execute();
        if (response.isSuccessful()){
            String result = response.body().string();
            System.out.println(result);

            JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
            id = jsonObject.get("id").getAsString();
            System.out.println("id is :"+id);
            return id;
        }else{
            //httplogin();
            System.out.println(response.body().string());
            return null;
        }
    }

    /*
    查找令牌的get请求
     */

    public String httpfind(String id)throws Exception {

        //创建一个Request Request是OkHttp中访问的请求，Builder是辅助类。Response即OkHttp中的响应。
        Request requestCreate = new Request.Builder()
                .url("http://47.105.120.203:30080/api/v1/deviceaccess/credentialbyid/" + id.toString())
                .get()
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Connection", "keep-alive")
                //.addHeader("Cookie", session.toString())
                .build();
        //得到一个call对象
        Response response = mOkHttpClient.newCall(requestCreate).execute();
        if (response.isSuccessful()) {

            String result = response.body().string();
//           Log.e("http", "find_response : " + result);

            JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
            deviceToken = jsonObject.get("deviceToken").getAsString();
            System.out.println("find_token is :"+deviceToken);
            return deviceToken;
        }
        return null;
    }

    /**
     * 查找设备的 Get 请求
     * @param id
     * @return
     * @throws Exception
     */
    public String httpGetDevice(String id) throws Exception{
        Request requestCreate = new Request.Builder()
                .url("http://47.105.120.203:30080/api/v1/deviceaccess/device/" + id)
                .get()
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Connection", "keep-alive")
                //.addHeader("Cookie", session.toString())
                .build();
        Response response = mOkHttpClient.newCall(requestCreate).execute();
        if (response.isSuccessful()) {

            String result = response.body().string();
            return result;
        }
        return null;
    }

    /**
     * 更新设备的 Post 请求
     * @param deviceInfo
     * @return
     * @throws IOException
     */
    public String UpdateDevice(String deviceInfo) throws IOException {

        RequestBody bodyCreate = RequestBody.create(js, deviceInfo);

        Request requestCreate = new Request.Builder()
                .url("http://47.105.120.203:30080/api/v1/deviceaccess/device")
                .post(bodyCreate)
                .addHeader("Accept","application/json, text/plain, */*")
//                .addHeader("Accept","text/plain, */*, q=0.01")
                .addHeader("Connection","keep-alive")
                .addHeader("Content-Type","application/json;charset=UTF-8")
                //.addHeader("Cookie",session.toString())
                .build();
        //得到一个call对象
        Response response = mOkHttpClient.newCall(requestCreate).execute();
        if (response.isSuccessful()){
            return response.body().string();
        }else{
            //httplogin();
            System.out.println(response.body().string());
            return null;
        }
    }
}
