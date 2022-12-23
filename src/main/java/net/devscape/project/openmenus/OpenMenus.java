package net.devscape.project.openmenus;

import com.google.common.base.*;
import me.clip.placeholderapi.*;
import net.devscape.project.openmenus.commands.*;
import net.devscape.project.openmenus.handlers.*;
import net.devscape.project.openmenus.listeners.*;
import net.devscape.project.openmenus.manager.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.*;

import java.io.*;
import java.util.*;
import java.util.Objects;

public final class OpenMenus extends JavaPlugin {

    private static OpenMenus instance;
    private IndexManager indexManager;

    private static final HashMap<Player, iUtils> menuUtilMap = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        saveDefaultConfig();

        indexManager = new IndexManager();
        indexManager.addMenus();

        getServer().getPluginManager().registerEvents(new CommandListener(), this);
        getServer().getPluginManager().registerEvents(new iListener(), this);

        Objects.requireNonNull(getCommand("openmenus")).setExecutor(new iCommands());

        if (isPlaceholderAPI()) {
            Bukkit.getConsoleSender().sendMessage("[OpenMenus] Hooking into PlaceholderAPI!");
        } else {
            Bukkit.getConsoleSender().sendMessage("[OpenMenus] This plugin supports PlaceholderAPI, install to add placeholders in guis...");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        indexManager.clearMenu();
    }

    public static OpenMenus getInstance() {
        return instance;
    }

    public static iUtils getIUtils(Player p, int slots, String displayname, String gui) {
        iUtils iU;
        if (!(menuUtilMap.containsKey(p))) {

            iU = new iUtils(p, slots, displayname, gui);
            menuUtilMap.put(p, iU);

            return iU;
        } else {
            return menuUtilMap.get(p);
        }
    }

    public void removeFromIUtils(Player p) {
        menuUtilMap.remove(p);
    }

    public void reload(Player player) {
        reloadConfig();
        saveConfig();

        indexManager.clearMenu();
        indexManager.addMenusPlayer(player);
    }

    public static String replacePlaceholderAPI(Player p, String message) {
        String holders = message;
        if (PlaceholderAPI.containsPlaceholders(holders))
            holders = PlaceholderAPI.setPlaceholders(p, holders);
        return holders;
    }

    public static List<String> replacePlaceholderAPIList(Player p, List<String> list) {
        List<String> holders = list;
        if (PlaceholderAPI.containsPlaceholders(String.valueOf(list)))
            holders = PlaceholderAPI.setPlaceholders(p, holders);
        return holders;
    }

    public boolean isPlaceholderAPI() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public IndexManager getIndexManager() {
        return indexManager;
    }
}