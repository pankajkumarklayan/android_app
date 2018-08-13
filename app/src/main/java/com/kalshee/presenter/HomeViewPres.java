package com.kalshee.presenter;

/**
 * Created by eWeb_A1 on 7/2/2018.
 */

public interface HomeViewPres
{

public void setInput(String user_id);
public void onLikeClick(String userId, String addId);
public void onFilterInput(String categoryId, String minimum, String maximum, String renttype, String userId);
}
