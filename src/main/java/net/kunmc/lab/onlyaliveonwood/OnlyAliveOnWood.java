package net.kunmc.lab.onlyaliveonwood;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class OnlyAliveOnWood extends JavaPlugin implements Listener {

    private final static String CMD = "aliveonwood";
    private final static String CMD_START = "start";
    private final static String CMD_STOP = "stop";

    private final static double HP = 0.0d;
    private final static int RELATIVE_Y = 1;


    @Override
    public void onEnable() {
        getLogger().info("Plugin enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(CMD.equals(command.getName())){
            if(1 == args.length){
                return (sender.hasPermission(CMD)
                        ? Stream.of(CMD_START,CMD_STOP)
                        : Stream.of(CMD_START,CMD_STOP))
                        .filter(e -> e.startsWith(args[0])).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!CMD.equals(command.getName())) {
            return true;
        }
        if (1 != args.length) {
            sender.sendMessage(ChatColor.RED + "usage: \n/aliveonwood <" + CMD_START + " | " + CMD_STOP + ">");
            return true;
        }
        switch(args[0]) {
            case CMD_START:
                getServer().getPluginManager().registerEvents(this, this);
                sender.sendMessage(ChatColor.GREEN + "info: OnlyAliveOnWood が有効化されました");
                break;
            case  CMD_STOP:
                HandlerList.unregisterAll((Listener) this);
                sender.sendMessage(ChatColor.GREEN + "info: OnlyAliveOnWood が無効化されました");
                break;
            default:
                sender.sendMessage(ChatColor.RED + "usage: \n/aliveonwood <" + CMD_START + " | " + CMD_STOP + ">");
                break;
        }

        return true;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location loc = player.getLocation();
        loc.setY(loc.getY() - RELATIVE_Y);

        if (isAliveBlock(loc.getBlock().getType())) {
            return;
        }

        player.setHealth(HP);
    }

    /**
     * 生存可能なブロックであるかどうか判定する
     * @param material 判定対象のブロック
     * @return boolean true:生存可能, false:生存不可
     */
    public boolean isAliveBlock(Material material) {
        if (
            // 水・マグマ・空気
            Material.WATER.equals(material)
            || Material.MAGMA_BLOCK.equals(material)
            || Material.AIR.equals(material)
            || Material.CAVE_AIR.equals(material)
            || Material.VOID_AIR.equals(material)
            // 板材
            || Material.OAK_PLANKS.equals(material)
            || Material.SPRUCE_PLANKS.equals(material)
            || Material.BIRCH_PLANKS.equals(material)
            || Material.JUNGLE_PLANKS.equals(material)
            || Material.ACACIA_PLANKS.equals(material)
            || Material.DARK_OAK_PLANKS.equals(material)
            || Material.CRIMSON_PLANKS.equals(material)
            || Material.WARPED_PLANKS.equals(material)
            // 原木
            || Material.OAK_LOG.equals(material)
            || Material.SPRUCE_LOG.equals(material)
            || Material.BIRCH_LOG.equals(material)
            || Material.JUNGLE_LOG.equals(material)
            || Material.ACACIA_LOG.equals(material)
            || Material.DARK_OAK_LOG.equals(material)
        ) {
            return true;
        }
        return false;
    }

}
