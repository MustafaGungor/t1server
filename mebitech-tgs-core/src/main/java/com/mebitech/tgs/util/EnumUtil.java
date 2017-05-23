package com.mebitech.tgs.util;


import java.util.HashMap;
import java.util.Map;


/**
 * Created by tayipdemircan on 23.01.2017.
 */
public class EnumUtil
{
    static Map<String, Object> enumMap;

    public Map<String, Object> getEnums() {
        if (enumMap != null) {
            return enumMap;
        } else {
            enumMap = new HashMap<String, Object>();
            for (Class cls : EnumUtil.class.getClasses()) {
                String className = cls.getName();
                enumMap.put(className.substring(className.indexOf("$") + 1), cls.getEnumConstants());
            }
            return enumMap;
        }
    }

    public Object getEnumByName(String enumName) {
        return getEnums().get(enumName);
    }

}
