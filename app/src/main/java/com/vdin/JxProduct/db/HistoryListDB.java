package com.vdin.JxProduct.db;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class HistoryListDB extends LitePalSupport {

    @Column(unique = true)
    private String mId;

    private String name;

    private String type;

    private String pic;

    private String time;//揽件日期

    private String sex;

    private String description;

    private String color;

    private String chepai;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getChepai() {
        return chepai;
    }

    public void setChepai(String chepai) {
        this.chepai = chepai;
    }

    public static List<HistoryListDB> selectAll(){
        return LitePal.findAll(HistoryListDB.class);
    }

    public static HistoryListDB selectItem(String id){

        List<HistoryListDB> result = LitePal.where("mId = ?", id).find(HistoryListDB.class);
        if (result.size() <= 0)return null;
        return result.get(0);
    }

}
