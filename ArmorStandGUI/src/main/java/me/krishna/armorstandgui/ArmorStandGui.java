package me.krishna.armorstandgui;

import me.krishna.armorstandgui.commands.ArmorStandCommand;
import me.krishna.armorstandgui.events.MenuHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class ArmorStandGui extends JavaPlugin {

    public HashMap<Player, ArmorStand> amorstands = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Plugin has started up!!!");
        //Commands
        getCommand("armorstand").setExecutor(new ArmorStandCommand(this));

        //Events
        getServer().getPluginManager().registerEvents(new MenuHandler(this), this);

        //config
        getConfig().options().copyDefaults();
        saveDefaultConfig();


    }

    public void openMainMenu(Player player){
        //creates main menu
        Inventory main_menu = Bukkit.createInventory(player, 9, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("main-menu")));

        //options for main menu
        //create
        ItemStack create = new ItemStack(Material.ARMOR_STAND);
        ItemMeta create_meta = create.getItemMeta();
        create_meta.setDisplayName(ChatColor.GREEN + "Create");
        ArrayList<String> create_lore = new ArrayList<>();
        create_lore.add(ChatColor.DARK_PURPLE + "Create a new armor stand.");
        create_meta.setLore(create_lore);
        create.setItemMeta(create_meta);


        //close
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta close_meta = close.getItemMeta();
        close_meta.setDisplayName(ChatColor.RED + "Close");
        close.setItemMeta(close_meta);

        //adding item to the gui
        main_menu.setItem(0,create);
        main_menu.setItem(8,close);
        player.openInventory(main_menu);
    }

    public void openCrateMenu(Player player){
        //creates build menu
        Inventory create_menu = Bukkit.createInventory(player, 9, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("create-menu")));

        //options in create menu
        ItemStack arms = new ItemStack(Material.STICK); // Hide and show arms
        ItemStack glow = new ItemStack(Material.BEACON); // make armor stand glow
        ItemStack armor = new ItemStack((Material.LEATHER_CHESTPLATE)); // add armor
        ItemStack base = new ItemStack(Material.STONE_SLAB); // show/hide base
        ItemStack complete = new ItemStack(Material.GREEN_WOOL); // complete and create armor stand
        ItemStack cancel = new ItemStack(Material.RED_WOOL); // cancel and return to main menu

        //meta data
        ItemMeta arms_meta = arms.getItemMeta();
        arms_meta.setDisplayName(ChatColor.YELLOW + "Arms");
        arms.setItemMeta((arms_meta));

        ItemMeta glow_meta = glow.getItemMeta();
        glow_meta.setDisplayName("Glow");
        glow.setItemMeta((glow_meta));

        ItemMeta armor_meta = armor.getItemMeta();
        armor_meta.setDisplayName(ChatColor.BLUE + "Armor");
        armor.setItemMeta((armor_meta));

        ItemMeta base_meta = base.getItemMeta();
        base_meta.setDisplayName(ChatColor.GOLD + "Base");
        base.setItemMeta((base_meta));

        ItemMeta complete_meta = complete.getItemMeta();
        complete_meta.setDisplayName(ChatColor.GREEN + "Complete");
        complete.setItemMeta((complete_meta));

        ItemMeta cancel_meta = cancel.getItemMeta();
        cancel_meta.setDisplayName(ChatColor.RED + "Cancel");
        cancel.setItemMeta((cancel_meta));

        //add items to inventory
        create_menu.setItem(0,arms);
        create_menu.setItem(1, glow);
        create_menu.setItem(2, armor);
        create_menu.setItem(3,base);
        create_menu.setItem(7, complete);
        create_menu.setItem(8, cancel);


        //makes player open the menu
        player.openInventory(create_menu);



    }

    public void openConfirmMenu(Player player, Material options ){
        //creates confirm menu
        Inventory confirm_menu = Bukkit.createInventory(player, 36, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("confirm-menu")));

        ItemStack option_item = new ItemStack(options); // represent selected material
        ItemMeta option_meta = option_item.getItemMeta();
        if(options == Material.STICK){
            option_meta.setDisplayName(ChatColor.YELLOW + "Give Arms?");
            option_item.setItemMeta((option_meta));
        }else if(options == Material.BEACON) {
            option_meta.setDisplayName(ChatColor.YELLOW + "Add Glow?");
            option_item.setItemMeta((option_meta));
        }else if(options == Material.STONE_SLAB) {
        option_meta.setDisplayName(ChatColor.YELLOW + "Add base?");
        option_item.setItemMeta((option_meta));
        }

        ItemStack yes = new ItemStack(Material.GREEN_WOOL); // confirm
        ItemStack no = new ItemStack(Material.RED_WOOL); // cancel

        ItemMeta yes_meta = yes.getItemMeta();
        yes_meta.setDisplayName(ChatColor.GREEN + "YES!");
        yes.setItemMeta((yes_meta));

        ItemMeta no_meta = no.getItemMeta();
        no_meta.setDisplayName(ChatColor.RED + "NO!");
        no.setItemMeta((no_meta));

        confirm_menu.setItem(13, option_item);
        confirm_menu.setItem(21, yes);
        confirm_menu.setItem(23, no);

        //makes player open menu
        player.openInventory(confirm_menu);
    }


    public void openArmorMenu(Player player){
        // add new inventory to select the different armor pieces
        Inventory armor_menu = Bukkit.createInventory(player,54, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("armor-menu")));
        ArmorStand stand = this.amorstands.get(player);
        //get the current armor
        ItemStack current_head = new ItemStack(stand.getHelmet());
        ItemStack current_chest = new ItemStack(stand.getChestplate());
        ItemStack current_legs = new ItemStack(stand.getLeggings());
        ItemStack current_boots= new ItemStack(stand.getBoots());
        //leather armor
        ItemStack leather_helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack leather_chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack leather_leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack leather_boots = new ItemStack(Material.LEATHER_BOOTS);

        //diamond armor
        ItemStack diamond_helmet = new ItemStack(Material.DIAMOND_HELMET);
        ItemStack diamond_chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemStack diamond_leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemStack diamond_boots = new ItemStack(Material.DIAMOND_BOOTS);

        ItemStack confirm = new ItemStack(Material.GREEN_WOOL); // confirm adding armor

        ItemMeta confirm_meta = confirm.getItemMeta();
        confirm_meta.setDisplayName(ChatColor.GREEN + "Confirm");
        confirm.setItemMeta((confirm_meta));

        //current armor
        armor_menu.setItem(4, current_head);
        armor_menu.setItem(13, current_chest);
        armor_menu.setItem(22, current_legs);
        armor_menu.setItem(31, current_boots);

        //adding leather armor
        armor_menu.setItem(0, leather_helmet);
        armor_menu.setItem(2, leather_chest);
        armor_menu.setItem(6, leather_leggings);
        armor_menu.setItem(8, leather_boots);

        //adding diamond armor
        armor_menu.setItem(36, diamond_helmet);
        armor_menu.setItem(38, diamond_chest);
        armor_menu.setItem(42, diamond_leggings);
        armor_menu.setItem(44, diamond_boots);

        //adding confirm
        armor_menu.setItem(49, confirm);

        //open menu
        player.openInventory((armor_menu));




    }
    public void updateArmorDisplay(Player player){
        ArmorStand stand = this.amorstands.get(player);
        //get the current armor
        //helmet
        if(stand.getHelmet() !=null){
            ItemStack current_head = (stand.getHelmet());

        }



    }


}
