package com.example.pj_webshop.entities.errors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@JsonIgnoreProperties({"stackTrace", "localization"})
public class CustomException {
    private final String message;
    private final String causeMessage;

    public CustomException(@NotNull Throwable cause) {
        this.message = cause.getMessage();
        this.causeMessage = cause.getCause() != null ? cause.getCause().getMessage() : null;
    }

}
