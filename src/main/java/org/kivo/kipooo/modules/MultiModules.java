package org.kivo.kipooo.modules;

import org.kivo.kipooo.Kipooo;

public interface MultiModules extends EssentialsModule{

    /**
     * ��ȡ����
     * û�м�����null
     * @param key ��
     * @return ����
     */
    default String getCommandKey(String key) {
        return Kipooo.INSTANCE.config.getString(
                getModules() + "commands." + key
        );
    }

}
