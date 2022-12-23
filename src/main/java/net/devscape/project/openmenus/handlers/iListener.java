package net.devscape.project.openmenus.handlers;

import net.devscape.project.openmenus.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;

public class iListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof iMenu) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }

            iMenu menu = (iMenu) holder;
            menu.handleMenu(e);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof iMenu) {
            OpenMenus.getInstance().removeFromIUtils(p);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        OpenMenus.getInstance().removeFromIUtils(p);
    }

}
