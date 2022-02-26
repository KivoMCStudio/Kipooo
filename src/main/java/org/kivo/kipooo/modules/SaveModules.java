package org.kivo.kipooo.modules;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.kivo.kipooo.Kipooo;
import org.kivo.kipooo.config.Lang;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
                /*
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(Kipooo.INSTANCE.saveFile); // 创建输出流
                    FileInputStream fileInputStream = new FileInputStream(Kipooo.INSTANCE.saveFile);
                    byte[] data = new byte[1024 * 10 * 1024]; // 10MB 我就不信了
                    int length;
                    String dataStr = new String(data);
                    dataStr = dataStr + "\n" + event.getMessage().replaceAll(getCommandKey("save") + " " , "") + ":" + playerLoc.getBlockX() + "," + playerLoc.getBlockY() + "," + playerLoc.getBlockZ() + "," + Objects.requireNonNull(playerLoc.getWorld()).getName();
                    data = dataStr.trim().getBytes(StandardCharsets.UTF_8);
                    length = data.length;
                    fileOutputStream.write(data , 0 , length);
                } catch (IOException e) {
                    e.printStackTrace();
                    Kipooo.consoleBroad("遇到错误，请反馈.");
                }
                 */
            }
            if (event.getMessage().startsWith(getCommandKey("del "))) {
                String name = event.getMessage().replaceAll(getCommandKey("del ") , "");
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
        }
    }

}
