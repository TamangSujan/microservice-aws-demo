import cluster.LocalCluster;
import container.ContainerProperties;
import container.LocalContainer;
import database.Database;
import database.DatabaseProperties;
import database.PostgresProperties;
import environment.EnvironmentVariables;
import loadbalancer.LoadBalancer;
import loadbalancer.LoadBalancerProperties;
import notification.NotificationProperties;
import stack.LocalStack;
import vpc.VirtualPrivateCloud;

import java.util.List;

public class CloudFormationApplication {
    public static void main(String[] args) {
        LocalStack stack = new LocalStack("LocalStack", "./template");
        VirtualPrivateCloud vpc = new VirtualPrivateCloud(stack.getStack(), "MyVpc");
        Database postgres = new Database(stack.getStack(), vpc, postgresProperties());
        LocalCluster cluster = new LocalCluster(stack.getStack(), vpc, "MyCluster");
        LocalContainer serviceA = new LocalContainer(cluster, serviceAProperties(), serviceAEnvironment(postgres));
        LocalContainer serviceB = new LocalContainer(cluster, serviceBProperties());
        LoadBalancer loadBalancer = new LoadBalancer(cluster, loadBalancerProperties());
        LocalContainer emailService = new LocalContainer(cluster, emailProperties(),
                emailPropertiesEnvironment());
        stack.generateTemplate();
    }

    private static ContainerProperties emailProperties(){
        return new ContainerProperties("MyEmailServer", "email-service", List.of(4000));
    }

    private static EnvironmentVariables emailPropertiesEnvironment(){
        EnvironmentVariables environmentVariables = new EnvironmentVariables();
        environmentVariables.add("SES_PRIMARY_SENDER_MAIL", "moktansujanj5@gmail.com");
        environmentVariables.add("SES_URL", "http://host.docker.internal:4566");
        return environmentVariables;
    }

    private static DatabaseProperties postgresProperties(){
        return new PostgresProperties( "postgres", "admin", "Postgres");
    }

    private static ContainerProperties serviceAProperties(){
        return new ContainerProperties("ServiceA", "service-a", List.of(8000));
    }

    private static ContainerProperties serviceBProperties(){
        return new ContainerProperties("ServiceB", "service-b", List.of(8001));
    }

    private static LoadBalancerProperties loadBalancerProperties(){
        return new LoadBalancerProperties("LoadBalancer", "api-gateway", List.of(9000));
    }

    private static EnvironmentVariables serviceAEnvironment(Database database){
        EnvironmentVariables environmentVariables = new EnvironmentVariables();
        environmentVariables.add("SPRING_DATASOURCE_DRIVER_CLASS_NAME", database.getDriverName());
        environmentVariables.add("SPRING_DATASOURCE_URL", database.getUrl());
        environmentVariables.add("SPRING_DATASOURCE_USERNAME", database.getUsername());
        environmentVariables.add("SPRING_DATASOURCE_PASSWORD", database.getPassword());
        environmentVariables.add("SPRING_JPA_HIBERNATE_DDL_AUTO", "update");
        environmentVariables.add("SPRING_SQL_MODE_INIT", "always");
        return environmentVariables;
    }
}
