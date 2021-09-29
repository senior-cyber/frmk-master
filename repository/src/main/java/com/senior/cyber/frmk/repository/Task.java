package com.senior.cyber.frmk.repository;

import java.io.Serializable;

public class Task implements Serializable {

    private String taskId;

    private String table;

    private byte draftYes;

    private byte draftNo;

    private byte reviewYes;

    private byte reviewNo;

    private String action;

    private String op;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public byte getDraftYes() {
        return draftYes;
    }

    public void setDraftYes(byte draftYes) {
        this.draftYes = draftYes;
    }

    public byte getDraftNo() {
        return draftNo;
    }

    public void setDraftNo(byte draftNo) {
        this.draftNo = draftNo;
    }

    public byte getReviewYes() {
        return reviewYes;
    }

    public void setReviewYes(byte reviewYes) {
        this.reviewYes = reviewYes;
    }

    public byte getReviewNo() {
        return reviewNo;
    }

    public void setReviewNo(byte reviewNo) {
        this.reviewNo = reviewNo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}