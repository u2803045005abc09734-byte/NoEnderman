package net.yeoxuhang.endit.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.yeoxuhang.endit.EnditFabric;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class EndPlatformSpawnConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir().resolve("end_platform_spawn_config.json");
    private static EndPlatformSpawnConfig instance;

    public int x = 100;
    public int y = 50;
    public int z = 0;

    private EndPlatformSpawnConfig() {
    }

    public static synchronized EndPlatformSpawnConfig get() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }

    private static EndPlatformSpawnConfig load() {
        if (Files.isRegularFile(CONFIG_PATH)) {
            try (var reader = Files.newBufferedReader(CONFIG_PATH)) {
                EndPlatformSpawnConfig loaded = GSON.fromJson(reader, EndPlatformSpawnConfig.class);
                if (loaded != null) {
                    return loaded;
                }
            } catch (Exception e) {
                EnditFabric.LOGGER.error("Could not read Endit config; using default coordinates.", e);
            }
        }

        EndPlatformSpawnConfig defaults = new EndPlatformSpawnConfig();
        defaults.save();
        return defaults;
    }

    public BlockPos blockPos() {
        return new BlockPos(x, y, z);
    }

    public synchronized void setPosition(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        save();
    }

    public synchronized void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (var writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException e) {
            EnditFabric.LOGGER.error("Could not save Endit config to {}", CONFIG_PATH, e);
        }
    }
}
