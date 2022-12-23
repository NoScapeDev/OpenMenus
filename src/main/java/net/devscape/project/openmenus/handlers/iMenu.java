package net.devscape.project.openmenus.handlers;

import net.devscape.project.openmenus.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;

import static net.devscape.project.openmenus.utils.iMessage.color;
import static net.devscape.project.openmenus.utils.iMessage.format;

public abstract class iMenu implements InventoryHolder {

    protected iUtils iu;
    protected Inventory inventory;

    public iMenu(iUtils iu) {
        this.iu = iu;
    }

    //let each menu decide their name
    public abstract String getMenuName();

    //let each menu decide their slot amount
    public abstract int getSlots();

    //let each menu decide how the items in the menu will be handled when clicked
    public abstract void handleMenu(InventoryClickEvent e);

    //let each menu decide what items are to be placed in the inventory menu
    public abstract void setMenuItems();

    //When called, an inventory is created and opened for the player
    public void iOpen() {
        //The owner of the inventory created is the Menu itself,
        // so we are able to reverse engineer the Menu object from the
        // inventoryHolder in the MenuListener class when handling clicks
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

        //grab all the items specified to be used for this menu and add to inventory
        this.setMenuItems();

        //open the inventory for the player
        iu.getIPlayer().openInventory(inventory);
    }

    //Overridden method from the InventoryHolder interface
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    //Helpful utility method to fill all remaining slots with "filler glass"
    public void setFillerGlass(Material material){
        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) == null){
                inventory.setItem(i, makeItem(material, " ", new ArrayList<>()));
            }
        }
    }

    public ItemStack makeItem(Material material, String displayName, List<String> lore) {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(format(displayName));

        itemMeta.setLore(color(lore));

        boolean isFlags = OpenMenus.getInstance().getConfig().getConfigurationSection("guis." + iu.getGui() + ".flags") != null;

        if (isFlags) {
            for (String flag : OpenMenus.getInstance().getConfig().getStringList("guis." + iu.getGui() + ".flags")) {
                itemMeta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
            }
        }
        
        item.setItemMeta(itemMeta);

        return item;
    }

}