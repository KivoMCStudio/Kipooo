package org.kivo.kipooo;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Kipooo extends JavaPlugin {

    public static Kipooo INSTANCE;

    {
        INSTANCE = this;
    }

    public File configFile = new File(this.getDataFolder() , "config.yml");
    public File saveFile = new File(this.getDataFolder() , "save.yml");

    public FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    public FileConfiguration save = YamlConfiguration.loadConfiguration(saveFile);

    @Override
    public void onLoad() {
        consoleBroad("正在加载配置文件...");
        if (!configFile.exists()) {
            consoleBroad("配置文件不存在...");
            consoleBroad("正在打包输入...");
            try {
                this.saveResource("config.yml" , true);
            } catch (Exception exception) {
                consoleBroad("&c配置文件读取失败，可能是硬盘空间不足或没有权限.");
                exception.printStackTrace();
            }
            consoleBroad("载入成功.");
        }
        consoleBroad("正在加载保存文件...");
        if (!saveFile.exists()) {
            consoleBroad("保存文件不存在...");
            consoleBroad("正在打包输入...");
            try {
                this.saveResource("save.yml" , true);
            } catch (Exception e) {
                consoleBroad("&c保存文件读取失败，可能是硬盘空间不足或没有权限.");
                e.printStackTrace();
            }
            consoleBroad("载入成功.");
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    /**
     * 返回带有颜色的字
     * @param text 原始文本
     * @return 处理之后的文本
     */
    public static String toColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * 以后台身份发送信息
     * @param text 文本
     */
    public static void consoleBroad(String text) {
        Bukkit.getConsoleSender().sendMessage(Kipooo.toColor("&dKipooo &f" + text));
    }

    /**
     * 向服务器内广播信息
     * @param text 文本
     */
    public static void broadCast(String text) {
        Bukkit.broadcastMessage(Kipooo.toColor(text));
    }

}