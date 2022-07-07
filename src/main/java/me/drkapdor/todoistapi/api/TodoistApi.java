package me.drkapdor.todoistapi.api;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import me.drkapdor.todoistapi.TodoistApiPlugin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class TodoistApi {

    private static final String ALL_TASKS = "https://api.todoist.com/rest/v1/tasks";
    private static final String SINGLE_SECTION = "https://api.todoist.com/rest/v1/sections/{id}";

    private static final JsonParser parser = new JsonParser();

    private final String token;

    /**
     * Конструктор интерфейса
     * @param token Пользовательский токен
     */

    public TodoistApi(String token) {
        this.token = token;
    }

    /**
     * Получить список активных задач
     * @return Список активных задач
     */

    public Collection<Task> getActiveTasks() {
        LinkedList<Task> activeTasks = new LinkedList<>();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(ALL_TASKS).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            if (connection.getResponseCode() == 200) {
                JsonArray response = parser.parse(new InputStreamReader(connection.getInputStream())).getAsJsonArray();
                for (JsonElement element : response) {
                    JsonObject object = element.getAsJsonObject();
                    Task task = new Task();
                    task.setContent(object.get("content").getAsString());
                    task.setDescription(object.get("description").getAsString());
                    task.setPriority(object.get("priority").getAsInt());
                    task.setOrder(object.get("order").getAsInt());
                    task.setSectionId(object.get("section_id").getAsInt());
                    activeTasks.add(task);
                }
                activeTasks.sort((a, b) -> b.getPriority() - a.getPriority());
            }
        } catch (IOException exception) {
            TodoistApiPlugin.getInstance().getLogger().severe("Не удалось получить список активных задач: " + exception.getMessage());
        }
        return activeTasks;
    }

    public Collection<Section> getSections() {
        Map<Integer, Section> sectionMap = new HashMap<>();
        for (Task task : getActiveTasks()) {
            if (sectionMap.containsKey(task.getSectionId()))
                sectionMap.get(task.getSectionId()).getTasks().add(task);
            else {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(SINGLE_SECTION.replace("{id}", String.valueOf(task.getSectionId()))).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Authorization", "Bearer " + token);
                    if (connection.getResponseCode() == 200) {
                        JsonObject response = parser.parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                        Section section = new Section();
                        section.setName(response.get("name").getAsString());
                        section.setOrder(response.get("order").getAsInt());
                        section.getTasks().add(task);
                        sectionMap.put(task.getSectionId(), section);
                    }
                } catch (IOException exception) {
                    TodoistApiPlugin.getInstance().getLogger().severe("Не удалось получить список секций: " + exception.getMessage());
                }
            }
        }
        List<Section> sections = new ArrayList<>(sectionMap.values());
        sections.sort(Comparator.comparingInt(Section::getOrder));
        return sections;
    }
}
