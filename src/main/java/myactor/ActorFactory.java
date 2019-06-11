package myactor;

public class ActorFactory {
    static MyActor actorof(Class acotrKlass) {
        //1. 创建名称为actorname的sqs消息队列sqsObj
        //2. 打包部署/更新java代码,handle为onReceiveHandle. 问题：能否自动打包java代码？
        //3. 设置并发最大次数，如果有状态，那么设置并发次数为1,否则不设置
        //4. 建立sqs和lambda的映射关系(这里存在一个问题，事件驱动的sqs+只允许并发一次的lambda会发生什么？)
        //    4.1： 当lambda正在处理上一次的请求，新的消息来到了sqs中，返回错误---这不是我们想要的，这是一个大问题
        //    4.2:  当lambda正在处理上一次的请求，新的消息来到了sqs中，等待结束然后再触发---这是我们想要的
        //5. 向注册中心注册actor名称
        //6.
        return null;
    }
    //根据actor名称查找actor，如果存在返回actor引用，否则返回Null
    static MyActor find(String actorName)
    {
        //查找注册中心，actor是否注册，如果有，那么返回引用，否则返回null
        return null;

    }
}
