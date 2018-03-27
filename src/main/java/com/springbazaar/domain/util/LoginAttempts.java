package com.springbazaar.domain.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SB_LOGIN_ATTEMPTS")
@Getter
@Setter
@NoArgsConstructor
public class LoginAttempts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private int attempts;
    private Date lastModified;
}
