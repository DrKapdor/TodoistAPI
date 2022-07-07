package me.drkapdor.todoistapi.api;

import java.util.Comparator;
import java.util.LinkedList;

public class Section {

    private final int order;
    private final String name;
    private final LinkedList<Task> tasks;

    /**
     * Конструктор класса Секции
     * @param order Порядковый номер секции
     * @param name Название секции
     */

    public Section(int order, String name) {
        this.order = order;
        this.name = name;
        tasks = new LinkedList<>();
    }

    /**
     * Возвращает позицию в списке
     * @return Позиция в списке
     */

    public int getOrder() {
        return order;
    }

    /**
     * Возвращает имя секции
     * @return Имя секции
     */

    public String getName() {
        return name;
    }

    /**
     * Возвращает список задач из секции
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
