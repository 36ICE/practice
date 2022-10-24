package org.example.openfeignservice;

public class CommonResult<T> {

    private T t;

    public String msg;
    public int code;
    public CommonResult(String msg,int code){
        this.msg=msg;
        this.code=code;
    }
    public CommonResult(T t,String msg,int code){
        this.t=t;
        this.msg=msg;
        this.code=code;
    }

}
