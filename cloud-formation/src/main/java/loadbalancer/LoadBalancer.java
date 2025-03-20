package loadbalancer;

import cluster.LocalCluster;
import container.ContainerProperties;
import environment.EnvironmentVariables;
import software.amazon.awscdk.services.ecs.*;

import java.util.stream.Collectors;

public class LoadBalancer {
    private final FargateService service;
    private final LoadBalancerProperties properties;
    private final EnvironmentVariables environment;
    private final LocalCluster cluster;
    public LoadBalancer(LocalCluster cluster, LoadBalancerProperties properties) {
        this.cluster = cluster;
        this.properties = properties;
        this.environment = new EnvironmentVariables();
        service = FargateService.Builder.create(cluster.getStack(), properties.getId())
                .assignPublicIp(true)
                .cluster(cluster.getCluster())
                .taskDefinition(task())
                .build();
    }
    public LoadBalancer(LocalCluster cluster, LoadBalancerProperties properties, EnvironmentVariables environment) {
        this.cluster = cluster;
        this.properties = properties;
        this.environment = environment;
        service = FargateService.Builder.create(cluster.getStack(), properties.getId())
                .assignPublicIp(true)
                .cluster(cluster.getCluster())
                .taskDefinition(task())
                .build();
    }

    public FargateService getContainerService(){ return service; }

    private FargateTaskDefinition task(){
        FargateTaskDefinition task = FargateTaskDefinition.Builder.create(cluster.getStack(), properties.getId()+"FargateTask")
                .cpu(256)
                .memoryLimitMiB(512)
                .build();
        task.addContainer(properties.getId()+"FargateContainer", getContainerOptions());
        return task;
    }

    private ContainerDefinitionOptions getContainerOptions(){
        return ContainerDefinitionOptions.builder()
                .image(ContainerImage.fromRegistry(properties.getImageName()))
                .environment(environment.getVariables())
                .portMappings(properties.getExposedPorts().stream().map(port-> PortMapping.builder()
                                .containerPort(port)
                                .hostPort(port)
                                .protocol(Protocol.TCP).build())
                        .collect(Collectors.toList()))
                .build();
    }
}
