/**
 * OfficeTypeMaster.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject;

public class OfficeTypeMaster  implements java.io.Serializable {
    private java.lang.Integer isActive;

    private java.lang.Integer officeTypeId;

    private java.lang.String officeTypeName;

    public OfficeTypeMaster() {
    }

    public OfficeTypeMaster(
           java.lang.Integer isActive,
           java.lang.Integer officeTypeId,
           java.lang.String officeTypeName) {
           this.isActive = isActive;
           this.officeTypeId = officeTypeId;
           this.officeTypeName = officeTypeName;
    }


    /**
     * Gets the isActive value for this OfficeTypeMaster.
     * 
     * @return isActive
     */
    public java.lang.Integer getIsActive() {
        return isActive;
    }


    /**
     * Sets the isActive value for this OfficeTypeMaster.
     * 
     * @param isActive
     */
    public void setIsActive(java.lang.Integer isActive) {
        this.isActive = isActive;
    }


    /**
     * Gets the officeTypeId value for this OfficeTypeMaster.
     * 
     * @return officeTypeId
     */
    public java.lang.Integer getOfficeTypeId() {
        return officeTypeId;
    }


    /**
     * Sets the officeTypeId value for this OfficeTypeMaster.
     * 
     * @param officeTypeId
     */
    public void setOfficeTypeId(java.lang.Integer officeTypeId) {
        this.officeTypeId = officeTypeId;
    }


    /**
     * Gets the officeTypeName value for this OfficeTypeMaster.
     * 
     * @return officeTypeName
     */
    public java.lang.String getOfficeTypeName() {
        return officeTypeName;
    }


    /**
     * Sets the officeTypeName value for this OfficeTypeMaster.
     * 
     * @param officeTypeName
     */
    public void setOfficeTypeName(java.lang.String officeTypeName) {
        this.officeTypeName = officeTypeName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OfficeTypeMaster)) return false;
        OfficeTypeMaster other = (OfficeTypeMaster) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.isActive==null && other.getIsActive()==null) || 
             (this.isActive!=null &&
              this.isActive.equals(other.getIsActive()))) &&
            ((this.officeTypeId==null && other.getOfficeTypeId()==null) || 
             (this.officeTypeId!=null &&
              this.officeTypeId.equals(other.getOfficeTypeId()))) &&
            ((this.officeTypeName==null && other.getOfficeTypeName()==null) || 
             (this.officeTypeName!=null &&
              this.officeTypeName.equals(other.getOfficeTypeName())));
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
        if (getIsActive() != null) {
            _hashCode += getIsActive().hashCode();
        }
        if (getOfficeTypeId() != null) {
            _hashCode += getOfficeTypeId().hashCode();
        }
        if (getOfficeTypeName() != null) {
            _hashCode += getOfficeTypeName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OfficeTypeMaster.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "officeTypeMaster"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isActive");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "IsActive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("officeTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "OfficeTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("officeTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "OfficeTypeName"));
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
