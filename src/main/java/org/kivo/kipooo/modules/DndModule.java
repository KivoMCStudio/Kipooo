package org.kivo.kipooo.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.kivo.kipooo.player.PlayerContainer;
import org.kivo.kipooo.player.PlayerData;

public class DndModule implements ToggleModules{

    @Override
    public String modulesName() {
        return "dnd";
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if (event.getMessage().equalsIgnoreCase(getToggle()) && isEnabled()) {
            if (event.getPlayer().getPersistentDataContainer().get(PlayerContainer.KEY , PlayerContainer.INSTANCE) != null) { // 已保存数据
                PlayerData data = event.getPlayer().getPersistentDataContainer().get(PlayerContainer.KEY , PlayerContainer.INSTANCE);
                if (data != null) {
                    boolean dndFlag = data.isDnd();
                    data.setDnd(!dndFlag);
                    String dndToggle = data.isDnd() ? "open" : "close";
                    event.getPlayer().getPersistentDataContainer().set(
                            PlayerContainer.KEY , PlayerContainer.INSTANCE , data
                    );
                    event.getPlayer().sendMessage(getMessages(dndToggle));
                }
            } else {
                event.getPlayer().getPersistentDataContainer().set(
                        PlayerContainer.KEY , PlayerContainer.INSTANCE , new PlayerData(event.getPlayer())
                );
                event.getPlayer().sendMessage(getMessages("open"));
            }
        }
    }
}
