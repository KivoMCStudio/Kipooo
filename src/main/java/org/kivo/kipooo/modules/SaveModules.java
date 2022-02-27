package org.kivo.kipooo.modules;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.kivo.kipooo.Kipooo;
import org.kivo.kipooo.config.Lang;

import java.io.IOException;
import java.util.Objects;

public class SaveModules implements MultiModules{

    @Override
    public String modulesName() {
        return "save";
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        Location playerLoc = event.getPlayer().getLocation();
        if (isEnabled()) {
            if (event.getMessage().startsWith(getCommandKey("save") + " ")) {
                Kipooo.INSTANCE.save.set(
                        event.getMessage().replaceAll(getCommandKey("save") + " " , ""),
                        playerLoc.getBlockX() + "," + playerLoc.getBlockY() + "," + playerLoc.getBlockZ() + "," + Objects.requireNonNull(playerLoc.getWorld()).getName()
                );
                try {
                    Kipooo.INSTANCE.save.save(Kipooo.INSTANCE.saveFile);
                    event.getPlayer().sendMessage(Lang.SAVE.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (event.getMessage().startsWith(getCommandKey("del") + " ")) {
                String name = event.getMessage().replaceAll(getCommandKey("del") + " " , "");
                if (Kipooo.INSTANCE.save.getKeys(false).contains(name)) {
                    Kipooo.INSTANCE.save.set(name , null);
                    try {
                        Kipooo.INSTANCE.save.save(Kipooo.INSTANCE.saveFile);
                        event.getPlayer().sendMessage(Lang.DEL.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    event.getPlayer().sendMessage(Lang.UNKNOWNLOC.getMessage());
                }
            }
            if (event.getMessage().equalsIgnoreCase(getCommandKey("list"))) {
                for (String key : Kipooo.INSTANCE.save.getKeys(false)) {
                    event.getPlayer().sendMessage(Lang.LIST.getMessage().replaceAll("%loc_Name%" , key).replaceAll("%loc%" , Objects.requireNonNull(Kipooo.INSTANCE.save.getString(key))));
                }
            }
        }
    }

}
