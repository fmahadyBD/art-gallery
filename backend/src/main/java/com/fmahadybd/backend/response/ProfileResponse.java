package com.fmahadybd.backend.response;

import java.util.Date;
import java.util.List;

import com.fmahadybd.backend.entity.Art;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProfileResponse {
    private String name;
    private String email;
    private Date date;
    private String role;

    private List<Art> arts;

}
