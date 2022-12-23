package net.devscape.project.openmenus.manager;

import net.devscape.project.openmenus.*;
import net.devscape.project.openmenus.handlers.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.*;

import java.util.*;

import static net.devscape.project.openmenus.utils.iMessage.format;

public class InputMenu extends iMenu {

    public InputMenu(iUtils iu) {
        super(iu);
    }

    @Override
    public String getMenuName() {
        return format(iu.getDisplayname());
    }

    @Override
    public int getSlots() {
        return iu.getSlots();
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        for (String items : OpenMenus.getInstance().getConfig().getConfigurationSection("guis." + iu.getGui() + ".items").getKeys(false)) {
            String mat = OpenMenus.getInstance().getConfig().getString("guis." + iu.getGui() + ".items." + items + ".material");
            String displayname = OpenMenus.getInstance().getConfig().getString("guis." + iu.getGui() + ".items." + items + ".displayname");
            int slot = OpenMenus.getInstance().getConfig().getInt("guis." + iu.getGui() + ".items." + items + ".slot");

            if (e.getCurrentItem().getType() == Material.valueOf(mat.toUpperCase()) && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(format(displayname)) && e.getSlot() == slot) {
                if (e.isLeftClick()) {
                    for (String lcommands : OpenMenus.getInstance().getConfig().getStringList("guis." + iu.getGui() + ".items." + items + ".action.left-click.commands")) {
                        if (lcommands != null) {
                            lcommands = OpenMenus.replacePlaceholderAPI(p, lcommands);
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), lcommands);
                        }
                    }

                    for (String lmessage : OpenMenus.getInstance().getConfig().getStringList("guis." + iu.getGui() + ".items." + items + ".action.left-click.message")) {
                        if (lmessage != null) {
                            lmessage = OpenMenus.replacePlaceholderAPI(p, lmessage);
                            p.sendMessage(format(lmessage));
                        }
                    }
                }

                if (e.isRightClick()) {
                    for (String rcommands : OpenMenus.getInstance().getConfig().getStringList("guis." + iu.getGui() + ".items." + items + ".action.right-click.commands")) {
                        if (rcommands != null) {
                            rcommands = OpenMenus.replacePlaceholderAPI(p, rcommands);
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), rcommands);
                        }
                    }

                    for (String rmessage : OpenMenus.getInstance().getConfig().getStringList("guis." + iu.getGui() + ".items." + items + ".action.right-click.message")) {
                        if (rmessage != null) {
                            rmessage = OpenMenus.replacePlaceholderAPI(p, rmessage);
                            p.sendMessage(format(rmessage));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        for (String items : OpenMenus.getInstance().getConfig().getConfigurationSection("guis." + iu.getGui() + ".items").getKeys(false)) {
            String mat = OpenMenus.getInstance().getConfig().getString("guis." + iu.getGui() + ".items." + items + ".material");
            String displayname = OpenMenus.getInstance().getConfig().getString("guis." + iu.getGui() + ".items." + items + ".displayname");
            int slot = OpenMenus.getInstance().getConfig().getInt("guis." + iu.getGui() + ".items." + items + ".slot");

            List<String> lore = OpenMenus.getInstance().getConfig().getStringList("guis." + iu.getGui() + ".items." + items + ".lore");

            displayname = OpenMenus.replacePlaceholderAPI(iu.getIPlayer(), displayname);
            lore = OpenMenus.replacePlaceholderAPIList(iu.getIPlayer(), lore);

            assert mat != null;
            getInventory().setItem(slot, makeItem(Material.valueOf(mat.toUpperCase()), displayname, lore));
        }

        boolean filler = OpenMenus.getInstance().getConfig().getBoolean("guis." + iu.getGui() + ".filler.enable");
        String fmat = OpenMenus.getInstance().getConfig().getString("guis." + iu.getGui() + ".filler.material");

        if (filler) {
            setFillerGlass(Material.valueOf(fmat));
        }
    }
}