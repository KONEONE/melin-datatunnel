package com.kone.datatunnel.common.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.DataFrameWriter;

import com.kone.datatunnel.api.DataTunnelException;
import com.kone.datatunnel.api.model.DataTunnelOption;

public class ReflectionUtils {
    private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentHashMap<>(256);
    
    private static final Field[] EMPTY_FIELD_ARRAY = new Field[0];

    /*
     * 描述： 根据Field Name 匹配 Class的Filed列表
     */
    public static Field findField(Class<?> clazz, String name){
        return findField(clazz, name, null);
    }

    /*
     * 描述： 根据Field Name 和 Field Type 匹配 Class的Filed列表
     */
    public static Field findField(Class<?> clazz, String name, Class<?> type){
        Class<?> searchType = clazz;

        while(Object.class != searchType && searchType != null){
            Field[] fields = getDeclaredFields(searchType);
            for(Field field : fields){
                if((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))){       // 匹配 name 和 type
                    return field;
                }
            }
        }
        return null;
    }
    /*
    * 描述： 对DataFrameReader 设置 option 参数
    */
    public static void setDataFrameReaderOptions(DataFrameReader reader, DataTunnelOption option){
        try{
            Field[] fields = option.getClass().getDeclaredFields();
            for(Field field : fields){
                field.setAccessible(true);
                Object value = field.get(option);  
                if(value != null){
                    reader.option(field.getName(), value.toString());
                }
            }
        }catch(IllegalAccessException e){
            throw new DataTunnelException(e.getMessage(), e);
        }
    }
    /*
     * 描述： 对DataFrameWriter 设置 option 参数
     */
    public static void setDataFrameWriterOptions(DataFrameWriter writer, DataTunnelOption option){
        try{
            Field[] fields = option.getClass().getDeclaredFields();
            for(Field field : fields){
                field.setAccessible(true);
                Object value = field.get(option);
                if(value != null){
                    writer.option(field.getName(), value.toString());
                }
            }
        }catch (IllegalAccessException e){
            throw new DataTunnelException(e.getMessage(), e);
        }
    }

    /*
     * 描述：获取Class的Field列表,并且保存到缓存中
     */
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
