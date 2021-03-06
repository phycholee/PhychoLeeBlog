package com.phycholee.blog.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Article {

    public final static Integer STATUS_PUBLISHED = 1;
    public final static Integer STATUS_SAVE = 2;

    private Integer id;

    private String title;

    private String subTitle;

    private Integer authorId;

    private Date createTime;

    private Integer likes;

    private Integer views;

    private String markdownContent;

    private String htmlContent;

    private String jumbotron;

    private Integer status;

    private String imgSrc;

    private Integer[] tagIds;

    private String authorName;

    private List<Tag> tags = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getMarkdownContent() {
        return markdownContent;
    }

    public void setMarkdownContent(String markdownContent) {
        this.markdownContent = markdownContent == null ? null : markdownContent.trim();
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent == null ? null : htmlContent.trim();
    }

    public String getJumbotron() {
        return jumbotron;
    }

    public void setJumbotron(String jumbotron) {
        this.jumbotron = jumbotron;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Integer[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(Integer[] tagIds) {
        this.tagIds = tagIds;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", authorId=" + authorId +
                ", createTime='" + createTime + '\'' +
                ", likes=" + likes +
                ", views=" + views +
                ", markdownContent='" + markdownContent + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                ", jumbotron='" + jumbotron + '\'' +
                ", status=" + status +
                ", imgSrc='" + imgSrc + '\'' +
                ", tagIds=" + Arrays.toString(tagIds) +
                '}';
    }
}