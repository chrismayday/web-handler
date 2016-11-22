package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.Gds;
import com.bluexiii.jwh.domain.GdsPic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by bluexiii on 16/8/25.
 */
public interface GdsPicRepository extends JpaRepository<GdsPic, Long> {
    public List<GdsPic> findByGds(Gds gds);
}
