package com.coreantech.data.dao;

import com.coreantech.data.entity.Asset;
import com.coreantech.data.model.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.*;

/**
 * ASSET
 */
public class AssetDBDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // DB
    private final String URL = "jdbc:postgresql://localhost/coreantech";
    private final String USER = "postgres";
    private final String DRIVER = "org.postgresql.Driver";
    private final String PASSWORD = "Admin@123";

    private final String SQL_UPDATE  = "UPDATE ASSET SET name=?, description=? WHERE id=?";
    private final String SQL_DELETE = "DELETE FROM ASSET WHERE id=?";

     /**
     * INSERT
     * @param parameters
     * @return
     */
    public GenericResponse<?> create(Map parameters) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource())
                .withTableName("asset")
                .usingGeneratedKeyColumns("id");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        keyHolder = simpleJdbcInsert.executeAndReturnKeyHolder(parameters);
        logger.info("Created id::::"+keyHolder.getKeyList());
        Object id = keyHolder.getKeys().get("id");
        GenericResponse<Map> response = new GenericResponse<>();
        if(id != null) {
            parameters.put("id",id);
            response.setStatus(true);
            response.setData(parameters);
        } else {
            response.setStatus(false);
        }
        return response;
    }

    /**
     * GET
     * @param parameters
     * @return
     */
    public GenericResponse<?> get(Map parameters) {
        GenericResponse<Map> response = new GenericResponse<>();
        Object oid = parameters.get("id");
        String query = null;
        if(oid == null) {
            return getAll();
        }
        UUID uuid = UUID.fromString(oid.toString());
        parameters.put("id", uuid);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        try {
            Map map = jdbcTemplate.queryForObject("SELECT * FROM ASSET WHERE id= ?", new Object[]{uuid}, (rs, rowNum) -> {
                Map<String, String> innerMap = new HashMap<>();
                innerMap.put("id", rs.getString("id"));
                innerMap.put("name", rs.getString("name"));
                innerMap.put("description", rs.getString("description"));
                return innerMap;
            });
            logger.info("DB Response::" + map);
            response.setStatus(true);
            response.setData(map);
            return response;
        } catch (EmptyResultDataAccessException e) {
            logger.error("Not found Asset for the request:"+parameters);
            response.setStatus(false);
            return response;
        }
    }

    private GenericResponse<?> getAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        GenericResponse<List> response = new GenericResponse<>();
        List<Map<String,Object>> rs = jdbcTemplate.queryForList("SELECT id,name,description FROM ASSET");
        response.setStatus(true);
        response.setData(rs);
        return response;
    }


    /**
     * UPDATE
     * @param parameters
     * @return
     */
    public GenericResponse<?> update(Map parameters) {
        GenericResponse<Integer> response = new GenericResponse<>();
        Object oid = parameters.get("id");
        if(oid != null) {
            UUID uuid = UUID.fromString(oid.toString());
            parameters.put("id", uuid);
        } else {
            response.setStatus(false);
            return response;
        }
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        int count = jdbcTemplate.update(SQL_UPDATE,parameters.get("name"),parameters.get("description"),parameters.get("id"));
        logger.info("Update result:::"+count);
        if(count > 0){
            response.setStatus(true);
            response.setData(count);
        } else {
            response.setStatus(false);
        }
        return response;
    }

    /**
     * DELETE
     * @param parameters
     * @return
     */
    public GenericResponse<?> delete(Map parameters) {
        GenericResponse<Integer> response = new GenericResponse<>();
        Object oid = parameters.get("id");
        if(oid != null) {
            UUID uuid = UUID.fromString(oid.toString());
            parameters.put("id", uuid);
        } else {
            response.setStatus(false);
            return response;
        }
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        int count = jdbcTemplate.update(this.SQL_DELETE, parameters.get("id"));
        logger.info("Update result:::"+count);
        if(count > 0){
            response.setStatus(true);
            response.setData(count);
        } else {
            response.setStatus(false);
        }
        return response;
    }

    private DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(URL);
        driverManagerDataSource.setUsername(USER);
        driverManagerDataSource.setPassword(PASSWORD);
        driverManagerDataSource.setDriverClassName(DRIVER);
        return driverManagerDataSource;
    }

}
