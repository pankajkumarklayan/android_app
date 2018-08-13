package com.kalshee.view;

/**
 * Created by eWeb_A1 on 7/5/2018.
 */

public interface DashboardView
{

    public void showSuccess(String response);
    public void showErrorr(String error);
    public void showProgressBar();
    public void hideProgressBar();
    public void showSoldSuccess(String response);
    public void deleteSuccess(String response);

}
