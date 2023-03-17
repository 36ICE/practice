package org.example.practice.myfreemarker;


import java.util.ArrayList;

public class Grad {
    public Grad(String id, String code,String sxw, String value) {
        this.id = id;
        this.code=code;
        this.sxw = sxw;
        this.value = value;
    }


    private String id;

    //元素code
    private String code;
    private String sxw;

    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSxw() {
        return sxw;
    }

    public void setSxw(String sxw) {
        this.sxw = sxw;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    ArrayList<Grad> grads = new ArrayList<>();


}
