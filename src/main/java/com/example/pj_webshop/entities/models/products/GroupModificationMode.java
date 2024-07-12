package com.example.pj_webshop.entities.models.products;

public enum GroupModificationMode {
    MODIFIABLE("modifiable"),
    UNMODIFIABLE("unmodifiable"),
    UNKNOWN("");

    private String command;

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

    public String getCommand() {
        return command;
    }
}
