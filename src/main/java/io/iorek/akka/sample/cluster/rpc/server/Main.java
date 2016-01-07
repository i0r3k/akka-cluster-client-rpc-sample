package io.iorek.akka.sample.cluster.rpc.server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.client.ClusterClientReceptionist;
import akka.routing.FromConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.iorek.akka.sample.cluster.rpc.test.ExampleInterface;
import io.iorek.akka.sample.cluster.rpc.test.ExampleInterfaceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linyang on 16/1/7.
 */
public class Main {
    public static void main(String[] args) {
        final Config config = ConfigFactory
                .parseString("akka.remote.netty.tcp.port=" + 12551)
                .withFallback(ConfigFactory.parseString("akka.cluster.roles = [RpcServer]"))
                .withFallback(ConfigFactory.load("server"));

        ActorSystem system = ActorSystem.create("ClusterSystem", config);

        // Server 加入发布的服务
        Map<String, Object> beans = new HashMap<>();
        beans.put(ExampleInterface.class.getName(), new ExampleInterfaceImpl());

        ActorRef rpcRouter = system.actorOf(FromConfig.getInstance().props(Props.create(RpcServerActor.class, beans)), "rpcServer");
        ClusterClientReceptionist.get(system).registerService(rpcRouter);
    }
}
