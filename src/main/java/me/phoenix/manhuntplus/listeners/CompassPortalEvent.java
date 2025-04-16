/*     */ package me.phoenix.manhuntplus.listeners;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import me.phoenix.manhuntplus.Main;
/*     */ import net.md_5.bungee.api.ChatMessageType;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.api.chat.TextComponent;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.StructureType;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.CompassMeta;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class CompassPortalEvent implements Listener {
/*  25 */   private HashMap<UUID, Location> portal = new HashMap<>();
/*     */   
/*     */   private Main plugin;
/*     */   private Player spedrun;
/*     */   private Player currentRunner;
/*     */   private Location netherfortress;
/*  31 */   private ItemStack normalCompass = new ItemStack(Material.COMPASS);
/*  32 */   private ItemMeta normalMeta = this.normalCompass.getItemMeta();
/*     */   
/*     */   public CompassPortalEvent(Main plugin) {
/*  35 */     this.plugin = plugin;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteract(PlayerInteractEvent event) {
/*  40 */     Player player = event.getPlayer();
/*  41 */     if (this.plugin.speedRunners.size() > 0) {
/*  42 */       if (this.plugin.isHunter(player) && 
/*  43 */         this.plugin.speedRunners.size() > 1 && (
/*  44 */         player.getInventory().getItemInMainHand().getType() == Material.COMPASS || player.getInventory().getItemInOffHand().getType() == Material.COMPASS)) {
/*  45 */         int playerNum = ((Integer)this.plugin.huntersNumber.get(player.getName())).intValue();
/*  46 */         String name = this.plugin.speedRunners.get(playerNum);
/*  47 */         this.currentRunner = Bukkit.getPlayer(name);
/*     */       } 
/*     */ 
/*     */       
/*  51 */       if (this.plugin.isHunter(player) && (
/*  52 */         event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) && 
/*  53 */         player.getInventory().getItemInMainHand().getType() == Material.COMPASS && 
/*  54 */         this.plugin.getServer().getOnlinePlayers().size() > 1 && this.plugin.speedRunners.size() > 1) {
/*  55 */         int currentPlayer = ((Integer)this.plugin.huntersNumber.get(player.getName())).intValue();
/*  56 */         this.plugin.huntersNumber.put(player.getName(), Integer.valueOf((currentPlayer + 1) % this.plugin.speedRunners.size()));
/*  57 */         player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.GREEN + "No longer Hunting: " + this.currentRunner.getDisplayName()));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  62 */       if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
/*  63 */         if (this.plugin.isHunter(player) && 
/*  64 */           player.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
/*  65 */           ItemStack compass = player.getInventory().getItemInMainHand();
/*  66 */           CompassMeta compassmeta = (CompassMeta)compass.getItemMeta();
/*  67 */           if (this.plugin.speedRunners.size() > 1) {
/*  68 */             Location hunterloc = player.getLocation();
/*  69 */             if (this.currentRunner == null) {
/*  70 */               player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.DARK_RED + "Player not found. Left click to hunt other runner"));
/*     */               return;
/*     */             } 
/*  73 */             if (player.getWorld().getEnvironment() == this.currentRunner.getWorld().getEnvironment()) {
/*     */               
/*  75 */               Location runnerloc = this.currentRunner.getLocation();
/*  76 */               int blocks = (int)Math.round(hunterloc.distance(runnerloc));
/*     */               
/*  78 */               if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
/*     */                 try {
/*  80 */                   compassmeta.setLodestoneTracked(false);
/*  81 */                 } catch (NullPointerException e) {
/*  82 */                   player.sendMessage(ChatColor.RED + "Something went wrong with the compass. Please contact the author, and send the error in the console if there is one");
/*     */                   return;
/*     */                 } 
/*  85 */                 compassmeta.setLodestone(this.currentRunner.getLocation());
/*  86 */                 compass.setItemMeta((ItemMeta)compassmeta);
/*  87 */                 if (this.plugin.getConfig().getBoolean("distanceTracker")) {
/*  88 */                   player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.translateAlternateColorCodes('&', "&7&lDistance: &b&l" + blocks + "m &f- &7&lHunting: &a&l" + this.currentRunner.getDisplayName())));
/*     */                   return;
/*     */                 } 
/*  91 */                 player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.GREEN + "Hunting: " + this.currentRunner.getDisplayName()));
/*     */                 return;
/*     */               } 
/*  94 */               if (compassmeta.hasLodestone()) {
/*  95 */                 compass.setItemMeta(this.normalMeta);
/*     */               }
/*  97 */               player.setCompassTarget(this.currentRunner.getLocation());
/*  98 */               if (this.plugin.getConfig().getBoolean("distanceTracker")) {
/*  99 */                 player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.translateAlternateColorCodes('&', "&7&lDistance: &b&l" + blocks + "m &f- &7&lHunting: &a&l" + this.currentRunner.getDisplayName())));
/*     */                 return;
/*     */               } 
/* 102 */               player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.GREEN + "Hunting: " + this.currentRunner.getDisplayName()));
/*     */               return;
/*     */             } 
/* 105 */             if (player.getWorld().getEnvironment() == World.Environment.NORMAL) {
/* 106 */               Location portaloc = this.portal.get(this.currentRunner.getUniqueId());
/* 107 */               if (portaloc == null) {
/* 108 */                 player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.RED + "Could not track portal of runner"));
/*     */                 return;
/*     */               } 
/* 111 */               int distance = (int)Math.round(hunterloc.distance(portaloc));
/* 112 */               if (compassmeta.hasLodestone()) {
/* 113 */                 compass.setItemMeta(this.normalMeta);
/*     */               }
/* 115 */               player.setCompassTarget(portaloc);
/* 116 */               if (this.plugin.getConfig().getBoolean("distanceTracker")) {
/* 117 */                 player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.translateAlternateColorCodes('&', "&7&lDistance: &b&l" + distance + "m &f- &7&lHunting: &a&l" + this.currentRunner.getDisplayName() + "'s portal")));
/*     */                 return;
/*     */               } 
/* 120 */               player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.GREEN + "Hunting: " + this.currentRunner.getDisplayName() + "'s portal"));
/*     */               return;
/*     */             } 
/* 123 */             if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
/* 124 */               player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.RED + "No runner in the nether to track"));
/*     */               return;
/*     */             } 
/* 127 */             if (player.getWorld().getEnvironment() == World.Environment.THE_END) {
/* 128 */               player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.RED + "No runner in the end to track"));
/*     */             }
/*     */           } 
/*     */           
/* 132 */           if (this.plugin.speedRunners.size() == 1) {
/* 133 */             for (String runnername : this.plugin.speedRunners) {
/* 134 */               this.spedrun = Bukkit.getPlayer(runnername);
/*     */             }
/* 136 */             Location hunterloc = player.getLocation();
/* 137 */             if (player.getWorld() == this.spedrun.getWorld()) {
/*     */               
/* 139 */               Location runnerloc = this.spedrun.getLocation();
/* 140 */               int blocks = (int)Math.round(hunterloc.distance(runnerloc));
/*     */               
/* 142 */               if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
/*     */                 try {
/* 144 */                   compassmeta.setLodestoneTracked(false);
/* 145 */                 } catch (NullPointerException e) {
/* 146 */                   player.sendMessage(ChatColor.RED + "Something went wrong with the compass. Please contact the author, and send the error in the console if there is one");
/*     */                   return;
/*     */                 } 
/* 149 */                 compassmeta.setLodestone(this.spedrun.getLocation());
/* 150 */                 compass.setItemMeta((ItemMeta)compassmeta);
/* 151 */                 if (this.plugin.getConfig().getBoolean("distanceTracker")) {
/* 152 */                   player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.translateAlternateColorCodes('&', "&7&lDistance: &b&l" + blocks + "m &f- &7&lHunting: &a&l" + this.spedrun.getDisplayName())));
/*     */                   return;
/*     */                 } 
/* 155 */                 player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.GREEN + "Hunting: " + this.spedrun.getDisplayName()));
/*     */                 return;
/*     */               } 
/* 158 */               if (compassmeta.hasLodestone()) {
/* 159 */                 compass.setItemMeta(this.normalMeta);
/*     */               }
/* 161 */               player.setCompassTarget(this.spedrun.getLocation());
/* 162 */               if (this.plugin.getConfig().getBoolean("distanceTracker")) {
/* 163 */                 player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.translateAlternateColorCodes('&', "&7&lDistance: &b&l" + blocks + "m &f- &7&lHunting: &a&l" + this.spedrun.getDisplayName())));
/*     */                 return;
/*     */               } 
/* 166 */               player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.GREEN + "Hunting: " + this.spedrun.getDisplayName()));
/*     */               return;
/*     */             } 
/* 169 */             if (player.getWorld().getEnvironment() == World.Environment.NORMAL) {
/* 170 */               Location portaloc = this.portal.get(this.spedrun.getUniqueId());
/* 171 */               int distance = (int)Math.round(hunterloc.distance(portaloc));
/* 172 */               if (compassmeta.hasLodestone()) {
/* 173 */                 compass.setItemMeta(this.normalMeta);
/*     */               }
/* 175 */               player.setCompassTarget(portaloc);
/* 176 */               if (this.plugin.getConfig().getBoolean("distanceTracker")) {
/* 177 */                 player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.translateAlternateColorCodes('&', "&7&lDistance: &b&l" + distance + "m &f- &7&lHunting: &a&l" + this.spedrun.getDisplayName() + "'s portal")));
/*     */                 return;
/*     */               } 
/* 180 */               player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.GREEN + "Hunting: " + this.spedrun.getDisplayName() + "'s portal"));
/*     */               return;
/*     */             } 
/* 183 */             if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
/* 184 */               player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.RED + "No runner in the nether to track"));
/*     */               return;
/*     */             } 
/* 187 */             if (player.getWorld().getEnvironment() == World.Environment.THE_END) {
/* 188 */               player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.RED + "No runner in the end to track"));
/*     */             }
/*     */           } 
/*     */         } 
/* 192 */         if (!this.plugin.inGame)
/* 193 */           return;  if (!this.plugin.isRunner(player))
/* 194 */           return;  if (player.getInventory().getItemInMainHand().getType() != Material.COMPASS)
/* 195 */           return;  if (!player.getInventory().getItemInMainHand().hasItemMeta())
/* 196 */           return;  if (player.getInventory().getItemInMainHand().getItemMeta().getLore() != null && 
/* 197 */           player.getWorld().getEnvironment() == World.Environment.NETHER) {
/* 198 */           ItemStack compass = player.getInventory().getItemInMainHand();
/* 199 */           CompassMeta compassMeta = (CompassMeta)compass.getItemMeta();
/* 200 */           if (!compassMeta.hasLodestone()) {
/* 201 */             Location fortress = player.getWorld().locateNearestStructure(player.getLocation(), StructureType.NETHER_FORTRESS, 2147483647, true);
/* 202 */             this.netherfortress = fortress;
/*     */           } 
/*     */           try {
/* 205 */             compassMeta.setLodestoneTracked(false);
/* 206 */           } catch (NullPointerException e) {
/* 207 */             e.printStackTrace();
/* 208 */             player.sendMessage(ChatColor.RED + "Something went wrong with the compass. Please contact the author, and send the error in the console");
/*     */             return;
/*     */           } 
/* 211 */           compassMeta.setLodestone(this.netherfortress);
/* 212 */           compass.setItemMeta((ItemMeta)compassMeta);
/* 213 */           player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.GREEN + "Tracking: Nether Fortress"));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 218 */     if (this.plugin.isHunter(player) && player.getInventory().getItemInMainHand().getType() == Material.COMPASS && (
/* 219 */       event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
/* 220 */       player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent)new TextComponent(ChatColor.RED + "No players to track"));
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPortal(PlayerTeleportEvent event) {
/* 226 */     Player player = event.getPlayer();
/* 227 */     if (this.plugin.isRunner(player)) {
/* 228 */       if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL || event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
/* 229 */         this.portal.put(player.getUniqueId(), player.getLocation());
/*     */       }
/* 231 */       if (!this.plugin.getConfig().getBoolean("fortressTracker"))
/* 232 */         return;  if (event.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)
/* 233 */         return;  if (player.getWorld().getEnvironment() == World.Environment.NORMAL) {
/* 234 */         if (player.getInventory().firstEmpty() == -1) {
/* 235 */           player.sendMessage(ChatColor.GREEN + "Your inventory is full. The nether fortress tracker has been dropped on the ground near you.");
/* 236 */           player.getWorld().dropItemNaturally(player.getLocation(), getFortressTracker());
/*     */           return;
/*     */         } 
/* 239 */         player.getInventory().addItem(new ItemStack[] { getFortressTracker() });
/* 240 */       } else if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
/* 241 */         ItemStack tracker = null;
/* 242 */         for (ItemStack i : player.getInventory().getContents()) {
/* 243 */           if (i != null && 
/* 244 */             i.getType() == Material.COMPASS && i.getItemMeta().getLore() != null) {
/* 245 */             tracker = i;
/*     */           }
/*     */         } 
/* 248 */         if (tracker != null)
/* 249 */           player.getInventory().remove(tracker); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private ItemStack getFortressTracker() {
/* 255 */     ItemStack fortressTracker = new ItemStack(Material.COMPASS);
/* 256 */     ItemMeta meta = fortressTracker.getItemMeta();
/* 257 */     meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Fortress Tracker");
/* 258 */     List<String> lore = new ArrayList<>();
/* 259 */     lore.add(ChatColor.GREEN + "Right click to locate the nearest fortress");
/* 260 */     meta.setLore(lore);
/* 261 */     fortressTracker.setItemMeta(meta);
/* 262 */     return fortressTracker;
/*     */   }
/*     */ }


/* Location:              C:\Users\gygab\Downloads\ManhuntPlus 1.4.1.jar!\me\phoenix\manhuntplus\listeners\CompassPortalEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */