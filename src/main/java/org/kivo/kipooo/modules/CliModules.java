package org.kivo.kipooo.modules;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.kivo.kipooo.Kipooo;

public interface CliModules extends EssentialsModule{

    String FROM = "From.";
    String TO = "To.";

    String SUB = "subTitle.";
    String MAJOR = "title.";

    String FADEIN = "fadeIn.";
    String STAY = "stay.";
    String FADEOUT = "fadeOut.";

    String ACTION = "actionbar";
    String TITLE = "title";
    String TEXT = "text";

    /**
     * ����ҷ�����Ϣ
     * @param player ���
     * @param fromOrTo ��Դ��ȥ��
     */
    default void send(Player player , String fromOrTo) {
        switch (getType(fromOrTo)) {
            case ACTION -> this.action(player , fromOrTo);
            case TITLE -> this.title(player , fromOrTo);
            case TEXT -> this.text(player , fromOrTo);
        }
    }

    /**
     * ����ҷ��ͱ���
     * @param player Ҫ���͵����
     * @param fromOrTo ��Դ��ȥ��
     */
    default void title(Player player , String fromOrTo) {
        player.sendTitle(Kipooo.replacePlayer(getTitle(fromOrTo , MAJOR), player), Kipooo.replacePlayer(getTitle(fromOrTo , SUB) , player) , getTime(FADEIN , fromOrTo) , getTime(STAY , fromOrTo) , getTime(FADEOUT , fromOrTo));
    }

    /**
     * ����ҷ���ActionBar
     * @param player Ҫ���͵����
     * @param fromOrTo ��Դ��ȥ��
     */
    default void action(Player player , String fromOrTo) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR , new TextComponent(Kipooo.replacePlayer(getActionBar(fromOrTo) , player)));
    }

    /**
     * ����ҷ����ı�
     * @param player Ҫ���͵����
     * @param fromOrTo ��Դ��ȥ��
     */
    default void text(Player player , String fromOrTo) {
        player.sendMessage(Kipooo.replacePlayer(getText(fromOrTo) , player));
    }

    /**
     * ��ȡ����
     * @param fromOrTo ��Դ��ȥ��
     * @return �ı�
     */
    default String getType(String fromOrTo) {
        return Kipooo.INSTANCE.config.getString(getModules() + "message." + fromOrTo + "type");
    }

    /**
     * ��ȡ�������ı�
     * @param fromOrTo ��Դ��ȥ��
     * @return �ı�
     */
    default String getActionBar(String fromOrTo) {
        return Kipooo.INSTANCE.config.getString(getModules() + "message." + fromOrTo + "text");
    }

    /**
     * ��ȡ�ı�
     * @param fromOrTo ��Դ��ȥ��
     * @return �ı�
     */
    default String getText(String fromOrTo) {
        return Kipooo.toColor(Kipooo.INSTANCE.config.getString(getModules() + "message." + fromOrTo + "text"));
    }

    /**
     * ��ȡ�����ı�
     * @param fromOrTO ��Դ��ȥ��
     * @param subOrMajor �ӱ����������
     * @return �����ı�
     */
    default String getTitle(String fromOrTO , String subOrMajor) {
        return subOrMajor.equals(SUB) ? Kipooo.toColor(Kipooo.INSTANCE.config.getString(getModules() + "message." + fromOrTO + SUB)) : Kipooo.toColor(Kipooo.INSTANCE.config.getString(getModules() + "message." + fromOrTO + MAJOR));
    }

    /**
     * ��ȡʱ��
     * @param time ʱ��
     * @param fromOrTo ��Դ��ȥ��
     * @return ʱ��
     */
    default int getTime(String time , String fromOrTo) {
        return Kipooo.INSTANCE.config.getInt(getModules() + "message." + fromOrTo + time);
    }

}
