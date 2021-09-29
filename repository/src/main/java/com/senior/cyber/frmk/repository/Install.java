package com.senior.cyber.frmk.repository;

import java.io.Serializable;

public class Install implements Serializable {

    private String installId;

    private String table;

    private String pk;

    private byte level;

    private boolean history;

    private byte draftNo;

    private byte draftYes;

    private byte reviewNo;

    private byte reviewYes;

    public String getInstallId() {
        return installId;
    }

    public void setInstallId(String installId) {
        this.installId = installId;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public byte getDraftNo() {
        return draftNo;
    }

    public void setDraftNo(byte draftNo) {
        this.draftNo = draftNo;
    }

    public byte getDraftYes() {
        return draftYes;
    }

    public void setDraftYes(byte draftYes) {
        this.draftYes = draftYes;
    }

    public byte getReviewNo() {
        return reviewNo;
    }

    public void setReviewNo(byte reviewNo) {
        this.reviewNo = reviewNo;
    }

    public byte getReviewYes() {
        return reviewYes;
    }

    public void setReviewYes(byte reviewYes) {
        this.reviewYes = reviewYes;
    }
}