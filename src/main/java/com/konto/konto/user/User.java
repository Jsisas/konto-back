package com.konto.konto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String email;
    private String password;
    private String fname;
    private String lname;
    private List<UserToken> tokens;
}
