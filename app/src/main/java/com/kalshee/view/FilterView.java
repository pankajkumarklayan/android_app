package com.kalshee.view;

/**
 * Created by eWeb_A1 on 7/2/2018.
 */

public interface FilterView {

    public void showProgressBar();

    public void hideProgressBar();

    public void showErrorr(String response);

    public void showSuccess(String response);
}
