package s3;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.s3.Bucket;

public class S3Bucket {
    public S3Bucket(Stack stack, S3BucketProperties bucketProperties){
        Bucket.Builder.create(stack, bucketProperties.getId())
                .bucketName(bucketProperties.getName())
                .versioned(bucketProperties.isPublic())
                .build();
    }
}
