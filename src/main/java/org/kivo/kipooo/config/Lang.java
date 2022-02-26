package org.kivo.kipooo.config;

import org.bukkit.entity.Player;
import org.kivo.kipooo.Kipooo;

public enum Lang {

    KICKWHITELIST("kick-whitelist"),
    KICKBANNED("kick-banned"),
    BANNEDBC("banned-broadcast"),
    JOIN("join-message"),
    QUIT("quit-message"),
    RELOAD("reload-succeed"),
    TPSUCCEED("tp-succeed"),
    TPTO("tp-to"),
    TPACCEPT("tp-accept"),
    TPDENY("tp-deny"),
    TPDENYFROM("tp-deny-from"),
    TPCANCEL("tp-cancel"),
    TPNOQUEST("tp-noquest"),
    TPTOOMANY("tp-too-many"),
    TPTOSELF("tp-to-self"),
    SETHOME("sethome"),
    HOME("home"),
    NOHOME("no-home");

    private String path;

    Lang(String path) {
        this.path = path;
    }

    /**
     * 返回语言文件信息
     * @return 语言文件信息（未经处理）
     */
    public String getMessage() {
        return Kipooo.toColor(Kipooo.INSTANCE.config.getString("lang." + this.path));
    }
}
