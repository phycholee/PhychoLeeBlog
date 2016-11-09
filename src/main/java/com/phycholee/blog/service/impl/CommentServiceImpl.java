package com.phycholee.blog.service.impl;

import com.phycholee.blog.base.service.impl.BaseServiceImpl;
import com.phycholee.blog.dao.CommentDao;
import com.phycholee.blog.model.Comment;
import com.phycholee.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by PhychoLee on 2016/11/9 21:07.
 * Description: 评论Service实现类
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService {

    private CommentDao commentDao;
    @Autowired
    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
        super.setDao(commentDao);
    }
}
