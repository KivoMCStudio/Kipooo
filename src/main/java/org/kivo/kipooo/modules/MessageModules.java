package org.kivo.kipooo.modules;

import org.kivo.kipooo.Kipooo;

public interface MessageModules extends EssentialsModule{

    /**
     * 获取提示信息
     * @return 提示信息
     */
    default String getMessage() {
        return Kipooo.toColor(Kipooo.INSTANCE.config.getString(getModules() + "message"));
    }

    /**
     * 获取命令
     * @return 命令
     */
    default String getUsage() {
        return Kipooo.INSTANCE.config.getString(getModules() + "usage");
    }

}
