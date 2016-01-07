package io.iorek.akka.sample.cluster.rpc;

import java.io.Serializable;

/**
 * Created by linyang on 16/1/7.
 */
public class RpcCallMethod implements Serializable {

    private String methodName;

    private Object[] params;

    public RpcCallMethod(String methodName, Object[] params) {
        super();
        this.methodName = methodName;
        this.params = params;
    }

    private String beanName;

    public RpcCallMethod(String methodName, Object[] params, String beanName) {
        super();
        this.methodName = methodName;
        this.params = params;
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

}
