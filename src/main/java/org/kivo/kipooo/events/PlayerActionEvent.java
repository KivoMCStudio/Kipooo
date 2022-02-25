package org.kivo.kipooo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kivo.kipooo.Kipooo;
import org.kivo.kipooo.config.Lang;
import org.kivo.kipooo.modules.EssentialsModule;

public class PlayerActionEvent implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event) {
        event.setJoinMessage(Lang.JOIN.getMessage());
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        event.setQuitMessage(Lang.QUIT.getMessage());
    }

    @EventHandler
    public void login(AsyncPlayerPreLoginEvent event) {
        switch (event.getLoginResult()) {
            case KICK_WHITELIST -> event.setKickMessage(Lang.KICKWHITELIST.getMessage());
            case KICK_BANNED -> {
                Kipooo.broadCast(Lang.BANNEDBC.getMessage());
                event.setKickMessage(Lang.KICKBANNED.getMessage().replaceAll("%player%" , event.getName()));
            }
        }
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if (event.getMessage().equalsIgnoreCase("!help")) {
            for (EssentialsModule module : EssentialsModule.modules) {
                event.getPlayer().sendMessage(module.getDescription() + "\n");
            }
        }
    }
}
