package org.kivo.kipooo.modules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.kivo.kipooo.player.PlayerContainer;

import java.util.Objects;

public class MentionModule implements CliModules , SoundModules , CompleteModules , DndModules{

    @Override
    public String modulesName() {
        return "mention";
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if (event.getMessage().startsWith(getComplete()) && isEnabled()) {
            if (event.getPlayer().getPersistentDataContainer().get(PlayerContainer.KEY , PlayerContainer.INSTANCE) != null) {
                Player toPlayer = Bukkit.getPlayer(event.getMessage().replaceAll(getComplete(), ""));
                if (toPlayer != null) {
                    if (!Objects.requireNonNull(event.getPlayer().getPersistentDataContainer().get(PlayerContainer.KEY, PlayerContainer.INSTANCE)).isDnd()) {
                        send(event.getPlayer(), FROM);
                        send(toPlayer, TO);
                        playSound(toPlayer);
                    } else {
                        sendDnd(TO, toPlayer);
                    }
                }
            } else {
                Player toPlayer = Bukkit.getPlayer(event.getMessage().replaceAll(getComplete(), ""));
                if (toPlayer != null) {
                    send(event.getPlayer(), FROM);
                    send(toPlayer, TO);
                    playSound(toPlayer);
                }
            }
        }
    }
}
