package com.example.pj_webshop.entities.errors;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.Nullable;
import org.springframework.web.ErrorResponse;

import java.net.URI;
import java.util.Locale;
import java.util.Map;

@Data
public class CustomErrorResponse implements ErrorResponse {
    private final HttpStatusCode statusCode;
    private final ProblemDetail problemDetail;
    private final String path;
    private final String method;
    private final String clientIp;
    private final Map<String, String> headers;
    private final Throwable cause;

    public CustomErrorResponse(HttpStatusCode statusCode, String detail, String path, String method, String clientIp, Map<String, String> headers, Throwable cause) {
        this.statusCode = statusCode;
        this.cause = cause;
        this.problemDetail = ProblemDetail.forStatusAndDetail(statusCode, detail);
        this.path = path;
        this.method = method;
        this.clientIp = clientIp;
        this.headers = headers;
        this.problemDetail.setProperty("path", path);
        this.problemDetail.setProperty("method", method);
        this.problemDetail.setProperty("clientIp", clientIp);
        this.problemDetail.setProperty("headers", headers);
        if (cause != null) {
            this.problemDetail.setProperty("cause", cause.toString());
        }
    }

    @Override
    public @NotNull HttpStatusCode getStatusCode() {
        return this.statusCode;
    }

    @Override
    public @NotNull HttpHeaders getHeaders() {
        return HttpHeaders.EMPTY;
    }

    @Override
    public @NotNull ProblemDetail getBody() {
        return this.problemDetail;
    }

    @Override
    public @NotNull String getTypeMessageCode() {
        return ErrorResponse.getDefaultTypeMessageCode(this.getClass());
    }

    @Override
    public @NotNull String getTitleMessageCode() {
        return ErrorResponse.getDefaultTitleMessageCode(this.getClass());
    }

    @Override
    public @NotNull String getDetailMessageCode() {
        return ErrorResponse.getDefaultDetailMessageCode(this.getClass(), null);
    }

    @Nullable
    @Override
    public Object[] getDetailMessageArguments() {
        return null;
    }

    @Nullable
    @Override
    public Object[] getDetailMessageArguments(@NotNull MessageSource messageSource, @NotNull Locale locale) {
        return this.getDetailMessageArguments();
    }

    @Override
    public @NotNull ProblemDetail updateAndGetBody(@Nullable MessageSource messageSource, @NotNull Locale locale) {
        if (messageSource != null) {
            String type = messageSource.getMessage(this.getTypeMessageCode(), null, null, locale);
            if (type != null) {
                this.problemDetail.setType(URI.create(type));
            }

            Object[] arguments = this.getDetailMessageArguments(messageSource, locale);
            String detail = messageSource.getMessage(this.getDetailMessageCode(), arguments, null, locale);
            if (detail != null) {
                this.problemDetail.setDetail(detail);
            }

            String title = messageSource.getMessage(this.getTitleMessageCode(), null, null, locale);
            if (title != null) {
                this.problemDetail.setTitle(title);
            }
        }

        return this.problemDetail;
    }
}
