package org.bpel2bpmn.models.bpmn;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

public class Example {

    public static BpmnModelInstance createInstance() {
        BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("processId")
                .startEvent()
                .userTask()
                .endEvent()
                .done();

        return modelInstance;
    }
}
