package com.vdin.JxProduct.Service;

import com.vdin.JxProduct.Gson.MetaDataResponse;
import com.vdin.JxProduct.Util.MetaDataUtility;

import java.util.List;

/**
 * @开发者 YanSY
 * @日期 2018/9/13
 * @描述 Vdin成都研发部
 */
public class MetaDataAPI {

    private List<MetaDataResponse.CollectionBean.LinksBean> metaDataLinks;

    /**
     * 构造单例
     *
     * @return 返回本类实例
     */
    public static MetaDataAPI getInstance() {
        return MetaDataAPIHolder.instance;
    }

    private static class MetaDataAPIHolder {
        private static final MetaDataAPI instance = new MetaDataAPI();
    }
    private MetaDataAPI() {};


    /**
     * 获取登录的URL
     * @return 返回登录的URL
     */
    public String getLoginURL(){
        return getMetaDataURLForRel("dosser/create/session");
    }


    /**
     * 获取元数据的links
     * @return
     */
    public List<MetaDataResponse.CollectionBean.LinksBean> getMetaDataLinks() {

        if (metaDataLinks == null || metaDataLinks.size() == 0){
            MetaDataResponse metaDataFile = MetaDataUtility.getMetaDataFile();
            metaDataLinks = metaDataFile.getCollection().get(0).getLinks();
        }

        if (metaDataLinks == null || metaDataLinks.size() == 0){
            MetaDataUtility.requestMetaDataSource();
        }

        return metaDataLinks;
    }


    /**
     * 传入对应的rel 返回对应的url
     * @param rel
     * @return
     */
    public String getMetaDataURLForRel(String rel){

        if (metaDataLinks == null || metaDataLinks.size() == 0){
            MetaDataResponse metaDataFile = MetaDataUtility.getMetaDataFile();
            metaDataLinks = metaDataFile.getCollection().get(0).getLinks();
        }

        if (metaDataLinks == null || metaDataLinks.size() == 0){
            MetaDataUtility.requestMetaDataSource();
            return null;
        }

        for (MetaDataResponse.CollectionBean.LinksBean linksBean: metaDataLinks) {
            if (linksBean.getRel().equals(rel)){
                return linksBean.getHref();
            }
        }
        return null;
    }

}
