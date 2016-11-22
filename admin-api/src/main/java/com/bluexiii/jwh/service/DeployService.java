package com.bluexiii.jwh.service;

import com.bluexiii.jwh.domain.Deploy;
import com.bluexiii.jwh.exception.BusinessException;
import com.bluexiii.jwh.exception.ResourceNotFoundException;
import com.bluexiii.jwh.repository.DeployRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * 应用部署管理
 */
@Service
@Transactional
public class DeployService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployService.class);
    private final DeployRepository deployRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public DeployService(DeployRepository deployRepository, RestTemplateBuilder restTemplateBuilder) {
        this.deployRepository = deployRepository;
        this.restTemplate = restTemplateBuilder.build();
    }


    /**
     * 获取全部应用
     *
     * @return
     */
    public Page<Deploy> findAllDeploys(Pageable pageable) {
        Page<Deploy> deploys = deployRepository.findAll(pageable);
        if (deploys.getTotalElements() == 0) {
            throw new ResourceNotFoundException(String.format("没有找到应用"));
        }
        return deploys;
    }

    /**
     * 刷新缓存
     */
    public void refreshCache(Long id) {
        Assert.notNull(id);
        Deploy deploy = deployRepository.findOne(id);
        String refreshUrl = deploy.getRefreshUrl();
        if (refreshUrl == null || refreshUrl.equals("")) {
            throw new BusinessException(901, "缓存刷新URL为空");
        }
        restTemplate.put(refreshUrl, null);
    }
}
