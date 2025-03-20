package demo;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.ecs.Protocol;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.rds.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemoStack extends Stack {
    public DemoStack(App scope, String id, StackProps stackProps){
        super(scope, id, stackProps);
        Vpc vpc = Vpc.Builder.create(this, "Vpc")
                .maxAzs(2)
                .build();
        Cluster cluster = Cluster.Builder.create(this, "ECSCluster")
                .vpc(vpc)
                .build();
        FargateTaskDefinition loadBalancerTask = FargateTaskDefinition.Builder.create(this, "LoadBalancerTask")
                .cpu(256)
                .memoryLimitMiB(512)
                .build();
        loadBalancerTask.addContainer("LoadBalancerContainer", ContainerDefinitionOptions.builder()
                        .image(ContainerImage.fromRegistry("api-gateway"))
                        .portMappings(Stream.of(9000).map(port->PortMapping.builder().containerPort(port)
                                .hostPort(port)
                                .protocol(Protocol.TCP)
                                .build()).collect(Collectors.toList()))
                .build());
        ApplicationLoadBalancedFargateService loadBalancer = ApplicationLoadBalancedFargateService.Builder.create(this, "LoadBalancer")
                .cluster(cluster)
                .assignPublicIp(true)
                .taskDefinition(loadBalancerTask)
                .build();

        FargateTaskDefinition serviceBTask = FargateTaskDefinition.Builder.create(this, "ServiceBTask")
                .cpu(256)
                .memoryLimitMiB(512)
                .build();

        serviceBTask.addContainer("ServiceBContainer", ContainerDefinitionOptions.builder()
                .image(ContainerImage.fromRegistry("service-b"))
                .portMappings(Stream.of(8001).map(port->PortMapping.builder().containerPort(port)
                        .hostPort(port)
                        .protocol(Protocol.TCP)
                        .build()).collect(Collectors.toList()))
                .build());

        FargateService serviceB = FargateService.Builder.create(this, "ServiceB")
                .cluster(cluster)
                .taskDefinition(serviceBTask)
                .assignPublicIp(false)
                .build();

        DatabaseInstance postgres = DatabaseInstance.Builder.create(this, "Postgres")
                .vpc(vpc)
                .engine(DatabaseInstanceEngine.postgres(PostgresInstanceEngineProps.builder()
                                .version(PostgresEngineVersion.VER_14_1)
                        .build()))
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE2, InstanceSize.MICRO))
                .credentials(Credentials.fromGeneratedSecret("admin"))
                .removalPolicy(RemovalPolicy.DESTROY)
                .allocatedStorage(20)
                .databaseName("postgres-db")
                .build();


        FargateTaskDefinition serviceATask = FargateTaskDefinition.Builder.create(this, "ServiceATask")
                .cpu(256)
                .memoryLimitMiB(512)
                .build();

        serviceATask.addContainer("ServiceAContainer", ContainerDefinitionOptions.builder()
                .image(ContainerImage.fromRegistry("service-a"))
                .portMappings(Stream.of(8000).map(port->PortMapping.builder().containerPort(port)
                        .hostPort(port)
                        .protocol(Protocol.TCP)
                        .build()).collect(Collectors.toList()))
                .environment(Map.of(
                        "SPRING_DATASOURCE_DRIVER_CLASS_NAME", "org.postgresql.Driver",
                        "SPRING_DATASOURCE_URL", String.format("jdbc:postgresql://%s:%s/%s",
                                postgres.getDbInstanceEndpointAddress(), postgres.getDbInstanceEndpointPort(),
                                "postgres-db"
                                ),
                        "SPRING_DATASOURCE_USERNAME", "admin",
                        "SPRING_DATASOURCE_PASSWORD", postgres.getSecret().secretValueFromJson("password").toString(),
                        "SPRING_JPA_HIBERNATE_DDL_AUTO", "update",
                        "SPRING_SQL_iINIT_MODE", "always"
                ))
                .build());

        FargateService serviceA = FargateService.Builder.create(this, "ServiceA")
                .cluster(cluster)
                .taskDefinition(serviceATask)
                .assignPublicIp(false)
                .build();
    }

    public static void main(String[] args) {
        App app = new App(AppProps.builder()
                .outdir("./template")
                .build());
        new DemoStack(app, "DemoStack", StackProps.builder()
                .synthesizer(BootstraplessSynthesizer.Builder.create().build())
                .build());
        app.synth();
    }
}
