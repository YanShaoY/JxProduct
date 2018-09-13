package com.vdin.JxProduct.db;

import org.litepal.crud.LitePalSupport;

/**
 * @开发者 YanSY
 * @日期 2018/9/13
 * @描述 Vdin成都研发部
 */
public class loginDataLinks extends LitePalSupport {

    private String rel;
    private String href;
    private String title;

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
