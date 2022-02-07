package com.coreantech.data.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

/**
 * This is Asset
 */
@Getter
@Setter
@Data
@Entity
public class Asset {

    @Id
    @GeneratedValue
    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID id;

    private String name;

    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID tenant;

    private String description;

    @Transient
    private Page page;
}
