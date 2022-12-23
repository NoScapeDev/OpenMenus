package net.devscape.project.openmenus.commands;

import net.devscape.project.openmenus.*;
import net.devscape.project.openmenus.manager.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import static net.devscape.project.openmenus.utils.iMessage.msgPlayer;

public class iCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {

            Player p = (Player) sender;

            if (cmd.getLabel().equalsIgnoreCase("openmenus")) {
                if (p.hasPermission("openmenus.access")) {
                    if (args.length == 0) {
                        msgPlayer(p,
                                "&8&l» &eOpenMenus Commands:",
                                "",
                                "&6/om open <menu> <player>",
                                "&6/om list",
                                "&6/om reload",
                                "");
                    }else if (args.length == 1) {
                        // /om open basicgui %player%
                        if (args[0].equalsIgnoreCase("reload")) {
                            // reload function here.
                            OpenMenus.getInstance().reload(p);
                            msgPlayer(p, "&8&l» &eReload Complete.");
                        } else if (args[0].equalsIgnoreCase("list")) {
                            // list here all menus
                            msgPlayer(p, "&8&l» &eList: &f" + OpenMenus.getInstance().getIndexManager().getMenus().toString().replace("[", "").replace("]", ""));
                        }
                    } else if (args.length == 3) {
                        // /om open basicgui %player%
                        if (args[0].equalsIgnoreCase("open")) {
                            String menu = args[1];
                            Player target = Bukkit.getPlayer(args[2]);

                            if (OpenMenus.getInstance().getIndexManager().isValidMenu(menu)) {
                                if (target != null) {
                                    int slots = OpenMenus.getInstance().getConfig().getInt("guis." + menu + ".slots");
                                    String displayname = OpenMenus.getInstance().getConfig().getString("guis." + menu + ".name");

                                    new InputMenu(OpenMenus.getIUtils(target, slots, displayname, menu)).iOpen();
                                    msgPlayer(p, "&8&l» &eOpening menu &f" + menu + "&e for &7" + target.getName());
                                } else {
                                    msgPlayer(p, "&cThis player is not online.");
                                }
                            } else {
                                msgPlayer(p, "&cThis is an invaild menu.");
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
