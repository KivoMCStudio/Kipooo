package org.kivo.kipooo.modules;

import org.kivo.kipooo.Kipooo;

public interface MessageModules extends EssentialsModule{

    default String getMessage() {
        return Kipooo.toColor(Kipooo.INSTANCE.config.getString("modules." + modulesName() + ".message"));
    }

    default String getUsage() {
        return Kipooo.INSTANCE.config.getString("modules." + modulesName() + ".usage");
    }

}
