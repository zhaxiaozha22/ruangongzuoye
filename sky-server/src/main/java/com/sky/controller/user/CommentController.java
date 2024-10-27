package com.sky.controller.user;


import com.sky.entity.CommentContent;
import com.sky.result.Result;
import com.sky.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping
    public Result<String> addComment(@RequestBody  CommentContent commentContent) {
       log.info("新增评论");
       commentService.addComment(commentContent);

        return Result.success();
    }

  }
}
