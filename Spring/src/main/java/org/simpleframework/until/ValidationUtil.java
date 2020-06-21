package org.simpleframework.until;

import java.util.Collection;
import java.util.Map;

public class ValidationUtil {

    //If the collection is empty or null,the true is returned
    public static boolean isEmpty(Collection<?> obj){
        return obj == null || obj.isEmpty();
    }

    //Determines if the string is empty
    public static boolean isEmpty(String obj){
        return (obj == null || "".equals(obj));
    }

    //Determine if the array is empty
    public static boolean isEmpty(Object[] objects){
        return objects == null || objects.length == 0;
    }

    //Determines the map is empty
    public static boolean isEmpty(Map<?,?> map){
        return map == null || map.isEmpty();
    }
}
