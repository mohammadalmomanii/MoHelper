package com.devmo.mohelper.api.model;

import java.io.Serializable;
import java.util.List;

public class Model implements Serializable {
    Msg msg;
    Data data;
    List<Data> dataList;


    public Msg getMsg() {
        return msg;
    }

    public Data getData() {
        return data;
    }

    public List<Data> getDataList() {
        return dataList;
    }
}