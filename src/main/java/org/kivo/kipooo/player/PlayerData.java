package org.kivo.kipooo.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.kivo.kipooo.Kipooo;

public class PlayerData {

    private Player player;
    private boolean dnd;
    private boolean score;
    private int uid;
    private Location homeLoc;
    private Location deadLoc;

    public PlayerData(Player player) {
        this.player = player;
        this.dnd = false;
        this.score = true;
        this.uid = Kipooo.getUid(player);
        this.homeLoc = player.getWorld().getSpawnLocation();
        this.deadLoc = player.getWorld().getSpawnLocation();
    }

    public Location getDeadLoc() {
        return deadLoc;
    }

    public void setDeadLoc(Location deadLoc) {
        this.deadLoc = deadLoc;
    }

    public Location getHomeLoc() {
        return homeLoc;
    }

    public boolean isScore() {
        return score;
    }

    public void setScore(boolean score) {
        this.score = score;
    }

    public void setHomeLoc(Location homeLoc) {
        this.homeLoc = homeLoc;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isDnd() {
        return dnd;
    }

    public void setDnd(boolean dnd) {
        this.dnd = dnd;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * 将此类对象转换为字符串
     * @param data 此类对象
     * @return 字符串
     */
    public static String toString(PlayerData data) {
        StringBuffer toStrBuffer = new StringBuffer();
        toStrBuffer.append(data.getPlayer().getName()).append(",")
                .append(data.isDnd()).append(",")
                .append(data.getUid()).append(",")
                .append(data.isScore()).append(",[")
                .append(data.getHomeLoc().getWorld().getName()).append("|").append(data.getHomeLoc().getX()).append("|").append(data.getHomeLoc().getY()).append("|").append(data.getHomeLoc().getZ()).append("].[")
                .append(data.getDeadLoc().getWorld().getName()).append("|").append(data.getDeadLoc().getX()).append("|").append(data.getDeadLoc().getY()).append("|").append(data.getDeadLoc().getZ()).append("]");
        return String.valueOf(toStrBuffer);
    }

    /**
     * 通过字符串获取本类对象
     * @param obj 字符串
     * @return 本类对象
     */
    public static PlayerData fromString(String obj) {
        String[] dataArray = obj.split(","); // 拆分字符串
        /*
        0 -> 玩家ID
        1 -> 玩家免打扰模式
        2 -> 玩家UID
        3 -> 玩家是否开启记分板
        4 -> 玩家家坐标数组
        5 -> 玩家死亡坐标数组
         */
        String[] homeArray = dataArray[4].replaceAll("[\\[\\]]" , "").split("|");
        /*
        0 -> 坐标世界
        1 -> 坐标X
        2 -> 坐标Y
        3 -> 坐标Z
         */
        String[] deadArray = dataArray[5].replaceAll("[\\[\\]]" , "").split("|");
        PlayerData data = new PlayerData(Bukkit.getPlayer(dataArray[0]));
        data.setDnd(Boolean.parseBoolean(dataArray[1]));
        data.setUid(Integer.parseInt(dataArray[2]));
        data.setScore(Boolean.parseBoolean(dataArray[3]));
        data.setHomeLoc(
                new Location(Bukkit.getWorld(homeArray[0]) , Double.parseDouble(homeArray[1]) , Double.parseDouble(homeArray[2]) , Double.parseDouble(homeArray[3]))
        );
        data.setDeadLoc(
                new Location(Bukkit.getWorld(deadArray[0]) , Double.parseDouble(deadArray[1]) , Double.parseDouble(deadArray[2]) , Double.parseDouble(deadArray[3]))
        );
        return data;
    }

}
