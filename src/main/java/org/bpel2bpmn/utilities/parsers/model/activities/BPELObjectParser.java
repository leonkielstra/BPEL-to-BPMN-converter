package org.bpel2bpmn.utilities.parsers.model.activities;

import org.bpel2bpmn.models.bpel.BPELObject;
import org.bpel2bpmn.models.bpel.activities.Activity;
import org.jdom.Attribute;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * This is a generic parser that can parse any BPELObject that only has the standard attributes.
 */
public class BPELObjectParser {

    private static Logger LOG = LoggerFactory.getLogger(BPELObjectParser.class);

    /**
     * This method parses the BPEL object with its standard attributes.
     *
     * @param element XML element to be parsed
     * @param BPELObjectClass The class to which it should be parsed
     * @param <T>
     * @return parsed activity
     */
    public static <T extends BPELObject> T parse(Element element, Class<T> BPELObjectClass) {
        try {
            T activity = BPELObjectClass.getDeclaredConstructor().newInstance();
            parseAttributes(activity, element);

            return activity;
        } catch (InstantiationException e) {
            LOG.error("Could not instantiate " + BPELObjectClass.toString());
            e.printStackTrace();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method parses the standard attributes of a BPEL object, iff they are defined.
     *
     * @param bpelObject to add the attributes to
     * @param element to extract the attributes from
     * @param <T>
     */
    private static <T extends BPELObject> void parseAttributes(T bpelObject, Element element) {
        for (String attributeName : Activity.STANDARD_ATTRIBUTES) {
            Attribute attribute = element.getAttribute(attributeName);
            if (attribute != null) {
                bpelObject.addAttribute(attributeName, attribute.getValue());
            }
        }
    }
}
