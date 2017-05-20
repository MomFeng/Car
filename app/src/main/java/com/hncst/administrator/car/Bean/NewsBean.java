package com.hncst.administrator.car.Bean;

/**
 * 存储对应新闻JSON的bean
 * Created by MomFeng on 2017/5/20 0020.
 *
 * 运用的知乎API，如有侵权，望告知，即刻更改。
 * API地址：http://news-at.zhihu.com/api/4/news/latest
 * API来源：http://blog.csdn.net/fanpeihua123/article/details/51210499
 */

public class NewsBean {

    //每个新闻的唯一标识符
    private String id;

    //新闻的标题
    private String title;

    //新闻的图片路径
    private String photourl;

    //点赞的数量
    private String star;

    //留言的数量
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
