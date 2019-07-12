package org.sadtech.vkbot.core.convert;

import com.vk.api.sdk.objects.board.TopicComment;
import org.sadtech.social.core.domain.content.BoardComment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class TopicCommentToBoardComment implements Convert<TopicComment, BoardComment> {
    @Override
    public BoardComment converting(TopicComment target) {
        BoardComment boardComment = new BoardComment();
        boardComment.setContentId(target.getTopicId());
        boardComment.setCreateDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(target.getDate()), TimeZone.getDefault().toZoneId()));
        boardComment.setText(target.getText());
        boardComment.setPersonId(target.getFromId());


        System.out.println(target);
        return boardComment;
    }
}
