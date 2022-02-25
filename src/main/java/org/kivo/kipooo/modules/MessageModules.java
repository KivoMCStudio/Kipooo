package org.kivo.kipooo.modules;

import org.kivo.kipooo.Kipooo;

public interface MessageModules extends EssentialsModule{

    /**
     * ��ȡ��ʾ��Ϣ
     * @return ��ʾ��Ϣ
     */
    default String getMessage() {
        return Kipooo.toColor(Kipooo.INSTANCE.config.getString(getModules() + "message"));
    }

    /**
     * ��ȡ����
     * @return ����
     */
    default String getUsage() {
        return Kipooo.INSTANCE.config.getString(getModules() + "usage");
    }

}
