package com.bysj.manage.vo;

import java.lang.reflect.Field;

public class ObjectUtils {

    public static boolean isAllFieldNull(Object obj) throws Exception{
        Class stuCla = (Class) obj.getClass();// 得到类对象
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        for (Field f : fs) {//遍历属性
            f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
            Object val = f.get(obj);// 得到此属性的值
            if(val!=null) {//只要有1个属性不为空,那么就不是所有的属性值都为空
                flag = false;
                break;
            }
        }
        return flag;
    }

    public static void fieldNullToNull(Object obj) throws Exception{
        Class stuCla = (Class) obj.getClass();
        Field[] fs = stuCla.getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true);
            Object val = f.get(obj);
            if(val=="") {
                f.set(obj,null);
            }
        }
    }
}
