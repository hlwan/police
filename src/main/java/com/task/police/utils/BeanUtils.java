package com.task.police.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * H.L.Wan 595710104@qq.com
 * Suzhou
 */
public class BeanUtils {


    public static Set<String> getNullPropertyNames(Object source) {
        return getNullPropertyNames(source,true);
    }

    /**
     * 获取非空的属性
     * @param source
     * @param simple
     * @return
     */
    public static Set<String> getNullPropertyNames(Object source,boolean simple) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            if(simple && !org.springframework.beans.BeanUtils.isSimpleProperty(pd.getPropertyType())){
                emptyNames.add(pd.getName());
            }
            if (src.getPropertyValue(pd.getName()) == null){
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames;
    }



    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        copyPropertiesIgnoreNull(source, target, getNullPropertyNames(source).toArray(new String[0]));
    }

    public static void copyPropertiesIgnoreNull(Object source, Object target,String ... ignoreProperties) {
        Set<String> igs=getNullPropertyNames(source);
        igs.addAll(ignoreProperties==null?new ArrayList<>(): Arrays.asList(ignoreProperties));
        copyProperties(source, target, igs.toArray(new String[0]));
    }

    public static void copyProperties(Object source,Object target,String ... ignoreProperties){
        org.springframework.beans.BeanUtils.copyProperties(source,target,ignoreProperties);
    }
}
