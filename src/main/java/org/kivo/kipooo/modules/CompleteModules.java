package org.kivo.kipooo.modules;

import org.kivo.kipooo.Kipooo;

public interface CompleteModules extends EssentialsModule{

    /**
     * ��ȡ��ȫǰ׺
     * @return ��ȫǰ׺
     */
    default String getComplete() {
        return Kipooo.INSTANCE.config.getString(getModules() + "complete");
    }

}
