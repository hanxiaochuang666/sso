package cn.eblcu.sso.infrastructure.util;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MapAndObject
 * @Author 焦冬冬
 * @Date 2019/3/22 17:00
 **/
public class MapAndObjectUtils {

    private MapAndObjectUtils(){}
    /**
     * @Author 焦冬冬
     * @Description MAp  转对象
     * @Date 17:05 2019/3/22
     * @Param 
     * @return 
     **/
    public static  Object MapToObject(Map<String,Object> map, Class<?>  entity)throws Exception {
        if (map == null)
            return null;

        Object obj = entity.newInstance();
        BeanUtils.populate(obj,map);

        return obj;
    }
    
    /**
     * @Author 焦冬冬
     * @Description  对象转MAP
     * @Date 17:19 2019/3/22
     * @Param 
     * @return 
     **/
    public static Map<String, Object> ObjectToMap(Object object)throws Exception{
        if(null==object)
            return  null;
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            map.put(declaredField.getName(),declaredField.get(object));
        }
        return  map;
    }
}
