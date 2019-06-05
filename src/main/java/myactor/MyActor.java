package myactor;

import java.lang.reflect.Field;
import java.util.Map;

public abstract class MyActor {
    abstract public void onReceive(Object message) throws Exception ;
    void init() {
        //获取actor id
        //检查actor注册中心，actor id是否已经注册（服务发现，注册发现中心可以考虑使用dynamodb实现）
        //如果已经注册，标识：actor已经创建，并且在某个BAAS服务中已经保存了状态
        //没有没有注册，那么：1. 注册 2. 在某个BAAS服务创建状态位置，设置初始值

    }
    void onReceiveHandle () throws Exception {
        init();
        //通过反射获取每个属性的key，通过key从BAAS服务获取每个属性的值，通过反射设置属性的值
        //调用onReceive方法
        //通过反射获取每个属性的值，将属性值保存到BAAS中
        //实现如下:
        Class<?> klass = this.getClass();
        Field[] fields = klass.getDeclaredFields();
        for (Field field: fields) {
            System.out.println(field);
            System.out.println("field name is :" + field.getName());
            field.setAccessible(true);
            System.out.println("value is :" + field.getInt(this));
            System.out.println("obtain value form BAAS");
            if (field.getType() == int.class)
            {
                field.setInt(this, 10);
            }
            System.out.println("after update, value is "+ field.getInt(this));
        }
        onReceive("");
        //获取属性名称，从S3或者Redis获取
        //设置属性值
    }

}
