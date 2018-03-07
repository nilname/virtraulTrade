package com.ooteco.model;

public class NiurenNews {
    private int id;
    private String name;
    private String createtime;

    private String content;
    private String source;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSource() {
        return source;
    }

//    public void setSource(String source) {
//        this.source = source;
//    }

    @Override
    public String toString() {
        return super.toString();
    }
}
