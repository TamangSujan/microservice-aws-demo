package cluster;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.ecs.Cluster;
import vpc.VirtualPrivateCloud;

public class LocalCluster {
    private final Cluster cluster;
    private final Stack stack;
    public LocalCluster(Stack stack, VirtualPrivateCloud vpc, String id){
        this.stack = stack;
        cluster = Cluster.Builder.create(stack, id)
                .vpc(vpc)
                .build();
    }

    public Cluster getCluster(){
        return cluster;
    }

    public Stack getStack(){ return stack; }
}
