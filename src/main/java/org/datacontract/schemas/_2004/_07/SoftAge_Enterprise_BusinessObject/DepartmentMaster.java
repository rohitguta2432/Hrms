/**
 * DepartmentMaster.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject;

public class DepartmentMaster  implements java.io.Serializable {
    private java.lang.Integer departmentId;

    private java.lang.String departmentName;

    private java.lang.Integer isActive;

    public DepartmentMaster() {
    }

    public DepartmentMaster(
           java.lang.Integer departmentId,
           java.lang.String departmentName,
           java.lang.Integer isActive) {
           this.departmentId = departmentId;
           this.departmentName = departmentName;
           this.isActive = isActive;
    }


    /**
     * Gets the departmentId value for this DepartmentMaster.
     * 
     * @return departmentId
     */
    public java.lang.Integer getDepartmentId() {
        return departmentId;
    }


    /**
     * Sets the departmentId value for this DepartmentMaster.
     * 
     * @param departmentId
     */
    public void setDepartmentId(java.lang.Integer departmentId) {
        this.departmentId = departmentId;
    }


    /**
     * Gets the departmentName value for this DepartmentMaster.
     * 
     * @return departmentName
     */
    public java.lang.String getDepartmentName() {
        return departmentName;
    }


    /**
     * Sets the departmentName value for this DepartmentMaster.
     * 
     * @param departmentName
     */
    public void setDepartmentName(java.lang.String departmentName) {
        this.departmentName = departmentName;
    }


    /**
     * Gets the isActive value for this DepartmentMaster.
     * 
     * @return isActive
     */
    public java.lang.Integer getIsActive() {
        return isActive;
    }


    /**
     * Sets the isActive value for this DepartmentMaster.
     * 
     * @param isActive
     */
    public void setIsActive(java.lang.Integer isActive) {
        this.isActive = isActive;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepartmentMaster)) return false;
        DepartmentMaster other = (DepartmentMaster) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.departmentId==null && other.getDepartmentId()==null) || 
             (this.departmentId!=null &&
              this.departmentId.equals(other.getDepartmentId()))) &&
            ((this.departmentName==null && other.getDepartmentName()==null) || 
             (this.departmentName!=null &&
              this.departmentName.equals(other.getDepartmentName()))) &&
            ((this.isActive==null && other.getIsActive()==null) || 
             (this.isActive!=null &&
              this.isActive.equals(other.getIsActive())));
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
        if (getDepartmentId() != null) {
            _hashCode += getDepartmentId().hashCode();
        }
        if (getDepartmentName() != null) {
            _hashCode += getDepartmentName().hashCode();
        }
        if (getIsActive() != null) {
            _hashCode += getIsActive().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepartmentMaster.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "DepartmentMaster"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departmentId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "DepartmentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("departmentName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "DepartmentName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isActive");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "IsActive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
