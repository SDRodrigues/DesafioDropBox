package com.desafioftp.desafio.exception;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErroPadrao {
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
