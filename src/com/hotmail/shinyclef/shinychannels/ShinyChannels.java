package com.hotmail.shinyclef.shinychannels;

import com.hotmail.shinyclef.shinybase.ModChatHandler;
import com.hotmail.shinyclef.shinybase.ShinyBase;
import com.hotmail.shinyclef.shinybase.ShinyBaseAPI;
import com.hotmail.shinyclef.shinybridge.ShinyBridge;
import com.hotmail.shinyclef.shinybridge.ShinyBridgeAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Author: Shinyclef
 * Date: 13/06/12
 * Time: 2:35 AM
 */

public class ShinyChannels extends JavaPlugin
{
    private static ShinyBaseAPI shinyBaseAPI;

    @Override
    public void onEnable()
    {
        Plugin base = Bukkit.getPluginManager().getPlugin("ShinyBase");
        if (base != null)
        {
            shinyBaseAPI = ((ShinyBase)base).getShinyBaseAPI();
        }

        new EventListener(this, shinyBaseAPI);
        CommandExecutor commandExecutor = new CmdExecutor(shinyBaseAPI.getModChatHandler());

        //MB
        shinyBaseAPI.takeOverBukkitCommand(this, "mb", commandExecutor);
        getCommand("mbadd").setExecutor(commandExecutor);
        getCommand("mbremove").setExecutor(commandExecutor);
        getCommand("mblist").setExecutor(commandExecutor);

        //EXP
        getCommand("sb").setExecutor(commandExecutor);
        getCommand("sbadd").setExecutor(commandExecutor);
        getCommand("sbremove").setExecutor(commandExecutor);
        getCommand("sblist").setExecutor(commandExecutor);

        //VIP
        getCommand("vip").setExecutor(commandExecutor);
        getCommand("vipadd").setExecutor(commandExecutor);
        getCommand("vipremove").setExecutor(commandExecutor);
        getCommand("viplist").setExecutor(commandExecutor);

        //T
        shinyBaseAPI.takeOverBukkitCommand(this, "t", commandExecutor);
        getCommand("mbon").setExecutor(commandExecutor);
        getCommand("mboff").setExecutor(commandExecutor);

        PermissionChat.initialize(this, shinyBaseAPI);
    }

    @Override
    public void onDisable()
    {

    }

    public static String makeSentence(String[] args)
    {
        int i = 0;
        String sentence = "";

        //put all the args starting from the 1st into string 'sentence'
        do
        {
            sentence = sentence + args[i] + " ";
            i++;
        }
        while (i < args.length);

        return sentence;
    }

    /* Getters */

    public static ShinyBaseAPI getShinyBaseAPI()
    {
        return shinyBaseAPI;
    }
}
