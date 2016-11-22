package com.bluexiii.jwh.service;

import com.bluexiii.jwh.repository.CategRepository;
import com.bluexiii.jwh.repository.GdsRepository;
import com.bluexiii.jwh.domain.Categ;
import com.bluexiii.jwh.domain.Gds;
import com.bluexiii.jwh.exception.DataFormatException;
import com.bluexiii.jwh.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

/**
 * 商品管理
 * Created by bluexiii on 16/9/13.
 */
@Service
@Transactional
public class GdsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GdsService.class);
    private final GdsRepository gdsRepository;
    private final CategRepository categRepository;

    public GdsService(GdsRepository gdsRepository, CategRepository categRepository) {
        this.gdsRepository = gdsRepository;
        this.categRepository = categRepository;
    }

    /**
     * 新增商品
     *
     * @param gds
     * @return
     */
    public Gds save(Gds gds) {
        Assert.notNull(gds);
        LOGGER.debug("Saving Gds {}", gds);
        LOGGER.debug("getCategs {}", gds.getCategs());

        return gdsRepository.save(gds);
    }


    /**
     * 修改商品
     *
     * @param gds
     * @return
     */
    public Gds update(Long id, Gds gds) {
        Assert.notNull(id);
        Assert.notNull(gds);
        LOGGER.debug("Updating Gds {}", gds);
        if (!gds.getId().equals(id)) {
            throw new DataFormatException("ID不匹配");
        }
        Gds existing = gdsRepository.findOne(id);
        if (existing == null) {
            throw new ResourceNotFoundException("商品不存在");
        }
        //gds.getGdsDetail().setId(existing.getGdsDetail().getId());
        return gdsRepository.save(gds);
    }

    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    public void delete(Long id) {
        Assert.notNull(id);
        Gds existing = gdsRepository.findOne(id);
        if (existing == null) {
            throw new ResourceNotFoundException(String.format("商品不存在 id=%s", id));
        }
        gdsRepository.delete(existing);
    }

    /**
     * 查询商品
     *
     * @param id
     * @return
     */
    public Gds findOne(Long id) {
        Assert.notNull(id);
        Gds gds = gdsRepository.findOne(id);
        if (gds == null) {
            throw new ResourceNotFoundException(String.format("商品不存在 id=%s", id));
        }
        return gds;
    }

    /**
     * 查询全部商品
     *
     * @return
     */
    public List<Gds> findAll() {
        List<Gds> all = gdsRepository.findAll();
        if (all.size() == 0) {
            throw new ResourceNotFoundException(String.format("没有找到商品"));
        }
        return all;
    }


    /**
     * 分页查询全部商品
     *
     * @param pageable
     * @return
     */
    public Page<Gds> findAllPageable(Pageable pageable) {
        Page<Gds> all = gdsRepository.findAll(pageable);
        if (all.getTotalElements() == 0) {
            throw new ResourceNotFoundException(String.format("没有找到商品"));
        }
        return all;
    }

    /**
     * 分页模糊查询商品
     *
     * @return
     */
    public Page<Gds> searchGdsesPageable(Pageable pageable, String searchValue) {
        Page<Gds> gdses = gdsRepository.searchPageable(searchValue, pageable);
        if (gdses.getTotalElements() == 0) {
            throw new ResourceNotFoundException(String.format("没有找到商品"));
        }
        return gdses;
    }

    /**
     * 根据商品名模糊查询商品
     *
     * @param gdsName
     * @param pageable
     * @return
     */
    public Page<Gds> findByGdsNamePageable(String gdsName, Pageable pageable) {
        Page<Gds> all = gdsRepository.findByGdsNameContaining(gdsName, pageable);
        if (all.getTotalElements() == 0) {
            throw new ResourceNotFoundException(String.format("没有找到商品"));
        }
        return all;
    }

    /**
     * 创建商品与类别关联
     *
     * @param categId
     * @param gdsId
     */
    public void createGdsCategRela(Long gdsId, Long categId) {
        Assert.notNull(gdsId);
        Assert.notNull(categId);
        Gds gds = gdsRepository.findOne(gdsId);
        if (gds == null) {
            throw new ResourceNotFoundException(String.format("商品不存在 gdsId=%s", gdsId));
        }
        Categ categ = categRepository.findOne(categId);
        if (categ == null) {
            throw new ResourceNotFoundException(String.format("类别不存在 categId=%s", categId));
        }

        Set<Categ> categs = gds.getCategs();
        categs.add(categ);
        gds.setCategs(categs);
        gdsRepository.save(gds);
    }

    /**
     * 删除商品与类别关联
     *
     * @param gdsId
     * @param categId
     */
    public void deleteCategGdsRela(Long gdsId, Long categId) {
        Assert.notNull(gdsId);
        Assert.notNull(categId);
        Gds gds = gdsRepository.findOne(gdsId);
        if (gds == null) {
            throw new ResourceNotFoundException(String.format("商品不存在 gdsId=%s", gdsId));
        }
        Categ categ = categRepository.findOne(categId);
        if (categ == null) {
            throw new ResourceNotFoundException(String.format("类别不存在 categId=%s", categId));
        }

        Set<Categ> categs = gds.getCategs();
        categs.remove(categ);
        gds.setCategs(categs);
        gdsRepository.save(gds);
    }

    /**
     * 修改商品状态
     *
     * @param id
     * @param status
     * @return
     */
    public void updateStatus(Long id, boolean status) {
        Assert.notNull(id);
        Assert.notNull(status);
        gdsRepository.updateStatus(id, status);
    }
}
