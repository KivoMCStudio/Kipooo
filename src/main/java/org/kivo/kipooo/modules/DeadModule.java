package org.kivo.kipooo.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.kivo.kipooo.Kipooo;
import org.kivo.kipooo.player.PlayerContainer;
import org.kivo.kipooo.player.PlayerData;

public class DeadModule implements MessageModules{

    @Override
    public String modulesName() {
        return "dead";
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if (event.getMessage().equalsIgnoreCase(getUsage()) && isEnabled()) {
            PlayerData data = event.getPlayer().getPersistentDataContainer().get(PlayerContainer.KEY, PlayerContainer.INSTANCE);
            if (data != null) {
                event.getPlayer().sendMessage(Kipooo.replacePlayer(getMessage() , event.getPlayer()));
            }
        }
    }

    @EventHandler
    public void actionDead(PlayerDeathEvent event) {
        if (isEnabled()) {
            PlayerData data = event.getEntity().getPersistentDataContainer().get(PlayerContainer.KEY, PlayerContainer.INSTANCE);
            if (data != null) {
                data.setDeadLoc(event.getEntity().getLocation());
            }
            if (data == null) {
                data = new PlayerData(event.getEntity());
                data.setDeadLoc(event.getEntity().getLocation());
            }
            event.getEntity().getPersistentDataContainer().set(PlayerContainer.KEY, PlayerContainer.INSTANCE, data);
            event.getEntity().sendMessage(Kipooo.replacePlayer(getMessage(), event.getEntity()));
        }
    }
}
