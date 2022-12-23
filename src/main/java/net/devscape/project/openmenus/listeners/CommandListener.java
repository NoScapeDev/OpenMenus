package net.devscape.project.openmenus.listeners;

import net.devscape.project.openmenus.*;
import net.devscape.project.openmenus.manager.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;

import static net.devscape.project.openmenus.utils.iMessage.msgPlayer;

public class CommandListener implements Listener {

    @EventHandler
    public void onGUICommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String m = e.getMessage();
        boolean isOpenCommand = OpenMenus.getInstance().getConfig().getBoolean("settings.open-command-gui-name");

        if (isOpenCommand) {
            for (String gui : OpenMenus.getInstance().getIndexManager().getMenus()) {
                if (m.equalsIgnoreCase("/" + gui)) {
                    int slots = OpenMenus.getInstance().getConfig().getInt("guis." + gui + ".slots");
                    String displayname = OpenMenus.getInstance().getConfig().getString("guis." + gui + ".name");
                    String permission = OpenMenus.getInstance().getConfig().getString("guis." + gui + ".open-permission.permission");
                    boolean isOpenPermEnabled = OpenMenus.getInstance().getConfig().getBoolean("guis." + gui + ".open-permission.enable");
                    String noPermMessage = OpenMenus.getInstance().getConfig().getString("guis." + gui + ".open-permission.message");

                    for (String world : OpenMenus.getInstance().getConfig().getStringList("settings.disabled-worlds")) {
                        if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                            if (isOpenPermEnabled) {
                                if (permission != null && p.hasPermission(permission)) {
                                    new InputMenu(OpenMenus.getIUtils(p, slots, displayname, gui)).iOpen();
                                } else {
                                    msgPlayer(p, noPermMessage);
                                }
                            } else {
                                new InputMenu(OpenMenus.getIUtils(p, slots, displayname, gui)).iOpen();
                            }
                        }
                    }
                }
            }
        }
    }
}