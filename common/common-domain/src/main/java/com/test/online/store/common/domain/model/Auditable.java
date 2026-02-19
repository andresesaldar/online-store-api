package com.test.online.store.common.domain.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.test.online.store.common.model.Creatable;
import com.test.online.store.common.model.Identifiable;
import com.test.online.store.common.model.Updatable;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@MappedSuperclass
@NoArgsConstructor
public class Auditable<ID> implements Identifiable<ID>, Creatable, Updatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "last_modified_at")
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
}
