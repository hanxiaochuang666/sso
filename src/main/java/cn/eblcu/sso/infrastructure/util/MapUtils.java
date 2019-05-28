package cn.eblcu.sso.infrastructure.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MapUtils
 * @Author 焦冬冬
 * @Date 2019/3/25 13:30
 **/
public class MapUtils {

    private MapUtils(){}
    
    /**
     * @Author 焦冬冬
     * @Description 
     * @Date 13:33 2019/3/25
     * @Param 
     * @return 
     **/
    public static Map<String,Object> initMap(String str, Object object){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put(str,object);
        return stringObjectHashMap;
    }

    public static Map<String,Object> initMap(){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        return stringObjectHashMap;
    }
}
