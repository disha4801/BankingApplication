package com.dnb.authservice.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class IdBasedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
}
