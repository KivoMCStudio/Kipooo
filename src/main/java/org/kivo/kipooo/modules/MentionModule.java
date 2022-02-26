package org.kivo.kipooo.modules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

public class MentionModule implements CliModules , SoundModules , CompleteModules{

    @Override
    public String modulesName() {
        return "mention";
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if (event.getMessage().startsWith(getComplete()) && isEnabled()) {
            send(event.getPlayer() , FROM);
            Player toPlayer = Bukkit.getPlayer(event.getMessage().replaceAll(getComplete() , ""));
            if (toPlayer != null) {
                send(toPlayer , TO);
                playSound(toPlayer);
            }
        }
    }
}
