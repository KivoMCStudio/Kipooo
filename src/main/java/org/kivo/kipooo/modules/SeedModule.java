package org.kivo.kipooo.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.kivo.kipooo.Kipooo;

public class SeedModule implements MessageModules{

    @Override
    public String modulesName() {
        return "seed";
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if (event.getMessage().equalsIgnoreCase(getUsage()) && isEnabled()) {
            event.getPlayer().sendMessage(Kipooo.replacePlayer(getMessage() , event.getPlayer()));
        }
    }

}
