package com.hotmail.shinyclef.shinychannels;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Author: Shinyclef
 * Date: 13/06/12
 * Time: 2:43 AM
 */

public class CmdExecutor implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
/*        if (command.getName().equalsIgnoreCase("sb"))
        {
            return StaffChat.staffChat(sender, args);
        }

        if (command.getName().equalsIgnoreCase("sbadd"))
        {
            return StaffChat.staffChatAdd(sender, args);
        }

        if (command.getName().equalsIgnoreCase("sbremove"))
        {
            return StaffChat.staffChatRemove(sender, args);
        }

        if (command.getName().equalsIgnoreCase("sblist"))
        {                                                 8
            return StaffChat.staffList(sender, args);
        }*/

        // MB is in event listener

        // -------------------- SB -------------------- //

        if (command.getName().equalsIgnoreCase("sb"))
        {
            return PermissionChat.chat(sender, args, "sb");
        }

        if (command.getName().equalsIgnoreCase("sbadd"))
        {
            return PermissionChat.add(sender, args, "sb");
        }

        if (command.getName().equalsIgnoreCase("sbremove"))
        {
            return PermissionChat.remove(sender, args, "sb");
        }

        if (command.getName().equalsIgnoreCase("sblist"))
        {
            return PermissionChat.list(sender, args, "sb");
        }

        // -------------------- VIP -------------------- //

        if (command.getName().equalsIgnoreCase("vip"))
        {
            return PermissionChat.chat(sender, args, "vip");
        }

        if (command.getName().equalsIgnoreCase("vipadd"))
        {
            return PermissionChat.add(sender, args, "vip");
        }

        if (command.getName().equalsIgnoreCase("vipremove"))
        {
            return PermissionChat.remove(sender, args, "vip");
        }

        if (command.getName().equalsIgnoreCase("viplist"))
        {
            return PermissionChat.list(sender, args, "vip");
        }

        return false;
    }
}
