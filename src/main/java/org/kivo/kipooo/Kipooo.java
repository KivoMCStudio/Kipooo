package org.kivo.kipooo;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.kivo.kipooo.events.PlayerActionEvent;
import org.kivo.kipooo.modules.*;
import org.kivo.kipooo.player.PlayerContainer;

import java.io.File;
import java.util.*;

public class Kipooo extends JavaPlugin {

    public static Kipooo INSTANCE;

    {
        INSTANCE = this;
    }

    public static Map<OfflinePlayer, Integer> playerWithUID = new HashMap<OfflinePlayer, Integer>();

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
        Bukkit.getScheduler().runTaskTimerAsynchronously(this , this::setPlayerWithUID , 0 , 30 * 20);
        // setRecipe(); // 设置合成表
        config = YamlConfiguration.loadConfiguration(configFile);
        consoleBroad("已加载配置文件.");
        save = YamlConfiguration.loadConfiguration(saveFile);
        consoleBroad("已加载保存文件.");
        consoleBroad("正在加载监听器...");
        loadModules(new SaveModules() , new HereModule() , new MentionModule() , new SeedModule() , new DndModule() ,
                new TeleportModule() , new DeadModule() , new SkipNightModule() , new HomeModule());
        if (isLoad()) {
            loadModules(new ScoreBoardModule());
        }
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
     * 检查依赖是否加载
     * @return 软依赖是否已加载
     */
    public boolean isLoad() {
        return Bukkit.getPluginManager().getPlugin("ProtocolLib") != null;
    }

    /**
     * 添加配方
     * @param recipes 配方
     */
    public void addRecipe(Recipe ... recipes) {
        for (Recipe recipe : recipes) {
            Bukkit.addRecipe(recipe);
        }
    }

    /**
     * 设置配方
     */
    public void setRecipe() {
        // TODO 胡吉需要添加的合成表
        // ShapedRecipe goldenReciped = new ShapedRecipe(PlayerContainer.KEY, new ItemStack(Material.GOLDEN_APPLE));
        // addRecipe(goldenReciped , goldenReciped , goldenReciped);
    }

    /**
     * 为玩家增加UID
     * @param player 玩家
     */
    public void addPlayer(Player player) {
        playerWithUID.put(player , playerWithUID.size() + 1);
    }

    /**
     * 排序玩家UID
     */
    public void setPlayerWithUID() {
        playerWithUID.clear(); // 清空数据
        List<OfflinePlayer> playerWithUIDCollection = Arrays.asList(Bukkit.getOfflinePlayers());
        playerWithUIDCollection.sort(new Comparator<OfflinePlayer>() {
            @Override
            public int compare(OfflinePlayer o1, OfflinePlayer o2) {
                long o1Date = o1.getFirstPlayed();
                long o2Date = o2.getFirstPlayed();
                return o1Date < o2Date ? -1 : 1;
            }
        });
        for (OfflinePlayer player : playerWithUIDCollection) {
            playerWithUID.put(player , playerWithUIDCollection.indexOf(player) + 1);
        }
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
        if (player.getPersistentDataContainer().get(PlayerContainer.KEY , PlayerContainer.INSTANCE) != null) {
            Location homeLoc = player.getPersistentDataContainer().get(PlayerContainer.KEY, PlayerContainer.INSTANCE).getHomeLoc();
            Location deadLoc = player.getPersistentDataContainer().get(PlayerContainer.KEY, PlayerContainer.INSTANCE).getDeadLoc();
            text = text.replaceAll(
                    "%player_Home_X%" , String.valueOf(homeLoc.getX())
            ).replaceAll(
                    "%player_Home_Y%" , String.valueOf(homeLoc.getY())
            ).replaceAll(
                    "%player_Home_Z%" , String.valueOf(homeLoc.getZ())
            ).replaceAll(
                    "%player_Home_World%" , toWorld(homeLoc.getWorld().getName())
            ).replaceAll(
                    "%player_Dead_X%" , String.valueOf(deadLoc.getBlockX())
            ).replaceAll(
                    "%player_Dead_Y%" , String.valueOf(deadLoc.getBlockY())
            ).replaceAll(
                    "%player_Dead_Z%" , String.valueOf(deadLoc.getBlockZ())
            ).replaceAll(
                    "%player_Dead_World%" , toWorld(deadLoc.getWorld().getName())
            );
        }
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
        ).replaceAll(
                "%tick%" , String.valueOf(player.getLocation().getWorld().getTime())
        );
    }

    /**
     * 获取玩家的UID
     * @param player 玩家
     * @return 玩家的UID
     */
    public static int getUid(Player player) {
        return playerWithUID.get(player);
    }

}
