package org.kivo.kipooo.modules;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.scheduler.BukkitTask;
import org.kivo.kipooo.Kipooo;

public class SkipNightModule implements MessageModules{

    public static BukkitTask sleepTask;

    @Override
    public String modulesName() {
        return "sleep";
    }

    @EventHandler
    public void actionEnter(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK && isEnabled()) {
            sleepTask = Bukkit.getScheduler().runTaskTimerAsynchronously(
                    Kipooo.INSTANCE , () -> {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Kipooo.toColor("&f今夜将在睡梦中度过.")));
                        }
                    } , 0 , 1
            );
            Bukkit.getScheduler().runTaskLaterAsynchronously(
                    Kipooo.INSTANCE , () -> {
                        event.getPlayer().getWorld().setStorm(false);
                        event.getPlayer().getWorld().setThundering(false);
                        event.getPlayer().getWorld().setTime(0);
                    } , 20 * 5
            );
        }
    }

    @EventHandler
    public void actionLeave(PlayerBedLeaveEvent event) {
        sleepTask.cancel();
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if (event.getMessage().equalsIgnoreCase(getUsage()) && isEnabled()) {
            event.getPlayer().sendMessage(Kipooo.replacePlayer(getMessage() , event.getPlayer()));
        }
    }
}
