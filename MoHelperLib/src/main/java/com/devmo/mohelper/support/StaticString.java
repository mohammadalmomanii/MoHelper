package com.devmo.mohelper.support;


import com.devmo.mohelperlib.R;

public enum StaticString {
    MRE(159632),

    data(125, R.string.date),

   ;


    private int numVal, id, textResource;
    private String text;

    StaticString(int id, int textResource) {
        this.id = id;
        this.textResource = textResource;

    }

    StaticString() {
    }

    StaticString(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }

    public int getId() {
        return id;
    }

    public int getTextResource() {
        return textResource;
    }

}
