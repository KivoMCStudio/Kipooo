package org.kivo.kipooo.player;

import org.bukkit.entity.Player;

public class PlayerData {

    private Player player;
    private boolean dnd;
    private int uid;

    public PlayerData(Player player , boolean dnd , int uid) {
        this.player = player;
        this.dnd = dnd;
        this.uid = uid;
    }

}
