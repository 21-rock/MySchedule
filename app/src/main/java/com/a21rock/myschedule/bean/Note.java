package com.a21rock.myschedule.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by 21rock on 2017/2/20.
 */

public class Note extends DataSupport {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String content;   // 记事内容

    private int createTime;  // 创建日期

    public Note() {

    }

    public Note(String content, int createTime) {
        this.content = content;
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }
}
