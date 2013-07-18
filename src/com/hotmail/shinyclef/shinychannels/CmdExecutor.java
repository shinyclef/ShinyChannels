package com.hotmail.shinyclef.shinychannels;
import com.hotmail.shinyclef.shinybase.ShinyBaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
        if (command.getName().equalsIgnoreCase("vip"))
        {
            return VIPChat.vip(sender, command, label, args);
        }

        if (command.getName().equalsIgnoreCase("vipadd"))
        {
            return VIPChat.vipAdd(sender, command, label, args);
        }

        if (command.getName().equalsIgnoreCase("vipremove"))
        {
            return VIPChat.vipRemove(sender, command, label, args);
        }

        if (command.getName().equalsIgnoreCase("viplist"))
        {
            return VIPChat.vipList(sender, command, label, args);
        }

        if (command.getName().equalsIgnoreCase("sb"))
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
        {
            return StaffChat.staffList(sender, args);
        }

        return false;
    }
}
