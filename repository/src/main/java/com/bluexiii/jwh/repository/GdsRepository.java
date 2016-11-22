package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.Categ;
import com.bluexiii.jwh.domain.Gds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Set;

/**
 * Created by bluexiii on 16/8/25.
 */
public interface GdsRepository extends JpaRepository<Gds, Long> {

    //查询所有商品
    Page<Gds> findAllByStatusIsTrue(Pageable pageable);

    //查询所有未分类商品
    @Query("select g from Gds g where  not exists elements(g.categs)")
    public Page<Gds> findeGdsByCategNullPageable(Pageable pageable);

    //查询类别下商品
    Page<Gds> findByCategs(Categ categs, Pageable pageable);
    Page<Gds> findByCategsAndStatusIsTrue(Categ categs, Pageable pageable);

    //查询类别下商品
    Page<Gds> findByCategs(Set<Categ> categs, Pageable pageable);


    //按名称搜索商品（TV用）
    Page<Gds> findByGdsNameContaining(String gdsName, Pageable pageable);
    Page<Gds> findByGdsNameContainingAndStatusIsTrue(String gdsName, Pageable pageable);

    //搜索全部商品
    @Query("select g from Gds g where g.gdsName like %:searchValue% or g.gdsSecName like %:searchValue%")
    public Page<Gds> searchPageable(@Param("searchValue") String searchValue, Pageable pageable);

    //搜索未分类商品
    @Query("select g from Gds g where  not exists elements(g.categs) and (g.gdsName like %:searchValue% or g.gdsSecName like %:searchValue%)")
    public Page<Gds> searchByCategsNullPageable(@Param("searchValue") String searchValue, Pageable pageable);

    //搜索类别下的商品
    @Query("select g from Gds g  inner join g.categs c where c in (:categs) and (g.gdsName like %:searchValue% or g.gdsSecName like %:searchValue%)")
    public Page<Gds> searchByCategsPageable(@Param("categs") Collection<Categ> categs, @Param("searchValue") String searchValue, Pageable pageable);


    //统计类别下的商品数量
    public int countByCategs(Collection<Categ> categs);

    @Modifying
    @Query("update Gds g set g.status=?2 where g.id=?1")
    public int updateStatus(long id, boolean status);
}
