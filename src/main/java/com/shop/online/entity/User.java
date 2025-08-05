package com.shop.online.entity;


import com.shop.online.utils.UserEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userName;

    @Column(name = "email")
    private String email;

    private String password;

    private String dateOfBirth;

    private String address;

    @Column(name = "expired_time")
    @CreationTimestamp
    private Instant expiredTime;

    @Column(name = "avatar", length = 1000)
    private String avatar;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "otp_phone")
    private String otpPhone;

    @Column(name = "otp_phone_created_at")
    private Instant otpPhoneCreatedAt;

    @Column(name = "otp_email")
    private String otpEmail;

    @Column(name = "otp_email_created_at")
    private Instant otpEmailCreatedAt;

    @Column(name = "phone")
    private String phone;

    //Default User or Admin
    private Integer type = 0;

    @Enumerated(value = EnumType.STRING)
    private UserEnum.Status status;

    @Column(columnDefinition = "text")
    private String resetToken;
}
