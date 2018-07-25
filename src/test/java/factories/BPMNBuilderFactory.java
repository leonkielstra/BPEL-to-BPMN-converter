package factories;

import org.bpel2bpmn.utilities.builders.BPMNBuilder;
import org.camunda.bpm.model.bpmn.instance.Definitions;

public class BPMNBuilderFactory {

    public static BPMNBuilder create() {
        BPMNBuilder builder = new BPMNBuilder();

        Definitions definitions = builder.createDefinitions("bpel-to-bpmn-testsuite");
        builder.createExecutableProcess(definitions, "testProcess");

        return builder;
    }
}
