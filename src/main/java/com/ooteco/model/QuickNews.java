package com.ooteco.model;

/**
 * Created by fangqing on 2/10/18.
 */
public class QuickNews {
    private int id;
    private String createtime;
    private String content;
    private String sourceurl;
    private String source;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return this.id + " " + this.createtime + " " + content + " " + sourceurl + " " + source;
    }
}
