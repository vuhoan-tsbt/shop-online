package com.shop.online.entity;

import com.shop.online.security.UserPrincipal;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    @JsonIgnore
    private Integer createdBy;

    @Column(name = "updated_by")
    @JsonIgnore
    private Integer updatedBy;

    @PrePersist
    private void prePersist() {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserPrincipal) {
            this.createdBy = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            this.updatedBy = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        }
    }

    @PreUpdate
    private void preUpdate() {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserPrincipal) {
            this.updatedBy = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        }
    }
}
