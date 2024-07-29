package com.example.pj_webshop.entities.models.products;

import lombok.Getter;

@Getter
public enum GroupModificationMode {
    MODIFIABLE("modifiable"),
    UNMODIFIABLE("unmodifiable"),
    UNKNOWN("");

    private final String command;

    GroupModificationMode(String command) {
        this.command = command;
    }

    public static GroupModificationMode fromCommand(String command) {
        for (GroupModificationMode mode : values()) {
            if (mode.command.equalsIgnoreCase(command)) {
                return mode;
            }
        }
        return UNKNOWN;
    }

}
