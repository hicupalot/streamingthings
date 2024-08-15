import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class randomTp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = StreamingThings.getInstance().getConfig();
        String generalPrefix = ChatColor.translateAlternateColorCodes('&', "&e[&cStreamingThings&e] ");
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("notPlayer")));
            return false;
        }
        if (!sender.hasPermission("streamingthings.admin")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("notStaff")));
            return false;
        }
        if (args.length > 1) {
            sender.sendMessage(generalPrefix + ChatColor.RED + "Command Usage: /randomtp");
            return false;
        }
        int playersOnline = Bukkit.getOnlinePlayers().size();
        int randomPlayer = new Random().nextInt(Bukkit.getOnlinePlayers().size());
        List<? extends Player> playerList = Bukkit.getOnlinePlayers().stream().toList();
        Player randomTpAnswer = playerList.get(randomPlayer);
        ((Player) sender).teleport(randomTpAnswer);
        String successfulTp = ChatColor.translateAlternateColorCodes('&',generalPrefix+"&c You have successfully teleported to "+ randomTpAnswer.getDisplayName());
        sender.sendMessage(successfulTp);
        return true;
    }
}
