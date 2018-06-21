package org.bpel2bpmn.controllers;

import org.bpel2bpmn.models.bpel.Process;
import org.bpel2bpmn.utilities.parsers.BPELParser;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BPELController {

    private static Logger LOG = LoggerFactory.getLogger(BPELController.class);

    private final String BASE_URL = "/BPEL";

    @RequestMapping(value = BASE_URL + "/toBPMN",
                    method = RequestMethod.POST,
                    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity convertToBPMN(@RequestParam("bpelFile") MultipartFile uploadFile) {
        Process bpelProcess = BPELParser.parse(uploadFile);

        BpmnModelInstance bpmnProcess = bpelProcess.toBPMN();
        String xml = Bpmn.convertToString(bpmnProcess);

        return ResponseEntity.status(HttpStatus.OK).body(xml);
    }

}
