package com.example.pj_webshop.entities.models.products;

import lombok.Getter;

@Getter
public enum AvailableOptionsState {
    SINGLE_OPTION_ALLOWED("single"),
    MULTIPLE_OPTIONS_ALLOWED("multiple"),
    UNKNOWN("");

    private final String optionsState;

    AvailableOptionsState(String optionsState) {
        this.optionsState = optionsState;
    }

    public static AvailableOptionsState fromOptionsState(String optionsState) {
        for (AvailableOptionsState mode : values()) {
            if (mode.optionsState.equalsIgnoreCase(optionsState)) {
                return mode;
            }
        }
        return UNKNOWN;
    }
}
