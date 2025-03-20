package environment;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentVariables {
    private Map<String, String> variables;
    public EnvironmentVariables(){
        variables = new HashMap<>();
    }

    public void add(String key, String value){
        variables.put(key, value);
    }

    public Map<String, String> getVariables(){
        return variables;
    }
}
