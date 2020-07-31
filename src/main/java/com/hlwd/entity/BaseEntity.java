package com.hlwd.entity;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity<key> implements Serializable {
    private static final long serialVersionUID = 3881100791165260190L;
    private key id;
    private int deleteFlag = 0;
    private String createdId = "";
    private String createdBy = "";
    private Date createdAt = new Date(0);
    private String modifiedId = "";
    private String modifiedBy = "";
    private Date modifiedAt = new Date(0);

    public BaseEntity() {
    }

    public key getId() {
        return this.id;
    }

    public void setId(key id) {
        this.id = id;
    }

    public int getDeleteFlag() {
        return this.deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public void setCreatedId(String createdId) {
        this.createdId = createdId;
    }

    public String getCreatedId() {
        return this.createdId;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setModifiedId(String modifiedId) {
        this.modifiedId = modifiedId;
    }

    public String getModifiedId() {
        return this.modifiedId;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Date getModifiedAt() {
        return this.modifiedAt;
    }
}