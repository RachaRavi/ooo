package com.coreantech.data;

import com.coreantech.data.dao.AssetDBDao;
import com.coreantech.data.model.GenericRequest;
import com.coreantech.data.model.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/lambda/asset")
public class AssetLamndaApplication {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping
    public GenericResponse<?> handleRequest(@RequestBody GenericRequest genericRequest) {
        logger.info("Got request for lambda::"+ genericRequest.toString());
        String method = genericRequest.getRequestContext().getHttp().getMethod();
        method = method.toUpperCase();
        String body = genericRequest.getBody();
        logger.info("Got request for lambda method::"+ method);
        logger.info("Got request for lambda method::"+ body);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> req = objectMapper.readValue(body, Map.class);
            AssetDBDao assetDBDao = new AssetDBDao();
            switch (method) {
                case "POST":
                    return assetDBDao.create(req);
                case "GET":
                    return assetDBDao.get(req);
                case "PUT":
                    return assetDBDao.update(req);
                case "DELETE":
                    return assetDBDao.delete(req);
            }
        }catch (Exception ex) {
            logger.error("Got error:"+ex.getMessage());
            ex.printStackTrace();
        }

        return null;
    }
}
