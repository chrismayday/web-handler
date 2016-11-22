package com.bluexiii.jwh.service;

import com.bluexiii.jwh.domain.Categ;
import com.bluexiii.jwh.domain.Gds;
import com.bluexiii.jwh.exception.BusinessException;
import com.bluexiii.jwh.exception.DataFormatException;
import com.bluexiii.jwh.exception.ResourceNotFoundException;
import com.bluexiii.jwh.repository.CategRepository;
import com.bluexiii.jwh.repository.GdsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

/**
 * 类别管理
 * Created by bluexiii on 16/8/23.
 */
@Service
@Transactional
public class CategService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategService.class);

    private final CategRepository categRepository;
    private final GdsRepository gdsRepository;

    @Autowired
    public CategService(CategRepository categRepository, GdsRepository gdsRepository) {
        this.categRepository = categRepository;
        this.gdsRepository = gdsRepository;
    }

    /**
     * 新增类别
     *
     * @param categ
     * @return
     */
    public Categ save(Categ categ) {
        Assert.notNull(categ);
        LOGGER.debug("Saving Categ {}", categ);
        return categRepository.save(categ);
    }

    /**
     * 修改类别
     *
     * @param categ
     * @return
     */
    public Categ update(Long id, Categ categ) {
        Assert.notNull(id);
        Assert.notNull(categ);
        LOGGER.debug("Updating Categ {}", categ);
        if (!categ.getId().equals(id)) {
            throw new DataFormatException("ID不匹配");
        }
        Categ existing = categRepository.findOne(id);
        if (existing == null) {
            throw new ResourceNotFoundException("类别不存在");
        }
//        BeanUtils.copyProperties(categ, existing);
        return categRepository.save(categ);
    }


    /**
     * 删除类别
     *
     * @param id
     * @return
     */
    public void delete(Long id) {
        Assert.notNull(id);
        Categ existing = categRepository.findOne(id);
        if (existing == null) {
            throw new ResourceNotFoundException(String.format("类别不存在 id=%s", id));
        }

        int ct = gdsRepository.countByCategs(new HashSet(Arrays.asList(existing)));
        if (ct > 0) {
            throw new BusinessException(901, "类别下还有商品，请先删除商品");
        }

        categRepository.delete(existing);
    }

    /**
     * 查询类别
     *
     * @param id
     * @return
     */
    public Categ findOne(Long id) {
        Assert.notNull(id);
        Categ categ = categRepository.findOne(id);
        if (categ == null) {
            throw new ResourceNotFoundException(String.format("类别不存在 id=%s", id));
        }
        return categ;
    }

    /**
     * 按名称查询类别
     *
     * @param categName
     * @return
     */
    public Categ findOneByCategName(String categName) {
        Assert.notNull(categName);
        Categ categ = categRepository.findOneByCategName(categName);
        if (categ == null) {
            throw new ResourceNotFoundException(String.format("类别不存在,categName=%s", categName));
        }
        return categ;
    }

    /**
     * 查询全部类别
     *
     * @return
     */
    public List<Categ> findAll() {
        List<Categ> all = categRepository.findAll();
        if (all.size() == 0) {
            throw new ResourceNotFoundException(String.format("没有找到类别"));
        }
        return all;
    }

    /**
     * 分页查询全部类别
     *
     * @return
     */
    public Page<Categ> findAllPageable(Pageable page) {
        Page<Categ> all = categRepository.findAll(page);
        if (all.getTotalElements() == 0) {
            throw new ResourceNotFoundException(String.format("没有找到类别"));
        }
        return all;
    }

    /**
     * 分页模糊查询类别
     *
     * @return
     */
    public Page<Categ> searchCategsPageable(Pageable pageable, String searchValue) {
        Page<Categ> categs = categRepository.searchCategsPageable(pageable, searchValue);
        if (categs.getTotalElements() == 0) {
            throw new ResourceNotFoundException(String.format("没有找到类别"));
        }
        return categs;
    }


    /**
     * 分页查询类别下的商品
     *
     * @param id
     * @param pageable
     * @return
     */
    public Page<Gds> findGdsByCategIdPageable(Long id, Pageable pageable) {
        LOGGER.debug("XXXXXXXXXXXXXXXXXXXX " + id);

        assertNotNull(id);
        Page<Gds> gdses = null;
        if (id == 0) {
            //全部类别
            gdses = gdsRepository.findAll(pageable);
        } else if (id == -1L) {
            //未分类
            gdses = gdsRepository.findeGdsByCategNullPageable(pageable);
        } else {
            //普通类别
            Categ categ = categRepository.findOne(id);
            if (categ == null) {
                throw new ResourceNotFoundException(String.format("类别不存在 id=%s", id));
            }
            gdses = gdsRepository.findByCategs(categ, pageable);
        }
        if (gdses == null || gdses.getTotalElements() == 0) {
            throw new ResourceNotFoundException(String.format("没有找到商品"));
        }
        return gdses;
    }

    /**
     * 分页搜索类别下的商品
     *
     * @param pageable
     * @param id
     * @param searchValue
     * @return
     */
    public Page<Gds> searchGdsByCategIdPageable(Pageable pageable, Long id, String searchValue) {
        assertNotNull(id);
        Page<Gds> gdses = null;

        if (id == 0) {
            //全部类别
            gdses = gdsRepository.searchPageable(searchValue, pageable);
        } else if (id == -1L) {
            //未分类
            gdses = gdsRepository.searchByCategsNullPageable(searchValue, pageable);
        } else {
            //普通类别
            Categ categ = categRepository.findOne(id);
            if (categ == null) {
                throw new ResourceNotFoundException(String.format("类别不存在 id=%s", id));
            }
            Set<Categ> categs = new LinkedHashSet<>(Arrays.asList(categ));
            gdses = gdsRepository.searchByCategsPageable(categs, searchValue, pageable);
        }
        if (gdses == null || gdses.getTotalElements() == 0) {
            throw new ResourceNotFoundException(String.format("没有找到商品"));
        }
        return gdses;
    }

    /**
     * 创建类别与商品关联
     *
     * @param categId
     * @param gdsId
     */
    public void createCategGdsRela(Long categId, Long gdsId) {
        Assert.notNull(categId);
        Assert.notNull(gdsId);
        Categ categ = categRepository.findOne(categId);
        if (categ == null) {
            throw new ResourceNotFoundException(String.format("类别不存在 categId=%s", categId));
        }
        Gds gds = gdsRepository.findOne(gdsId);
        if (gds == null) {
            throw new ResourceNotFoundException(String.format("商品不存在 gdsId=%s", gdsId));
        }

        Set<Categ> categs = gds.getCategs();
        categs.add(categ);
        gds.setCategs(categs);
        gdsRepository.save(gds);
    }


    /**
     * 删除类别与商品关联
     *
     * @param categId
     * @param gdsId
     */
    public void deleteCategGdsRela(Long categId, Long gdsId) {
        Assert.notNull(categId);
        Assert.notNull(gdsId);
        Categ categ = categRepository.findOne(categId);
        if (categ == null) {
            throw new ResourceNotFoundException(String.format("类别不存在 categId=%s", categId));
        }
        Gds gds = gdsRepository.findOne(gdsId);
        if (gds == null) {
            throw new ResourceNotFoundException(String.format("商品不存在 gdsId=%s", gdsId));
        }

        Set<Categ> categs = gds.getCategs();
        categs.remove(categ);
        gds.setCategs(categs);
        gdsRepository.save(gds);
    }
}
