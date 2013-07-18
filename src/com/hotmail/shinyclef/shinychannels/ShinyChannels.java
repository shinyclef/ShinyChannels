package com.hotmail.shinyclef.shinychannels;

import com.hotmail.shinyclef.shinybase.ShinyBase;
import org.bukkit.Bukkit;
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
    private CmdExecutor commandExecutor;
    private EventListener eventListener;
    private ShinyBase shinyBase;

    @Override
    public void onEnable()
    {
        Plugin p = Bukkit.getPluginManager().getPlugin("ShinyBase");
        if (p != null)
        {
            shinyBase =(ShinyBase) p;
        }

        eventListener = new EventListener(this);
        commandExecutor = new CmdExecutor();
        getCommand("vip").setExecutor(commandExecutor);
        getCommand("vipadd").setExecutor(commandExecutor);
        getCommand("vipremove").setExecutor(commandExecutor);
        getCommand("viplist").setExecutor(commandExecutor);
        getCommand("mb").setExecutor(commandExecutor);
        getCommand("sb").setExecutor(commandExecutor);
        getCommand("sbadd").setExecutor(commandExecutor);
        getCommand("sbremove").setExecutor(commandExecutor);
        getCommand("sblist").setExecutor(commandExecutor);


        VIPChat.initializeVipChat(this, shinyBase.getShinyBaseAPI());
        StaffChat.initializeStaffChat(this, shinyBase.getShinyBaseAPI());
    }

    @Override
    public void onDisable()
    {

    }

    public static List<String> getVIPList()
    {
        return VIPChat.getVIPList();
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
