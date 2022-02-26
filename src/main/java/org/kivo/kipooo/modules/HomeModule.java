package org.kivo.kipooo.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatEvent;
import org.kivo.kipooo.config.Lang;
import org.kivo.kipooo.player.PlayerContainer;
import org.kivo.kipooo.player.PlayerData;

public class HomeModule implements MultiModules , SoundModules {

    enum Cmds{
        SETHOME("sethome"),
        HOME("home");

        private final String key;

        Cmds(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    @Override
    public String modulesName() {
        return "home";
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void chat(PlayerChatEvent event) {
        if (isEnabled()) {
            if (event.getMessage().equalsIgnoreCase(getCommandKey(Cmds.SETHOME.getKey()))) {
                PlayerData data = event.getPlayer().getPersistentDataContainer().get(PlayerContainer.KEY , PlayerContainer.INSTANCE);
                if (data == null) {
                    data = new PlayerData(event.getPlayer());
                }
                data.setHomeLoc(event.getPlayer().getLocation());
                event.getPlayer().getPersistentDataContainer().set(PlayerContainer.KEY , PlayerContainer.INSTANCE , data);
                event.getPlayer().sendMessage(Lang.SETHOME.getMessage());
                playSound(event.getPlayer());
            }
            if (event.getMessage().equalsIgnoreCase(getCommandKey(Cmds.HOME.getKey()))) {
                PlayerData data = event.getPlayer().getPersistentDataContainer().get(PlayerContainer.KEY , PlayerContainer.INSTANCE);
                if (data != null) {
                    event.getPlayer().teleport(data.getHomeLoc());
                    event.getPlayer().sendMessage(Lang.HOME.getMessage());
                    playSound(event.getPlayer());
                }
                if (data == null) {
                    event.getPlayer().sendMessage(Lang.NOHOME.getMessage());
                }
            }
        }
    }
}
