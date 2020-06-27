package me.krishna.armorstandgui.commands;

import me.krishna.armorstandgui.ArmorStandGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ArmorStandCommand implements CommandExecutor {

    //reference to the main class
    ArmorStandGui plugin;

    public ArmorStandCommand(ArmorStandGui plugin) {
        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    // makes sure a player is opening the gui
        if(sender instanceof Player){
            Player player = (Player) sender;
            plugin.openMainMenu(player);

        }

        return true;
    }
}
