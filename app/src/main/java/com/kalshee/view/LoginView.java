package com.kalshee.view;

/**
 * Created by eWeb_A1 on 7/6/2018.
 */

public interface LoginView  {
    public void showSuccess(String response);
    public void showErrorr(String error);
    public void showProgressBar();
    public void hideProgressBar();
    public void showValidation(String response);
    public void showFacebookResponse(String response);

}
