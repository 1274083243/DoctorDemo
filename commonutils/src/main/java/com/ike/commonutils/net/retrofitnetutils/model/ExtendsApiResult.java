package com.ike.commonutils.net.retrofitnetutils.model;

/**
 * Created by dell on 2017/5/20.
 */

public class ExtendsApiResult<T> extends ApiResult<T> {
    public int op;

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }
}
