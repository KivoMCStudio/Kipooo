package org.kivo.kipooo.modules;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.kivo.kipooo.Kipooo;
import org.kivo.kipooo.player.PlayerContainer;
import org.kivo.kipooo.player.PlayerData;
import org.kivo.kipooo.utils.Leader;

import java.util.Objects;

public class ScoreBoardModule implements TimerModules , ToggleModules{

    private int top = 1;
    public static BukkitTask sbTask;

    @Override
    public String modulesName() {
        return "score";
    }

    @SuppressWarnings("all")
    @EventHandler
    public void chat(PlayerChatEvent event) {
        if (isEnabled()) {
            if (getToggle().equalsIgnoreCase(event.getMessage())) {
                PersistentDataContainer container = event.getPlayer().getPersistentDataContainer();
                PlayerData data = container.get(PlayerContainer.KEY , PlayerContainer.INSTANCE);
                if (data != null) {
                    boolean flag = data.isScore();
                    data.setScore(!flag);
                    container.set(PlayerContainer.KEY , PlayerContainer.INSTANCE , data);
                    if (data.isScore()) {
                        // 记分板开启
                        openScoreBoard(event.getPlayer());
                    }
                    if (!data.isScore()) {
                        // 记分板关闭
                        closeScoreBoard(event.getPlayer());
                    }
                }
            }
        }
    }

    @EventHandler
    public void actionJoin(PlayerJoinEvent event) {
        PersistentDataContainer container = event.getPlayer().getPersistentDataContainer();
        PlayerData data = container.get(PlayerContainer.KEY , PlayerContainer.INSTANCE);
        if (data != null) {
            if (data.isScore()) { // 启用记分板
                openScoreBoard(event.getPlayer());
            }
        } else {
            data = new PlayerData(event.getPlayer());
            data.setScore(true);
            container.set(PlayerContainer.KEY , PlayerContainer.INSTANCE , data);
        }
    }

    /**
     * 启用记分板
     */
    public void openScoreBoard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager != null) {
            Scoreboard scoreboard = manager.getNewScoreboard();
            Objective blockObj = scoreboard.registerNewObjective(
                    "block" , "dummy" , Kipooo.toColor("&6挖掘榜")
            );
            sbTask = Bukkit.getScheduler().runTaskTimer(Kipooo.INSTANCE , () -> updateBoard(blockObj , player) , 0 , Kipooo.INSTANCE.config.getInt(getModules() + ".board.update-time"));
        }
    }

    /**
     * 关闭记分板
     */
    public void closeScoreBoard(Player player) {
        if (sbTask != null) {
            sbTask.cancel();
        }
        player.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard());
    }

    private void updateBoard(Objective objective , Player playerSB) {
        Leader leader = new Leader(15 , Statistic.MINE_BLOCK , Material.STONE);
        for (OfflinePlayer player : leader.setLeader()) {
            objective.getScore(Kipooo.toColor("&7" + top + ". &f" + player.getName())).setScore(player.getStatistic(Statistic.MINE_BLOCK , Material.STONE));
            top ++;
        }
        playerSB.setScoreboard(Objects.requireNonNull(objective.getScoreboard()));
    }
}
