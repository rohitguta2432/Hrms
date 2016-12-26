/**
 * SpokeMaster.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject;

public class SpokeMaster  implements java.io.Serializable {
    private java.lang.String spokeCode;

    private java.lang.Integer spokeId;

    private java.lang.String spokeName;

    private java.lang.Integer circleid;

    public SpokeMaster() {
    }

    public SpokeMaster(
           java.lang.String spokeCode,
           java.lang.Integer spokeId,
           java.lang.String spokeName,
           java.lang.Integer circleid) {
           this.spokeCode = spokeCode;
           this.spokeId = spokeId;
           this.spokeName = spokeName;
           this.circleid = circleid;
    }


    /**
     * Gets the spokeCode value for this SpokeMaster.
     * 
     * @return spokeCode
     */
    public java.lang.String getSpokeCode() {
        return spokeCode;
    }


    /**
     * Sets the spokeCode value for this SpokeMaster.
     * 
     * @param spokeCode
     */
    public void setSpokeCode(java.lang.String spokeCode) {
        this.spokeCode = spokeCode;
    }


    /**
     * Gets the spokeId value for this SpokeMaster.
     * 
     * @return spokeId
     */
    public java.lang.Integer getSpokeId() {
        return spokeId;
    }


    /**
     * Sets the spokeId value for this SpokeMaster.
     * 
     * @param spokeId
     */
    public void setSpokeId(java.lang.Integer spokeId) {
        this.spokeId = spokeId;
    }


    /**
     * Gets the spokeName value for this SpokeMaster.
     * 
     * @return spokeName
     */
    public java.lang.String getSpokeName() {
        return spokeName;
    }


    /**
     * Sets the spokeName value for this SpokeMaster.
     * 
     * @param spokeName
     */
    public void setSpokeName(java.lang.String spokeName) {
        this.spokeName = spokeName;
    }


    /**
     * Gets the circleid value for this SpokeMaster.
     * 
     * @return circleid
     */
    public java.lang.Integer getCircleid() {
        return circleid;
    }


    /**
     * Sets the circleid value for this SpokeMaster.
     * 
     * @param circleid
     */
    public void setCircleid(java.lang.Integer circleid) {
        this.circleid = circleid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SpokeMaster)) return false;
        SpokeMaster other = (SpokeMaster) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.spokeCode==null && other.getSpokeCode()==null) || 
             (this.spokeCode!=null &&
              this.spokeCode.equals(other.getSpokeCode()))) &&
            ((this.spokeId==null && other.getSpokeId()==null) || 
             (this.spokeId!=null &&
              this.spokeId.equals(other.getSpokeId()))) &&
            ((this.spokeName==null && other.getSpokeName()==null) || 
             (this.spokeName!=null &&
              this.spokeName.equals(other.getSpokeName()))) &&
            ((this.circleid==null && other.getCircleid()==null) || 
             (this.circleid!=null &&
              this.circleid.equals(other.getCircleid())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getSpokeCode() != null) {
            _hashCode += getSpokeCode().hashCode();
        }
        if (getSpokeId() != null) {
            _hashCode += getSpokeId().hashCode();
        }
        if (getSpokeName() != null) {
            _hashCode += getSpokeName().hashCode();
        }
        if (getCircleid() != null) {
            _hashCode += getCircleid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SpokeMaster.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "SpokeMaster"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spokeCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "SpokeCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spokeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "SpokeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spokeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "SpokeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("circleid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "circleid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
