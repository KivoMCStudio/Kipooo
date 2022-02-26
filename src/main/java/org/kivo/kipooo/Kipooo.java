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
        config = YamlConfiguration.loadConfiguration(configFile);
        consoleBroad("�Ѽ��������ļ�.");
        save = YamlConfiguration.loadConfiguration(saveFile);
        consoleBroad("�Ѽ��ر����ļ�.");
        consoleBroad("���ڼ��ؼ�����...");
        loadModules(new HereModule() , new MentionModule() , new SeedModule());
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
