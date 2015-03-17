/**
 * =============================================================================
 *
 * ORCID (R) Open Source
 * http://orcid.org
 *
 * Copyright (c) 2012-2014 ORCID, Inc.
 * Licensed under an MIT-Style License (MIT)
 * http://orcid.org/open-source-license
 *
 * This copyright and license information (including a link to the full license)
 * shall be included in its entirety in all copies or substantial portion of
 * the software.
 *
 * =============================================================================
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.07.22 at 06:46:00 PM BST 
//

package org.orcid.jaxb.model.notification.custom;

import javax.xml.bind.annotation.XmlRegistry;

import org.orcid.jaxb.model.notification.addactivities.NotificationAddActivities;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the org.orcid.jaxb.model.notification package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of
     * schema derived classes for package:
     * org.orcid.jaxb.model.notification.custom
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NotificationAddActivities }
     * 
     */
    public NotificationCustom createNotificationCustom() {
        return new NotificationCustom();
    }

}
