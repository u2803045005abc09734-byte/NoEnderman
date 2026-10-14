package net.yeoxuhang.endit;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.yeoxuhang.endit.command.EndSpawnCommand;
import net.yeoxuhang.endit.config.EndPlatformSpawnConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EnditFabric implements ModInitializer {
    public static final String MOD_ID = "endit";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        EndPlatformSpawnConfig.get();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                EndSpawnCommand.register(dispatcher));
        LOGGER.info("Endit for Minecraft 26.3 initialized.");
    }
}
