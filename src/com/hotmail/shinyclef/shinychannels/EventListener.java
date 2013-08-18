package com.hotmail.shinyclef.shinychannels;

import com.hotmail.shinyclef.shinybase.ModChatHandler;
import com.hotmail.shinyclef.shinybase.ShinyBaseAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

/*
 * User: Shinyclef
 * Date: 14/07/13
 * Time: 5:23 AM
 */

public class EventListener implements Listener
{
    private ShinyBaseAPI base;

    public EventListener(ShinyChannels plugin, ShinyBaseAPI base)
    {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.base = base;
    }

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent e)
    {
        if (e.isCancelled())
        {
            return;
        }

        Player player = e.getPlayer();

        //if player's a mod and they do not have modChat toggled off
        if (base.getModChatHandler().defaultIsModChat(e.getPlayer()))
        {
            if (e.getMessage().startsWith(ModChatHandler.MOD_BYPASS_CHAR))
            {
                e.setMessage(e.getMessage().substring(1));
            }
            else
            {
                base.getModChatHandler().newModChatEvent(e);
                e.setCancelled(true);
            }
        }
    }


}
