package com.example.groupbuy.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class JpaUtils {
    /**
     * 创建jpa更新实体
     *
     * @param oldEntity 旧更新实体 (持久层获取)
     * @param newEntity 新更新实体(前端获取)
     * @param <E> 更新实体
     */
    public static <E> void createUpdateEntity(E oldEntity, E newEntity) {
        BeanUtils.copyProperties(oldEntity,newEntity,getNotNullProperties(newEntity));
    }

    /**
     * 获取对象的非空属性
     */
    private static String[] getNotNullProperties(Object src) {
        //1.获取Bean
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        //2.获取Bean的属性描述
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        //3.获取Bean的空属性
        Set<String> properties = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : pds) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = srcBean.getPropertyValue(propertyName);
            if(propertyValue!=null){
                properties.add(propertyName);
            }

        }
        return properties.toArray(new String[0]);
    }
}
