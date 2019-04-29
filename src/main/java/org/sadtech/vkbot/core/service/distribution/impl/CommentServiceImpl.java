package org.sadtech.vkbot.core.service.distribution.impl;

import org.sadtech.bot.core.domain.Comment;
import org.sadtech.bot.core.repository.CommentRepository;
import org.sadtech.vkbot.core.service.distribution.CommentService;

import java.util.List;

public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void add(Comment comment) {
        commentRepository.add(comment);
    }

    @Override
    public List<Comment> getEvent(Integer timeFrom, Integer timeTo) {
        return null;
    }

    @Override
    public List<Comment> getFirstEventByTime(Integer timeFrom, Integer timeTo) {
        return null;
    }

    @Override
    public List<Comment> getLastEventByTime(Integer timeFrom, Integer timeTo) {
        return null;
    }

}
