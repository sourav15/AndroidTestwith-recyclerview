package com.sourav.loktraandroidtest.helpers.beans.response;

import java.util.ArrayList;

/**
 * Created by sourav on 7/6/16.
 */
public class Commit {

    CommitAuthor author;
    CommitCommitter committer;
    Tree tree;
    String message;
    String url;
    String comment_count;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public CommitAuthor getAuthor() {
        return author;
    }

    public void setAuthor(CommitAuthor author) {
        this.author = author;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public CommitCommitter getCommitter() {
        return committer;
    }

    public void setCommitter(CommitCommitter committer) {
        this.committer = committer;
    }
}
