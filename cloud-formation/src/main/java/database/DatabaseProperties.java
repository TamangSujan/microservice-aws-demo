package database;

import software.amazon.awscdk.services.rds.IInstanceEngine;

public abstract class DatabaseProperties {
    private String databaseId;
    private String username;
    private String databaseName;
    public String getDatabaseId(){ return databaseId; }
    public String getUsername(){ return  username; }
    protected abstract String getUrl();
    public abstract String getDatabaseDriver();
    protected abstract IInstanceEngine getEngine();
    public String getDatabaseName() {
        return databaseName;
    }
    protected void setUsername(String username){
        this.username = username;
    }
    protected void setDatabaseId(String databaseId){
        this.databaseId = databaseId;
    }
    protected void setDatabaseName(String databaseName){
        this.databaseName = databaseName;
    }

}
