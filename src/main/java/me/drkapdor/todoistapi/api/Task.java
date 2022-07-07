package me.drkapdor.todoistapi.api;

public class Task {

    private final String content;
    private final String description;
    private final int priority;
    private final int order;
    private final int sectionId;

    public Task(String content, String description, int priority, int order, int sectionId) {
        this.content = content;
        this.description = description;
        this.priority = priority;
        this.order = order;
        this.sectionId = sectionId;
    }

    /**
     * Получить имя задачи
     * @return Имя задачи
     */

    public String getContent() {
        return content;
    }

    /**
     * Получить описание задачи
     * @return Описание задачи
     */

    public String getDescription() {
        return description;
    }

    /**
     * Получить приоритет задачи
     * @return Приопритет задачи
     */

    public int getPriority() {
        return priority;
    }

    /**
     * Получить позицию в списке
     * @return Позиция в списке
     */

    public int getOrder() {
        return order;
    }

    /**
     * Получить идентификатор секции
     * @return Идентификатор секции
     */

    public int getSectionId() {
        return sectionId;
    }


    @Override
    public String toString() {
        return "[ " + priority + ", \"" + content + "\", \"" + description + "\" ]";
    }
}
