package com.example.teamcity.api.models;

import com.example.teamcity.api.annotations.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder // включает аннотации с геттерами/сеттерами, ту стринг, иквалс и хэшкод, а так же @RequiredArgsConstructor
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseModel{
    private String id;
    @Random
    private String username;
    @Random
    private String password;
    private Roles roles;
}