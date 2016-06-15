
/**
 * VerifyCertificateUnsupportedEncodingExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package model;

public class VerifyCertificateUnsupportedEncodingExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1420888341151L;
    
    private model.VerifyCertificateStub.VerifyCertificateUnsupportedEncodingException faultMessage;

    
        public VerifyCertificateUnsupportedEncodingExceptionException() {
            super("VerifyCertificateUnsupportedEncodingExceptionException");
        }

        public VerifyCertificateUnsupportedEncodingExceptionException(java.lang.String s) {
           super(s);
        }

        public VerifyCertificateUnsupportedEncodingExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public VerifyCertificateUnsupportedEncodingExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(model.VerifyCertificateStub.VerifyCertificateUnsupportedEncodingException msg){
       faultMessage = msg;
    }
    
    public model.VerifyCertificateStub.VerifyCertificateUnsupportedEncodingException getFaultMessage(){
       return faultMessage;
    }
}
    