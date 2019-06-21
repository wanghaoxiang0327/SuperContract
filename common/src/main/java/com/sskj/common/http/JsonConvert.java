package com.sskj.common.http;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.convert.Converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class JsonConvert<T> implements Converter<T> {
    @Override
    public T convertResponse(Response response) throws Throwable {
        T data;
        ResponseBody body = response.body();
        if (body != null) {
            Type genericSuperclass = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            data = JSON.parseObject(body.string(), type);
            if (data == null) {
                throw new ApiException("数据解析失败");
            } else {
                if (data instanceof HttpResult) {
                    HttpResult result = (HttpResult) data;
                    if (result.getStatus() == BaseHttpConfig.OK) {
                        return data;
                    } else {
                        throw new ApiException(result.getMsg());
                    }
                }
            }
        }
        return null;
    }
}
