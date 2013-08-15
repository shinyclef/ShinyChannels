package com.hotmail.shinyclef.shinychannels;

import com.hotmail.shinyclef.shinybase.ShinyBaseAPI;
import com.hotmail.shinyclef.shinybridge.ShinyBridgeAPI;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Shinyclef
 * Date: 1/08/13
 * Time: 6:59 PM
 */

public class PermissionChat
{
    private static ShinyChannels plugin;
    private static ShinyBaseAPI shinyBaseAPI;
    private static ShinyBridgeAPI shinyBridgeAPI;
    private static Server server;
    private static Map<String, ChannelData> dataMap;
    private static boolean hasBridge = true;

    public static void initialize(ShinyChannels thePlugin, ShinyBaseAPI theShinyBaseAPI,
                                  ShinyBridgeAPI theShinyBridgeAPI)
    {
        plugin = thePlugin;
        shinyBaseAPI = theShinyBaseAPI;
        shinyBridgeAPI = theShinyBridgeAPI;
        server = plugin.getServer();
        if (shinyBridgeAPI == null)
        {
            hasBridge = false;
        }

        //individual guest maps
        List<String> mbGuestList = new ArrayList<String>();
        List<String> sbGuestList = new ArrayList<String>();
        List<String> vipGuestList = new ArrayList<String>();

        //dataMap
        dataMap = new HashMap<String, ChannelData>();
        dataMap.put("mb", new ChannelData("mb", "mod", "Mod", "[MB]", ChatColor.GREEN,
                ChatColor.BLUE, "rolyd.mod", "rolyd.admin", mbGuestList));
        dataMap.put("sb", new ChannelData("sb", "staff", "Staff", "[Staff]", ChatColor.AQUA,
                ChatColor.YELLOW, "rolyd.exp", "rolyd.mod", sbGuestList));
        dataMap.put("vip", new ChannelData("vip", "VIP", "VIP", "[VIP]", ChatColor.YELLOW,
                ChatColor.AQUA, "rolyd.vip", "rolyd.mod", vipGuestList));
    }

    private static class ChannelData
    {
        public String channel;
        public String nameNormal;
        public String nameCapitalized;
        public String tag;
        public ChatColor colour;
        public ChatColor colourHighlight;
        public String chatPerm;
        public String addPerm;
        public List<String> guestList;

        private ChannelData(String channel, String nameNormal, String nameCapitalized, String tag, ChatColor colour,
                            ChatColor colourHighlight, String chatPerm, String addPerm, List guestList)
        {
            this.channel = channel;
            this.nameNormal = nameNormal;
            this.nameCapitalized = nameCapitalized;
            this.tag = tag;
            this.colour = colour;
            this.colourHighlight = colourHighlight;
            this.chatPerm = chatPerm;
            this.addPerm = addPerm;
            this.guestList = guestList;
        }
    }

    public static boolean chat(CommandSender sender, String[] args, String channel)
    {
        //get channel data
        ChannelData ch = dataMap.get(channel);
        if (ch == null)
        {
            sender.sendMessage("ShinyChannels error. Please notify plugin author: \"Channel data not found in maps.\"");
            return true;
        }

        //args length
        if (args.length < 1)
        {
            sender.sendMessage("Usage: /" + channel + " [message]");
            return true;
        }

        //check perm
        if (!sender.hasPermission(ch.chatPerm) && !ch.guestList.contains(sender.getName().toLowerCase()))
        {
            sender.sendMessage(ChatColor.RED + "Sorry, you do not have permission to do that.");
            return true;
        }

        //make sentence
        String sentence = ShinyChannels.makeSentence(args);

        //guest qty
        String guestQty = " ";
        if (ch.guestList.size() > 0)
        {
            guestQty = "(+" + ch.guestList.size() + ") ";
        }

        //broadcast
        broadcastMessage(ChatColor.RED + ch.tag + guestQty + sender.getName() + ": " +
                ch.colour + sentence, ch.chatPerm);

        //guest broadcast
        for (String playerName : ch.guestList)
        {
            if (server.getOfflinePlayer(playerName).isOnline())
            {
                server.getPlayer(playerName).sendMessage(ChatColor.RED + ch.tag + " " + sender.getName() +
                        ": " + ch.colour + sentence);
            }
        }

        return true;
    }

