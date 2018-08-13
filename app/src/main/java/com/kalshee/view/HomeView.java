package com.kalshee.view;

/**
 * Created by eWeb_A1 on 7/2/2018.
 */

public interface HomeView
{
public void showSuccess(String response);
public void showErrorr(String error);
public void showSpinner(String Response);
public void showLikeSuccess(String addID, String Response);
public void showCityResponse(String Response);
public void showProgressBar();
public void hideProgressBar();
public void showFilterResponse(String response);


}
