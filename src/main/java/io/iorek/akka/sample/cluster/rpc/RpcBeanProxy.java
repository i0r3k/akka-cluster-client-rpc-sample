package io.iorek.akka.sample.cluster.rpc;

import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * Created by linyang on 16/1/7.
 */
public class RpcBeanProxy implements InvocationHandler {

    private ActorRef rpcClient;

    private Class<?> clz;

    @SuppressWarnings("unchecked")
    public <T> T proxy(ActorRef rpcClient, Class<T> clz) {
        this.rpcClient = rpcClient;
        this.clz = clz;
        Class<?>[] clzz = new Class<?>[] { clz };
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), clzz, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Object result = null;
        RpcCallMethod callMethod = new RpcCallMethod(method.getName(), args, clz.getName());
        Future<Object> future = Patterns.ask(rpcClient, callMethod, new Timeout(Duration.create(5, TimeUnit.SECONDS)));
        Object o = Await.result(future, Duration.create(5, TimeUnit.SECONDS));
        result = o;
        return result;
    }

}

