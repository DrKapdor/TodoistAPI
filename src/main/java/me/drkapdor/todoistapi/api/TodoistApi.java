package me.drkapdor.todoistapi.api;

import com.google.gson.*;
import me.drkapdor.todoistapi.TodoistApiPlugin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class TodoistApi {

    private static final String ALL_TASKS = "https://api.todoist.com/rest/v1/tasks";
    private static final String SINGLE_SECTION = "https://api.todoist.com/rest/v1/sections/{id}";

    private static final JsonParser jsonParser = new JsonParser();

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
                JsonArray response = jsonParser.parse(new InputStreamReader(connection.getInputStream())).getAsJsonArray();
                for (JsonElement element : response) {
                    JsonObject object = element.getAsJsonObject();
                    Task task = new Task(object.get("content").getAsString(),
                            object.get("description").getAsString(),
                            object.get("priority").getAsInt(),
                            object.get("order").getAsInt(),
                            object.get("section_id").getAsInt());
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
                        JsonObject response = jsonParser.parse(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                        Section section = new Section(response.get("order").getAsInt(),
                                response.get("name").getAsString());
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
