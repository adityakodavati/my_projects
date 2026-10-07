package com.system.readycrudop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Response <T, S, B>{
    private T data;
    private String message;
    private Boolean success;
}
