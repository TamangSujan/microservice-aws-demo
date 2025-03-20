package database;

import software.amazon.awscdk.services.rds.DatabaseInstanceEngine;
import software.amazon.awscdk.services.rds.IInstanceEngine;
import software.amazon.awscdk.services.rds.PostgresEngineVersion;
import software.amazon.awscdk.services.rds.PostgresInstanceEngineProps;

public class PostgresProperties extends DatabaseProperties{
    public PostgresProperties(String id, String username, String databaseName){
        setUsername(username);
        setDatabaseId(id);
        setDatabaseName(databaseName);
    }
    @Override
    public String getUrl() {
        return "jdbc:postgresql://%s:%s/%s";
    }

    @Override
    public String getDatabaseDriver() {
        return "org.postgresql.Driver";
    }

    @Override
    protected IInstanceEngine getEngine() {
        return DatabaseInstanceEngine.postgres(PostgresInstanceEngineProps.builder()
                        .version(PostgresEngineVersion.VER_14_1)
                .build());
    }
}
