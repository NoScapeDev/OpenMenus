package net.devscape.project.openmenus.manager;

import net.devscape.project.openmenus.*;
import org.bukkit.entity.*;

import java.util.*;

import static net.devscape.project.openmenus.utils.iMessage.format;

public class IndexManager {

    private final List<String> menus = new ArrayList<>();

    public IndexManager() {}

    public void clearMenu() {
        menus.clear();
    }

    public boolean isValidMenu(String menu) {
        return menus.contains(menu);
    }

    public void addMenus() {
        for (String m : OpenMenus.getInstance().getConfig().getConfigurationSection("guis").getKeys(false)) {
            if (!menus.contains(m)) {
                menus.add(m);
            }
        }
    }

    public void addMenusPlayer(Player player) {
        int count = 0;
        for (String m : OpenMenus.getInstance().getConfig().getConfigurationSection("guis").getKeys(false)) {
            if (!menus.contains(m)) {
                menus.add(m);
                count++;
            }
        }

        player.sendMessage(format("&8&l» &eLoaded &f" + count + " &emenu(s) successfully."));
    }

    public List<String> getMenus() {
        return menus;
    }
}