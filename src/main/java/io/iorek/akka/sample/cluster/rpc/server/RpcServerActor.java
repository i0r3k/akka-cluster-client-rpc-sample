package io.iorek.akka.sample.cluster.rpc.server;

import akka.actor.UntypedActor;
import io.iorek.akka.sample.cluster.rpc.RpcCallMethod;
import org.apache.commons.lang3.reflect.MethodUtils;

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
            String methodName = event.getMethodName();
            Object[] params = event.getParams();

            Object o = MethodUtils.invokeMethod(bean, methodName, params);
            getSender().tell(o, getSelf());
        }
    }
}
