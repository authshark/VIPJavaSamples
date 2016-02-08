
/**
 * AuthenticationServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.symantec.vipuserservices.wsclient;

    /**
     *  AuthenticationServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class AuthenticationServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public AuthenticationServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public AuthenticationServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for checkOtp method
            * override this method for handling normal response from checkOtp operation
            */
           public void receiveResultcheckOtp(
                    com.symantec.vipuserservices.wsclient.AuthenticationServiceStub.CheckOtpResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkOtp operation
           */
            public void receiveErrorcheckOtp(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for authenticateCredentials method
            * override this method for handling normal response from authenticateCredentials operation
            */
           public void receiveResultauthenticateCredentials(
                    com.symantec.vipuserservices.wsclient.AuthenticationServiceStub.AuthenticateCredentialsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from authenticateCredentials operation
           */
            public void receiveErrorauthenticateCredentials(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for confirmRisk method
            * override this method for handling normal response from confirmRisk operation
            */
           public void receiveResultconfirmRisk(
                    com.symantec.vipuserservices.wsclient.AuthenticationServiceStub.ConfirmRiskResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from confirmRisk operation
           */
            public void receiveErrorconfirmRisk(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for authenticateUser method
            * override this method for handling normal response from authenticateUser operation
            */
           public void receiveResultauthenticateUser(
                    com.symantec.vipuserservices.wsclient.AuthenticationServiceStub.AuthenticateUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from authenticateUser operation
           */
            public void receiveErrorauthenticateUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for denyRisk method
            * override this method for handling normal response from denyRisk operation
            */
           public void receiveResultdenyRisk(
                    com.symantec.vipuserservices.wsclient.AuthenticationServiceStub.DenyRiskResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from denyRisk operation
           */
            public void receiveErrordenyRisk(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for evaluateRisk method
            * override this method for handling normal response from evaluateRisk operation
            */
           public void receiveResultevaluateRisk(
                    com.symantec.vipuserservices.wsclient.AuthenticationServiceStub.EvaluateRiskResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from evaluateRisk operation
           */
            public void receiveErrorevaluateRisk(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for authenticateUserWithPush method
            * override this method for handling normal response from authenticateUserWithPush operation
            */
           public void receiveResultauthenticateUserWithPush(
                    com.symantec.vipuserservices.wsclient.AuthenticationServiceStub.AuthenticateUserWithPushResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from authenticateUserWithPush operation
           */
            public void receiveErrorauthenticateUserWithPush(java.lang.Exception e) {
            }
                


    }
    