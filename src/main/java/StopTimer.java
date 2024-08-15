import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class StopTimer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = StreamingThings.getInstance().getConfig();
        String generalPrefix = ChatColor.translateAlternateColorCodes('&',"&e[&cStreamingThings&e] ");
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("notPlayer")));
            return false;
        }
        if (!sender.hasPermission("streamingthings.admin")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("notAdmin")));
            return false;
        }
        if (args.length > 0) {
            sender.sendMessage(generalPrefix+ ChatColor.RED + "Command Usage: /stoptimer");
            return false;
        }
        if (!config.getBoolean("timer")){
            String noTimer = generalPrefix + ChatColor.RED+"There is no timer running!";
            sender.sendMessage(noTimer);
            return false;
        }
        Bukkit.getScheduler().cancelTasks(StreamingThings.getInstance());
        String cancelled = generalPrefix + ChatColor.RED+ "Cancelled the current timer!";
        String cancelAnnounce = ChatColor.translateAlternateColorCodes('&',"&e[&c&lSmidge&r&e] "+"&cThe current timer was cancelled by "+
                sender.getName());
        sender.sendMessage(cancelled);
        config.set("timer",false);
        StreamingThings.getInstance().saveConfig();
        for (Player player : Bukkit.getOnlinePlayers()){
            if (player!=sender){
                player.sendMessage(cancelAnnounce);
            }
        }
        return true;
    }
}
