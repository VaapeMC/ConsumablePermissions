package me.vaape.consumablepermissions;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ConsumablePermissions extends JavaPlugin implements Listener {
    private final FileConfiguration config = this.getConfig();

    public static ConsumablePermissions instance;
    public static ConsumablePermissions getInstance() {
        return instance;
    }

    public LuckPerms luckPerms;
    public LuckPerms getLuckPerms() { return luckPerms; }

    public ConsumeListener consumeListener;
    public ConsumeListener getConsumeListener() { return consumeListener; }

    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
        loadConfiguration();
        getLogger().info(ChatColor.GREEN + "ConsumablePermissions has been enabled!");

        consumeListener = new ConsumeListener(getInstance());
        this.getServer().getPluginManager().registerEvents(consumeListener, instance);

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }

    }

    public void loadConfiguration() {
        config.set(("time of server start"), new Date());
        config.options().copyDefaults(true);
        saveConfig();
    }

    public void onDisable() {
        saveConfig();
        instance = null;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("hideeffects")) {
            if (sender.hasPermission("consumablepermissions.hideeffects")) {
                if (sender instanceof Player player) {
                    ItemStack hand = player.getInventory().getItemInMainHand();
                    if (hand.getType() != Material.POTION) { player.sendMessage(ChatColor.RED + "Hold a potion to do this."); return false; }
                    hand.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    hand.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
                }
            }
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
        }

        return false;
    }

    public void addPermission(Player player, String permission) {
        User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
        // Add the permission
        user.data().add(Node.builder(permission).build());

        // Now we need to save changes.
        luckPerms.getUserManager().saveUser(user);
    }

}