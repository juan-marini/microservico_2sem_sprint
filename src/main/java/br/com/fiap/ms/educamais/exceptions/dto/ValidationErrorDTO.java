package br.com.fiap.ms.educamais.exceptions.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ValidationErrorDTO extends CustomErrorDTO {

    private final List<FieldMessageDTO> errors = new ArrayList<>();

    public ValidationErrorDTO(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public void addError(String fieldName, String message) {
        errors.removeIf(item -> item.getFieldName().equals(fieldName));
        errors.add(new FieldMessageDTO(fieldName, message));
    }
}
