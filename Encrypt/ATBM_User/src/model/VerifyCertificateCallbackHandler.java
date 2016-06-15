
/**
 * VerifyCertificateCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package model;

    /**
     *  VerifyCertificateCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class VerifyCertificateCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public VerifyCertificateCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public VerifyCertificateCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for verifyIncorp method
            * override this method for handling normal response from verifyIncorp operation
            */
           public void receiveResultverifyIncorp(
                    model.VerifyCertificateStub.VerifyIncorpResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from verifyIncorp operation
           */
            public void receiveErrorverifyIncorp(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for verifyPersom method
            * override this method for handling normal response from verifyPersom operation
            */
           public void receiveResultverifyPersom(
                    model.VerifyCertificateStub.VerifyPersomResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from verifyPersom operation
           */
            public void receiveErrorverifyPersom(java.lang.Exception e) {
            }
                


    }
    