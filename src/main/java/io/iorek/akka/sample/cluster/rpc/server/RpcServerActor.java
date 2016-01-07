package io.iorek.akka.sample.cluster.rpc.server;

import akka.actor.UntypedActor;
import io.iorek.akka.sample.cluster.rpc.RpcCallMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by linyang on 16/1/7.
 */
public class RpcServerActor extends UntypedActor {
    private Map<String, Object> proxyBeans;

    public RpcServerActor(Map<String, Object> beans) {
        proxyBeans = beans;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof RpcCallMethod) {
            RpcCallMethod event = (RpcCallMethod) message;
            Object bean = proxyBeans.get(event.getBeanName());
            Object[] params = event.getParams();
            List<Class<?>> paraTypes = new ArrayList<Class<?>>();
            Class<?>[] paramerTypes = new Class<?>[] {};
            if (params != null) {
                for (Object param : params) {
                    paraTypes.add(param.getClass());
                }
            }
            Method method = bean.getClass().getMethod(event.getMethodName(),
                    paraTypes.toArray(paramerTypes));
            Object o = method.invoke(bean, params);
            getSender().tell(o, getSelf());
        }
    }
}
