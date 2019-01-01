package com.example.reddit.permissions.checker;

import com.example.reddit.model.Account;
import com.example.reddit.model.Comment;

import java.util.Optional;

public class CommentPermissionChecker extends PermissionChecker {

    private Comment comment;
    private PostPermissionChecker postPermissionChecker;

    public CommentPermissionChecker(Account authenticatedAccount, Comment comment) {
        super(authenticatedAccount);
        this.comment = comment;
        postPermissionChecker = new PostPermissionChecker(authenticatedAccount, comment.getPost());
    }

    @Override
    public boolean canUpdate() {
        return comment.getAccount().equals(authenticatedAccount) || postPermissionChecker.canUpdate();
    }

    /**
     * Can be deleted by post owner and if it's a reply - also by owner of the root comment.
     */
    @Override
    public boolean canDelete() {
        return canUpdate() ||
                comment.getPost().getAccount().equals(authenticatedAccount) ||
                Optional.ofNullable(comment.getParent())
                        .filter(c -> c.getAccount().equals(authenticatedAccount)).isPresent();
    }

    @Override
    public boolean canManage() {
        return false;
    }
}
