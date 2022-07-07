package me.drkapdor.todoistapi;

public enum PriorityColor {

    RED("§c"),
    ORANGE("§6"),
    BLUE("§9"),
    GRAY("§7");

    public static PriorityColor of(int priority) {
        switch (priority) {
            case 2: return BLUE;
            case 3: return ORANGE;
            case 4: return RED;
            default: return GRAY;
        }
    }

    private final String code;

    PriorityColor(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

}
