package io.iorek.akka.sample.cluster.rpc.client;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.cluster.client.ClusterClient;
import io.iorek.akka.sample.cluster.rpc.RpcCallMethod;

/**
 * Created by linyang on 16/1/7.
 */
public class RpcClientActor extends UntypedActor {

    private ActorRef clusterCLient;

    public RpcClientActor(ActorRef clusterClient) {
        this.clusterCLient = clusterClient;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof RpcCallMethod) {
            clusterCLient.tell(new ClusterClient.Send("/user/rpcServer", message), getSender());
        }
    }
}
