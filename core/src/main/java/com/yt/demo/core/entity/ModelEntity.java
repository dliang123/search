package com.yt.demo.core.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 型号
 * Created by zhangshixin on 17/2/6.
 */
@Entity
@Table(name = "models")
@EntityListeners(AuditingEntityListener.class)
public class ModelEntity extends AuditEntity<Long> {

    private static final long serialVersionUID = 1L;

    //主键、自动生成
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    //中文名称
    @Column(name = "zh_name", nullable = false)
    private String zhName;

    //英文名称
    @Column(name = "en_name")
    private String enName;

    // 型号下的商品数量
    @Column(name = "item_quantity")
    private Integer itemQuantity;

    //型号的UUID
    @Column(name = "merchant_number")
    private String merchantNumber;

    //型号描述、简介
    @Column(name = "description", length = 100)
    private String description;

    //上级型号 ID
    @Column(name = "parent_id")
    private Long parentId;

    //品牌 ID
    @Column(name = "brand_id", nullable = false)
    private Long brandId;

    //品牌路径
    @Column(name = "brand_path")
    private String brandPath;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    @Column(name = "image_id")
    private Long imageId;

    public ModelEntity() {
    }

    private ModelEntity(Builder builder) {
        setCreatedDate(builder.createdDate);
        setLastModifiedDate(builder.lastModifiedDate);
        setId(builder.id);
        setZhName(builder.zhName);
        setEnName(builder.enName);
        setDescription(builder.description);
        setParentId(builder.parentId);
        setBrandId(builder.brandId);
        setBrandPath(builder.brandPath);
        setMerchantNumber(builder.merchantNumber);
        setItemQuantity(builder.itemQuantity);
        isDelete = builder.isDelete;
        imageId = builder.imageId;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandPath() {
        return brandPath;
    }

    public void setBrandPath(String brandPath) {
        this.brandPath = brandPath;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getMerchantNumber() {
        if (null != this.merchantNumber) {
            this.merchantNumber = this.merchantNumber.toUpperCase();
        }
        return merchantNumber;
    }

    public void setMerchantNumber(String merchantNumber) {
        this.merchantNumber = merchantNumber;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public static final class Builder {
        private Date createdDate;
        private Date lastModifiedDate;
        private Long id;
        private String code;
        private String zhName;
        private String enName;
        private String description;
        private Long parentId;
        private Long brandId;
        private String brandPath;
        private Boolean isDelete;
        private String letterSpelling;
        private Integer itemQuantity;
        private String merchantNumber;
        private Long imageId;

        public Builder() {
        }

        public Builder createdDate(Date val) {
            createdDate = val;
            return this;
        }

        public Builder lastModifiedDate(Date val) {
            lastModifiedDate = val;
            return this;
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder code(String val) {
            code = val;
            return this;
        }

        public Builder zhName(String val) {
            zhName = val;
            return this;
        }

        public Builder enName(String val) {
            enName = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder parentId(Long val) {
            parentId = val;
            return this;
        }

        public Builder brandId(Long val) {
            brandId = val;
            return this;
        }

        public Builder brandPath(String val) {
            brandPath = val;
            return this;
        }

        public Builder letterSpelling(String val) {
            letterSpelling = val;
            return this;
        }

        public Builder itemQuantity(Integer val) {
            itemQuantity = val;
            return this;
        }

        public Builder merchantNumber(String val) {
            merchantNumber = val;
            return this;
        }

        public Builder isDelete(Boolean val) {
            isDelete = val;
            return this;
        }

        public Builder imageId(Long val) {
            imageId = val;
            return this;
        }

        public ModelEntity build() {
            return new ModelEntity(this);
        }
    }

}
