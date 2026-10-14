package net.yeoxuhang.endit.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.yeoxuhang.endit.config.EndPlatformSpawnConfig;

public final class EndSpawnCommand {
    private EndSpawnCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("setendspawn")
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(Commands.argument("pos", BlockPosArgument.blockPos())
                        .executes(context -> {
                            BlockPos pos = BlockPosArgument.getBlockPos(context, "pos");
                            ServerLevel end = context.getSource().getServer().getLevel(Level.END);

                            if (pos.getY() <= 1 || pos.getY() > 257) {
                                context.getSource().sendFailure(Component.translatable("endit.command.ycoordinate"));
                                return 0;
                            }

                            if (end == null || !end.getWorldBorder().isWithinBounds(pos)) {
                                context.getSource().sendFailure(Component.translatable("endit.command.outofworld"));
                                return 0;
                            }

                            EndPlatformSpawnConfig.get().setPosition(pos);
                            context.getSource().sendSuccess(
                                    () -> Component.translatable("endit.command.success", pos.getX(), pos.getY(), pos.getZ()),
                                    true);
                            return 1;
                        })));
    }
}
