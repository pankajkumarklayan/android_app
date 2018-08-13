package com.kalshee.pojo;

/**
 * Created by eWeb_A1 on 6/20/2018.
 */

public class CategoryModal
{
    private String created_date;

    private String cat_slug;

    private String eng_title;

    private String update_date;

    private String ar_title;

    private String category_id;

    public String getCreated_date ()
    {
        return created_date;
    }

    public void setCreated_date (String created_date)
    {
        this.created_date = created_date;
    }

    public String getCat_slug ()
    {
        return cat_slug;
    }

    public void setCat_slug (String cat_slug)
    {
        this.cat_slug = cat_slug;
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

    public String getCategory_id ()
    {
        return category_id;
    }

    public void setCategory_id (String category_id)
    {
        this.category_id = category_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [created_date = "+created_date+", cat_slug = "+cat_slug+", eng_title = "+eng_title+", update_date = "+update_date+", ar_title = "+ar_title+", category_id = "+category_id+"]";
    }
}