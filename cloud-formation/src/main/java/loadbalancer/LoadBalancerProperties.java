package loadbalancer;

import java.util.List;

public class LoadBalancerProperties {
    private final String id;
    private final String imageName;
    private final List<Integer> exposedPorts;
    public LoadBalancerProperties(String id, String imageName, List<Integer> exposedPorts){
        this.id = id;
        this.imageName = imageName;
        this.exposedPorts = exposedPorts;
    }
    public String getId(){ return id; }
    public String getImageName(){ return imageName; }
    public List<Integer> getExposedPorts(){ return exposedPorts; }
}
