package com.yt.demo.core.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by jackdeng on 2017/9/5.
 */
@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass
public class AuditEntity<USER extends Serializable> implements Serializable {
    @CreatedBy
    @Column(
            name = "create_by",
            updatable = false
    )
    protected USER createBy;
    @CreatedDate
    @Column(
            name = "created_date",
            updatable = false
    )
    protected Date createdDate;
    @LastModifiedBy
    @Column(
            name = "last_modified_by"
    )
    protected USER lastModifiedBy;
    @LastModifiedDate
    @Column(
            name = "last_modified_date"
    )
    protected Date lastModifiedDate;
    @Version
    protected Long version;

    public AuditEntity() {
    }

    public USER getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(USER createBy) {
        this.createBy = createBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public USER getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(USER lastModifiedBy) {
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
