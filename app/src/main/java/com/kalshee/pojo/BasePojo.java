package com.kalshee.pojo;

/**
 * Created by eWeb_A1 on 6/18/2018.
 */

public class BasePojo <T>
{
    private String message;

    private String status;

    private T data;

    private String code;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public T getData ()
    {
        return data;
    }

    public void setData (T data)
    {
        this.data = data;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", status = "+status+", data = "+data+", code = "+code+"]";
    }
}