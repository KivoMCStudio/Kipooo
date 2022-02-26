package org.kivo.kipooo.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.kivo.kipooo.Kipooo;

public class HereModule implements MessageModules{

    @Override
    public String modulesName() {
        return "here";
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if (event.getMessage().equalsIgnoreCase(getUsage()) && isEnabled()) {
            Kipooo.broadCast(Kipooo.replacePlayer(getMessage() , event.getPlayer()));
        }
    }

}
