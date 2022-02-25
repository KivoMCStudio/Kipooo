package org.kivo.kipooo.modules;

import org.kivo.kipooo.Kipooo;

public interface CompleteModules extends EssentialsModule{

    /**
     * 获取补全前缀
     * @return 补全前缀
     */
    default String getComplete() {
        return Kipooo.INSTANCE.config.getString(getModules() + "complete");
    }

}
