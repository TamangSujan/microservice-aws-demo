package stack;

import software.amazon.awscdk.*;

public class LocalStack {
    private final App app;
    private final Stack stack;
    public LocalStack(String id, String templateFolder){
        app = new App(AppProps.builder()
                .outdir(templateFolder)
                .build());
        stack = new Stack(app, id, StackProps.builder()
                .synthesizer(BootstraplessSynthesizer.Builder.create().build())
                .build());
    }
    public Stack getStack(){ return stack; }

    public void generateTemplate(){ app.synth(); }
}
