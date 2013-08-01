package com.hotmail.shinyclef.shinychannels;

import com.hotmail.shinyclef.shinybase.ShinyBase;
import com.hotmail.shinyclef.shinybase.ShinyBaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Author: Shinyclef
 * Date: 13/06/12
 * Time: 2:35 AM
 */

public class ShinyChannels extends JavaPlugin
{
    private ShinyBase shinyBase;

    @Override
    public void onEnable()
    {
        Plugin p = Bukkit.getPluginManager().getPlugin("ShinyBase");
        if (p != null)
        {
            shinyBase =(ShinyBase) p;
        }

        new EventListener(this);
        CommandExecutor commandExecutor = new CmdExecutor();

        //MB
        getCommand("mb").setExecutor(commandExecutor);
        getCommand("mbadd").setExecutor(commandExecutor);
        getCommand("mbremove").setExecutor(commandExecutor);
        getCommand("mblist").setExecutor(commandExecutor);

        //EXP
        getCommand("sb").setExecutor(commandExecutor);
        getCommand("sbadd").setExecutor(commandExecutor);
        getCommand("sbremove").setExecutor(commandExecutor);
        getCommand("sblist").setExecutor(commandExecutor);

        // VIP
        getCommand("vip").setExecutor(commandExecutor);
        getCommand("vipadd").setExecutor(commandExecutor);
        getCommand("vipremove").setExecutor(commandExecutor);
        getCommand("viplist").setExecutor(commandExecutor);

        ShinyBaseAPI shinyBaseAPI = shinyBase.getShinyBaseAPI();
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
}
