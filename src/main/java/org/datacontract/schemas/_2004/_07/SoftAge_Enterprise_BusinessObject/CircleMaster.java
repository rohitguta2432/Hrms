/**
 * CircleMaster.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject;

public class CircleMaster  implements java.io.Serializable {
    private java.lang.Integer circleId;

    private java.lang.String circleName;

    public CircleMaster() {
    }

    public CircleMaster(
           java.lang.Integer circleId,
           java.lang.String circleName) {
           this.circleId = circleId;
           this.circleName = circleName;
    }


    /**
     * Gets the circleId value for this CircleMaster.
     * 
     * @return circleId
     */
    public java.lang.Integer getCircleId() {
        return circleId;
    }


    /**
     * Sets the circleId value for this CircleMaster.
     * 
     * @param circleId
     */
    public void setCircleId(java.lang.Integer circleId) {
        this.circleId = circleId;
    }


    /**
     * Gets the circleName value for this CircleMaster.
     * 
     * @return circleName
     */
    public java.lang.String getCircleName() {
        return circleName;
    }


    /**
     * Sets the circleName value for this CircleMaster.
     * 
     * @param circleName
     */
    public void setCircleName(java.lang.String circleName) {
        this.circleName = circleName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CircleMaster)) return false;
        CircleMaster other = (CircleMaster) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.circleId==null && other.getCircleId()==null) || 
             (this.circleId!=null &&
              this.circleId.equals(other.getCircleId()))) &&
            ((this.circleName==null && other.getCircleName()==null) || 
             (this.circleName!=null &&
              this.circleName.equals(other.getCircleName())));
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
        if (getCircleId() != null) {
            _hashCode += getCircleId().hashCode();
        }
        if (getCircleName() != null) {
            _hashCode += getCircleName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CircleMaster.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "CircleMaster"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("circleId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "CircleId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("circleName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "CircleName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
