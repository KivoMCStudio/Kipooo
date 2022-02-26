package org.kivo.kipooo.modules;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.kivo.kipooo.Kipooo;

import java.util.Objects;

public interface DndModules extends CliModules{

    /**
     * 以免打扰的形式发送信息
     * @param fromOrTo 来源或去向
     * @param player 玩家
     */
    default void sendDnd(String fromOrTo , Player player) {
        switch (getDndType(fromOrTo)) {
            case TEXT -> {
                player.sendMessage(Kipooo.replacePlayer(Kipooo.toColor(Objects.requireNonNull(Kipooo.INSTANCE.config.getString(
                        getDnd(fromOrTo) + "text"
                ))) , player));
            }
            case ACTION -> {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR , new TextComponent(
                        Kipooo.replacePlayer(Kipooo.toColor(Kipooo.INSTANCE.config.getString(getDnd(fromOrTo) + "text")) , player)
                ));
            }
        }
    }

    /**
     * 返回免打扰键值对
     * @param fromOrTo 来源或去向
     * @return 免打扰键值对
     */
    default String getDnd(String fromOrTo) {
        return getModules() + "message." + fromOrTo + "dnd.";
    }

    /**
     * 获取免打扰消息类型
     * @param fromOrTo 来源或去向
     * @return 免打扰
     */
    default String getDndType(String fromOrTo) {
        return Kipooo.INSTANCE.config.getString(
            getDnd(fromOrTo) + "type"
        );
    }

}
