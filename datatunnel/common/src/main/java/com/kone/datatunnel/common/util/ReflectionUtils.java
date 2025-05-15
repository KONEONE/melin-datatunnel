package com.kone.datatunnel.common.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectionUtils {
    private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentHashMap<>(256);
    
    private static final Field[] EMPTY_FIELD_ARRAY = new Field[0];

    public static Field findField(Class<?> clazz, String name){
        return null;
    }

    public static Field findField(Class<?> clazz, String name, Class<?> type){
        Class<?> searchType = clazz;

        while(Object.class != searchType && searchType != null){
            Field[] fields = getDeclaredFields(searchType);

        }
    }

    private static Field[] getDeclaredFields(Class<?> clazz){
        Field[] res = declaredFieldsCache.get(clazz);       // 从缓存中获取
        if(res == null){
            try{
                res = clazz.getDeclaredFields();
                declaredFieldsCache.put(clazz, (res.length == 0? EMPTY_FIELD_ARRAY : res)); // 将结果存入缓存
            }catch (Throwable ex){
                throw new IllegalStateException(
                     "Failed to introspect Class [" + clazz.getName() + "] from ClassLoader ["
                                + clazz.getClassLoader() + "]", ex
                );
            }
        }
        return res;
    }
}
