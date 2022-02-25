package org.kivo.kipooo;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.kivo.kipooo.events.PlayerActionEvent;
import org.kivo.kipooo.modules.EssentialsModule;

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
        consoleBroad("���ڼ��ؼ�����...");
        this.registerListener();
        consoleBroad("�������.");
    }

    @Override
    public void onDisable() {
        consoleBroad("��������䷽...");
        Bukkit.clearRecipes();
        consoleBroad("�����ж��.");
    }

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
        return ChatColor.translateAlternateColorCodes('&', text);
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
        return INSTANCE.config.getStringList("options.world-alias").contains(text) ? Kipooo.toColor(INSTANCE.config.getString("options.world-alias." + text)) : text;
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
