package com.kalshee.pojo;

/**
 * Created by eWeb_A1 on 6/18/2018.
 */

public class SpinnerModal
{
    private String created_date;

    private String city_id;

    private String eng_title;

    private String update_date;

    private String ar_title;

    public String getCreated_date ()
    {
        return created_date;
    }

    public void setCreated_date (String created_date)
    {
        this.created_date = created_date;
    }

    public String getCity_id ()
    {
        return city_id;
    }

    public void setCity_id (String city_id)
    {
        this.city_id = city_id;
    }

    public String getEng_title ()
    {
        return eng_title;
    }

    public void setEng_title (String eng_title)
    {
        this.eng_title = eng_title;
    }

    public String getUpdate_date ()
    {
        return update_date;
    }

    public void setUpdate_date (String update_date)
    {
        this.update_date = update_date;
    }

    public String getAr_title ()
    {
        return ar_title;
    }

    public void setAr_title (String ar_title)
    {
        this.ar_title = ar_title;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [created_date = "+created_date+", city_id = "+city_id+", eng_title = "+eng_title+", update_date = "+update_date+", ar_title = "+ar_title+"]";
    }
}
