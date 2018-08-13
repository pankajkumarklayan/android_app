package com.kalshee.view;

/**
 * Created by eWeb_A1 on 7/7/2018.
 */

public interface SigninView {

    public void showSuccess(String response);
    public void showErrorr(String error);
    public void showProgressBar();
    public void hideProgressBar();
    public void showFacebookResponse(String response);
}
