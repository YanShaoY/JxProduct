package com.vdin.JxProduct.Gson;

import com.google.gson.JsonObject;
import com.vdin.JxProduct.Util.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class BaseResponse {


    /**
     * code : string
     * collection : [{}]
     * errors : {}
     * message : string
     * meta : {}
     * size : 0
     * success : false
     */

    private String message;
    private boolean success;
    private List<JsonObject> collection;


    public boolean isNull() {
        if (!ListUtils.isEmpty(collection)) {
            return false;
        }
        return true;
    }

    public JsonObject getBean() {
        if (!ListUtils.isEmpty(collection)) {
            return collection.get(0);
        }
        return null;
    }

    public List<JsonObject> getList() {
        if (!ListUtils.isEmpty(collection)) {
            return collection;
        }
        return new ArrayList<>();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<JsonObject> getCollection() {
        return collection;
    }

    public void setCollection(List<JsonObject> collection) {
        this.collection = collection;
    }
}
