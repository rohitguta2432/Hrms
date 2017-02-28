/**
 * AssetDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject;

public class AssetDetails  implements java.io.Serializable {
    private java.lang.String allocatedDate;

    private java.lang.String allocated_By;

    private java.util.Calendar allocated_Date;

    private java.lang.String asset_Name;

    private java.lang.String barcode_No;

    private java.lang.Integer department_Id;

    private java.lang.String department_Name;

    private java.lang.String emp_code;

    public AssetDetails() {
    }

    public AssetDetails(
           java.lang.String allocatedDate,
           java.lang.String allocated_By,
           java.util.Calendar allocated_Date,
           java.lang.String asset_Name,
           java.lang.String barcode_No,
           java.lang.Integer department_Id,
           java.lang.String department_Name,
           java.lang.String emp_code) {
           this.allocatedDate = allocatedDate;
           this.allocated_By = allocated_By;
           this.allocated_Date = allocated_Date;
           this.asset_Name = asset_Name;
           this.barcode_No = barcode_No;
           this.department_Id = department_Id;
           this.department_Name = department_Name;
           this.emp_code = emp_code;
    }


    /**
     * Gets the allocatedDate value for this AssetDetails.
     * 
     * @return allocatedDate
     */
    public java.lang.String getAllocatedDate() {
        return allocatedDate;
    }


    /**
     * Sets the allocatedDate value for this AssetDetails.
     * 
     * @param allocatedDate
     */
    public void setAllocatedDate(java.lang.String allocatedDate) {
        this.allocatedDate = allocatedDate;
    }


    /**
     * Gets the allocated_By value for this AssetDetails.
     * 
     * @return allocated_By
     */
    public java.lang.String getAllocated_By() {
        return allocated_By;
    }


    /**
     * Sets the allocated_By value for this AssetDetails.
     * 
     * @param allocated_By
     */
    public void setAllocated_By(java.lang.String allocated_By) {
        this.allocated_By = allocated_By;
    }


    /**
     * Gets the allocated_Date value for this AssetDetails.
     * 
     * @return allocated_Date
     */
    public java.util.Calendar getAllocated_Date() {
        return allocated_Date;
    }


    /**
     * Sets the allocated_Date value for this AssetDetails.
     * 
     * @param allocated_Date
     */
    public void setAllocated_Date(java.util.Calendar allocated_Date) {
        this.allocated_Date = allocated_Date;
    }


    /**
     * Gets the asset_Name value for this AssetDetails.
     * 
     * @return asset_Name
     */
    public java.lang.String getAsset_Name() {
        return asset_Name;
    }


    /**
     * Sets the asset_Name value for this AssetDetails.
     * 
     * @param asset_Name
     */
    public void setAsset_Name(java.lang.String asset_Name) {
        this.asset_Name = asset_Name;
    }


    /**
     * Gets the barcode_No value for this AssetDetails.
     * 
     * @return barcode_No
     */
    public java.lang.String getBarcode_No() {
        return barcode_No;
    }


    /**
     * Sets the barcode_No value for this AssetDetails.
     * 
     * @param barcode_No
     */
    public void setBarcode_No(java.lang.String barcode_No) {
        this.barcode_No = barcode_No;
    }


    /**
     * Gets the department_Id value for this AssetDetails.
     * 
     * @return department_Id
     */
    public java.lang.Integer getDepartment_Id() {
        return department_Id;
    }


    /**
     * Sets the department_Id value for this AssetDetails.
     * 
     * @param department_Id
     */
    public void setDepartment_Id(java.lang.Integer department_Id) {
        this.department_Id = department_Id;
    }


    /**
     * Gets the department_Name value for this AssetDetails.
     * 
     * @return department_Name
     */
    public java.lang.String getDepartment_Name() {
        return department_Name;
    }


    /**
     * Sets the department_Name value for this AssetDetails.
     * 
     * @param department_Name
     */
    public void setDepartment_Name(java.lang.String department_Name) {
        this.department_Name = department_Name;
    }


    /**
     * Gets the emp_code value for this AssetDetails.
     * 
     * @return emp_code
     */
    public java.lang.String getEmp_code() {
        return emp_code;
    }


    /**
     * Sets the emp_code value for this AssetDetails.
     * 
     * @param emp_code
     */
    public void setEmp_code(java.lang.String emp_code) {
        this.emp_code = emp_code;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AssetDetails)) return false;
        AssetDetails other = (AssetDetails) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.allocatedDate==null && other.getAllocatedDate()==null) || 
             (this.allocatedDate!=null &&
              this.allocatedDate.equals(other.getAllocatedDate()))) &&
            ((this.allocated_By==null && other.getAllocated_By()==null) || 
             (this.allocated_By!=null &&
              this.allocated_By.equals(other.getAllocated_By()))) &&
            ((this.allocated_Date==null && other.getAllocated_Date()==null) || 
             (this.allocated_Date!=null &&
              this.allocated_Date.equals(other.getAllocated_Date()))) &&
            ((this.asset_Name==null && other.getAsset_Name()==null) || 
             (this.asset_Name!=null &&
              this.asset_Name.equals(other.getAsset_Name()))) &&
            ((this.barcode_No==null && other.getBarcode_No()==null) || 
             (this.barcode_No!=null &&
              this.barcode_No.equals(other.getBarcode_No()))) &&
            ((this.department_Id==null && other.getDepartment_Id()==null) || 
             (this.department_Id!=null &&
              this.department_Id.equals(other.getDepartment_Id()))) &&
            ((this.department_Name==null && other.getDepartment_Name()==null) || 
             (this.department_Name!=null &&
              this.department_Name.equals(other.getDepartment_Name()))) &&
            ((this.emp_code==null && other.getEmp_code()==null) || 
             (this.emp_code!=null &&
              this.emp_code.equals(other.getEmp_code())));
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
        if (getAllocatedDate() != null) {
            _hashCode += getAllocatedDate().hashCode();
        }
        if (getAllocated_By() != null) {
            _hashCode += getAllocated_By().hashCode();
        }
        if (getAllocated_Date() != null) {
            _hashCode += getAllocated_Date().hashCode();
        }
        if (getAsset_Name() != null) {
            _hashCode += getAsset_Name().hashCode();
        }
        if (getBarcode_No() != null) {
            _hashCode += getBarcode_No().hashCode();
        }
        if (getDepartment_Id() != null) {
            _hashCode += getDepartment_Id().hashCode();
        }
        if (getDepartment_Name() != null) {
            _hashCode += getDepartment_Name().hashCode();
        }
        if (getEmp_code() != null) {
            _hashCode += getEmp_code().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AssetDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "AssetDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allocatedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "AllocatedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allocated_By");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "Allocated_By"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allocated_Date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "Allocated_Date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asset_Name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "Asset_Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("barcode_No");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "Barcode_No"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("department_Id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "Department_Id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("department_Name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "Department_Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emp_code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/SoftAge.Enterprise.BusinessObject", "Emp_code"));
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
