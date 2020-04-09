package com.sskj.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.sskj.common.App;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static com.sskj.common.CommonConfig.LOGIN;
import static com.sskj.common.CommonConfig.MOBILE;

public class SpUtil {

    private static final String FILE_NAME = "share_data";

    public SpUtil() {
    }

    public static void putBean(String key, Serializable object) {
        if (object != null) {
            SharedPreferences.Editor editor = init("share_data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = null;

            try {
                out = new ObjectOutputStream(baos);
                out.writeObject(object);
                String objectVal = new String(Base64.encode(baos.toByteArray(), 0));
                editor.putString(key, objectVal);
            } catch (IOException var14) {
                var14.printStackTrace();
            } finally {
                try {
                    baos.close();
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException var13) {
                    var13.printStackTrace();
                }

            }

            editor.commit();
        }
    }

    public static Object getBean(String key) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences("share_data", 0);
        if (sp.contains(key)) {
            String objectVal = sp.getString(key, "");
            if (TextUtils.isEmpty(objectVal)) {
                return null;
            } else {
                byte[] buffer = Base64.decode(objectVal, 0);
                ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                ObjectInputStream ois = null;

                Object var6;
                try {
                    ois = new ObjectInputStream(bais);
                    var6 = ois.readObject();
                } catch (Exception var16) {
                    var16.printStackTrace();
                    return null;
                } finally {
                    try {
                        bais.close();
                        if (ois != null) {
                            ois.close();
                        }
                    } catch (IOException var15) {
                        var15.printStackTrace();
                    }

                }

                return var6;
            }
        } else {
            return null;
        }
    }

    private static SharedPreferences.Editor init(String extra) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(extra, 0);
        return sp.edit();
    }

    public static void put(String key, Object object) {
        SharedPreferences.Editor editor = init("share_data");
        if (object instanceof String) {
            editor.putString(key, (String)object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer)object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean)object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float)object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long)object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    public static void put(String key, Object object, String extra) {
        SharedPreferences.Editor editor = init(extra);
        if (object instanceof String) {
            editor.putString(key, (String)object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer)object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean)object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float)object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long)object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }


    public static String getString(String key,String defaultObject){
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return  sp.getString(key,  defaultObject);
    }

    public static Integer getInt(String key,Integer defaultObject){
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return  sp.getInt(key,  defaultObject);
    }
    public static Float getFloat(String key,Float defaultObject){
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return  sp.getFloat(key,  defaultObject);
    }
    public static Long getLong(String key,Long defaultObject){
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return  sp.getLong(key,  defaultObject);
    }
    public static Boolean getBoolean(String key,Boolean defaultObject){
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return  sp.getBoolean(key,  defaultObject);
    }

    public static void clear() {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences("share_data", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    public static void exit(String mobile) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences("share_data", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.putString(MOBILE,mobile);
        editor.putBoolean(LOGIN, false);
        SharedPreferencesCompat.apply(editor);
    }

    public static boolean contains(String key) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences("share_data", 0);
        return sp.contains(key);
    }

    public static Map<String, ?> getAll() {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences("share_data", 0);
        return sp.getAll();
    }

    public static void remove(String key) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences("share_data", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        private SharedPreferencesCompat() {
        }

        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("commit");
            } catch (NoSuchMethodException var1) {
                return null;
            }
        }

        public static boolean apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return true;
                }
            } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException var2) {
                System.out.println(var2);
            }

            return editor.commit();
        }
    }
}
