import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class StreamingThings extends JavaPlugin {
    private static StreamingThings instance;
    public static StreamingThings getInstance() {
        return instance;
    }
    @Override
    public void onEnable(){
        instance = this;
        String startup = ChatColor.translateAlternateColorCodes('&',"&6[SmidgeCode] is starting");
        getLogger().info(startup);
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getCommand("timer").setExecutor(new Timer());
        getCommand("coinflip").setExecutor(new CoinFlip());
        getCommand("StopTimer").setExecutor(new StopTimer());
        getCommand("PlayerRaffle").setExecutor(new PlayerRaffle());

    }
    @Override
    public void onDisable() {
        String shutdown = ChatColor.translateAlternateColorCodes('&',"&c[SmidgeCode] Shutting Down!");
        getLogger().info(shutdown);
    }
}

