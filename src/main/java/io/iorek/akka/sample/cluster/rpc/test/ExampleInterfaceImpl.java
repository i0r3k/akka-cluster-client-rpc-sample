package io.iorek.akka.sample.cluster.rpc.test;

/**
 * Created by linyang on 16/1/7.
 */
public class ExampleInterfaceImpl implements ExampleInterface {
    @Override
    public String test(String text) {
        return "Hello -> " + text;
    }
}
