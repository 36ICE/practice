package org.example.practice.myfreemarker;


import java.util.ArrayList;
import java.util.List;

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

    public void setValue(String value) {
        this.value = value;
    }

    public static List<Grad> getList(){
        ArrayList<Grad> grads = new ArrayList<>();
        grads.add(new Grad("999990006","JIJING","C_INSTANCE_","1"));
        grads.add(new Grad("999990006001","JIJING","C_INSTANCE_","2"));
        grads.add(new Grad("999990006002","JIJING","C_INSTANCE_","3"));
        return grads;
    }

}
