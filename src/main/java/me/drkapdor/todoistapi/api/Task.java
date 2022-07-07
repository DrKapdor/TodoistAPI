package me.drkapdor.todoistapi.api;

public class Task {

    private final String content;
    private final String description;
    private final int priority;
    private final int order;
    private final int sectionId;

    /**
     * Конструктор класса Задачи
     * @param content Текст задачи
     * @param description Описание задачи
     * @param priority Приоритет задачи
     * @param order Порядковый номер задачи
     * @param sectionId Идентификатор секции, в которой находится задача
     */

    public Task(String content, String description, int priority, int order, int sectionId) {
        this.content = content;
        this.description = description;
        this.priority = priority;
        this.order = order;
        this.sectionId = sectionId;
    }

    /**
     * Возвращает текст задачи
     * @return Текст задачи
     */

    public String getContent() {
        return content;
    }

    /**
     * Возвращает описание задачи
     * @return Описание задачи
     */

    public String getDescription() {
        return description;
    }

    /**
     * Возвращает приоритет задачи
     * @return Приопритет задачи
     */

    public int getPriority() {
        return priority;
    }

    /**
     * Возвращает порядковый номер задачи
     * @return Порядковый номер задачи
     */

    public int getOrder() {
        return order;
    }

    /**
     * Возвращает идентификатор секции
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
