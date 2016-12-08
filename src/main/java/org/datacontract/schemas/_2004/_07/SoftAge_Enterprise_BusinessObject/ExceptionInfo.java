/**
 * ExceptionInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject;

public class ExceptionInfo  extends org.apache.axis.AxisFault  implements java.io.Serializable {
    private java.lang.Integer applicationId;

    private java.lang.String errormessage;

    private java.lang.String methodName;

    private java.lang.String errorurl;

    public ExceptionInfo() {
    }

    public ExceptionInfo(
           java.lang.Integer applicationId,
           java.lang.String errormessage,
           java.lang.String methodName,
           java.lang.String errorurl) {
        this.applicationId = applicationId;
        this.errormessage = errormessage;
        this.methodName = methodName;
        this.errorurl = errorurl;
    }


    /**
     * Gets the applicationId value for this ExceptionInfo.
     * 
     * @return applicationId
     */
    public java.lang.Integer getApplicationId() {
        return applicationId;
    }


    /**
     * Sets the applicationId value for this ExceptionInfo.
     * 
     * @param applicationId
     */
    public void setApplicationId(java.lang.Integer applicationId) {
        this.applicationId = applicationId;
    }


    /**
     * Gets the errormessage value for this ExceptionInfo.
     * 
     * @return errormessage
     */
    public java.lang.String getErrormessage() {
        return errormessage;
    }


    /**
     * Sets the errormessage value for this ExceptionInfo.
     * 
     * @param errormessage
     */
    public void setErrormessage(java.lang.String errormessage) {
        this.errormessage = errormessage;
    }


    /**
     * Gets the methodName value for this ExceptionInfo.
     * 
     * @return methodName
     */
    public java.lang.String getMethodName() {
        return methodName;
    }


    /**
     * Sets the methodName value for this ExceptionInfo.
     * 
     * @param methodName
     */
    public void setMethodName(java.lang.String methodName) {
        this.methodName = methodName;
    }


    /**
     * Gets the errorurl value for this ExceptionInfo.
     * 
     * @return errorurl
     */
    public java.lang.String getErrorurl() {
        return errorurl;
    }


    /**
     * Sets the errorurl value for this ExceptionInfo.
     * 
     * @param errorurl
     */
    public void setErrorurl(java.lang.String errorurl) {
        this.errorurl = errorurl;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ExceptionInfo)) return false;
        ExceptionInfo other = (ExceptionInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.applicationId==null && other.getApplicationId()==null) || 
             (this.applicationId!=null &&
              this.applicationId.equals(other.getApplicationId()))) &&
            ((this.errormessage==null && other.getErrormessage()==null) || 
             (this.errormessage!=null &&
              this.errormessage.equals(other.getErrormessage()))) &&
            ((this.methodName==null && other.getMethodName()==null) || 
             (this.methodName!=null &&
              this.methodName.equals(other.getMethodName()))) &&
            ((this.errorurl==null && other.getErrorurl()==null) || 
             (this.errorurl!=null &&
              this.errorurl.equals(other.getErrorurl())));
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
        if (getApplicationId() != null) {
            _hashCode += getApplicationId().hashCode();
        }
        if (getErrormessage() != null) {
            _hashCode += getErrormessage().hashCode();
        }
        if (getMethodName() != null) {
            _hashCode += getMethodName().hashCode();
        }
        if (getErrorurl() != null) {
            _hashCode += getErrorurl().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ExceptionInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "ExceptionInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applicationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "ApplicationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errormessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "Errormessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("methodName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "MethodName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorurl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "errorurl"));
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


    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, this);
    }
}
