package me.drkapdor.todoistapi.api;

import java.util.Comparator;
import java.util.LinkedList;

public class Section {

    private int order;
    private String name;
    private LinkedList<Task> tasks;

    public Section() {
        order = -1;
        name = "Unknown section";
        tasks = new LinkedList<>();
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
     * Получить имя секции
     * @return Имя секции
     */

    public String getName() {
        return name;
    }

    /**
     * Назначить имя секции
     * @param name Имя секции
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получить список задач из секции
     * @return Список задач
     */

    public LinkedList<Task> getTasks() {
        //Сортировка по порядку
        tasks.sort(Comparator.comparingInt(Task::getOrder));
        //Сортировка по приоритету
        tasks.sort((a, b) -> b.getPriority() - a.getPriority());
        return tasks;
    }
}
