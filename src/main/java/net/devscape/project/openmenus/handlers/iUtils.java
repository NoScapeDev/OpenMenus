package net.devscape.project.openmenus.handlers;

import org.bukkit.entity.*;

public class iUtils {

    private final Player iPlayer;
    private int slots;
    private String displayname;
    private String gui;

    public iUtils(Player iPlayer, int slots, String displayname, String gui) {
        this.iPlayer = iPlayer;
        this.slots = slots;
        this.displayname = displayname;
        this.gui = gui;
    }

    public Player getIPlayer() {
        return iPlayer;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getGui() {
        return gui;
    }

    public void setGui(String gui) {
        this.gui = gui;
    }
}
