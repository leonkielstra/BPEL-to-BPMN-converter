package org.bpel2bpmn.utilities.parsers.model.activities.structured;

import org.bpel2bpmn.exceptions.BPELParseException;
import org.bpel2bpmn.models.bpel.activities.structured.Branch;
import org.bpel2bpmn.models.bpel.activities.structured.If;
import org.bpel2bpmn.utilities.parsers.BPELParser;
import org.bpel2bpmn.utilities.parsers.model.activities.BPELObjectParser;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IfParser {

    private static Logger LOG = LoggerFactory.getLogger(IfParser.class);

    public static If parse(Element element) throws BPELParseException {
        If bpelIf = BPELObjectParser.parse(element, If.class);
        parseElements(element, bpelIf);

        return bpelIf;
    }

    private static void parseElements(Element element, If bpelIf) throws BPELParseException {
        Branch branch;

        // Parse if branch
        branch = parseBranch(element, bpelIf, true);
        bpelIf.setIfBranch(branch);

        for (Object object : element.getChildren()) {
            Element child = (Element) object;

            // Parse elsif branches
            if (child.getName().equals("elseif")) {
                branch = parseBranch(child, bpelIf, true);
                bpelIf.addElseIfBranch(branch);
            }

            // Parse if branch
            if (child.getName().equals("else")) {
                branch = parseBranch(child, bpelIf, false);
                bpelIf.setElseBranch(branch);
            }
        }
    }

    private static Branch parseBranch(Element element, If bpelIf, boolean hasCondition) throws BPELParseException {
        Branch branch = new Branch();
        Element condition = null;

        for (Object object : element.getChildren()) {
            Element child = (Element) object;
            if (child.getName().equals("condition")) {
                condition = child;
                break;
            }
        }

        if (hasCondition) {
            if (condition == null) {
                throw new BPELParseException("Expected a condition for this if-elseif-else branch.");
            }

            branch.setCondition(condition.getValue());
            String expLanguage = element.getAttributeValue("expressionLanguage");
            if (expLanguage != null) {
                branch.setExpressionLanguage(expLanguage);
            } else {
                branch.setExpressionLanguage(bpelIf.getParentExpressionLanguage());
            }
        }

        BPELParser.parseChildren(branch, element);

        return branch;
    }
}
