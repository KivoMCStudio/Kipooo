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
     * 给玩家发送信息
     * @param player 玩家
     * @param fromOrTo 来源或去向
     */
    default void send(Player player , String fromOrTo) {
        switch (getType(fromOrTo)) {
            case ACTION -> this.action(player , fromOrTo);
            case TITLE -> this.title(player , fromOrTo);
            case TEXT -> this.text(player , fromOrTo);
        }
    }

    /**
     * 给玩家发送标题
     * @param player 要发送的玩家
     * @param fromOrTo 来源或去向
     */
    default void title(Player player , String fromOrTo) {
        player.sendTitle(Kipooo.replacePlayer(getTitle(fromOrTo , MAJOR), player), Kipooo.replacePlayer(getTitle(fromOrTo , SUB) , player) , getTime(FADEIN , fromOrTo) , getTime(STAY , fromOrTo) , getTime(FADEOUT , fromOrTo));
    }

    /**
     * 给玩家发送ActionBar
     * @param player 要发送的玩家
     * @param fromOrTo 来源或去向
     */
    default void action(Player player , String fromOrTo) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR , new TextComponent(Kipooo.replacePlayer(getActionBar(fromOrTo) , player)));
    }

    /**
     * 给玩家发送文本
     * @param player 要发送的玩家
     * @param fromOrTo 来源或去向
     */
    default void text(Player player , String fromOrTo) {
        player.sendMessage(Kipooo.replacePlayer(getText(fromOrTo) , player));
    }

    /**
     * 获取类型
     * @param fromOrTo 来源或去向
     * @return 文本
     */
    default String getType(String fromOrTo) {
        return Kipooo.INSTANCE.config.getString(getModules() + "message." + fromOrTo + "type");
    }

    /**
     * 获取操作栏文本
     * @param fromOrTo 来源或去向
     * @return 文本
     */
    default String getActionBar(String fromOrTo) {
        return Kipooo.INSTANCE.config.getString(getModules() + "message." + fromOrTo + "text");
    }

    /**
     * 获取文本
     * @param fromOrTo 来源或去向
     * @return 文本
     */
    default String getText(String fromOrTo) {
        return Kipooo.toColor(Kipooo.INSTANCE.config.getString(getModules() + "message." + fromOrTo + "text"));
    }

    /**
     * 获取标题文本
     * @param fromOrTO 来源或去向
     * @param subOrMajor 子标题或主标题
     * @return 标题文本
     */
    default String getTitle(String fromOrTO , String subOrMajor) {
        return subOrMajor.equals(SUB) ? Kipooo.toColor(Kipooo.INSTANCE.config.getString(getModules() + "message." + fromOrTO + SUB)) : Kipooo.toColor(Kipooo.INSTANCE.config.getString(getModules() + "message." + fromOrTO + MAJOR));
    }

    /**
     * 获取时间
     * @param time 时间
     * @param fromOrTo 来源或去向
     * @return 时间
     */
    default int getTime(String time , String fromOrTo) {
        return Kipooo.INSTANCE.config.getInt(getModules() + "message." + fromOrTo + time);
    }

}
