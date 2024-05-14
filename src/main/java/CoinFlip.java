import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Random;

public class CoinFlip implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = StreamingThings.getInstance().getConfig();
        if (!(sender instanceof Player) && !(sender instanceof ConsoleCommandSender)) {
            return false;
        }
        if (!sender.hasPermission("streamingthings.admin")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("notAdmin")));
            return false;
        }
        int headsOrTails = new Random().nextInt(2);
        if (headsOrTails == 1) {
            String heads = ChatColor.translateAlternateColorCodes('&',
                    "&cThe coin landed on heads!");
            sender.sendMessage(heads);
            return false;
        }
        else {
            String tails = ChatColor.translateAlternateColorCodes('&',
                    "&cThe coin landed on tails!");
            sender.sendMessage(tails);
            return true;

        }
    }
}