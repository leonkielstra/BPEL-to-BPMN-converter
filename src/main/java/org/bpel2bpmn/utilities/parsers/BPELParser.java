package org.bpel2bpmn.utilities.parsers;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.Process;
import org.bpel2bpmn.utilities.parsers.model.ProcessParser;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class BPELParser {

    private static Logger LOG = LoggerFactory.getLogger(BPELParser.class);

    public static Process parse(MultipartFile bpelFile) {

        SAXBuilder builder = new SAXBuilder();
        InputStream inputStream = null;
        try {
            inputStream = bpelFile.getInputStream();
            Document bpelXML = builder.build(inputStream);

            Element root = bpelXML.getRootElement();
            return (Process) parseElement(root);
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static BPELObject parseElement(Element element) {
        LOG.debug("Parsing element: " + element.getName());
        BPELObject bpelObject;

        switch (element.getName().toLowerCase()) {
            case "process":
                bpelObject = ProcessParser.parse(element);
                break;
            default:
                return null; // TODO: action if not recognized.
        }

        for (Object child : element.getChildren()) {
            Element childElement = (Element) child;
            BPELObject parsedChild = parseElement(childElement);
            if (parsedChild != null) {
                bpelObject.addChild(parsedChild);
                parsedChild.setParent(bpelObject);
            }
        }

        return bpelObject;
    }
}
