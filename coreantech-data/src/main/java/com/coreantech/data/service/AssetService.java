package com.coreantech.data.service;

import com.coreantech.data.entity.Asset;
import com.coreantech.data.repo.AssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssetService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AssetRepository assetRepository;

    public Asset save(Asset asset) {
        Asset result = this.assetRepository.save(asset);
        return result;
    }

    public boolean delete(Asset asset) {
            this.assetRepository.delete(asset);
        return true;
    }

    public List<Asset> getAll() {
        Pageable firstPageWithTwoElements = PageRequest.of(0, 10);
        Page<Asset> assets = this.assetRepository.findAll(firstPageWithTwoElements);
        return assets.toList();
    }
}
