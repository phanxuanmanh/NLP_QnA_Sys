
/**
 * VerifyCertificateNoSuchAlgorithmExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package model;

public class VerifyCertificateNoSuchAlgorithmExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1420888341132L;
    
    private model.VerifyCertificateStub.VerifyCertificateNoSuchAlgorithmException faultMessage;

    
        public VerifyCertificateNoSuchAlgorithmExceptionException() {
            super("VerifyCertificateNoSuchAlgorithmExceptionException");
        }

        public VerifyCertificateNoSuchAlgorithmExceptionException(java.lang.String s) {
           super(s);
        }

        public VerifyCertificateNoSuchAlgorithmExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public VerifyCertificateNoSuchAlgorithmExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(model.VerifyCertificateStub.VerifyCertificateNoSuchAlgorithmException msg){
       faultMessage = msg;
    }
    
    public model.VerifyCertificateStub.VerifyCertificateNoSuchAlgorithmException getFaultMessage(){
       return faultMessage;
    }
}
    