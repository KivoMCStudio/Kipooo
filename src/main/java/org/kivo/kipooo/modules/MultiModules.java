package org.kivo.kipooo.modules;

import org.kivo.kipooo.Kipooo;

public interface MultiModules extends EssentialsModule{

    /**
     * 获取命令
     * 没有即返回null
     * @param key 键
     * @return 命令
     */
    default String getCommandKey(String key) {
        return Kipooo.INSTANCE.config.getString(
                getModules() + "commands." + key
        );
    }

}
