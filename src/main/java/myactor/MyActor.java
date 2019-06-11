package myactor;

import java.lang.reflect.Field;
import java.util.concurrent.Future;

public abstract class MyActor {
    String actorName = "";
    Object sqsObj=null;
    public MyActor(String actorName) {
        this.actorName = actorName;
        //1. 创建名称为actorname的sqs消息队列sqsObj
        //2. 打包部署/更新java代码,handle为onReceiveHandle. 问题：能否自动打包java代码？
        //3. 设置并发最大次数，如果有状态，那么设置并发次数为1,否则不设置
        //4. 建立sqs和lambda的映射关系(这里存在一个问题，事件驱动的sqs+只允许并发一次的lambda会发生什么？)
        //    4.1： 当lambda正在处理上一次的请求，新的消息来到了sqs中，返回错误---这不是我们想要的，这是一个大问题
        //    4.2:  当lambda正在处理上一次的请求，新的消息来到了sqs中，等待结束然后再触发---这是我们想要的
        //5. 向注册中心注册actor名称
    }
    public void tell(Object msg, MyActor sender) {
        //发送消息給sqsObj，因为消息队列和lambda关联，因此接收到消息后消息队列会触发lambda函数
    }

    //ask没想好如何实现
    public Future ask(Object msg, int timeout) {
        //发送消息給sqsObj,返回futrue值
        return null;
    }
    //用户实现
    abstract public void onReceive(Object message) throws Exception ;

    //创建actor实例时，会有初始值，怎么处理初始值，在哪将初始值放到BAAS中？
    //方法1：如果在onReceiveHandle中，那么需要设置一个标志位initFlag
    //          在构造函数在BAAS中设置initFlag=false
    //方法2：不使用继承机制，采用复合机制，如同faasactor和akka类似的方式，好处：actor构造函数后，初始化actor属性值
    //        ActorRef ar = System.actorof(Count.class) //考虑带参数的构造函数
    // 使用ActorFactory创建actor
    private void initActorAttrs() {
        //检查initFlag是否为true
        //      是： 返回
        //      否：使用反射机制获取属性值，将属性值放到BAAS中

    }
    void onReceiveHandle (Object message) throws Exception {
        initActorAttrs();
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
        onReceive(message);
        //获取属性名称，从S3或者Redis获取
        //设置属性值
    }

}
