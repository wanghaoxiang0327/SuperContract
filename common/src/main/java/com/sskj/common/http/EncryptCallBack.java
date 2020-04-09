package com.sskj.common.http;

import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.Request;
import com.sskj.common.encrypt.EncodeUtils;

import java.util.LinkedHashMap;
import java.util.List;

public abstract class EncryptCallBack<T> extends JsonCallBack<T> {

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        encrypt(request.getParams());
    }

    public void encrypt(HttpParams params){
        LinkedHashMap<String, List<String>> map = params.urlParamsMap;
        for (String key:map.keySet()){
            if (key.contains("pwd")){
                List<String> values = map.get(key);
                for (int i = 0; i < values.size(); i++) {
                    String value=values.get(i);
                    String encodeValue= EncodeUtils.encodeAES(value);
                    values.set(i, encodeValue);
                }
            }
        }

    }

}
