package org.kivo.kipooo.player;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.kivo.kipooo.Kipooo;

import java.util.Objects;

public class PlayerContainer implements PersistentDataType<PersistentDataContainer, PlayerData> {

    public static final NamespacedKey KEY = new NamespacedKey(Kipooo.INSTANCE , "player");

    public static PlayerContainer INSTANCE = new PlayerContainer();

    @Override
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    public Class<PlayerData> getComplexType() {
        return PlayerData.class;
    }

    /**
     * 转换为原始数据
     * @param complex 复合数据
     * @param context 内容
     * @return 原始数据
     */
    @Override
    public PersistentDataContainer toPrimitive(PlayerData complex, PersistentDataAdapterContext context) {
        PersistentDataContainer container = context.newPersistentDataContainer(); // 创建一个容器
        container.set(KEY , PersistentDataType.STRING , PlayerData.toStringArray(complex));
        return container;
    }

    /**
     * 转换为复合数据
     * @param primitive 原始数据
     * @param context 内容
     * @return 复合数据
     */
    @Override
    public PlayerData fromPrimitive(PersistentDataContainer primitive, PersistentDataAdapterContext context) {
        return Objects.requireNonNull(PlayerData.fromString(primitive.get(KEY, PersistentDataType.STRING)));
    }
}
