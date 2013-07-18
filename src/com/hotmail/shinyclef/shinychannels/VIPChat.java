package com.hotmail.shinyclef.shinychannels;

import com.hotmail.shinyclef.shinybase.ShinyBaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Shinyclef
 * Date: 14/07/13
 * Time: 3:36 AM
 */

public class VIPChat
{
    private static ShinyBaseAPI shinyBaseAPI;
    private static ShinyChannels plugin;

    private static List<String> VIPList;


    public static void initializeVipChat(ShinyChannels thePlugin, ShinyBaseAPI theShinyBaseAPI)
    {
        shinyBaseAPI = theShinyBaseAPI;
        plugin = thePlugin;

        VIPList = plugin.getConfig().getStringList("Players");
        if (VIPList == null)
        {
            VIPList = new ArrayList<String>(); //if the copy is null, initialize a new arraylist
        }
    }

    public static boolean vip(CommandSender sender, Command command, String label, String[] args)
    {
        //make sure there's at least one arg
        if (args.length < 1)
        {
            return false;
        }

        //if the player is not in the list
        if (!VIPList.contains(sender.getName().toLowerCase()))
        {
            sender.sendMessage(ChatColor.RED + "Sorry, you don't have permission to do that.");
            return true;
        }

        String sentence = ShinyChannels.makeSentence(args);

        //remove the last white space from sentence
        sentence = sentence.substring(0, sentence.length() - 1);

        //send the sentence to every online player in recipients list
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (VIPList.contains(player.getName().toLowerCase()))
            {
                player.sendMessage(ChatColor.RED + "[VIP] " + sender.getName() + ": "
                        + ChatColor.YELLOW + sentence);
            }
        }

        return true;
    }

    public static boolean vipAdd(CommandSender sender, Command command, String label, String[] args)
    {
        //If there are not enough arguments
        if (args.length < 1)
        {
            return false;
        }

        String playername = args[0].toLowerCase();

        //if there is no such player on server
        if (!shinyBaseAPI.isExistingPlayer(playername))
        {
            sender.sendMessage(ChatColor.RED + "That player does not exist.");
            return true;
        }

        //if the list copy is null
        if (VIPList == null)
        {
            VIPList = new ArrayList<String>(); //if the copy is null, initialize a new arraylist
        }

        //if the player is already in the list
        if (VIPList.contains(args[0].toLowerCase()))
        {
            sender.sendMessage(ChatColor.RED + "That player is already part of the vip channel.");
            return true;
        }

        //notify player who is being added if they are online
        if (Bukkit.getServer().getOfflinePlayer(args[0]).isOnline())
        {
            Bukkit.getServer().getPlayer(args[0]).sendMessage(ChatColor.AQUA
                    + "You have been added to the vip channel. Use /vip to talk (e.g. /vip Hello everyone!)");
        }

        //announce add to every player in recipients list
        for(String recipient : VIPList)
        {
            Player player = Bukkit.getServer().getPlayer(recipient);
            if (player != null && player.isOnline())
            {
                player.sendMessage(ChatColor.GREEN + args[0] + " has been added to the vip channel.");
            }
        }

        //add player to copy of list, sort it alphabetically and notify sender
        VIPList.add(args[0].toLowerCase());
        java.util.Collections.sort(VIPList);
        sender.sendMessage(ChatColor.GREEN + "Successfully added " + args[0]);

        //overwrite config list with updated copy of list
        plugin.getConfig().set("Players", VIPList);
        plugin.saveConfig();
        return true;
    }

    public static boolean vipRemove(CommandSender sender, Command command, String label, String[] args)
    {
        //If there are not enough arguments
        if (args.length < 1)
        {
            return false;
        }

        String playername = args[0].toLowerCase();

        //if the list copy is null
        if (VIPList == null)
        {
            VIPList = new ArrayList<String>(); //if the copy is null, initialize a new arraylist
        }

        //if the player is not in the list
        if (!VIPList.contains(playername))
        {
            sender.sendMessage(ChatColor.RED + "That player is not in the vip chat channel.");
            return true;
        }

        //remove the player from the list and notify sender
        VIPList.remove(playername);
        sender.sendMessage(ChatColor.GREEN + "Successfully removed " + args[0]);

        //announce removal to every player in recipients list
        for(String recipient : VIPList)
        {
            Player player = Bukkit.getServer().getPlayer(recipient);
            if (player != null && player.isOnline())
            {
                player.sendMessage(ChatColor.GREEN + args[0] + " has been removed from the vip channel.");
            }
        }

        //notify player who was removed if they are online.
        if (Bukkit.getServer().getOfflinePlayer(args[0]).isOnline())
        {
            Bukkit.getServer().getPlayer(args[0]).sendMessage(ChatColor.RED
                    + "You have been removed from the vip channel.");
        }

        //overwrite config list with updated copy of list
        plugin.getConfig().set("Players", VIPList);
        plugin.saveConfig();
        return true;
    }

    public static boolean vipList(CommandSender sender, Command command, String label, String[] args)
    {
        //if the player is not in the list
        if (!VIPList.contains(sender.getName().toLowerCase()))
        {
            sender.sendMessage(ChatColor.RED + "Sorry, you don't have permission to do that.");
            return true;
        }

        //return a list of players to command sender
        sender.sendMessage(ChatColor.GREEN + VIPList.toString());
        return true;
    }

    public static List<String> getVIPList()
    {
        return VIPList;
    }
}
