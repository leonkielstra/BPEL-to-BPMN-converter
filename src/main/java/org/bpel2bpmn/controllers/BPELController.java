package org.bpel2bpmn.controllers;

import org.bpel2bpmn.models.bpel.Process;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.bpel2bpmn.utilities.parsers.BPELParser;

import java.text.ParseException;

@RestController
public class BPELController {

    private static Logger LOG = LoggerFactory.getLogger(BPELController.class);

    private final String BASE_URL = "/BPEL";

    @RequestMapping(value = BASE_URL + "/toBPMN",
                    method = RequestMethod.POST,
                    consumes = MediaType.TEXT_XML_VALUE)
    public ResponseEntity convertToBPMN(@RequestBody String bpelXML) {
        Process bpelProcess;
        try {
            bpelProcess = BPELParser.parse(bpelXML);
        } catch (ParseException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The given BPEL is invalid.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(bpelProcess);
    }

}
