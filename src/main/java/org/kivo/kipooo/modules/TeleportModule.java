package org.kivo.kipooo.modules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatEvent;
import org.kivo.kipooo.Kipooo;
import org.kivo.kipooo.config.Lang;

import java.util.HashMap;
import java.util.Map;

public class TeleportModule implements MultiModules , CliModules , SoundModules , TimerModules{

    enum Cmds {

        TP("teleport"),
        TPHERE("teleportHere"),
        ACCEPT("accept"),
        DENY("deny");

        private final String cmd;
        Cmds(String cmd) {
            this.cmd = cmd;
        }

        public String getCmd() {
            return cmd;
        }
    }

    public static Map<Player , Player> fromAndTo = new HashMap<>(); // ��һ�����յ�����ģ��ڶ����Ƿ��������
    public static Map<Player , Player> toAndFrom = new HashMap<>(); // ��һ�����յ�����ģ��ڶ����Ƿ��������

    @Override
    public String modulesName() {
        return "teleport";
    }

    /*
     * ��Ϊ���漰��Location��أ��޷�ʹ���첽����
     */
    @SuppressWarnings("deprecation")
    @EventHandler
    public void chat(PlayerChatEvent event) {
        if (isEnabled()) {
            if (event.getMessage().startsWith(getCommandKey(Cmds.TP.getCmd()))) {
                Player toPlayer = Bukkit.getPlayer(event.getMessage().replaceAll(getCommandKey(Cmds.TP.getCmd()) + " " , ""));
                if (!fromAndTo.containsValue(event.getPlayer()) && !toAndFrom.containsValue(event.getPlayer())) { // ���û�з�������
                    if (toPlayer != null) {
                        if (toPlayer.equals(event.getPlayer())) {
                            event.getPlayer().sendMessage(Lang.TPTOSELF.getMessage());
                            return;
                        }
                        send(toPlayer, TO);
                        playSound(toPlayer);
                        fromAndTo.put(toPlayer , event.getPlayer());
                        event.getPlayer().sendMessage(Lang.TPTO.getMessage());
                        Bukkit.getScheduler().runTaskLaterAsynchronously(
                                Kipooo.INSTANCE , () -> fromAndTo.remove(toPlayer), getTimer() * 20
                        );
                    }
                } else { // ����з��ʹ�������
                    event.getPlayer().sendMessage(Lang.TPTOOMANY.getMessage());
                }
            }
            if (event.getMessage().startsWith(getCommandKey(Cmds.TPHERE.getCmd()))) {
                Player toPlayer = Bukkit.getPlayer(event.getMessage().replaceAll(getCommandKey(Cmds.TPHERE.getCmd()) + " ", ""));
                if (!toAndFrom.containsValue(event.getPlayer()) && !fromAndTo.containsValue(event.getPlayer())) {
                    if (toPlayer != null) {
                        if (toPlayer.equals(event.getPlayer())) {
                            event.getPlayer().sendMessage(Lang.TPTOSELF.getMessage());
                            return;
                        }
                        send(toPlayer, FROM);
                        playSound(toPlayer);
                        toAndFrom.put(toPlayer, event.getPlayer());
                        event.getPlayer().sendMessage(Lang.TPTO.getMessage());
                        Bukkit.getScheduler().runTaskLaterAsynchronously(
                                Kipooo.INSTANCE , () -> toAndFrom.remove(toPlayer), getTimer() * 20
                        );
                    }
                } else {
                    event.getPlayer().sendMessage(Lang.TPTOOMANY.getMessage());
                }
            }
            if (event.getMessage().equalsIgnoreCase(getCommandKey(Cmds.ACCEPT.getCmd()))) {
                if (fromAndTo.containsKey(event.getPlayer())) {
                    for (Map.Entry<Player , Player> playerEntry : fromAndTo.entrySet()) {
                        if (playerEntry.getKey().equals(event.getPlayer())) {
                            playerEntry.getValue().teleport(playerEntry.getKey()); // ����
                            playerEntry.getValue().sendMessage(Lang.TPSUCCEED.getMessage());
                            playerEntry.getKey().sendMessage(Lang.TPACCEPT.getMessage());
                        }
                    }
                    fromAndTo.remove(event.getPlayer());
                } else if (toAndFrom.containsKey(event.getPlayer())){
                    for (Map.Entry<Player , Player> playerEntry : toAndFrom.entrySet()) {
                        if (playerEntry.getKey().equals(event.getPlayer())) {
                            playerEntry.getKey().teleport(playerEntry.getValue());
                            playerEntry.getKey().sendMessage(Lang.TPSUCCEED.getMessage());
                            playerEntry.getValue().sendMessage(Lang.TPACCEPT.getMessage());
                        }
                    }
                    toAndFrom.remove(event.getPlayer());
                } else {
                    event.getPlayer().sendMessage(Lang.TPNOQUEST.getMessage());
                }
            }
            if (event.getMessage().equalsIgnoreCase(getCommandKey(Cmds.DENY.getCmd()))) {
                if (fromAndTo.containsKey(event.getPlayer()) || toAndFrom.containsKey(event.getPlayer())) {
                    fromAndTo.remove(event.getPlayer());
                    toAndFrom.remove(event.getPlayer());
                } else {
                    event.getPlayer().sendMessage(Lang.TPNOQUEST.getMessage());
                }
            }
        }
    }
}
