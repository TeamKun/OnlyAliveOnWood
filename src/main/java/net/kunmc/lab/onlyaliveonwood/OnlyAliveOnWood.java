package net.kunmc.lab.onlyaliveonwood;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

    private final static double HP = 20.0d;
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
                sender.sendMessage(ChatColor.GREEN + "info: OnlyAliveOnWood ???????????????????????????");
                break;
            case  CMD_STOP:
                HandlerList.unregisterAll((Listener) this);
                sender.sendMessage(ChatColor.GREEN + "info: OnlyAliveOnWood ???????????????????????????");
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

        if(GameMode.SPECTATOR.equals(player.getGameMode())) {
            return;
        }
        if(!player.isOnGround()) {
            return;
        }

        Location loc = player.getLocation();
        loc.setY(loc.getY() - RELATIVE_Y);

        if (isAliveBlock(loc.getBlock())) {
            return;
        }

        player.damage(HP);
    }

    /**
     * ????????????????????????????????????????????????????????????
     * @param block ???????????????????????????
     * @return boolean true:????????????, false:????????????
     */
    public boolean isAliveBlock(Block block) {
        if(block.isLiquid()){
            // ????????????
            return true;
        }

        Material material = block.getType();
        if (
            // ??????
             Material.AIR.equals(material)
            || Material.CAVE_AIR.equals(material)
            || Material.VOID_AIR.equals(material)
            // ??????
            || Material.OAK_PLANKS.equals(material)
            || Material.SPRUCE_PLANKS.equals(material)
            || Material.BIRCH_PLANKS.equals(material)
            || Material.JUNGLE_PLANKS.equals(material)
            || Material.ACACIA_PLANKS.equals(material)
            || Material.DARK_OAK_PLANKS.equals(material)
            || Material.CRIMSON_PLANKS.equals(material)
            || Material.WARPED_PLANKS.equals(material)
            // ??????
            || Material.OAK_LOG.equals(material)
            || Material.SPRUCE_LOG.equals(material)
            || Material.BIRCH_LOG.equals(material)
            || Material.JUNGLE_LOG.equals(material)
            || Material.ACACIA_LOG.equals(material)
            || Material.DARK_OAK_LOG.equals(material)
            // ???????????????
            || Material.OAK_SLAB.equals(material)
            || Material.SPRUCE_SLAB.equals(material)
            || Material.BIRCH_SLAB.equals(material)
            || Material.JUNGLE_SLAB.equals(material)
            || Material.ACACIA_SLAB.equals(material)
            || Material.DARK_OAK_SLAB.equals(material)
            || Material.CRIMSON_SLAB.equals(material)
            || Material.WARPED_SLAB.equals(material)
            // ??????????????????
            || Material.OAK_STAIRS.equals(material)
            || Material.SPRUCE_STAIRS.equals(material)
            || Material.BIRCH_STAIRS.equals(material)
            || Material.JUNGLE_STAIRS.equals(material)
            || Material.ACACIA_STAIRS.equals(material)
            || Material.DARK_OAK_STAIRS.equals(material)
            || Material.CRIMSON_STAIRS.equals(material)
            || Material.WARPED_STAIRS.equals(material)
        ) {
            return true;
        }
        return false;
    }

}
