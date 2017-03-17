/**
 * ISoftAgeEnterprise.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface ISoftAgeEnterprise extends java.rmi.Remote {
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.UserDetails getUserDetails(java.lang.String emp_code, java.lang.String password, java.lang.String companyId) throws java.rmi.RemoteException, org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.ExceptionInfo;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.UserDetails authanticateUser(java.lang.String emp_code, java.lang.String password) throws java.rmi.RemoteException, org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.ExceptionInfo;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.UserDetails updateDeviceID(java.lang.String emp_code, java.lang.String deviceID) throws java.rmi.RemoteException, org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.ExceptionInfo;
    public java.lang.Boolean getUserDetailByEVM(java.lang.String emp_code) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.AssetDetails[] getAssetDetailUserWise(java.lang.String emp_code) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.UserDetails getUserDetail(java.lang.String emp_code) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.OfficeTypeMaster[] getOfficeType_Master_new() throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CompanyMaster[] getCompanyDetails() throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CountryMaster[] getCountryDetails() throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CircleMaster[] getCircleDetails(java.lang.Integer countrtyId, java.lang.String typeuser, java.lang.Integer spokeid) throws java.rmi.RemoteException, org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.ExceptionInfo;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CircleMaster[] getCircleDetails_byid(java.lang.Integer countrtyId, java.lang.String typeuser, java.lang.Integer spokeid) throws java.rmi.RemoteException, org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.ExceptionInfo;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.SpokeMaster[] getSpokeDetails(java.lang.Integer circleId, java.lang.String typeuse, java.lang.Integer spokeid) throws java.rmi.RemoteException, org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.ExceptionInfo;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.RoleMaster[] getRoleDetails() throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.Office_Details[] getofficedetails() throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.DepartmentMaster[] getDepartmentDeails() throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.StateMaster[] getStateDetails(java.lang.Integer countryId) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.OfficeTypeMaster[] getOfficeTypeMaster(java.lang.String typeuser) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.OfficeTypeMaster[] getOfficeTypeMaster_byID(java.lang.Integer ID) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.SpokeMaster[] getSpokeDetails_BYID(java.lang.Integer circleId, java.lang.Integer spokeid) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CircleMaster[] getCircleDetails_BYID_ID(java.lang.Integer countryId, java.lang.Integer circleid) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.SpokeMaster[] getSpokeDetails_ByCircleID(java.lang.Integer circleId) throws java.rmi.RemoteException;
    public void insertExceptionDetails(java.lang.Integer applicationId, java.lang.String errorMessage, java.lang.String errorUrl, java.lang.String methodName) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CircleMaster[] getCircleDetails_byCircleid(java.lang.Integer countryId, java.lang.String typeuser, java.lang.Integer circleid) throws java.rmi.RemoteException;
    public java.lang.String sendMail(java.lang.String emailSource, java.lang.Integer emailPort, java.lang.String emailFrom, java.lang.String emailPassword, java.lang.String toEmail, java.lang.String subject, java.lang.String body) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CircleMaster[] getCircleMaster() throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.SpokeMaster[] getSpokeMaster() throws java.rmi.RemoteException;
    public java.lang.String getReasonCode(java.lang.Integer circleId) throws java.rmi.RemoteException;
    public java.lang.String getSpokeCode(java.lang.Integer spokeId) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.OfficeMaster[] getCorporateOfficeMaster(java.lang.Integer officeTypeId) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CenterMaster[] getCenterMaster() throws java.rmi.RemoteException;
    public java.lang.Integer getUserCenter(java.lang.Integer userId) throws java.rmi.RemoteException;
    public java.lang.String getAssetBarCode(java.lang.Integer officeTypeID, java.lang.Integer officeID) throws java.rmi.RemoteException;
    public java.lang.String enterPriseDataService(java.lang.String service, java.lang.String operation, java.lang.String[] keys, java.lang.String[] values) throws java.rmi.RemoteException;
    public java.lang.String assetDeallocation(java.lang.String emp, java.lang.String barcode, java.lang.String deallocatedBy) throws java.rmi.RemoteException;
}
