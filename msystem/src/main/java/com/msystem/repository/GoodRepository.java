package com.msystem.repository;

import com.msystem.entity.Company;
import com.msystem.entity.Employee;
import com.msystem.entity.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description: GoodRepository接口
 * @author: Eker
 * @date: 2023/4/20 下午 11:52
 * @version: V1.0
 */
public interface GoodRepository  extends JpaRepository<Good, Integer> {
    Good findBylineId(String lineId);
    Page<Good> findByState(Integer state, Pageable pageable);

    @Query("SELECT g FROM Good g WHERE g.state = :state AND (g.lineName LIKE %:lineName% OR g.lineType LIKE %:lineType% OR g.lineId LIKE %:lineId% OR g.company.companyName LIKE %:companyName%)")
    Page<Good> findGoodsByStateAndLineNameOrLineTypeOrLineIdOrCompanyNameContaining(@Param("state") Integer state, @Param("lineName") String lineName, @Param("lineType") String lineType, @Param("lineId") String lineId, @Param("companyName") String companyName, Pageable pageable);
    Page<Good> findGoodsByLineNameContainingOrLineTypeContainingOrLineIdContainingOrCompany_CompanyNameContaining(String lineName, String lineType, String lineId, String companyName, Pageable pageable);
}