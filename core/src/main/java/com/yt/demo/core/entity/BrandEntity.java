package com.yt.demo.core.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 品牌实体类
 */
@Entity
@Table(name = "brands")
@EntityListeners(AuditingEntityListener.class)
public class BrandEntity extends AuditEntity<Long>{

    private static final long serialVersionUID = 1L;

    //主键、自动生成
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",insertable = false, updatable = false, nullable = false)
    private Long id;

    //品牌缩写
    @Column(name = "abbreviation")
    private String abbreviation;

    //中文名称
    @Column(name = "zh_name")
    private String zhName;

    //展示顺序
    @Column(name = "display_order")
    private Integer displayOrder;

    //英文名称
    @Column(name = "en_name", nullable = false)
    private String enName;

    //品牌国家
    @Column(name = "country", nullable = false)
    private String country;

    //国家代码
    @Column(name = "country_code", nullable = false)
    private String countryCode;

    //品牌描述、简介
    @Column(name = "description",length = 100)
    private String description;

    //首字母
    @Column(name = "first_letter")
    private String firstLetter;

    // 品牌下的商品数量
    @Column(name = "item_quantity")
    private Integer itemQuantity;

    //品牌的 GUID，老数据
    @Column(name = "merchant_number")
    private String merchantNumber;

    //父品牌 ID
    @Column(name = "parent_id")
    private Long parentId = 0l;

    //图标ID
    @Column(name = "logo_id")
    private Long logoId;

    //官网
    @Column(name = "url", length = 200)
    private String url;

    //是否删除（1为删除、0为正常）
    @Column(name = "is_delete", nullable = false)
    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLogoId() {
        return logoId;
    }

    public void setLogoId(Long logoId) {
        this.logoId = logoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

    public String getMerchantNumber() {
        if(null!=this.merchantNumber){
            this.merchantNumber = this.merchantNumber.toUpperCase();
        }
        return merchantNumber;
    }

    public void setMerchantNumber(String merchantNumber) {
        this.merchantNumber = merchantNumber;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
