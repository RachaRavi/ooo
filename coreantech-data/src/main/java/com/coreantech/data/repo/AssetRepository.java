package com.coreantech.data.repo;

import com.coreantech.data.entity.Asset;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends PagingAndSortingRepository<Asset, String>  {
}
