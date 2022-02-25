package org.kivo.kipooo.modules;

import org.bukkit.event.Listener;
import org.kivo.kipooo.Kipooo;

import java.util.ArrayList;
import java.util.List;

public interface EssentialsModule extends Listener {

    List<EssentialsModule> modules = new ArrayList<>();

    String modulesName();

    /**
     * ��ȡģ�����
     * @return ģ�����
     */
    default String getModules() {
        return "modules." + modulesName() + ".";
    }

    /**
     * ��ȡ����
     * @return ����
     */
    default String getDescription() {
        return Kipooo.toColor(Kipooo.INSTANCE.config.getString(getModules() + ".description"));
    }

    /**
     * �Ƿ�����
     * @return �Ƿ�����
     */
    default boolean isEnabled() {
        return Kipooo.INSTANCE.config.getBoolean(getModules() + ".enabled");
    }
}
