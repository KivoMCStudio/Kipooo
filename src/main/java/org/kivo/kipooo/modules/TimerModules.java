package org.kivo.kipooo.modules;

import org.kivo.kipooo.Kipooo;

public interface TimerModules extends EssentialsModule{

    default long getTimer() {
        return Kipooo.INSTANCE.config.getLong(getModules() + "time");
    }

}
