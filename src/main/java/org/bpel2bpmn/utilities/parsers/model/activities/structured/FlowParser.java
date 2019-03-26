package org.bpel2bpmn.utilities.parsers.model.activities.structured;

import org.bpel2bpmn.exceptions.BPELParseException;
import org.bpel2bpmn.models.bpel.activities.structured.Flow;
import org.bpel2bpmn.utilities.parsers.BPELParser;
import org.bpel2bpmn.utilities.parsers.model.activities.BPELObjectParser;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;

public class FlowParser {

    private static Logger LOG = LoggerFactory.getLogger(FlowParser.class);

    public static Flow parse(Element element) throws BPELParseException {
        Flow flow = BPELObjectParser.parse(element, Flow.class);
        parseElements(element, flow);

        return flow;
    }

    private static void parseElements(Element element, Flow flow) throws BPELParseException {
        // Find and parse links
        for (Object object : element.getChildren()) {
            Element child = (Element) object;

            switch (child.getName().toLowerCase()) {
                case "links":
                    parseLinks(child, flow);
                    break;
            }
        }

        // Parse other activities in flow
        BPELParser.parseChildren(flow, element);
    }

    private static void parseLinks(Element element, Flow flow) throws BPELParseException {
        for (Object object : element.getChildren()) {
            Element child = (Element) object;

            switch (child.getName().toLowerCase()) {
                case "link":
                    String name = child.getAttributeValue("name");

                    if (name != null) {
                        flow.addLink(child.getAttributeValue("name"));
                    } else {
                        throw new BPELParseException("Link object should contain a 'name' attribute.");
                    }
                    break;
                case "documentation":
                    LOG.debug("Documentation gets lost in conversion, since there is no actual mapping of a link object");
                    break;
            }
        }
    }
}
