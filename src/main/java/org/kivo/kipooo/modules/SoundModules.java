package org.kivo.kipooo.modules;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.kivo.kipooo.Kipooo;

public interface SoundModules extends EssentialsModule{

    default void playSound(Player player) {
        player.playSound(player.getLocation() , getSound() , 1.0F , 1.0F);
    }

    default Sound getSound() {
        return Sound.valueOf(getSoundString());
    }

    default String getSoundString() {
        return Kipooo.INSTANCE.config.getString(getModules() +  "sound");
    }
}
