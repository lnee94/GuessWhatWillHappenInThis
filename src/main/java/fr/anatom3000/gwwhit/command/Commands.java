package fr.anatom3000.gwwhit.command;

import fr.anatom3000.gwwhit.GWWHIT;
import fr.anatom3000.gwwhit.Python;
import fr.anatom3000.gwwhit.block.entity.InfectedMassBlockEntity;
import fr.anatom3000.gwwhit.block.entity.RandomisingBlockEntity;
import fr.anatom3000.gwwhit.config.ConfigManager;
import fr.anatom3000.gwwhit.config.ModConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class Commands {
    private Commands() {
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(CommandManager.literal(GWWHIT.MOD_ID)
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("config")
                        .then(CommandManager.literal("sync")
                                .executes(context -> {
                                    for (ServerPlayerEntity player : context.getSource().getMinecraftServer().getPlayerManager().getPlayerList()) {
                                        ServerPlayNetworking.send(player, GWWHIT.CONFIG_SYNC_ID, ConfigManager.toPacketByteBuf());
                                    }
                                    return 1;
                                })
                                .then(CommandManager.argument("targets", EntityArgumentType.players())
                                        .executes(context -> {
                                            for (ServerPlayerEntity player : EntityArgumentType.getPlayers(context, "targets")) {
                                                ServerPlayNetworking.send(player, GWWHIT.CONFIG_SYNC_ID, ConfigManager.toPacketByteBuf());
                                            }
                                            return 1;
                                        })
                                )
                        )
                        .then(CommandManager.literal("load")
                                .executes(context -> {
                                    ConfigHolder<ModConfig> configHolder = ConfigManager.getHolder();
                                    configHolder.load();
                                    return 1;
                                })
                        )
                        .then(CommandManager.literal("reload")
                                .executes(context -> {
                                    ConfigManager.load();

                                    for (ServerPlayerEntity player : context.getSource().getMinecraftServer().getPlayerManager().getPlayerList()) {
                                        ServerPlayNetworking.send(player, GWWHIT.CONFIG_SYNC_ID, ConfigManager.toPacketByteBuf());
                                    }
                                    return 1;
                                })
                        )
                )
                .then(CommandManager.literal("debug")
                        .then(CommandManager.literal("remove_destructive_blocks")
                                .executes(context -> {
                                    int ticks = context.getSource().getMinecraftServer().getTicks();
                                    InfectedMassBlockEntity.removeTick = ticks;
                                    RandomisingBlockEntity.removeTick = ticks;
                                    return 1;
                                })
                        )
                )
                .then(CommandManager.literal("python")
                        .requires(source -> ConfigManager.getLoadedConfig().content.scripting)
                        .then(CommandManager.literal("execute")
                                .then(CommandManager.argument("script", new ScriptArgumentType())
                                        .executes(context -> {
                                            try {
                                                Python.execute(ScriptArgumentType.getScript(context, "script"));
                                                return 1;
                                            } catch (RuntimeException e) {
                                                context.getSource().sendError(new LiteralText(createErrorMessage(e, 0)));
                                                return 0;
                                            }
                                        })
                                )
                        )
                )
                .then(CommandManager.literal("fillinv")
                        .then(CommandManager.argument("item", ItemStackArgumentType.itemStack())
                                .executes(context -> {
                                    ItemStackArgument item = ItemStackArgumentType.getItemStackArgument(context, "item");
                                    ItemStack stack = item.createStack(item.getItem().getMaxCount(), false);
                                    ServerPlayerEntity player = context.getSource().getPlayer();
                                    if (player == null) return 0;

                                    for (int i = 0; i < player.getInventory().size(); i++) {
                                        player.getInventory().setStack(i, stack.copy());
                                    }

                                    for (int i = 0; i < player.getEnderChestInventory().size(); i++) {
                                        player.getEnderChestInventory().setStack(i, stack.copy());
                                    }

                                    return 1;
                                })
                        )
                )
        ));
    }

    private static String createErrorMessage(Throwable e, int indentation) {
        StringBuilder sb = new StringBuilder();
        sb.append("  ".repeat(indentation));
        sb.append(e.getMessage());
        sb.append('\n');
        for (Throwable t : e.getSuppressed()) sb.append(createErrorMessage(t, indentation + 1));
        return sb.toString();
    }
}