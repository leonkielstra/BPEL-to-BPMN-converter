package org.bpel2bpmn.utilities.parsers;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class WSDLParser {

    private static Logger LOG = LoggerFactory.getLogger(WSDLParser.class);

    public static HashMap<String, Document> parse(MultipartFile[] wsdlFiles) throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder();
        InputStream inputStream = null;

        HashMap<String, Document> wsdlList = new HashMap<>();
        Document wsdlDocument;
        String targetNamespace;
        for (MultipartFile wsdlFile : wsdlFiles) {
            inputStream = wsdlFile.getInputStream();
            wsdlDocument = builder.build(inputStream);
            targetNamespace = wsdlDocument.getRootElement().getAttributeValue("targetNamespace");
            wsdlList.put(targetNamespace, wsdlDocument);
        }

        return wsdlList;
    }
}
