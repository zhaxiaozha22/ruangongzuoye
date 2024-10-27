package com.sky.mapper;


import com.sky.entity.CommentContent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {


    @Insert("insert into `new-sky-take-out`.comment ( user_id, order_id, comment_detail) value " +
            "(#{userId},#{orderId},#{content})")
    public void addComment(CommentContent commentContent);

}
