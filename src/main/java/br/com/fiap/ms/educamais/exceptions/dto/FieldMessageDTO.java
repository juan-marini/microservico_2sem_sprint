package br.com.fiap.ms.educamais.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FieldMessageDTO {

    private String fieldName;
    private String message;
}
