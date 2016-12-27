/**
 * SoftAgeEnterpriseServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class SoftAgeEnterpriseServiceLocator extends org.apache.axis.client.Service implements org.tempuri.SoftAgeEnterpriseService {

    public SoftAgeEnterpriseServiceLocator() {
    }


    public SoftAgeEnterpriseServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SoftAgeEnterpriseServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_ISoftAgeEnterprise
    private java.lang.String BasicHttpBinding_ISoftAgeEnterprise_address = "http://172.25.38.139/EServiceDev/SoftAgeEnterprise.svc";

    public java.lang.String getBasicHttpBinding_ISoftAgeEnterpriseAddress() {
        return BasicHttpBinding_ISoftAgeEnterprise_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_ISoftAgeEnterpriseWSDDServiceName = "BasicHttpBinding_ISoftAgeEnterprise";

    public java.lang.String getBasicHttpBinding_ISoftAgeEnterpriseWSDDServiceName() {
        return BasicHttpBinding_ISoftAgeEnterpriseWSDDServiceName;
    }

    public void setBasicHttpBinding_ISoftAgeEnterpriseWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_ISoftAgeEnterpriseWSDDServiceName = name;
    }

    public org.tempuri.ISoftAgeEnterprise getBasicHttpBinding_ISoftAgeEnterprise() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_ISoftAgeEnterprise_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_ISoftAgeEnterprise(endpoint);
    }

    public org.tempuri.ISoftAgeEnterprise getBasicHttpBinding_ISoftAgeEnterprise(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.BasicHttpBinding_ISoftAgeEnterpriseStub _stub = new org.tempuri.BasicHttpBinding_ISoftAgeEnterpriseStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_ISoftAgeEnterpriseWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_ISoftAgeEnterpriseEndpointAddress(java.lang.String address) {
        BasicHttpBinding_ISoftAgeEnterprise_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.ISoftAgeEnterprise.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.BasicHttpBinding_ISoftAgeEnterpriseStub _stub = new org.tempuri.BasicHttpBinding_ISoftAgeEnterpriseStub(new java.net.URL(BasicHttpBinding_ISoftAgeEnterprise_address), this);
                _stub.setPortName(getBasicHttpBinding_ISoftAgeEnterpriseWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_ISoftAgeEnterprise".equals(inputPortName)) {
            return getBasicHttpBinding_ISoftAgeEnterprise();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "SoftAgeEnterpriseService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_ISoftAgeEnterprise"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_ISoftAgeEnterprise".equals(portName)) {
            setBasicHttpBinding_ISoftAgeEnterpriseEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
