/**
 * Office_Details.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject;

public class Office_Details  implements java.io.Serializable {
    private java.lang.Integer circle_id;

    private java.lang.Integer corp_office_id;

    private java.lang.Integer office_id;

    private java.lang.Integer office_type_id;

    private java.util.Calendar rent_agreement_expiry_date;

    private java.util.Calendar rent_agreement_start_date;

    private java.lang.Integer spoke_id;

    private java.util.Calendar trade_license_expiry_date;

    private java.util.Calendar trade_license_starte_date;

    public Office_Details() {
    }

    public Office_Details(
           java.lang.Integer circle_id,
           java.lang.Integer corp_office_id,
           java.lang.Integer office_id,
           java.lang.Integer office_type_id,
           java.util.Calendar rent_agreement_expiry_date,
           java.util.Calendar rent_agreement_start_date,
           java.lang.Integer spoke_id,
           java.util.Calendar trade_license_expiry_date,
           java.util.Calendar trade_license_starte_date) {
           this.circle_id = circle_id;
           this.corp_office_id = corp_office_id;
           this.office_id = office_id;
           this.office_type_id = office_type_id;
           this.rent_agreement_expiry_date = rent_agreement_expiry_date;
           this.rent_agreement_start_date = rent_agreement_start_date;
           this.spoke_id = spoke_id;
           this.trade_license_expiry_date = trade_license_expiry_date;
           this.trade_license_starte_date = trade_license_starte_date;
    }


    /**
     * Gets the circle_id value for this Office_Details.
     * 
     * @return circle_id
     */
    public java.lang.Integer getCircle_id() {
        return circle_id;
    }


    /**
     * Sets the circle_id value for this Office_Details.
     * 
     * @param circle_id
     */
    public void setCircle_id(java.lang.Integer circle_id) {
        this.circle_id = circle_id;
    }


    /**
     * Gets the corp_office_id value for this Office_Details.
     * 
     * @return corp_office_id
     */
    public java.lang.Integer getCorp_office_id() {
        return corp_office_id;
    }


    /**
     * Sets the corp_office_id value for this Office_Details.
     * 
     * @param corp_office_id
     */
    public void setCorp_office_id(java.lang.Integer corp_office_id) {
        this.corp_office_id = corp_office_id;
    }


    /**
     * Gets the office_id value for this Office_Details.
     * 
     * @return office_id
     */
    public java.lang.Integer getOffice_id() {
        return office_id;
    }


    /**
     * Sets the office_id value for this Office_Details.
     * 
     * @param office_id
     */
    public void setOffice_id(java.lang.Integer office_id) {
        this.office_id = office_id;
    }


    /**
     * Gets the office_type_id value for this Office_Details.
     * 
     * @return office_type_id
     */
    public java.lang.Integer getOffice_type_id() {
        return office_type_id;
    }


    /**
     * Sets the office_type_id value for this Office_Details.
     * 
     * @param office_type_id
     */
    public void setOffice_type_id(java.lang.Integer office_type_id) {
        this.office_type_id = office_type_id;
    }


    /**
     * Gets the rent_agreement_expiry_date value for this Office_Details.
     * 
     * @return rent_agreement_expiry_date
     */
    public java.util.Calendar getRent_agreement_expiry_date() {
        return rent_agreement_expiry_date;
    }


    /**
     * Sets the rent_agreement_expiry_date value for this Office_Details.
     * 
     * @param rent_agreement_expiry_date
     */
    public void setRent_agreement_expiry_date(java.util.Calendar rent_agreement_expiry_date) {
        this.rent_agreement_expiry_date = rent_agreement_expiry_date;
    }


    /**
     * Gets the rent_agreement_start_date value for this Office_Details.
     * 
     * @return rent_agreement_start_date
     */
    public java.util.Calendar getRent_agreement_start_date() {
        return rent_agreement_start_date;
    }


    /**
     * Sets the rent_agreement_start_date value for this Office_Details.
     * 
     * @param rent_agreement_start_date
     */
    public void setRent_agreement_start_date(java.util.Calendar rent_agreement_start_date) {
        this.rent_agreement_start_date = rent_agreement_start_date;
    }


    /**
     * Gets the spoke_id value for this Office_Details.
     * 
     * @return spoke_id
     */
    public java.lang.Integer getSpoke_id() {
        return spoke_id;
    }


    /**
     * Sets the spoke_id value for this Office_Details.
     * 
     * @param spoke_id
     */
    public void setSpoke_id(java.lang.Integer spoke_id) {
        this.spoke_id = spoke_id;
    }


    /**
     * Gets the trade_license_expiry_date value for this Office_Details.
     * 
     * @return trade_license_expiry_date
     */
    public java.util.Calendar getTrade_license_expiry_date() {
        return trade_license_expiry_date;
    }


    /**
     * Sets the trade_license_expiry_date value for this Office_Details.
     * 
     * @param trade_license_expiry_date
     */
    public void setTrade_license_expiry_date(java.util.Calendar trade_license_expiry_date) {
        this.trade_license_expiry_date = trade_license_expiry_date;
    }


    /**
     * Gets the trade_license_starte_date value for this Office_Details.
     * 
     * @return trade_license_starte_date
     */
    public java.util.Calendar getTrade_license_starte_date() {
        return trade_license_starte_date;
    }


    /**
     * Sets the trade_license_starte_date value for this Office_Details.
     * 
     * @param trade_license_starte_date
     */
    public void setTrade_license_starte_date(java.util.Calendar trade_license_starte_date) {
        this.trade_license_starte_date = trade_license_starte_date;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Office_Details)) return false;
        Office_Details other = (Office_Details) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.circle_id==null && other.getCircle_id()==null) || 
             (this.circle_id!=null &&
              this.circle_id.equals(other.getCircle_id()))) &&
            ((this.corp_office_id==null && other.getCorp_office_id()==null) || 
             (this.corp_office_id!=null &&
              this.corp_office_id.equals(other.getCorp_office_id()))) &&
            ((this.office_id==null && other.getOffice_id()==null) || 
             (this.office_id!=null &&
              this.office_id.equals(other.getOffice_id()))) &&
            ((this.office_type_id==null && other.getOffice_type_id()==null) || 
             (this.office_type_id!=null &&
              this.office_type_id.equals(other.getOffice_type_id()))) &&
            ((this.rent_agreement_expiry_date==null && other.getRent_agreement_expiry_date()==null) || 
             (this.rent_agreement_expiry_date!=null &&
              this.rent_agreement_expiry_date.equals(other.getRent_agreement_expiry_date()))) &&
            ((this.rent_agreement_start_date==null && other.getRent_agreement_start_date()==null) || 
             (this.rent_agreement_start_date!=null &&
              this.rent_agreement_start_date.equals(other.getRent_agreement_start_date()))) &&
            ((this.spoke_id==null && other.getSpoke_id()==null) || 
             (this.spoke_id!=null &&
              this.spoke_id.equals(other.getSpoke_id()))) &&
            ((this.trade_license_expiry_date==null && other.getTrade_license_expiry_date()==null) || 
             (this.trade_license_expiry_date!=null &&
              this.trade_license_expiry_date.equals(other.getTrade_license_expiry_date()))) &&
            ((this.trade_license_starte_date==null && other.getTrade_license_starte_date()==null) || 
             (this.trade_license_starte_date!=null &&
              this.trade_license_starte_date.equals(other.getTrade_license_starte_date())));
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
        if (getCircle_id() != null) {
            _hashCode += getCircle_id().hashCode();
        }
        if (getCorp_office_id() != null) {
            _hashCode += getCorp_office_id().hashCode();
        }
        if (getOffice_id() != null) {
            _hashCode += getOffice_id().hashCode();
        }
        if (getOffice_type_id() != null) {
            _hashCode += getOffice_type_id().hashCode();
        }
        if (getRent_agreement_expiry_date() != null) {
            _hashCode += getRent_agreement_expiry_date().hashCode();
        }
        if (getRent_agreement_start_date() != null) {
            _hashCode += getRent_agreement_start_date().hashCode();
        }
        if (getSpoke_id() != null) {
            _hashCode += getSpoke_id().hashCode();
        }
        if (getTrade_license_expiry_date() != null) {
            _hashCode += getTrade_license_expiry_date().hashCode();
        }
        if (getTrade_license_starte_date() != null) {
            _hashCode += getTrade_license_starte_date().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Office_Details.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "office_Details"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("circle_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "circle_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corp_office_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "corp_office_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("office_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "office_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("office_type_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "office_type_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rent_agreement_expiry_date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "rent_agreement_expiry_date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rent_agreement_start_date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "rent_agreement_start_date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spoke_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "spoke_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trade_license_expiry_date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "trade_license_expiry_date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trade_license_starte_date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "trade_license_starte_date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
