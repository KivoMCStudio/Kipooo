package org.kivo.kipooo.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

import java.util.*;

public class Leader {

    private int top;

    private Statistic statistic;
    private Material material;

    public Leader(int top , Statistic statistic , Material material) {
        this.top = top;
        this.statistic = statistic;
        this.material = material;
    }

    public Leader(int top , Statistic statistic) {
        this.top = top;
        this.statistic = statistic;
        this.material = Material.AIR;
    }

    public Set<OfflinePlayer> setLeader() {
        Set<OfflinePlayer> players = new TreeSet<>((o1, o2) -> {
            if (o1.getStatistic(statistic, material) > o2.getStatistic(statistic, material)) {
                return 1;
            } else if (o1.getStatistic(statistic, material) == o2.getStatistic(statistic, material)) {
                return Objects.requireNonNull(o1.getName()).compareTo(Objects.requireNonNull(o2.getName()));
            }
            return -1;
        });
        int leaderNum = 1;
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            players.add(player);
            if (leaderNum == top) {
                break;
            }
            leaderNum ++;
        }
        return players;
    }

    public Set<OfflinePlayer> setLeaderWithNoMaterial() {
        Set<OfflinePlayer> players = new TreeSet<>((o1, o2) -> {
            if (o1.getStatistic(statistic) > o2.getStatistic(statistic)) {
                return 1;
            } else if (o1.getStatistic(statistic) == o2.getStatistic(statistic)) {
                return Objects.requireNonNull(o1.getName()).compareTo(Objects.requireNonNull(o2.getName()));
            }
            return -1;
        });
        int leaderNum = 1;
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            players.add(player);
            if (leaderNum == top) {
                break;
            }
            leaderNum ++;
        }
        return players;
    }

}
