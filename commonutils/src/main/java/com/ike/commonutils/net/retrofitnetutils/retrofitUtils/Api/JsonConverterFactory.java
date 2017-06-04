package com.ike.commonutils.net.retrofitnetutils.retrofitUtils.Api;

import android.util.Log;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.ike.commonutils.net.retrofitnetutils.model.ExtendsApiResult;



import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
* author ike
* create time 16:41 2017/5/20
* function: 自定义的json转换器
**/

public class JsonConverterFactory extends Converter.Factory{
    private String Tag="JsonConverterFactory";
    public static JsonConverterFactory create(){
        return new JsonConverterFactory();
    }
    private JsonConverterFactory(){

    }
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = new Gson().getAdapter(TypeToken.get(type));
        return new JsonResponseBodyConverter(adapter);
    }
    class JsonResponseBodyConverter<T> implements Converter<ResponseBody,T>{

        private TypeAdapter<T> adapter;

        public JsonResponseBodyConverter(TypeAdapter<T> adapter) {
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            T temp=null;
            try {
                temp= adapter.fromJson(value.string());
                Log.e(Tag,"解析完毕");
            } catch (Exception e) {
                Log.e(Tag,"解析出错了："+e.getMessage());
                e.printStackTrace();
            }finally {
                value.close();
            }
            return temp;
        }
    }
}
