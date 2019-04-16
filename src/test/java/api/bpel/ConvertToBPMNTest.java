package api.bpel;

import org.bpel2bpmn.controllers.BPELController;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class ConvertToBPMNTest {

    private MultipartFile readFile(String name, String path) throws IOException {
        String contentType = "application/xml";
        byte[] content = null;

        ClassLoader classLoader = this.getClass().getClassLoader();
        content = classLoader.getResourceAsStream(path).readAllBytes();

        return new MockMultipartFile(name, name, contentType, content);
    }

    private BpmnModelInstance callApiAndParse(MultipartFile bpelFile, MultipartFile[] wsdlFiles) {
        // Call API
        BPELController controller = new BPELController();
        ResponseEntity response = controller.convertToBPMN(bpelFile, wsdlFiles);

        // Parse BPMN
        InputStream in = new ByteArrayInputStream(response.getBody().toString().getBytes());
        BpmnModelInstance bpmn = Bpmn.readModelFromStream(in);

        return bpmn;
    }

    @Test
    public void convertToBPMN() throws Exception {
        MultipartFile bpelFile = readFile("test.bpel", "bpel/test.bpel");

        MultipartFile wsdlFiles[] = { null };
        wsdlFiles[0] = readFile("test.wsdl", "wsdl/test.wsdl");

        BpmnModelInstance bpmn = callApiAndParse(bpelFile, wsdlFiles);

        // Assertions on converted model
        Bpmn.validateModel(bpmn);

        Collection processes = bpmn.getModelElementsByType(Process.class);
        assertEquals(4, processes.size());

        // TODO: Add more assertions
    }
}
