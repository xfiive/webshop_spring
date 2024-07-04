package com.example.pj_webshop.components;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class DateTimeFormatConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(@NotNull FormatterRegistry registry) {
        registry.addFormatterForFieldType(ZonedDateTime.class, new ZonedDateTimeFormatter());
    }

    private static class ZonedDateTimeFormatter implements org.springframework.format.Formatter<ZonedDateTime> {

        @Contract(pure = true)
        @Override
        public @NotNull ZonedDateTime parse(String text, java.util.Locale locale) {
            return ZonedDateTime.parse(text, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        }

        @Override
        public @NotNull String print(ZonedDateTime object, java.util.Locale locale) {
            return DateTimeFormatter.ISO_ZONED_DATE_TIME.format(object);
        }
    }
}
