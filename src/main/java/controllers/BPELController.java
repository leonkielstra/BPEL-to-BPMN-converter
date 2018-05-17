package controllers;

import models.bpel.Process;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utilities.BPELParser;

@RestController
public class BPELController {

    private final String BASE_URL = "/BPEL";

    @RequestMapping(value = BASE_URL + "/toBPMN",
                    method = RequestMethod.POST,
                    consumes = MediaType.TEXT_XML_VALUE)
    public String convertToBPMN(@RequestBody String bpelXML) {
        Process bpelProcess = BPELParser.parse(bpelXML);

        return bpelXML;
    }

}
