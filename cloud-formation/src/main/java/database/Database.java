package database;

import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.ec2.InstanceClass;
import software.amazon.awscdk.services.ec2.InstanceSize;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.rds.Credentials;
import software.amazon.awscdk.services.rds.DatabaseInstance;
import vpc.VirtualPrivateCloud;

public class Database {
    private final DatabaseProperties properties;
    private final DatabaseInstance instance;
    public Database(Stack stack, VirtualPrivateCloud vpc, DatabaseProperties properties){
        this.properties = properties;
        instance = DatabaseInstance.Builder.create(stack, properties.getDatabaseId())
                .vpc(vpc)
                .engine(properties.getEngine())
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE2, InstanceSize.MICRO))
                .credentials(Credentials.fromGeneratedSecret(properties.getUsername()))
                .removalPolicy(RemovalPolicy.DESTROY)
                .allocatedStorage(20)
                .databaseName(properties.getDatabaseName())
                .build();
    }
    public String getUrl(){
        return String.format(properties.getUrl(),
                instance.getDbInstanceEndpointAddress(),
                instance.getDbInstanceEndpointPort(),
                properties.getDatabaseName());
    }

    public String getUsername(){
        return properties.getUsername();
    }

    public String getPassword(){
        return instance.getSecret().secretValueFromJson("password").toString();
    }
    public String getDriverName() {
        return properties.getDatabaseDriver();
    }
}
