package me.drkapdor.todoistapi.command;

import me.drkapdor.todoistapi.util.PriorityColor;
import me.drkapdor.todoistapi.TodoistApiPlugin;
import me.drkapdor.todoistapi.api.Section;
import me.drkapdor.todoistapi.api.Task;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DisplayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.isOp()) {
            sender.sendMessage("§7§oПодготовка отчёта...");
            Bukkit.getScheduler().runTaskAsynchronously(TodoistApiPlugin.getInstance(), () -> {
                for (Section section : TodoistApiPlugin.getTodoistApi().getSections()) {
                    sender.sendMessage("§r §r");
                    sender.sendMessage(" §f§l" + section.getName());
                    for (Task task : section.getTasks()) {
                        sender.sendMessage(PriorityColor.of(task.getPriority()) + "  ● §f" + task.getContent());
                        if (!task.getDescription().isEmpty())
                            sender.sendMessage("     §8➥ §f" + task.getDescription());
                    }
                }
                sender.sendMessage("§r §r");
            });
        } else sender.sendMessage("§cУ Вас недостаточно полномочий!");
        return false;
    }
}
