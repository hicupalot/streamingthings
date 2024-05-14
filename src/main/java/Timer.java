import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Timer implements CommandExecutor, TabCompleter {
    int time;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = StreamingThings.getInstance().getConfig();
        String generalPrefix = ChatColor.translateAlternateColorCodes('&',"&e[&cStreamingThings&e] ");
        if (!(sender instanceof Player)) {
            String convertedNotPlayer = ChatColor.translateAlternateColorCodes('&',
                    config.getString("notPlayer"));
            sender.sendMessage(convertedNotPlayer);
            return false;
        }
        if (!sender.hasPermission("streamingthings.admin")) {
            String convertedNotStaff = ChatColor.translateAlternateColorCodes('&',
                    config.getString("notStaff"));
            sender.sendMessage(convertedNotStaff);
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(generalPrefix+ ChatColor.RED + "Command Usage /starttimer (seconds)");
            return false;
        }
        if (args.length > 1) {
            sender.sendMessage(generalPrefix+ ChatColor.RED + "Command Usage /starttimer (seconds)");
            return false;
        }
        if (config.getBoolean("timer",true)){
            sender.sendMessage(generalPrefix+ ChatColor.RED + "There is currently a timer running!");
            return false;
        }
        time = Integer.parseInt(args[0]);
        config.set("timer", true);
        StreamingThings.getInstance().saveConfig();
        Bukkit.getScheduler().runTaskTimer(StreamingThings.getInstance(), new Runnable() {
            //------------------------------------------------------------------------------//
            @Override
            public void run() {
                int timeleft = time;
                int seconds = timeleft;
                int S = seconds % 60;
                int H = seconds / 60;
                int M = H % 60;
                H = H / 60;
                if (time == 0) {
                    Bukkit.getScheduler().cancelTasks(StreamingThings.getInstance());
                    Bukkit.broadcastMessage(generalPrefix+ ChatColor.YELLOW+ "The Timer Is Up!");
                    config.set("timer",false);
                    StreamingThings.getInstance().saveConfig();
                    return;
                }
                time--;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (H!=0) {
                        player.sendActionBar("Time Remaining: " + H + " Hours " + M + " Minutes " + S + " Seconds");
                    }
                    else if (M!=0){
                        player.sendActionBar("Time Remaining: " + M + " Minutes " + S + " Seconds");
                    }
                    else if (time>=1) {
                        player.sendActionBar("Time Remaining: " + S+ " Seconds");
                    }
                    if (S==1){
                        player.sendActionBar("Time Remaining: " + S+ " Second");
                    }
                }
                // player.sendMessage(String.valueOf(timeleft)); //Debug
            }
        }, 0L, 20L);
        return false;
    }

    @Override
    public java.util.List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) {
            List<String> time = new ArrayList<>();
            time.add("Time");
            return time;
        }
        return null;
    }
}