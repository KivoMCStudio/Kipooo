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
        consoleBroad("���ڼ��������ļ�...");
        if (!configFile.exists()) {
            consoleBroad("�����ļ�������...");
            consoleBroad("���ڴ������...");
            try {
                this.saveResource("config.yml" , true);
            } catch (Exception exception) {
                consoleBroad("&c�����ļ���ȡʧ�ܣ�������Ӳ�̿ռ䲻���û��Ȩ��.");
                exception.printStackTrace();
            }
            consoleBroad("����ɹ�.");
        }
        consoleBroad("���ڼ��ر����ļ�...");
        if (!saveFile.exists()) {
            consoleBroad("�����ļ�������...");
            consoleBroad("���ڴ������...");
            try {
                this.saveResource("save.yml" , true);
            } catch (Exception e) {
                consoleBroad("&c�����ļ���ȡʧ�ܣ�������Ӳ�̿ռ䲻���û��Ȩ��.");
                e.printStackTrace();
            }
            consoleBroad("����ɹ�.");
        }
    }

    @Override
    public void onEnable() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this , this::setPlayerWithUID , 0 , 30 * 20);
        // setRecipe(); // ���úϳɱ�
        config = YamlConfiguration.loadConfiguration(configFile);
        consoleBroad("�Ѽ��������ļ�.");
        save = YamlConfiguration.loadConfiguration(saveFile);
        consoleBroad("�Ѽ��ر����ļ�.");
        consoleBroad("���ڼ��ؼ�����...");
        loadModules(new SaveModules() , new HereModule() , new MentionModule() , new SeedModule() , new DndModule() ,
                new TeleportModule() , new DeadModule() , new SkipNightModule() , new HomeModule());
        if (isLoad()) {
            loadModules(new ScoreBoardModule());
        }
        this.registerListener();
        consoleBroad("�������.");
    }

    @Override
    public void onDisable() {
        consoleBroad("��������䷽...");
        Bukkit.clearRecipes();
        consoleBroad("�����ж��.");
    }

    /**
     * ��������Ƿ����
     * @return �������Ƿ��Ѽ���
     */
    public boolean isLoad() {
        return Bukkit.getPluginManager().getPlugin("ProtocolLib") != null;
    }

    /**
     * ����䷽
     * @param recipes �䷽
     */
    public void addRecipe(Recipe ... recipes) {
        for (Recipe recipe : recipes) {
            Bukkit.addRecipe(recipe);
        }
    }

    /**
     * �����䷽
     */
    public void setRecipe() {
        // TODO ������Ҫ��ӵĺϳɱ�
        // ShapedRecipe goldenReciped = new ShapedRecipe(PlayerContainer.KEY, new ItemStack(Material.GOLDEN_APPLE));
        // addRecipe(goldenReciped , goldenReciped , goldenReciped);
    }

    /**
     * Ϊ�������UID
     * @param player ���
     */
    public void addPlayer(Player player) {
        playerWithUID.put(player , playerWithUID.size() + 1);
    }

    /**
     * �������UID
     */
    public void setPlayerWithUID() {
        playerWithUID.clear(); // �������
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
     * ����ΪĬ�������ļ�
     */
    public void updateDefaultConfig() {
        saveResource("config.yml" , true);
        consoleBroad("���ǳɹ�.");
    }

    /**
     * ���������ļ�
     */
    public void reloadConfig() {
        if (!configFile.exists()) {
            consoleBroad("�����ļ������ڣ����ڼ���...");
            saveResource("config.yml" , true);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        consoleBroad("�����ļ�������");
    }

    /**
     * ����ģ��
     * @param modules �����ģ��
     */
    public void loadModules(EssentialsModule ... modules) {
        for (EssentialsModule module : modules) {
            if (module.isEnabled()) {
                EssentialsModule.modules.add(module);
                consoleBroad(module.modulesName() + "ģ���Ѽ���.");
            }
        }
    }

    /**
     * ע�������
     */
    public void registerListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerActionEvent() , this);
        for (EssentialsModule module : EssentialsModule.modules) {
            Bukkit.getPluginManager().registerEvents(module , this);
        }
    }

    /**
     * ���ش�����ɫ����
     * @param text ԭʼ�ı�
     * @return ����֮����ı�
     */
    public static String toColor(String text) {
        return ChatColor.translateAlternateColorCodes('&' , text);
    }

    /**
     * �Ժ�̨��ݷ�����Ϣ
     * @param text �ı�
     */
    public static void consoleBroad(String text) {
        Bukkit.getConsoleSender().sendMessage(Kipooo.toColor("&dKipooo &f" + text));
    }

    /**
     * ��������ڹ㲥��Ϣ
     * @param text �ı�
     */
    public static void broadCast(String text) {
        Bukkit.broadcastMessage(Kipooo.toColor(text));
    }

    /**
     * ת��Ϊ�����ļ��е���д
     * @param text �����ı�
     * @return ת���ı�
     */
    public static String toWorld(String text) {
        return INSTANCE.config.getConfigurationSection("options.world-alias").getKeys(false).contains(text) ? Kipooo.toColor(INSTANCE.config.getString("options.world-alias." + text)) : text;
    }

    /**
     * �滻�����ļ�����
     * @param text ԭʼ�ı�
     * @param player �滻���
     * @return �����ı�
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
     * ��ȡ��ҵ�UID
     * @param player ���
     * @return ��ҵ�UID
     */
    public static int getUid(Player player) {
        return playerWithUID.get(player);
    }

}
