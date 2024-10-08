package com.example.teamcity.api.model;

import lombok.*;

@Builder // включает аннотации с геттерами/сеттерами, ту стринг, иквалс и хэшкод, а так же @RequiredArgsConstructor
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;
    private String password;
}