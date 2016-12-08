/**
 * StateMaster.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject;

public class StateMaster  implements java.io.Serializable {
    private java.lang.Integer countryId;

    private java.lang.Integer isActive;

    private java.lang.Integer stateId;

    private java.lang.String stateName;

    public StateMaster() {
    }

    public StateMaster(
           java.lang.Integer countryId,
           java.lang.Integer isActive,
           java.lang.Integer stateId,
           java.lang.String stateName) {
           this.countryId = countryId;
           this.isActive = isActive;
           this.stateId = stateId;
           this.stateName = stateName;
    }


    /**
     * Gets the countryId value for this StateMaster.
     * 
     * @return countryId
     */
    public java.lang.Integer getCountryId() {
        return countryId;
    }


    /**
     * Sets the countryId value for this StateMaster.
     * 
     * @param countryId
     */
    public void setCountryId(java.lang.Integer countryId) {
        this.countryId = countryId;
    }


    /**
     * Gets the isActive value for this StateMaster.
     * 
     * @return isActive
     */
    public java.lang.Integer getIsActive() {
        return isActive;
    }


    /**
     * Sets the isActive value for this StateMaster.
     * 
     * @param isActive
     */
    public void setIsActive(java.lang.Integer isActive) {
        this.isActive = isActive;
    }


    /**
     * Gets the stateId value for this StateMaster.
     * 
     * @return stateId
     */
    public java.lang.Integer getStateId() {
        return stateId;
    }


    /**
     * Sets the stateId value for this StateMaster.
     * 
     * @param stateId
     */
    public void setStateId(java.lang.Integer stateId) {
        this.stateId = stateId;
    }


    /**
     * Gets the stateName value for this StateMaster.
     * 
     * @return stateName
     */
    public java.lang.String getStateName() {
        return stateName;
    }


    /**
     * Sets the stateName value for this StateMaster.
     * 
     * @param stateName
     */
    public void setStateName(java.lang.String stateName) {
        this.stateName = stateName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StateMaster)) return false;
        StateMaster other = (StateMaster) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.countryId==null && other.getCountryId()==null) || 
             (this.countryId!=null &&
              this.countryId.equals(other.getCountryId()))) &&
            ((this.isActive==null && other.getIsActive()==null) || 
             (this.isActive!=null &&
              this.isActive.equals(other.getIsActive()))) &&
            ((this.stateId==null && other.getStateId()==null) || 
             (this.stateId!=null &&
              this.stateId.equals(other.getStateId()))) &&
            ((this.stateName==null && other.getStateName()==null) || 
             (this.stateName!=null &&
              this.stateName.equals(other.getStateName())));
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
        if (getCountryId() != null) {
            _hashCode += getCountryId().hashCode();
        }
        if (getIsActive() != null) {
            _hashCode += getIsActive().hashCode();
        }
        if (getStateId() != null) {
            _hashCode += getStateId().hashCode();
        }
        if (getStateName() != null) {
            _hashCode += getStateName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StateMaster.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "StateMaster"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "CountryId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isActive");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "IsActive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "StateId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "StateName"));
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
