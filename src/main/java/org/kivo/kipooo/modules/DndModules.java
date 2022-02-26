package org.kivo.kipooo.modules;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.kivo.kipooo.Kipooo;

import java.util.Objects;

public interface DndModules extends CliModules{

    /**
     * ������ŵ���ʽ������Ϣ
     * @param fromOrTo ��Դ��ȥ��
     * @param player ���
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
     * ��������ż�ֵ��
     * @param fromOrTo ��Դ��ȥ��
     * @return ����ż�ֵ��
     */
    default String getDnd(String fromOrTo) {
        return getModules() + "message." + fromOrTo + "dnd.";
    }

    /**
     * ��ȡ�������Ϣ����
     * @param fromOrTo ��Դ��ȥ��
     * @return �����
     */
    default String getDndType(String fromOrTo) {
        return Kipooo.INSTANCE.config.getString(
            getDnd(fromOrTo) + "type"
        );
    }

}
