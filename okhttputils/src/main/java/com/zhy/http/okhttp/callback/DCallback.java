package com.zhy.http.okhttp.callback;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * @author tylz
 * @time 2016/4/5 0005 12:43
 * @des
 *
 * @updateAuthor
 * @updateDate 2016/4/5 0005
 * @updateDes
 */
public abstract class DCallback<T>
        extends Callback<T>
{
    @Override
    public T parseNetworkResponse(Response response)
            throws Exception
    {
        String result = response.body()
                                .string();
        Log.d("tylz",result);
        //解析对象
        JsonParser  parser     = new JsonParser();
        JsonElement root       = parser.parse(result);
        JsonObject  rootObject = root.getAsJsonObject();
        //获取code
        JsonPrimitive codeJson = rootObject.getAsJsonPrimitive("code");
        int           code     = codeJson.getAsInt();
        String msg = rootObject.getAsJsonPrimitive("message")
                               .getAsString();
        //得到泛型
        Type type = this.getClass()
                        .getGenericSuperclass();
        if (code == 0) {
            //访问接口成功
            JsonObject contentJson = rootObject.getAsJsonObject("content");
            if (type instanceof ParameterizedType) {
                //如果用户写了泛型，就会进入这里，否者不会执行
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type beanType = parameterizedType.getActualTypeArguments()[0];
                if (beanType == String.class) {
                    //如果是String类型，直接返回字符串
                    return (T) response.body()
                                       .string();
                } else {
                    //如果是 Bean List Map ，则解析完后返回
                    return new Gson().fromJson(contentJson, beanType);
                }
            } else {
                //如果没有写泛型，直接返回Response对象
                return (T) response;
            }


        }
        //当code != 0的时候
        if (type instanceof ParameterizedType) {
            //如果用户写了泛型，就会进入这里，否者不会执行
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type beanType = parameterizedType.getActualTypeArguments()[0];
            if (beanType == String.class) {
                //如果是String类型，直接返回字符串
                return (T) response.body()
                                   .string();
            } else {
                //如果是 Bean List Map ，则解析完后返回
                return new Gson().fromJson(result, beanType);
            }
        } else {
            //如果没有写泛型，直接返回Response对象
            return (T) response;
        }

    }


}
