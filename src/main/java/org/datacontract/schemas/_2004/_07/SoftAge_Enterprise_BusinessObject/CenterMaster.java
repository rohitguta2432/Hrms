/**
 * CenterMaster.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject;

public class CenterMaster  implements java.io.Serializable {
    private java.lang.String centerCode;

    private java.lang.Integer centerId;

    private java.lang.String centerLocation;

    private java.lang.String centerName;

    public CenterMaster() {
    }

    public CenterMaster(
           java.lang.String centerCode,
           java.lang.Integer centerId,
           java.lang.String centerLocation,
           java.lang.String centerName) {
           this.centerCode = centerCode;
           this.centerId = centerId;
           this.centerLocation = centerLocation;
           this.centerName = centerName;
    }


    /**
     * Gets the centerCode value for this CenterMaster.
     * 
     * @return centerCode
     */
    public java.lang.String getCenterCode() {
        return centerCode;
    }


    /**
     * Sets the centerCode value for this CenterMaster.
     * 
     * @param centerCode
     */
    public void setCenterCode(java.lang.String centerCode) {
        this.centerCode = centerCode;
    }


    /**
     * Gets the centerId value for this CenterMaster.
     * 
     * @return centerId
     */
    public java.lang.Integer getCenterId() {
        return centerId;
    }


    /**
     * Sets the centerId value for this CenterMaster.
     * 
     * @param centerId
     */
    public void setCenterId(java.lang.Integer centerId) {
        this.centerId = centerId;
    }


    /**
     * Gets the centerLocation value for this CenterMaster.
     * 
     * @return centerLocation
     */
    public java.lang.String getCenterLocation() {
        return centerLocation;
    }


    /**
     * Sets the centerLocation value for this CenterMaster.
     * 
     * @param centerLocation
     */
    public void setCenterLocation(java.lang.String centerLocation) {
        this.centerLocation = centerLocation;
    }


    /**
     * Gets the centerName value for this CenterMaster.
     * 
     * @return centerName
     */
    public java.lang.String getCenterName() {
        return centerName;
    }


    /**
     * Sets the centerName value for this CenterMaster.
     * 
     * @param centerName
     */
    public void setCenterName(java.lang.String centerName) {
        this.centerName = centerName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CenterMaster)) return false;
        CenterMaster other = (CenterMaster) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.centerCode==null && other.getCenterCode()==null) || 
             (this.centerCode!=null &&
              this.centerCode.equals(other.getCenterCode()))) &&
            ((this.centerId==null && other.getCenterId()==null) || 
             (this.centerId!=null &&
              this.centerId.equals(other.getCenterId()))) &&
            ((this.centerLocation==null && other.getCenterLocation()==null) || 
             (this.centerLocation!=null &&
              this.centerLocation.equals(other.getCenterLocation()))) &&
            ((this.centerName==null && other.getCenterName()==null) || 
             (this.centerName!=null &&
              this.centerName.equals(other.getCenterName())));
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
        if (getCenterCode() != null) {
            _hashCode += getCenterCode().hashCode();
        }
        if (getCenterId() != null) {
            _hashCode += getCenterId().hashCode();
        }
        if (getCenterLocation() != null) {
            _hashCode += getCenterLocation().hashCode();
        }
        if (getCenterName() != null) {
            _hashCode += getCenterName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CenterMaster.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "CenterMaster"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("centerCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "CenterCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("centerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "CenterId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("centerLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "CenterLocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("centerName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "CenterName"));
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
