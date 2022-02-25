package org.kivo.kipooo.modules;

import org.bukkit.event.Listener;
import org.kivo.kipooo.Kipooo;

import java.util.ArrayList;
import java.util.List;

public interface EssentialsModule extends Listener {

    List<EssentialsModule> modules = new ArrayList<>();

    String modulesName();

    default String getDescription() {
        return Kipooo.toColor(Kipooo.INSTANCE.config.getString("modules." + modulesName() + ".description"));
    }

    default boolean isEnabled() {
        return Kipooo.INSTANCE.config.getBoolean("modules." + modulesName() + ".enabled");
    }
}