package com.hotmail.shinyclef.shinychannels;

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
    private static ShinyChannels plugin;
    public static final String MOD_BYPASS_CHAR = "ʘ";

    public EventListener(ShinyChannels plugin)
    {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        EventListener.plugin = plugin;
    }

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent e)
    {
        Player player = e.getPlayer();

        //if player's a mod and they do not have modChat toggled off
        if (player.hasPermission("rolyd.mod") &&
                !PermissionChat.getModChatToggledOff().contains(player.getName().toLowerCase()))
        {
            if (e.getMessage().startsWith(MOD_BYPASS_CHAR))
            {
                e.setMessage(e.getMessage().substring(1));
            }
            else
            {
                new ModChatEvent(e.getPlayer(), e.getMessage()).runTask(plugin);
                e.setCancelled(true);
            }
        }
    }

    private class ModChatEvent extends BukkitRunnable
    {
        Player player;
        String message;

        private ModChatEvent(Player player, String message)
        {
            this.player = player;
            this.message = message;
        }

        @Override
        public void run()
        {
            player.performCommand("mb " + message);
        }
    }
}
