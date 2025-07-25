package com.wdcftgg.spacetime.command;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.gui.book.BookRegistry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CommandSpaceTime extends CommandBase {
    private final List<String> aliases;

    public CommandSpaceTime() {
        this.aliases = new ArrayList<>();
        this.aliases.add("st");
    }

    @Nonnull
    @Override
    public String getName() {
        return SpaceTime.MODID;
    }

    @Nonnull
    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender icommandsender) {
        return "/spacetime <action>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] astring, int i) {
        return i == 1;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
//        if (args.length == 1) {
//            return getListOfStringsMatchingLastWord(args, "view", "clear", "add", "help", "remove");
//        } else if (args.length == 2 && ("view".equals(args[0]) || "clear".equals(args[0]) || "add".equals(args[0]) || "remove".equals(args[0]))) {
//            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
//        } else if (args.length == 3 && ("add".equals(args[0]) || "remove".equals(args[0]))) {
//            return getListOfStringsMatchingLastWord(args, "amulet", "ring", "belt", "trinket", "head", "body", "charm");
//        }
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "book");
        }
        if (args.length == 2 && "book".equals(args[1])) {
            return getListOfStringsMatchingLastWord(args, "reload");
        }

//        else if (args.length == 2 && ("view".equals(args[0]) || "clear".equals(args[0]) || "add".equals(args[0]) || "remove".equals(args[0]))) {
//            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
//        } else if (args.length == 3 && ("add".equals(args[0]) || "remove".equals(args[0]))) {
//            return getListOfStringsMatchingLastWord(args, "amulet", "ring", "belt", "trinket", "head", "body", "charm");
//        }
        return Collections.emptyList();
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(new TextComponentTranslation("command.baubles.alias_help"));
            sender.sendMessage(new TextComponentTranslation("command.baubles.view_help"));
            sender.sendMessage(new TextComponentTranslation("command.baubles.clear_help"));
            sender.sendMessage(new TextComponentTranslation("command.baubles.add_help"));
            sender.sendMessage(new TextComponentTranslation("command.baubles.remove_help"));
        } else {
//            EntityPlayerMP entityplayermp = getPlayer(server, sender, args[1]);

            if (args[0].equalsIgnoreCase("book")) {
                if (args[1].equalsIgnoreCase("reload")) {
                    BookRegistry.reloadBook();
                }
            }
        }
    }
}
