package org.kivo.kipooo.modules;

import org.bukkit.event.Listener;
import org.kivo.kipooo.Kipooo;

import java.util.ArrayList;
import java.util.List;

public interface EssentialsModule extends Listener {

    List<EssentialsModule> modules = new ArrayList<>();

    String modulesName();

    /**
     * 获取模块参数
     * @return 模块参数
     */
    default String getModules() {
        return "modules." + modulesName() + ".";
    }

    /**
     * 获取描述
     * @return 描述
     */
    default String getDescription() {
        return Kipooo.toColor(Kipooo.INSTANCE.config.getString(getModules() + ".description"));
    }

    /**
     * 是否启用
     * @return 是否启用
     */
    default boolean isEnabled() {
        return Kipooo.INSTANCE.config.getBoolean(getModules() + ".enabled");
    }
}
