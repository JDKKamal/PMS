package com.jdkgroup.validator;

import android.widget.EditText;

import java.util.regex.Pattern;

public class Validator {

    public static String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static boolean isEmplty(EditText editText) {
        if (getText(editText).equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }

    public static boolean isEmail(String email){
          String EMAIL_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z]{2,8}" +
                ")+";

        if(Pattern.compile(EMAIL_PATTERN).matcher(email).matches()){
            return true;
        }

        return false;
    }
    public static boolean isBusinessName(String businessName)
    {
        String patern = "^[a-zA-Z0-9]+$";

        if(Pattern.compile(patern).matcher(businessName).matches()){
            return true;
        }

        return false;
    }
    public static boolean isValidePassword(String password){
        String patern = "[^\\w\\d]*(([0-9]+.*[A-Za-z]+.*)|[A-Za-z]+.*([0-9]+.*))";

        if(Pattern.compile(patern).matcher(password).matches() &&  password.length() > 4){
            return true;
        }

        return false;
    }


}
