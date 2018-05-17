package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BPELController {

    private final String BASE_URL = "/BPEL";

    @RequestMapping(value = BASE_URL + "/toBPMN", method = RequestMethod.POST)
    public String parseBPEL() {
        System.out.println("Request received");
        return "hello";
    }
}
