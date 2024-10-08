package com.example.teamcity.api.models;

import lombok.*;

@Builder // включает аннотации с геттерами/сеттерами, ту стринг, иквалс и хэшкод, а так же @RequiredArgsConstructor
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel{
    private String username;
    private String password;
}