    public static boolean add(CommandSender sender, String[] args, String channel)
    {
        //get channel data
        ChannelData ch = dataMap.get(channel);
        if (ch == null)
        {
            sender.sendMessage("Plugin error. Please notify plugin author: \"Channel data not found in maps.\"");
            return true;
        }

        //check perm
        if (!sender.hasPermission(ch.addPerm))
        {
            sender.sendMessage(ChatColor.RED + "Sorry, you do not have permission to do that.");
            return true;
        }

        //args length
        if (args.length < 1)
        {
            sender.sendMessage("Usage: /" + channel + "add [player]");
            return true;
        }

        //has played check
        String playerName = args[0].toLowerCase();
        if (!shinyBaseAPI.isExistingPlayer(playerName))
        {
            sender.sendMessage(ChatColor.RED + "That player does not exist.");
            return true;
        }

        //if the player is already in the list
        if (ch.guestList.contains(playerName))
        {
            sender.sendMessage(ChatColor.RED + "That player is already in the " + ch.nameNormal + " channel.");
            return true;
        }

        //add to list and inform users
        ch.guestList.add(playerName);
        server.broadcast(ch.colourHighlight + playerName + ch.colour
                + " has been added to the " + ch.nameNormal + " channel.", ch.chatPerm);

        //notify guests
        for (String listName : ch.guestList)
        {
            if (server.getOfflinePlayer(listName).isOnline() && listName != playerName)
            {
                server.getPlayer(listName).sendMessage(ch.colourHighlight + playerName + ch.colour
                        + " has been added to the " + ch.nameNormal + " channel.");
            }
        }

        //notify added player if online
        if (server.getOfflinePlayer(args[0]).isOnline())
        {
            plugin.getServer().getPlayer(playerName).sendMessage(ch.colour +
                    "You have been added to the " + ch.nameNormal + " channel. Type " + ch.colourHighlight +
                    "/" + ch.channel + " [message]" + ch.colour + " to post a message.");
        }

        return true;
    }

    public static boolean remove(CommandSender sender, String[] args, String channel)
    {
        //get channel data
        ChannelData ch = dataMap.get(channel);
        if (ch == null)
        {
            sender.sendMessage("Plugin error. Please notify plugin author: \"Channel data not found in maps.\"");
            return true;
        }

        //check perm
        if (!sender.hasPermission(ch.addPerm))
        {
            sender.sendMessage(ChatColor.RED + "Sorry, you do not have permission to do that.");
            return true;
        }

        //args length
        if (args.length < 1)
        {
            sender.sendMessage("Usage: /" + channel + "remove [player]");
            return true;
        }

        //has played check
        String playerName = args[0].toLowerCase();
        if (!shinyBaseAPI.isExistingPlayer(playerName))
        {
            sender.sendMessage(ChatColor.RED + "That player does not exist.");
            return true;
        }

        //if the player is not in the list
        if (!ch.guestList.contains(playerName))
        {
            sender.sendMessage(ChatColor.RED + "That player is not in the " +ch.nameNormal + " channel.");
            return true;
        }

        //remove from list and inform users
        ch.guestList.remove(playerName);
        server.broadcast(ch.colourHighlight + playerName + ch.colour +
                " has been removed from the " + ch.nameNormal + " channel.", ch.chatPerm);

        //notify guests
        for (String name : ch.guestList)
        {
            if (server.getOfflinePlayer(name).isOnline())
            {
                server.getPlayer(name).sendMessage(ch.colourHighlight + playerName + ch.colour +
                        " has been removed from the " + ch.nameNormal + " channel.");
            }
        }

        //notify removed player if online
        if (server.getOfflinePlayer(args[0]).isOnline())
        {
            plugin.getServer().getPlayer(playerName).sendMessage(ChatColor.BLUE +
                    "You have been removed from the " + ch.nameNormal + " channel.");
        }

        return true;
    }

    public static boolean list(CommandSender sender, String[] args, String channel)
    {
        //get channel data
        ChannelData ch = dataMap.get(channel);
        if (ch == null)
        {
            sender.sendMessage("Plugin error. Please notify plugin author: \"Channel data not found in maps.\"");
            return true;
        }

        //check perm
        if (!sender.hasPermission(ch.chatPerm))
        {
            sender.sendMessage(ChatColor.RED + "Sorry, you do not have permission to do that.");
            return true;
        }

        String userList;
        if (ch.guestList.size() > 0)
        {
            userList = ChatColor.BLUE + ch.tag + " guests: " + ch.guestList.toString();
        }
        else
        {
            userList = ChatColor.BLUE + "There are no guests in " + ch.tag + ".";
        }

        //return a list of players to command sender
        sender.sendMessage(userList);
        return true;
    }

    /* Helpers */
    private static void broadcastMessage(String message, String permission)
    {
        //send to bridge clients if we have bridge
        if (hasBridge)
        {
            shinyBridgeAPI.broadcastMessage(message, permission, true);
        }
        else
        {
            //standard broadcast
            server.broadcast(message, permission);
        }
    }



    public static void test()
    {
        plugin.getServer().broadcastMessage("YOLO!!!");
    }
}
