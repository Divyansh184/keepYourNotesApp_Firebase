package com.example.firbasenotes;

public class firebasemodel {
    private  String title;
    private  String content;
    public firebasemodel(){

    }

    public firebasemodel(String title,String content){
        this.title=title;
        this.content=content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
