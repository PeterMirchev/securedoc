package com.builder.securedoc.utils;

public class EmailUtils {

    public static  String getEMailMessage(String name, String host, String token) {

        return "Hello " + name + ",\n\nYour new account has been created. Please click on the bellow link to verify your account.\n\n" +
                getVerificationUrl(host, token) +  "\n\nThe SupportTeam";
    }
    public static  String getResetPasswordMessage(String name, String host, String token) {


        return "Hello " + name + ",\n\nYour new account has been created. Please click on the bellow link to verify your account.\n\n" +
                getResetPasswordUrl(host, token) +  "\n\nThe SupportTeam";
    }

    public static String getVerificationUrl(String host, String token) {

        return host + "/verify/account?token=" + token;
    }

    public static String getResetPasswordUrl(String host, String token) {

        return host + "/verify/password?token=" + token;
    }

}
