package com.coreantech.data.controller;

import com.coreantech.data.entity.Asset;
import com.coreantech.data.model.AssetBean;
import com.coreantech.data.model.GenericResponse;
import com.coreantech.data.service.AssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/asset")
public class AssetController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AssetService assetService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<GenericResponse<?>> get(@RequestBody Asset asset) {
        logger.info("Started the asset get():"+asset.toString());
        List<Asset> assets =  Collections.emptyList();
        if(asset.getId() == null)
            assets = this.assetService.getAll();
        GenericResponse<List<Asset>> res = new GenericResponse<>(true,assets);
        return new ResponseEntity<>(res, getHeaders(), HttpStatus.OK);
    }


    @PostMapping(consumes = "application/json",produces = "application/json")
    public ResponseEntity<GenericResponse<?>> save(@RequestBody Asset asset) {
        logger.info("Started the asset save():"+asset.toString());
        asset =  this.assetService.save(asset);
        GenericResponse<Asset> res = new GenericResponse<>(true,asset);
        return new ResponseEntity<GenericResponse<?>>(res, getHeaders(), HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json",produces = "application/json")
    public ResponseEntity<GenericResponse<Asset>> update(@RequestBody Asset asset) {
        logger.info("Started the asset update():"+asset.toString());
        asset =  this.assetService.save(asset);
        GenericResponse<Asset> res = new GenericResponse<>(true,asset);
        return new ResponseEntity<GenericResponse<Asset>>(res, getHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(consumes = "application/json",produces = "application/json")
    public ResponseEntity<GenericResponse<?>> delete(@RequestBody Asset asset) {
        logger.info("Started the asset delete():"+asset.toString());
        this.assetService.delete(asset);
        GenericResponse<Asset> res = new GenericResponse<>(true,null);
        return new ResponseEntity<GenericResponse<?>>(res, getHeaders(), HttpStatus.OK);
    }


    public static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccessControlAllowOrigin("*");
        headers.setAccessControlMaxAge(1000*60*30);
        headers.setAccessControlAllowMethods((Arrays.asList(HttpMethod.GET,HttpMethod.POST,
                HttpMethod.PUT,HttpMethod.OPTIONS,HttpMethod.DELETE,HttpMethod.TRACE)));
        headers.setAccessControlAllowHeaders(Arrays.asList("Accept","Content-Type","X-PINGOTHER"));
        headers.set("Cache-Control", "no-cache");
        return headers;
    }
}
