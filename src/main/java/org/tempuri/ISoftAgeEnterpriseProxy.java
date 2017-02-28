package org.tempuri;

public class ISoftAgeEnterpriseProxy implements org.tempuri.ISoftAgeEnterprise {
  private String _endpoint = null;
  private org.tempuri.ISoftAgeEnterprise iSoftAgeEnterprise = null;
  
  public ISoftAgeEnterpriseProxy() {
    _initISoftAgeEnterpriseProxy();
  }
  
  public ISoftAgeEnterpriseProxy(String endpoint) {
    _endpoint = endpoint;
    _initISoftAgeEnterpriseProxy();
  }
  
  private void _initISoftAgeEnterpriseProxy() {
    try {
      iSoftAgeEnterprise = (new org.tempuri.SoftAgeEnterpriseServiceLocator()).getBasicHttpBinding_ISoftAgeEnterprise();
      if (iSoftAgeEnterprise != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iSoftAgeEnterprise)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iSoftAgeEnterprise)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iSoftAgeEnterprise != null)
      ((javax.xml.rpc.Stub)iSoftAgeEnterprise)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.ISoftAgeEnterprise getISoftAgeEnterprise() {
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise;
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.UserDetails getUserDetails(java.lang.String emp_code, java.lang.String password, java.lang.String companyId) throws java.rmi.RemoteException, org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.ExceptionInfo{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getUserDetails(emp_code, password, companyId);
  }
  
  public java.lang.Boolean getUserDetailByEVM(java.lang.String emp_code) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getUserDetailByEVM(emp_code);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.AssetDetails[] getAssetDetailUserWise(java.lang.String emp_code) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getAssetDetailUserWise(emp_code);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.UserDetails getUserDetail(java.lang.String emp_code) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getUserDetail(emp_code);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.OfficeTypeMaster[] getOfficeType_Master_new() throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getOfficeType_Master_new();
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CompanyMaster[] getCompanyDetails() throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getCompanyDetails();
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CountryMaster[] getCountryDetails() throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getCountryDetails();
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CircleMaster[] getCircleDetails(java.lang.Integer countrtyId, java.lang.String typeuser, java.lang.Integer spokeid) throws java.rmi.RemoteException, org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.ExceptionInfo{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getCircleDetails(countrtyId, typeuser, spokeid);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CircleMaster[] getCircleDetails_byid(java.lang.Integer countrtyId, java.lang.String typeuser, java.lang.Integer spokeid) throws java.rmi.RemoteException, org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.ExceptionInfo{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getCircleDetails_byid(countrtyId, typeuser, spokeid);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.SpokeMaster[] getSpokeDetails(java.lang.Integer circleId, java.lang.String typeuse, java.lang.Integer spokeid) throws java.rmi.RemoteException, org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.ExceptionInfo{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getSpokeDetails(circleId, typeuse, spokeid);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.RoleMaster[] getRoleDetails() throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getRoleDetails();
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.Office_Details[] getofficedetails() throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getofficedetails();
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.DepartmentMaster[] getDepartmentDeails() throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getDepartmentDeails();
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.StateMaster[] getStateDetails(java.lang.Integer countryId) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getStateDetails(countryId);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.OfficeTypeMaster[] getOfficeTypeMaster(java.lang.String typeuser) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getOfficeTypeMaster(typeuser);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.OfficeTypeMaster[] getOfficeTypeMaster_byID(java.lang.Integer ID) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getOfficeTypeMaster_byID(ID);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.SpokeMaster[] getSpokeDetails_BYID(java.lang.Integer circleId, java.lang.Integer spokeid) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getSpokeDetails_BYID(circleId, spokeid);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CircleMaster[] getCircleDetails_BYID_ID(java.lang.Integer countryId, java.lang.Integer circleid) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getCircleDetails_BYID_ID(countryId, circleid);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.SpokeMaster[] getSpokeDetails_ByCircleID(java.lang.Integer circleId) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getSpokeDetails_ByCircleID(circleId);
  }
  
  public void insertExceptionDetails(java.lang.Integer applicationId, java.lang.String errorMessage, java.lang.String errorUrl, java.lang.String methodName) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    iSoftAgeEnterprise.insertExceptionDetails(applicationId, errorMessage, errorUrl, methodName);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CircleMaster[] getCircleDetails_byCircleid(java.lang.Integer countryId, java.lang.String typeuser, java.lang.Integer circleid) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getCircleDetails_byCircleid(countryId, typeuser, circleid);
  }
  
  public java.lang.String sendMail(java.lang.String emailSource, java.lang.Integer emailPort, java.lang.String emailFrom, java.lang.String emailPassword, java.lang.String toEmail, java.lang.String subject, java.lang.String body) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.sendMail(emailSource, emailPort, emailFrom, emailPassword, toEmail, subject, body);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CircleMaster[] getCircleMaster() throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getCircleMaster();
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.SpokeMaster[] getSpokeMaster() throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getSpokeMaster();
  }
  
  public java.lang.String getReasonCode(java.lang.Integer circleId) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getReasonCode(circleId);
  }
  
  public java.lang.String getSpokeCode(java.lang.Integer spokeId) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getSpokeCode(spokeId);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.OfficeMaster[] getCorporateOfficeMaster(java.lang.Integer officeTypeId) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getCorporateOfficeMaster(officeTypeId);
  }
  
  public org.datacontract.schemas._2004._07.SoftAge_Enterprise_BusinessObject.CenterMaster[] getCenterMaster() throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getCenterMaster();
  }
  
  public java.lang.Integer getUserCenter(java.lang.Integer userId) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getUserCenter(userId);
  }
  
  public java.lang.String getAssetBarCode(java.lang.Integer officeTypeID, java.lang.Integer officeID) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.getAssetBarCode(officeTypeID, officeID);
  }
  
  public java.lang.String enterPriseDataService(java.lang.String service, java.lang.String operation, java.lang.String[] keys, java.lang.String[] values) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.enterPriseDataService(service, operation, keys, values);
  }
  
  public java.lang.String assetDeallocation(java.lang.String emp, java.lang.String barcode, java.lang.String deallocatedBy) throws java.rmi.RemoteException{
    if (iSoftAgeEnterprise == null)
      _initISoftAgeEnterpriseProxy();
    return iSoftAgeEnterprise.assetDeallocation(emp, barcode, deallocatedBy);
  }
  
  
}