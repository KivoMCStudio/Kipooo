package org.kivo.kipooo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;
import org.kivo.kipooo.events.PlayerActionEvent;
import org.kivo.kipooo.modules.EssentialsModule;
import org.kivo.kipooo.modules.HereModule;
import org.kivo.kipooo.modules.MentionModule;
import org.kivo.kipooo.modules.SeedModule;
import org.kivo.kipooo.player.PlayerData;

import java.io.File;

public class Kipooo extends JavaPlugin {

    public static Kipooo INSTANCE;

    {
        INSTANCE = this;
    }

    public static NamespacedKey key = new NamespacedKey(INSTANCE , "playerData");

    public File configFile = new File(this.getDataFolder() , "config.yml");
    public File saveFile = new File(this.getDataFolder() , "save.yml");

    public FileConfiguration config;
    public FileConfiguration save;

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
        config = YamlConfiguration.loadConfiguration(configFile);
        consoleBroad("已加载配置文件.");
        save = YamlConfiguration.loadConfiguration(saveFile);
        consoleBroad("已加载保存文件.");
        consoleBroad("正在加载监听器...");
        loadModules(new HereModule() , new MentionModule() , new SeedModule());
        this.registerListener();
        consoleBroad("加载完毕.");
    }

    @Override
    public void onDisable() {
        consoleBroad("正在清空配方...");
        Bukkit.clearRecipes();
        consoleBroad("插件已卸载.");
    }

    /**
     * 重置为默认配置文件
     */
    public void updateDefaultConfig() {
        saveResource("config.yml" , true);
        consoleBroad("覆盖成功.");
    }

    /**
     * 重载配置文件
     */
    public void reloadConfig() {
        if (!configFile.exists()) {
            consoleBroad("配置文件不存在，正在加载...");
            saveResource("config.yml" , true);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        consoleBroad("配置文件已重载");
    }

    /**
     * 加载模块
     * @param modules 传入的模块
     */
    public void loadModules(EssentialsModule ... modules) {
        for (EssentialsModule module : modules) {
            if (module.isEnabled()) {
                EssentialsModule.modules.add(module);
                consoleBroad(module.modulesName() + "模块已加载.");
            }
        }
    }

    /**
     * 注册监听器
     */
    public void registerListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerActionEvent() , this);
        for (EssentialsModule module : EssentialsModule.modules) {
            Bukkit.getPluginManager().registerEvents(module , this);
        }
    }

    /**
     * 返回带有颜色的字
     * @param text 原始文本
     * @return 处理之后的文本
     */
    public static String toColor(String text) {
        return ChatColor.translateAlternateColorCodes('&' , text);
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

    /**
     * 转换为配置文件中的缩写
     * @param text 传入文本
     * @return 转出文本
     */
    public static String toWorld(String text) {
        return INSTANCE.config.getConfigurationSection("options.world-alias").getKeys(false).contains(text) ? Kipooo.toColor(INSTANCE.config.getString("options.world-alias." + text)) : text;
    }

    /**
     * 替换配置文件参数
     * @param text 原始文本
     * @param player 替换玩家
     * @return 处理文本
     */
    public static String replacePlayer(String text , Player player) {
        return text.replaceAll(
                "%player%" , player.getName()
        ).replaceAll(
                "%player_X%" , String.valueOf(player.getLocation().getBlockX())
        ).replaceAll(
                "%player_Y%" , String.valueOf(player.getLocation().getBlockY())
        ).replaceAll(
                "%player_Z%" , String.valueOf(player.getLocation().getBlockZ())
        ).replaceAll(
                "%player_World%" , toWorld(player.getWorld().getName())
        ).replaceAll(
                "%seed%" , String.valueOf(player.getWorld().getSeed())
        );
    }

}
