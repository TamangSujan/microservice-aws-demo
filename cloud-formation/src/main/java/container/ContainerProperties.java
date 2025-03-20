package container;

import software.amazon.awscdk.services.ecs.ICluster;
import software.amazon.awscdk.services.ecs.TaskDefinition;

import java.util.List;

public class ContainerProperties {
    private final String id;
    private final String imageName;
    private final List<Integer> exposedPorts;
    public ContainerProperties(String id, String imageName, List<Integer> exposedPorts){
        this.id = id;
        this.imageName = imageName;
        this.exposedPorts = exposedPorts;
    }
    public String getId(){ return id; }
    public String getImageName(){ return imageName; }
    public List<Integer> getExposedPorts(){ return exposedPorts; }
}
