package vpc;

import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;

public class VirtualPrivateCloud extends Vpc {
    public VirtualPrivateCloud(Construct scope, String id) {
        super(scope, id);
    }
}
