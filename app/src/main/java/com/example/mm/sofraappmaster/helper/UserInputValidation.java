package com.example.mm.sofraappmaster.helper;

import android.util.Patterns;

public class UserInputValidation {

    public static boolean isValidMail(String emailInput) {
        if (emailInput.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            return false;
        }
        return true;
    }

    public static boolean isValidMobile (String number){
        if (number.isEmpty() || !Patterns.PHONE.matcher(number).matches() || number.length() < 10 || number.length() > 11){
            return false;
        }
        return true;
    }

    public static boolean isValidName (String name){
        if (name.isEmpty() || name.length() < 3 || name.length() > 20){
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(String passwordInput) {
        if (passwordInput.isEmpty() || passwordInput.length() < 3){
            return false;
        }
        return true;
    }

    public static boolean isValidRePassword (String rePassword, String password){
        if (rePassword.isEmpty() || !rePassword.contentEquals(password)){
            return false;
        }
        return true;
    }

}
