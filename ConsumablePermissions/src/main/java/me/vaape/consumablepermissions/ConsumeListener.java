package me.vaape.consumablepermissions;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ConsumeListener implements Listener{

	public ConsumablePermissions plugin;
	public LuckPerms luckPerms;

	public ConsumeListener(ConsumablePermissions passedPlugin) {
		this.plugin = passedPlugin;
		this.luckPerms = plugin.getLuckPerms();
	}

	@EventHandler
	public void onConsumePotion (PlayerItemConsumeEvent event) {
		//Potoin
		if (event.getItem().getType() != Material.POTION) return;

		if (!event.getItem().getItemFlags().contains(ItemFlag.HIDE_POTION_EFFECTS)) return;
		ItemStack potion = event.getItem();
		String auraName = getAuraName(potion);
		if (auraName == null) return;
		String permission = formatAuraName(auraName);
		Player player = event.getPlayer();
		if (player.hasPermission(permission)) { player.sendMessage(ChatColor.RED + "You already own this aura."); event.setCancelled(true); return; }
		ConsumablePermissions.getInstance().addPermission(player, permission);
		player.sendMessage(ChatColor.of("#c2fff8") + "" + ChatColor.BOLD + auraName + " aura unlocked! Use /aura to activate.");
		Bukkit.getServer().broadcastMessage(ChatColor.of("#c2fff8") + "" + ChatColor.BOLD + player.getName() + " has unlocked the " + auraName + " aura!");
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1f, 1f);
	}

	@EventHandler
	public void onConsumeCookie (PlayerItemConsumeEvent event) {
		//Cookie
		if (event.getItem().getType() != Material.COOKIE) return;

		ItemStack cookie = event.getItem();
		if (!isPetCookie(cookie)) return;
		String petName = getPetName(cookie);
		if (petName == null) return;
		String permission = "mpet." + getPermissionFromPetName(petName);
		Player player = event.getPlayer();
		if (player.hasPermission(permission)) { player.sendMessage(ChatColor.RED + "You already own this pet."); event.setCancelled(true); return; }
		ConsumablePermissions.getInstance().addPermission(player, permission);
		player.sendMessage(ChatColor.of("#ff6200") + "" + ChatColor.BOLD + petName + " pet unlocked! Use /pet to activate.");
		Bukkit.getServer().broadcastMessage(ChatColor.of("#ff6200") + "" + ChatColor.BOLD + player.getName() + " has unlocked a " + petName + " pet!");
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_MOOSHROOM_CONVERT, 1f, 0.7f);
	}

	public boolean isPetCookie(ItemStack item) {
		if (item.getType() != Material.COOKIE) return false;;
		if (!item.getItemMeta().hasDisplayName()) return false;
		String displayName = item.getItemMeta().getDisplayName();
		if (!displayName.contains("Pet Cookie")) return false;
		if (!item.getItemMeta().hasLore()) return false;
		return true;
	}

	public String getPetName(ItemStack cookie) {
		ItemMeta meta = cookie.getItemMeta();
		String displayName = meta.getDisplayName();
		int endOfNameIndex = displayName.indexOf(" Pet");
		return displayName.substring(4, endOfNameIndex);
	}

	public String getAuraName(ItemStack item) {
		String name = item.getItemMeta().getDisplayName();
		if (!name.contains("Elixir")) return null;
		int secondSpaceIndex = name.indexOf(" ", name.indexOf(" ") + 1);
		return name.substring(secondSpaceIndex + 1);
	}

	public String formatAuraName(String auraName) {
		String formattedAuraName = auraName.toLowerCase().replace(" ", "_");
		return "auras.aura." + formattedAuraName;
	}

	public void addPermission(Player player, String permission) {
		User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
		// Add the permission
		user.data().add(Node.builder(permission).build());

		// Now we need to save changes.
		luckPerms.getUserManager().saveUser(user);
	}

	public String getPermissionFromPetName(String petName) {
		switch (petName) {
			case "MiniMi": return "cartme";
			case "Drowzee": return "drowzee";
			case "Easter Steve": return "eastersteve";
			case "Gifterino": return "gifterino";
			case "Snowman": return "snowman";
			case "Halloween Pumpkin": return "halloweenpumpkin";
			case "Pumpkin": return "pumpkinslime1";
			case "Lantern": return "pumpkinslime2";
			case "Uncle Sam": return "unclesam";
			case "4th of July": return "4thjuly";
			case "Ghost": return "ghost";
			case "Globe": return "globe";
			case "Hippie Van": return "hippievan";
			case "King of Fire": return "kingoffire";
			case "Penguin": return "penguin";
			case "Red Panda": return "redpanda";
			case "Tie Fighter": return "tiefighter";
			case "Archer Minion": return "archerminion";
			case "BB-8": return "bb8";
			case "BB-9E": return "bb9e";
			case "Bear": return "bear";
			case "Beacon": return "beacon";
			case "Baby Ghast": return "babyghast";
			case "Chomp": return "chomp";
			case "Dragon": return "dragon";
			case "Curious George": return "curiousgeorge";
			case "Park Ranger Car": return "parkrangercar";
			case "Ambulance": return "ambulance";
			case "Police Car": return "policecar";
			case "Jackal": return "jackal";
			case "King Slime": return "kingslime";
			case "Pride": return "pride";
			case "Ninja": return "ninjaminion";
			case "Shark": return "shark";
			case "Toilet": return "toilet";
			case "Tree": return "tree";
			case "Wasp": return "wasp";
			case "Royal Guard Minion": return "royalguardminion";
			case "Captain Rex": return "captainrex";
			case "332nd Company Clone Trooper": return "332ndCT";
			case "501st Company Clone Trooper": return "501stCT";
			case "Jesse": return "jesse";
			case "Phase 2 Clone Trooper": return "phase2CT";
			case "Commander Cody": return "commandercody";
			case "Blue Koopa": return "bluekoopa";
			case "Grey Koopa": return "greykoopa";
			case "Koop": return "koop";
			case "Koopa": return "koopa";
			case "Green Koopa": return "koopared";
			case "Red Koopa": return "koopagreen";
			case "Lime Koopa": return "limekoopa";
			case "Evaverse": return "Evaverse";
			case "Gamer Girl": return "gamergirl";
			case "Insect": return "Insect1";
			case "Green Car": return "greencar";
			case "Yellow Car": return "yellowcar";
			case "Red Car": return "redcar";
			case "Snake": return "snake";
			case "Spearman Minion": return "spearmanminion";
			case "Tadpole": return "tadpole";
			case "Allagator": return "allagator";
			case "Axalotl": return "axalotl";
			case "Blue Slime": return "blueslime";
			case "Cool Slime": return "coolslime";
			case "Cyan Slime": return "cyanslime";
			case "Cyborg Slime": return "cyborgslime";
			case "Diamond Slime": return "diamondslime";
			case "Emerald Slime": return "emeraldslime";
			case "Fishy Slime": return "fishyslime";
			case "Gold Slime": return "goldslime";
			case "Honey Slime": return "honeyslime";
			case "Mossy Slime": return "mossyslime";
			case "Orange Slime": return "orangeslime";
			case "Pink Slime": return "pinkslime";
			case "Pride Slime": return "prideslime";
			case "Red Slime": return "redslime";
			case "Shulker Slime": return "shulkerslime";
			case "Stone Slime": return "stoneslime";
			case "Wood Slime": return "woodslime";
			case "Yellow Slime": return "yellowslime";
			case "Black Bee": return "blackbee";
			case "Blue Bee": return "bluebee";
			case "Brown Bee": return "brownbee";
			case "Grey Bee": return "greybee";
			case "Green Bee": return "greenbee";
			case "Light Blue Bee": return "lightbluebee";
			case "Light Grey Bee": return "lightgreybee";
			case "Lilac Bee": return "lilacbee";
			case "Lime Bee": return "limebee";
			case "Magenta Bee": return "magentabee";
			case "Orange Bee": return "orangebee";
			case "Pink Bee": return "pinkbee";
			case "Purple Bee": return "purplebee";
			case "Red Bee": return "redbee";
			case "Unicorn": return "unicorn";
			case "Bathwater": return "bathwater";

			case "Zombie Horse": return "zombiehorse";
			case "Zombie": return "zombie";
			case "Wolf": return "wolf";
			case "Wither Skeleton": return "witherskeleton";
			case "Witch": return "witch";
			case "Vindicator": return "vindicator";
			case "Villager": return "villager";
			case "Turtle": return "turtle";
			case "Tropical Fish": return "tropicalfish";
			case "Stray": return "stray";
			case "Spider": return "spider";
			case "Skeleton Horse": return "skeletonhorse";
			case "Skeleton": return "skeleton";
			case "Silverfish": return "silverfish";
			case "Sheep": return "sheep";
			case "Salmon": return "salmon";
			case "Rabbit": return "rabbit";
			case "Polarbear": return "polarbear";
			case "Pillager": return "hi";
			case "Pig": return "pig";
			case "Phantom": return "phantom";
			case "Parrot": return "parrot";
			case "Ocelot": return "ocelot";
			case "Mule": return "mule";
			case "Magma Cube": return "magma cube";
			case "Llama": return "llama";
			case "Husk": return "husk";
			case "Horse": return "horse";
			case "Fox": return "fox";
			case "Evoker": return "evoker";
			case "Endermite": return "endermite";
			case "Enderman": return "enderman";
			case "Drowned": return "drowned";
			case "Donkey": return "donkey";
			case "Dolphin": return "dolphin";
			case "Creeper": return "creeper";
			case "Cow": return "cow";
			case "Cod": return "cod";
			case "Chicken": return "chicken";
			case "Cave Spider": return "cavespider";
			case "Cat": return "cat";
			case "Blaze": return "blaze";
			case "Bat": return "bat";

			case "Baby Zombie Horse": return "babyzombiehorse";
			case "Baby Zombie": return "babyzombie";
			case "Baby Turtle": return "babyturtle";
			case "Baby Skeleton Horse": return "babyskeletonhorse";
			case "Baby Sheep": return "babysheep";
			case "Baby Rabbit": return "babyrabbit";
			case "Baby Polarbear": return "babypolarbear";
			case "Baby Pig": return "babypig";
			case "Baby Ocelot": return "babyocelot";
			case "Baby Llama": return "babyllama";
			case "Baby Horse": return "babyhorse";
			case "Baby Fox": return "babyfox";
			case "Baby Drowned": return "babydrowned";
			case "Baby Donkey": return "babydonkey";
			case "Baby Cow": return "babycow";
			case "Baby Chicken": return "babychicken";
			default: return null;
		}
	}
}