package org.sadtech.vkbot.core.distribution;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vk.api.sdk.objects.wall.WallComment;
import org.apache.log4j.Logger;
import org.sadtech.vkbot.core.service.distribution.CommentService;

public class CommentSubscriber implements EventSubscribe<JsonObject> {

    public static final Logger log = Logger.getLogger(CommentSubscriber.class);

    private CommentService commentService;

    public CommentSubscriber(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public void update(JsonObject object) {
        Gson gson = new Gson();
        WallComment wallComment = gson.fromJson(object, WallComment.class);
        log.info(wallComment);
    }

}
