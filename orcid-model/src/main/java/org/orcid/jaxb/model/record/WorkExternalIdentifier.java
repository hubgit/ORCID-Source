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
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.08.13 at 11:03:27 AM BST 
//

package org.orcid.jaxb.model.record;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.io.Serializable;

/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 *  
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType( propOrder = { "workExternalIdentifierType", "workExternalIdentifierId" })
@XmlRootElement(name = "workExternalIdentifier")
public class WorkExternalIdentifier implements ExternalIdentifier, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "external-identifier-type", namespace = "http://www.orcid.org/ns/work", required = true)
    protected WorkExternalIdentifierType workExternalIdentifierType;
    @XmlElement(name = "external-identifier-id", namespace = "http://www.orcid.org/ns/work", required = true)
    protected WorkExternalIdentifierId workExternalIdentifierId;

    public WorkExternalIdentifier() {
        
    }
    
    /**
     * Gets the value of the workExternalIdentifierType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public WorkExternalIdentifierType getWorkExternalIdentifierType() {
        return workExternalIdentifierType;
    }

    /**
     * Sets the value of the workExternalIdentifierType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkExternalIdentifierType(WorkExternalIdentifierType value) {
        this.workExternalIdentifierType = value;
    }

    /**
     * Gets the value of the workExternalIdentifierId property.
     * 
     * @return
     *     possible object is
     *     {@link WorkExternalIdentifierId }
     *     
     */
    public WorkExternalIdentifierId getWorkExternalIdentifierId() {
        return workExternalIdentifierId;
    }

    /**
     * Sets the value of the workExternalIdentifierId property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkExternalIdentifierId }
     *     
     */
    public void setWorkExternalIdentifierId(WorkExternalIdentifierId value) {
        this.workExternalIdentifierId = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkExternalIdentifier)) {
            return false;
        }

        WorkExternalIdentifier that = (WorkExternalIdentifier) o;

        if (workExternalIdentifierId != null ? !workExternalIdentifierId.equals(that.workExternalIdentifierId) : that.workExternalIdentifierId != null) {
            return false;
        }
        if (workExternalIdentifierType != that.workExternalIdentifierType) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = workExternalIdentifierType != null ? workExternalIdentifierType.hashCode() : 0;
        result = 31 * result + (workExternalIdentifierId != null ? workExternalIdentifierId.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        String result = "";
        if(workExternalIdentifierType != null)
            result += workExternalIdentifierType.toString() + "(";
        if(workExternalIdentifierId != null)
            result += workExternalIdentifierId.getContent();
        if(workExternalIdentifierType != null)
            result += ")";        
        return result;
    }
    
    @Override
    public boolean passGroupingValidation() {
        if(WorkExternalIdentifierType.ISSN.equals(workExternalIdentifierType))
            return false;
        return true;
    }
}
