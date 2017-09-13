package com.yt.demo.core.dao;


import com.yt.demo.core.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 品牌
 */
@Repository
public interface BrandDao extends JpaRepository<BrandEntity, Long> {
    List<BrandEntity> findByParentIdAndIsDeleted(Long brandId, Boolean isDelete);

    List<BrandEntity> findByIsDeletedAndIdIn(Boolean isDeleted, Collection brandIds);

    List<BrandEntity> findByIsDeletedAndZhName(Boolean isDeleted, String zhName);

    List<BrandEntity> findByParentIdIn(List<Long> brandIds);
}
