/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.registration;

import java.io.Serializable;

/**
 *
 * @author bchao
 */
public class RegistrationCreateError implements Serializable{
    private String usernameLengthErr;
    private String passwordLengthErr;
    private String confirmNotMatchErr;
    private String fullnameLengthErr;
    private String usernameIsExistedErr;

    public RegistrationCreateError() {
    }

    /**
     * @return the usernameLengthErr
     */
    public String getUsernameLengthErr() {
        return usernameLengthErr;
    }

    /**
     * @param usernameLengthErr the usernameLengthErr to set
     */
    public void setUsernameLengthErr(String usernameLengthErr) {
        this.usernameLengthErr = usernameLengthErr;
    }

    /**
     * @return the passwordLengthErr
     */
    public String getPasswordLengthErr() {
        return passwordLengthErr;
    }

    /**
     * @param passwordLengthErr the passwordLengthErr to set
     */
    public void setPasswordLengthErr(String passwordLengthErr) {
        this.passwordLengthErr = passwordLengthErr;
    }

    /**
     * @return the confirmNotMatchErr
     */
    public String getConfirmNotMatchErr() {
        return confirmNotMatchErr;
    }

    /**
     * @param confirmNotMatchErr the confirmNotMatchErr to set
     */
    public void setConfirmNotMatchErr(String confirmNotMatchErr) {
        this.confirmNotMatchErr = confirmNotMatchErr;
    }

    /**
     * @return the fullnameLengthErr
     */
    public String getFullnameLengthErr() {
        return fullnameLengthErr;
    }

    /**
     * @param fullnameLengthErr the fullnameLengthErr to set
     */
    public void setFullnameLengthErr(String fullnameLengthErr) {
        this.fullnameLengthErr = fullnameLengthErr;
    }

    /**
     * @return the usernameIsExistedErr
     */
    public String getUsernameIsExistedErr() {
        return usernameIsExistedErr;
    }

    /**
     * @param usernameIsExistedErr the usernameIsExistedErr to set
     */
    public void setUsernameIsExistedErr(String usernameIsExistedErr) {
        this.usernameIsExistedErr = usernameIsExistedErr;
    }
    
    
}
