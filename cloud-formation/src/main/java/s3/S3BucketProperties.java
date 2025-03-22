package s3;

public class S3BucketProperties {
    public final String id;
    private final String name;
    private boolean isPublic;
    public S3BucketProperties(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName(){ return name; }
    public void setPublic(boolean isPublic){
        this.isPublic = isPublic;
    }

    public boolean isPublic(){ return isPublic; }
}
