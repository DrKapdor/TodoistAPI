package me.drkapdor.todoistapi.api;

public class Task {

    private String content;
    private String description;
    private int priority;
    private int order;
    private int sectionId;

    public Task() {
        content = "Unknown task";
        description = "No comments";
        priority = -1;
        order = -1;
        sectionId = -1;
    }

    /**
     * Получить имя задачи
     * @return Имя задачи
     */

    public String getContent() {
        return content;
    }

    /**
     * Установить имя задачи
     * @param content Имя задачи
     */

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Получить описание задачи
     * @return Описание задачи
     */

    public String getDescription() {
        return description;
    }

    /**
     * Установить описание задачи
     * @param description Описание задачи
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Получить приоритет задачи
     * @return Приопритет задачи
     */

    public int getPriority() {
        return priority;
    }

    /**
     * Установить приоритет задачи
     * @param priority Приоритет задачи
     */

    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Получить позицию в списке
     * @return Позиция в списке
     */

    public int getOrder() {
        return order;
    }

    /**
     * Установить позицию в списке
     * @param order Позиция в списке
     */

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Получить идентификатор секции
     * @return Идентификатор секции
     */

    public int getSectionId() {
        return sectionId;
    }

    /**
     * Установить идентификатор секции
     * @param sectionId Идентификатор секции
     */

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public String toString() {
        return "[ " + priority + ", \"" + content + "\", \"" + description + "\" ]";
    }
}
