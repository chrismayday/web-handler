package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.Categ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by bluexiii on 16/8/25.
 */
public interface CategRepository extends JpaRepository<Categ, Long> {
    public List<Categ> findByMainOrderIsNotNullOrderByMainOrderAsc();

    //查询首页类别
    @Query("select c from Categ c where c.status = true and c.mainOrder > 0 order by c.mainOrder ASC")
    public List<Categ> findCategsInMainPage();

    //查询列表页类别
    @Query("select c from Categ c where c.status = true and c.listOrder > 0 order by c.listOrder ASC")
    public List<Categ> findCategsInListPage();

    public List<Categ> findByOrderByListOrderAsc();

    public Categ findOneByCategName(String categName);


    @Query("select c from Categ c where c.categName like %:searchValue% or c.categDesc like %:searchValue%")
    public Page<Categ> searchCategsPageable(Pageable pageable, @Param("searchValue") String searchValue);

}
