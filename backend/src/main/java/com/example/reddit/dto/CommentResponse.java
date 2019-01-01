package com.example.reddit.dto;

import com.example.reddit.model.Account;
import com.example.reddit.model.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommentResponse {

    public Long id;
    public String content;
    public List<CommentResponse> replies;
    public Instant createdAt;
    public CommentCreator author;
    public String groupName;
    public Integer isUpvoted;
    public Integer upvotedCount;

    public boolean isCreator;

    public CommentResponse(Comment comment) {
        id = comment.getId();
        content = comment.getContent();
        author = new CommentCreator(comment.getAccount());
        createdAt = comment.getCreatedAt();
        replies = comment.getReplies().stream().map(CommentResponse::new).collect(Collectors.toList());
        groupName = comment.getPost().getGroup().getName();
    }

    public CommentResponse(ICommentResponseDto dto) {
        id = dto.getId();
        content = dto.getContent();
        author = new CommentCreator(dto.getUsername(), dto.getAvatar());
        createdAt = dto.getCreatedAt();
        replies = new ArrayList<>();
        groupName = dto.getGroupName();
        isUpvoted = dto.getUpvoted();
        upvotedCount = dto.getUpvotedCount();
    }

    public void addReply(CommentResponse commentResponse) {
        replies.add(commentResponse);
    }

    @Getter
    @Setter
    public class CommentCreator {
        public String username;
        public String avatar;

        public CommentCreator(Account account) {
            username = account.getUsername();
            avatar = account.getAvatar();
        }

        public CommentCreator(String username, String avatar) {
            this.username = username;
            this.avatar = avatar;
        }
    }
}
