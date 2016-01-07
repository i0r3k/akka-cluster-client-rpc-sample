package io.iorek.akka.sample.cluster.rpc.client;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.client.ClusterClient;
import akka.cluster.client.ClusterClientSettings;
import akka.routing.FromConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.iorek.akka.sample.cluster.rpc.RpcBeanProxy;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by linyang on 16/1/7.
 */
public final class RpcClient {
    private static final AtomicBoolean INTIALIZED = new AtomicBoolean(false);

    private ActorSystem system;

    private ActorRef clusterClient;

    private ActorRef clientRouter;

    private static RpcClient instance = null;

    private RpcClient() {
        final Config config = ConfigFactory
                .parseString("akka.remote.netty.tcp.port=" + 12552)
                .withFallback(ConfigFactory.load("client"));
        system = ActorSystem.create("ClusterClientSystem", config);

        clusterClient = system.actorOf(ClusterClient.props(ClusterClientSettings.create(system).withInitialContacts(initialContacts(config))), "clusterClient");
        clientRouter = system.actorOf(FromConfig.getInstance().props(Props.create(RpcClientActor.class, clusterClient)), "rpcClient");
    }

    private Set<ActorPath> initialContacts(Config config) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static final RpcClient getInstance() {
        if (INTIALIZED.compareAndSet(false, true)) {
            if (instance == null) {
                instance = new RpcClient();

            }
        }
        return instance;
    }

    public <T> T getBean(Class<T> clz) {
        return new RpcBeanProxy().proxy(clientRouter, clz);
    }
}