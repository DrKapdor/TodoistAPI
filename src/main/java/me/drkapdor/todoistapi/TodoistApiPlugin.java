package me.drkapdor.todoistapi;

import me.drkapdor.todoistapi.api.Section;
import me.drkapdor.todoistapi.api.Task;
import me.drkapdor.todoistapi.api.TodoistApi;
import me.drkapdor.todoistapi.command.DisplayCommand;
import me.drkapdor.todoistapi.util.PriorityColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class TodoistApiPlugin extends JavaPlugin {

    private static TodoistApiPlugin instance;
    private static FileConfiguration configuration;
    private static TodoistApi todoistApi;

    /**
     * Получить ссылку на экземпляр главного класса
     * @return Ссылка на экземпляр главного класса
     */

    public static TodoistApiPlugin getInstance() {
        return instance;
    }

    /**
     * Получить файл конфигурации
     * @return Файл конфигурации
     */

    public static FileConfiguration getConfiguration() {
        return configuration;
    }


    /**
     * Получить экземпляр интерфейса взаимодействия с Todoist
     * @return Экземпляр интерфейса взаимодействия с Todoist
     */

    public static TodoistApi getTodoistApi() {
        return todoistApi;
    }

    /**
     * Инициализация проекта
     */

    @Override
    public void onEnable() {
        instance = this;
        createDefaultConfig();
        todoistApi = new TodoistApi(configuration.getString("API_TOKEN"));
        registerCommands();
        if (configuration.getBoolean("DEBUG_MODE")) {
            Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
                for (Section section : todoistApi.getSections()) {
                    getLogger().info("§r §r");
                    getLogger().info("§f§l" + section.getName());
                    for (Task task : section.getTasks()) {
                        getLogger().info(PriorityColor.of(task.getPriority()) + " ● §f" + task.getContent());
                        if (!task.getDescription().isEmpty())
                            getLogger().info("   §7➥ §f" + task.getDescription());
                    }
                }
                getLogger().info("§r §r");
            });
        }
    }

    /**
     * Инициализация файла конфигурации
     */

    private void createDefaultConfig() {
        configuration = getConfig();
        configuration.addDefault("API_TOKEN", "insert_your_token_here");
        configuration.addDefault("DEBUG_MODE", false);
        configuration.options().copyDefaults(true);
        saveConfig();
    }

    /**
     * Инициализация команд
     */

    public void registerCommands() {
        getCommand("tasks").setExecutor(new DisplayCommand());
    }
}
