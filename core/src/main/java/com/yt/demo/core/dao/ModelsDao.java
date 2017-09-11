package com.yt.demo.core.dao;


import com.yt.demo.core.entity.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 型号
 * Created by zhangshixin on 17/2/8.
 */
public interface ModelsDao extends JpaRepository<ModelEntity, Long> {
    List<ModelEntity> findByBrandIdAndIsDelete(Long brandId, Boolean aFalse);
    List<ModelEntity> findByBrandIdInAndIsDelete(List<Long> brandIds, Boolean aFalse);

    List<ModelEntity> findByParentIdAndIsDelete(Long id, Boolean aFalse);

    @Query(value = "select * from models where find_in_set(?1, brand_path)", nativeQuery = true)
    List<ModelEntity> findByBrandPath(Long brandId);

    ModelEntity findByZhName(String zhName);
}
