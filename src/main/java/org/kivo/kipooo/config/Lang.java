package org.kivo.kipooo.config;

import org.bukkit.entity.Player;
import org.kivo.kipooo.Kipooo;

public enum Lang {

    KICKWHITELIST("kick-whitelist"),
    KICKBANNED("kick-banned"),
    BANNEDBC("banned-broadcast"),
    JOIN("join-message"),
    QUIT("quit-message"),
    RELOAD("reload-succeed");

    private String path;

    Lang(String path) {
        this.path = path;
    }

    /**
     * ���������ļ���Ϣ
     * @return �����ļ���Ϣ��δ������
     */
    public String getMessage() {
        return Kipooo.toColor(Kipooo.INSTANCE.config.getString("lang." + this.path));
    }
}
