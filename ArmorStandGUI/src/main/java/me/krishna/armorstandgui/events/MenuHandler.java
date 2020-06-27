package me.krishna.armorstandgui.events;

import me.krishna.armorstandgui.ArmorStandGui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuHandler implements Listener {

    ArmorStandGui plugin;

    public MenuHandler(ArmorStandGui plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        //get who clicked inventory
        Player player = (Player) e.getWhoClicked();

        //Menu List
        final String MAIN_MENU = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("main-menu"));
        final String CREATE_MENU = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("create-menu"));
        final String CONFIRM_MENU = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("confirm-menu"));
        final String ARMOR_MENU = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("armor-menu"));
        //make sure we are in the right inventory
        if(e.getView().getTitle().equalsIgnoreCase(MAIN_MENU)){
            e.setCancelled(true);
           //check which item they have clicked on
            switch(e.getCurrentItem().getType()){
                case ARMOR_STAND:
                    player.sendMessage("Opened Armor Stand Create Menu");
//                    player.closeInventory();
                    //Open the armor stand menu
                    plugin.openCrateMenu(player);
                    break;
                case BARRIER:
                    player.sendMessage(ChatColor.RED +"Closing Main Menu.");
                    player.closeInventory();
                    break;
                default:
                    break;

            }

        }else if (e.getView().getTitle().equalsIgnoreCase(CREATE_MENU)){
            //checks if the player already has an armor stand in the hashmap
            if(!plugin.amorstands.containsKey(player)){
                // make armor stand and adds is it to the hash map
                ArmorStand stand = (ArmorStand)player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
                plugin.amorstands.put(player, stand);
//                stand.setVisible(false);
            }


            switch (e.getCurrentItem().getType()) {
                case STICK:
                    player.sendMessage("Add Arms?");
                    plugin.openConfirmMenu(player, Material.STICK);
                    break;
                case BEACON:
                    player.sendMessage("Glow?");
                    plugin.openConfirmMenu(player, Material.BEACON);
                    break;
                case LEATHER_CHESTPLATE:
                    player.sendMessage("Add Armor?");
                    //armor select menu
                    plugin.openArmorMenu(player);
                    break;
                case STONE_SLAB:
                    player.sendMessage("Add Base?");
                    plugin.openConfirmMenu(player, Material.STONE_SLAB);
                    break;
                case GREEN_WOOL:
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("create-message")));
                    player.closeInventory();
                    plugin.amorstands.remove(player);
                    break;
                case RED_WOOL:
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("delete-message")));
                    player.closeInventory();
                    if(plugin.amorstands.containsKey(player)){
                        ArmorStand stand = plugin.amorstands.get(player);
                        stand.remove();
                        plugin.amorstands.remove(player);
                    }
                    break;


            }
            e.setCancelled(true);
        }else if(e.getView().getTitle().equalsIgnoreCase(CONFIRM_MENU)){
            if(e.getClickedInventory().contains(Material.STICK)){
                switch (e.getCurrentItem().getType()){
                    case GREEN_WOOL:
                       if(plugin.amorstands.containsKey(player)){
                           ArmorStand stand = plugin.amorstands.get(player);
                           stand.setArms(true);
                       }
                        plugin.openCrateMenu(player);
                       break;
                    case RED_WOOL:
                        if(plugin.amorstands.containsKey(player)){
                            ArmorStand stand = plugin.amorstands.get(player);
                            stand.setArms(false);
                        }
                        plugin.openCrateMenu(player);
                        break;
                }
            }else if(e.getClickedInventory().contains(Material.BEACON)){
                switch (e.getCurrentItem().getType()){
                    case GREEN_WOOL:
                        if(plugin.amorstands.containsKey(player)){
                            ArmorStand stand = plugin.amorstands.get(player);
                            stand.setGlowing(true);
                        }
                        plugin.openCrateMenu(player);
                        break;
                    case RED_WOOL:
                        if(plugin.amorstands.containsKey(player)){
                            ArmorStand stand = plugin.amorstands.get(player);
                            stand.setGlowing(false);
                        }
                        plugin.openCrateMenu(player);
                        break;
                }
            }else if(e.getClickedInventory().contains(Material.STONE_SLAB)){
                switch (e.getCurrentItem().getType()){
                    case GREEN_WOOL:
                        if(plugin.amorstands.containsKey(player)){
                            ArmorStand stand = plugin.amorstands.get(player);
                            stand.setBasePlate(true);
                        }
                        plugin.openCrateMenu(player);
                        break;
                    case RED_WOOL:
                        if(plugin.amorstands.containsKey(player)){
                            ArmorStand stand = plugin.amorstands.get(player);
                            stand.setBasePlate(false);
                        }
                        plugin.openCrateMenu(player);
                        break;
                }
            }

            e.setCancelled(true);
        }else if(e.getView().getTitle().equalsIgnoreCase(ARMOR_MENU)){
            //check if player is in the hashmap
            if(plugin.amorstands.containsKey(player)){
                //get an instance of the armor stand
                ArmorStand stand = plugin.amorstands.get(player);
                switch (e.getCurrentItem().getType()){
                    //Leather Armor
                    case LEATHER_HELMET:
                        if(stand.getHelmet().getType() == Material.LEATHER_HELMET){
                            stand.setHelmet(null);
                            player.sendMessage("Remove!");

                        }else{
                            stand.setHelmet(new ItemStack(Material.LEATHER_HELMET));
                            player.sendMessage("Added!");

                        }
                        plugin.openArmorMenu(player);
                        break;
                    case LEATHER_CHESTPLATE:
                        if(stand.getChestplate().getType() == Material.LEATHER_CHESTPLATE){
                            stand.setChestplate(null);
                            player.sendMessage("Remove!");

                        }else{
                            stand.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                            player.sendMessage("Added!");

                        }
                        plugin.openArmorMenu(player);
                        break;
                    case LEATHER_LEGGINGS:
                        if(stand.getLeggings().getType() == Material.LEATHER_LEGGINGS){
                            stand.setLeggings(null);
                            player.sendMessage("Remove!");

                        }else{
                            stand.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                            player.sendMessage("Added!");

                        }
                        plugin.openArmorMenu(player);
                        break;
                    case LEATHER_BOOTS:
                        if(stand.getBoots().getType() == Material.LEATHER_BOOTS){
                            stand.setBoots(null);
                            player.sendMessage("Remove!");

                        }else{
                            stand.setBoots(new ItemStack(Material.LEATHER_BOOTS));
                            player.sendMessage("Added!");

                        }
                        plugin.openArmorMenu(player);
                        break;

                    //Diamond armor
                    case DIAMOND_HELMET:
                       if(stand.getHelmet().getType() == Material.DIAMOND_HELMET){
                           stand.setHelmet(null);
                           player.sendMessage("Remove!");

                       }else{
                           stand.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                           player.sendMessage("Added!");


                       }
                        plugin.openArmorMenu(player);
                        break;
                    case DIAMOND_CHESTPLATE:
                        if(stand.getChestplate().getType() == Material.DIAMOND_CHESTPLATE){
                            stand.setChestplate(null);
                            player.sendMessage("Remove!");

                        }else{
                            stand.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            player.sendMessage("Added!");

                        }
                        plugin.openArmorMenu(player);
                        break;
                    case DIAMOND_LEGGINGS:
                        if(stand.getLeggings().getType() == Material.DIAMOND_LEGGINGS){
                            stand.setLeggings(null);
                            player.sendMessage("Remove!");

                        }else{
                            stand.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            player.sendMessage("Added!");

                        }
                        plugin.openArmorMenu(player);
                        break;
                    case DIAMOND_BOOTS:
                        if(stand.getBoots().getType() == Material.DIAMOND_BOOTS){
                            stand.setBoots(null);
                            player.sendMessage("Remove!");

                        }else{
                            stand.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            player.sendMessage("Added!");

                        }
                        plugin.openArmorMenu(player);
                        break;
                    // Confirm button
                    case GREEN_WOOL:
                        player.sendMessage("Armor Confirmed");
                        plugin.openCrateMenu(player);

                }
            }

            e.setCancelled(true);
        }
    }
}
