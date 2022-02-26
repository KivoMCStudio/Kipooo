package org.kivo.kipooo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kivo.kipooo.Kipooo;
import org.kivo.kipooo.config.Lang;
import org.kivo.kipooo.modules.EssentialsModule;

public class PlayerActionEvent implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event) {
        event.setJoinMessage(Kipooo.replacePlayer(Lang.JOIN.getMessage() , event.getPlayer()));
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        event.setQuitMessage(Kipooo.replacePlayer(Lang.QUIT.getMessage() , event.getPlayer()));
    }

    @EventHandler
    public void login(PlayerLoginEvent event) {
        switch (event.getResult()) {
            case KICK_WHITELIST -> event.setKickMessage(Kipooo.replacePlayer(Lang.KICKWHITELIST.getMessage() , event.getPlayer()));
            case KICK_BANNED -> {
                Kipooo.broadCast(Kipooo.replacePlayer(Lang.BANNEDBC.getMessage() , event.getPlayer()));
                event.setKickMessage(Kipooo.replacePlayer(Lang.KICKBANNED.getMessage() , event.getPlayer()));
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
        if (event.getMessage().equalsIgnoreCase("!reload") && event.getPlayer().isOp()) {
            Kipooo.INSTANCE.reloadConfig();
            event.getPlayer().sendMessage(Lang.RELOAD.getMessage());
        }
        if (event.getMessage().equalsIgnoreCase("!default") && event.getPlayer().isOp()) {
            Kipooo.INSTANCE.updateDefaultConfig();
            event.getPlayer().sendMessage(Lang.RELOAD.getMessage());
        }
    }
}
