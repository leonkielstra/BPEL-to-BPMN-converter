package org.bpel2bpmn.controllers;

import org.bpel2bpmn.exceptions.BPELConversionException;
import org.bpel2bpmn.exceptions.BPELParseException;
import org.bpel2bpmn.exceptions.WSDLParseException;
import org.bpel2bpmn.models.bpel.Process;
import org.bpel2bpmn.utilities.parsers.BPELParser;
import org.bpel2bpmn.utilities.parsers.WSDLParser;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.jdom.Document;
import org.jdom.JDOMException;
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

import java.io.IOException;
import java.util.HashMap;

@RestController
public class BPELController {

    private static Logger LOG = LoggerFactory.getLogger(BPELController.class);

    private final String BASE_URL = "/bpel";

    @RequestMapping(value = BASE_URL + "/convert-to-bpmn",
                    method = RequestMethod.POST,
                    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity convertToBPMN(@RequestParam("bpel") MultipartFile bpelFile, @RequestParam("wsdl") MultipartFile[] wsdlFiles) {
        HashMap<String, Document> wsdlList = null;
        Process bpelProcess = null;
        
        try {
            wsdlList = WSDLParser.parse(wsdlFiles);
            bpelProcess = BPELParser.parse(bpelFile, wsdlList);
            BpmnModelInstance bpmnProcess = bpelProcess.toBPMN();
            String xml = Bpmn.convertToString(bpmnProcess);

            return ResponseEntity.status(HttpStatus.OK).body(xml);
        } catch (WSDLParseException e) {
            LOG.error("WSDL parse error;");
            LOG.error(e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error when parsing WSDL files; " + e.getMessage());
        } catch (BPELParseException e) {
            LOG.error("BPEL parse error;");
            LOG.error(e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error when parsing BPEL file; " + e.getMessage());
        } catch (BPELConversionException e) {
            LOG.error("BPEL conversion error;");
            LOG.error(e.getMessage());

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("This BPEL file cannot be converted; " + e.getMessage());
        }
    }
}
