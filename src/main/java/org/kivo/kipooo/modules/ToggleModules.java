package org.kivo.kipooo.modules;

import org.kivo.kipooo.Kipooo;

public interface ToggleModules extends EssentialsModule {

    String ON = "on";
    String OFF = "off";

    default String getToggle() {
        return Kipooo.INSTANCE.config.getString(getModules() + "toggle");
    }

    default String getMessages(String switchStr) {
        return Kipooo.INSTANCE.config.getString(getModules() + "message." + switchStr);
    }

}
