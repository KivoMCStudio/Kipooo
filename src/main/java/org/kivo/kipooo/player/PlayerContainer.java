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
     * ת��Ϊԭʼ����
     * @param complex ��������
     * @param context ����
     * @return ԭʼ����
     */
    @Override
    public PersistentDataContainer toPrimitive(PlayerData complex, PersistentDataAdapterContext context) {
        PersistentDataContainer container = context.newPersistentDataContainer(); // ����һ������
        container.set(KEY , PersistentDataType.STRING , PlayerData.toStringArray(complex));
        return container;
    }

    /**
     * ת��Ϊ��������
     * @param primitive ԭʼ����
     * @param context ����
     * @return ��������
     */
    @Override
    public PlayerData fromPrimitive(PersistentDataContainer primitive, PersistentDataAdapterContext context) {
        return Objects.requireNonNull(PlayerData.fromString(primitive.get(KEY, PersistentDataType.STRING)));
    }
}
