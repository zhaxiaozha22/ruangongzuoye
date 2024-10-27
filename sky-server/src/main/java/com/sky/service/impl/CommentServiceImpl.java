package com.sky.service.impl;

import com.sky.entity.CommentContent;
import com.sky.mapper.CommentMapper;
import com.sky.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public void addComment(CommentContent commentContent) {
        commentMapper.addComment(commentContent);
    }
}
