package com.sky.entity;


public class CommentContent {
    private Integer userId;
    private Integer orderId;
    private String content;

    public CommentContent() {
    }

    public CommentContent(Integer userId, Integer commentId, String content) {
        this.userId = userId;
        this.orderId = commentId;
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentContent{" +
                "userId=" + userId +
                ", commentId=" + orderId +
                ", content='" + content + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
