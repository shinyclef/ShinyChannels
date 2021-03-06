package com.hotmail.shinyclef.shinychannels;

import com.hotmail.shinyclef.shinybase.ModChatHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Shinyclef
 * Date: 13/06/12
 * Time: 2:43 AM
 */

public class CmdExecutor implements CommandExecutor
{
    private ModChatHandler modChatHandler;

    public CmdExecutor(ModChatHandler modChatHandler)
    {
        this.modChatHandler = modChatHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        // -------------------- SB -------------------- //

        String com = command.getName().toLowerCase();

        if (com.equals("mb"))
        {
            return PermissionChat.chat(sender, args, "mb");
        }

        else if (com.equals("mbadd"))
        {
            return PermissionChat.add(sender, args, "mb");
        }

        else if (com.equals("mbremove"))
        {
            return PermissionChat.remove(sender, args, "mb");
        }

        else if (com.equals("mblist"))
        {
            return PermissionChat.list(sender, args, "mb");
        }

        // -------------------- SB -------------------- //

        else if (com.equals("sb"))
        {
            return PermissionChat.chat(sender, args, "sb");
        }

        else if (com.equals("sbadd"))
        {
            return PermissionChat.add(sender, args, "sb");
        }

        else if (com.equals("sbremove"))
        {
            return PermissionChat.remove(sender, args, "sb");
        }

        else if (com.equals("sblist"))
        {
            return PermissionChat.list(sender, args, "sb");
        }

        // -------------------- VIP -------------------- //

        else if (com.equals("vip"))
        {
            return PermissionChat.chat(sender, args, "vip");
        }

        else if (com.equals("vipadd"))
        {
            return PermissionChat.add(sender, args, "vip");
        }

        else if (com.equals("vipremove"))
        {
            return PermissionChat.remove(sender, args, "vip");
        }

        else if (com.equals("viplist"))
        {
            return PermissionChat.list(sender, args, "vip");
        }

        // -------------------- T -------------------- //


        else if (com.equals("t"))
        {
            return PermissionChat.processNormalTChat(sender, args);
        }

        else if (com.equals("mbon"))
        {
            return modChatHandler.changeModChatToggle(sender, true);
        }

        else if (com.equals("mboff"))
        {
            return modChatHandler.changeModChatToggle(sender, false);
        }

        return false;
    }
}
