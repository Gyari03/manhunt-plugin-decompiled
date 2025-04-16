/*     */ package me.phoenix.manhuntplus.commands;
/*     */ import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/*     */ import me.phoenix.manhuntplus.Main;
/*     */ import me.phoenix.manhuntplus.api.HuntEndEvent;
/*     */ import me.phoenix.manhuntplus.api.HuntStartEvent;
/*     */ import me.phoenix.manhuntplus.api.TeamRandomizeEvent;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ public class HuntPlus implements CommandExecutor {
/*     */   private Main plugin;
/*  23 */   private List<String> roleShuffleList = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public HuntPlus(Main plugin) {
/*  27 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, final String[] args) {
/*  32 */     if (label.equalsIgnoreCase("huntplus")) {
/*  33 */       if (!(sender instanceof Player)) {
/*  34 */         sender.sendMessage("[Manhunt+] You must be a player to run this command!");
/*  35 */         return true;
/*     */       } 
/*  37 */       final Player player = (Player)sender;
/*  38 */       if (player.hasPermission("manhuntplus.huntplus")) {
/*  39 */         if (args.length == 0) {
/*  40 */           player.sendMessage(ChatColor.GREEN + "Please use '/huntplus help' to see list of commands");
/*  41 */           return true;
/*     */         } 
/*  43 */         if (args.length == 1) {
/*  44 */           switch (args[0].toLowerCase())
/*     */           { case "help":
/*  46 */               player.sendMessage(ChatColor.YELLOW + "/huntplus start <seconds>:");
/*  47 */               player.sendMessage(ChatColor.GREEN + "Starts a hunt with <seconds> headstart");
/*  48 */               player.sendMessage(ChatColor.YELLOW + "/huntplus stop: " + ChatColor.GREEN + "Stops the hunt");
/*  49 */               player.sendMessage(ChatColor.YELLOW + "/huntplus info: " + ChatColor.GREEN + "Gives info on the plugin");
/*  50 */               player.sendMessage(ChatColor.YELLOW + "/huntplus randomize <count>:");
/*  51 */               player.sendMessage(ChatColor.GREEN + "Randomizes teams with <count> runners");
/*  52 */               player.sendMessage(ChatColor.YELLOW + "/huntplus reload: " + ChatColor.GREEN + "Reloads the config");
/*  53 */               player.sendMessage(ChatColor.YELLOW + "/huntplus rules: " + ChatColor.GREEN + "Describes rules of manhunt");
/*  54 */               player.sendMessage(ChatColor.YELLOW + "/huntplus help: " + ChatColor.GREEN + "Displays this page");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  95 */               return true;case "stop": if (this.plugin.inGame || this.plugin.countingDown || this.plugin.waitingRunner) { HuntEndEvent gameEndEvent = new HuntEndEvent(); Bukkit.getServer().getPluginManager().callEvent((Event)gameEndEvent); Bukkit.broadcastMessage(ChatColor.GREEN + "The hunt has ended. Use /huntplus start <time> to start again."); this.plugin.inGame = false; this.plugin.countingDown = false; this.plugin.waitingRunner = false; for (String names : this.plugin.hunters) { Player hunter = Bukkit.getPlayer(names); for (PotionEffect e : hunter.getActivePotionEffects()) hunter.removePotionEffect(e.getType());  }  if (this.plugin.deadRunners.size() > 0) { this.plugin.speedRunners.addAll(this.plugin.deadRunners); this.plugin.deadRunners.clear(); }  return true; }  player.sendMessage(ChatColor.RED + "No hunt is currently active!"); return true;case "info": player.sendMessage(ChatColor.GREEN + "Manhunt+ by Umbral Phoenix"); player.sendMessage(ChatColor.GREEN + "Version: " + this.plugin.getDescription().getVersion()); player.sendMessage(ChatColor.GREEN + this.plugin.getDescription().getWebsite()); return true;case "reload": this.plugin.reloadConfig(); player.sendMessage(ChatColor.GREEN + "Manhunt+ has been reloaded!"); player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F); return true;case "rules": player.openBook(this.plugin.ruleBook()); return true; }  player.sendMessage(ChatColor.RED + "Please use '/huntplus help' for commands"); return true;
/*     */         } 
/*  97 */         if (args.length == 2) {
/*  98 */           if (args[0].equalsIgnoreCase("start")) {
/*  99 */             if (this.plugin.inGame) {
/* 100 */               player.sendMessage(ChatColor.GREEN + "The hunt has already started!");
/* 101 */               return true;
/*     */             } 
/* 103 */             if (checkPlayer(player)) {
/* 104 */               if (args[1].equalsIgnoreCase("0")) {
/* 105 */                 Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "The hunt will begin once a runner strikes a hunter.");
/* 106 */                 useConfig(player);
/* 107 */                 this.plugin.waitingRunner = true;
/* 108 */                 return true;
/*     */               } 
/*     */               try {
/* 111 */                 Integer.parseInt(args[1]);
/* 112 */               } catch (NumberFormatException ex) {
/* 113 */                 player.sendMessage(ChatColor.RED + "Please insert an integer to set the time of the headstart");
/* 114 */                 return true;
/*     */               } 
/* 116 */               Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "The hunters will be released in " + args[1] + " seconds!");
/* 117 */               this.plugin.countingDown = true;
/* 118 */               useConfig(player);
/* 119 */               for (String name : this.plugin.hunters) {
/* 120 */                 Player hunter = Bukkit.getPlayer(name);
/* 121 */                 hunter.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2147483647, 3, true, false));
/* 122 */                 hunter.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2147483647, -50, true, false));
/* 123 */                 hunter.setWalkSpeed(0.0F);
/*     */               } 
/* 125 */               Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable()
/*     */                   {
/* 127 */                     int countdown = Integer.parseInt(args[1]);
/*     */ 
/*     */                     
/*     */                     public void run() {
/* 131 */                       if (this.countdown == 0) {
/* 132 */                         Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c&lThe hunters have been released!"));
/* 133 */                         for (String name : HuntPlus.this.plugin.speedRunners) {
/* 134 */                           Player runner = Bukkit.getPlayer(name);
/* 135 */                           runner.sendTitle(ChatColor.RED + "Hunters Released!", ChatColor.GRAY + "Run Away", 5, 25, 5);
/*     */                           try {
/* 137 */                             runner.getLocation().getWorld().playSound(player.getLocation(), Sound.valueOf(HuntPlus.this.plugin.getConfig().getString("startSound")), 1.0F, 1.0F);
/* 138 */                           } catch (Exception e) {
/* 139 */                             Bukkit.broadcastMessage(ChatColor.RED + "Playing default start sound as an error occurred. More information can be found in the console.");
/* 140 */                             HuntPlus.this.plugin.getLogger().severe("Start sound is invalid. Change configuration option to a valid start sound");
/* 141 */                             runner.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1.0F, 1.0F);
/*     */                           } 
/*     */                         } 
/* 144 */                         for (String names : HuntPlus.this.plugin.hunters) {
/* 145 */                           Player hunter = Bukkit.getPlayer(names);
/* 146 */                           hunter.setWalkSpeed(0.2F);
/* 147 */                           for (PotionEffect e : hunter.getActivePotionEffects()) {
/* 148 */                             hunter.removePotionEffect(e.getType());
/*     */                           }
/* 150 */                           hunter.getWorld().playSound(hunter.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F);
/* 151 */                           hunter.getWorld().createExplosion(hunter.getLocation(), 5.0F, false, false);
/* 152 */                           if (HuntPlus.this.plugin.speedRunners.size() > 1) {
/* 153 */                             hunter.sendTitle(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Kill the runners", ChatColor.GRAY + "It is your sole purpose", 15, 70, 20); continue;
/*     */                           } 
/* 155 */                           hunter.sendTitle(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Kill the runner", ChatColor.GRAY + "It is your sole purpose", 15, 70, 20);
/*     */                         } 
/*     */                         
/* 158 */                         HuntPlus.this.plugin.countingDown = false;
/* 159 */                         HuntPlus.this.plugin.inGame = true;
/* 160 */                         HuntStartEvent gameStartEvent = new HuntStartEvent();
/* 161 */                         Bukkit.getServer().getPluginManager().callEvent((Event)gameStartEvent);
/* 162 */                         Bukkit.getScheduler().cancelTasks((Plugin)HuntPlus.this.plugin);
/* 163 */                       } else if (this.countdown == 1) {
/* 164 */                         Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "The hunters shall be released in one second");
/* 165 */                         HuntPlus.this.playHunterCountdownSound();
/* 166 */                       } else if (this.countdown == 2) {
/* 167 */                         Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "The hunters shall be released in two seconds");
/* 168 */                         HuntPlus.this.playHunterCountdownSound();
/* 169 */                       } else if (this.countdown == 3) {
/* 170 */                         Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "The hunters shall be released in three seconds");
/* 171 */                         HuntPlus.this.playHunterCountdownSound();
/*     */                       } 
/* 173 */                       this.countdown--;
/*     */                     }
/*     */                   }0L, 20L);
/*     */               
/* 177 */               return true;
/*     */             } 
/* 179 */             return false;
/* 180 */           }  if (args[0].equalsIgnoreCase("randomize")) {
/* 181 */             if (args[1].equalsIgnoreCase("0")) {
/* 182 */               player.sendMessage(ChatColor.RED + "Please set an integer above zero to set runner amount");
/* 183 */               return true;
/*     */             } 
/*     */             try {
/* 186 */               Integer.parseInt(args[1]);
/* 187 */             } catch (NumberFormatException ex) {
/* 188 */               player.sendMessage(ChatColor.RED + "Please set an integer above zero to set runner amount");
/* 189 */               return true;
/*     */             } 
/* 191 */             int runnerAmount = Integer.parseInt(args[1]);
/* 192 */             int maximumAllowedRunners = Bukkit.getOnlinePlayers().size() - 1;
/* 193 */             if (runnerAmount > maximumAllowedRunners) {
/* 194 */               player.sendMessage(ChatColor.RED + "Please lower your number until there can be at least one hunter set");
/* 195 */               return true;
/*     */             } 
/* 197 */             for (String names : this.plugin.hunters) {
/* 198 */               Player hunter = Bukkit.getPlayer(names);
/* 199 */               hunter.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.COMPASS) });
/*     */             } 
/* 201 */             this.plugin.hunters.clear();
/* 202 */             this.plugin.speedRunners.clear();
/* 203 */             for (Player p : Bukkit.getOnlinePlayers()) {
/* 204 */               this.roleShuffleList.add(p.getName());
/*     */             }
/* 206 */             Random random = new Random();
/* 207 */             for (int i = 0; i < runnerAmount; i++) {
/* 208 */               int randomNumber = random.nextInt(this.roleShuffleList.size());
/* 209 */               String chosenName = this.roleShuffleList.get(randomNumber);
/* 210 */               this.plugin.speedRunners.add(chosenName);
/* 211 */               this.roleShuffleList.remove(chosenName);
/*     */             } 
/* 213 */             this.plugin.hunters.addAll(this.roleShuffleList);
/* 214 */             for (String names : this.plugin.hunters) {
/* 215 */               Player hunter = Bukkit.getPlayer(names);
/* 216 */               hunter.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COMPASS) });
/* 217 */               this.plugin.huntersNumber.put(hunter.getName(), Integer.valueOf(0));
/*     */             } 
/* 219 */             this.roleShuffleList.clear();
/* 220 */             if (this.plugin.speedRunners.size() == 1) {
/* 221 */               Player runner = Bukkit.getPlayer(this.plugin.speedRunners.get(0));
/* 222 */               Bukkit.broadcastMessage(ChatColor.GREEN + "The teams have been randomized. The runner is " + runner.getDisplayName());
/* 223 */               player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F);
/* 224 */               TeamRandomizeEvent teamRandomizeEvent = new TeamRandomizeEvent();
/* 225 */               Bukkit.getServer().getPluginManager().callEvent((Event)teamRandomizeEvent);
/* 226 */               return true;
/*     */             } 
/* 228 */             for (String displayName : this.plugin.speedRunners) {
/* 229 */               Player runner = Bukkit.getPlayer(displayName);
/* 230 */               this.roleShuffleList.add(runner.getDisplayName());
/*     */             } 
/* 232 */             String runnerList = String.join(", ", (Iterable)this.roleShuffleList);
/* 233 */             Bukkit.broadcastMessage(ChatColor.GREEN + "The teams have been randomized. The runners are: " + runnerList);
/* 234 */             this.roleShuffleList.clear();
/* 235 */             player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F);
/* 236 */             TeamRandomizeEvent shuffleEvent = new TeamRandomizeEvent();
/* 237 */             Bukkit.getServer().getPluginManager().callEvent((Event)shuffleEvent);
/* 238 */             return true;
/*     */           } 
/* 240 */           player.sendMessage(ChatColor.RED + "Please use '/huntplus help' for commands");
/* 241 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 245 */     return false;
/*     */   }
/*     */   
/*     */   private void playHunterCountdownSound() {
/* 249 */     for (String hunterName : this.plugin.hunters) {
/* 250 */       Player p = Bukkit.getPlayer(hunterName);
/* 251 */       p.getWorld().playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean checkPlayer(Player player) {
/* 256 */     if (this.plugin.speedRunners.size() == 0 && this.plugin.hunters.size() == 0) {
/* 257 */       player.sendMessage(ChatColor.GREEN + "Please select a runner and a hunter before starting");
/* 258 */       return false;
/*     */     } 
/* 260 */     if (this.plugin.speedRunners.size() == 0) {
/* 261 */       player.sendMessage(ChatColor.GREEN + "Please select a runner before starting");
/* 262 */       return false;
/*     */     } 
/* 264 */     if (this.plugin.hunters.size() == 0) {
/* 265 */       player.sendMessage(ChatColor.GREEN + "Please select a hunter before starting");
/* 266 */       return false;
/*     */     } 
/* 268 */     return true;
/*     */   }
/*     */   
/*     */   private void useConfig(Player player) {
/* 272 */     if (this.plugin.getConfig().getBoolean("healStart")) {
/* 273 */       for (String names : this.plugin.hunters) {
/* 274 */         Player hunt = Bukkit.getPlayer(names);
/* 275 */         hunt.setHealth(20.0D);
/*     */       } 
/* 277 */       for (String strings : this.plugin.speedRunners) {
/* 278 */         Player runner = Bukkit.getPlayer(strings);
/* 279 */         runner.setHealth(20.0D);
/*     */       } 
/*     */     } 
/* 282 */     if (this.plugin.getConfig().getBoolean("feedStart")) {
/* 283 */       for (String names : this.plugin.hunters) {
/* 284 */         Player hunt = Bukkit.getPlayer(names);
/* 285 */         hunt.setFoodLevel(20);
/*     */       } 
/* 287 */       for (String strings : this.plugin.speedRunners) {
/* 288 */         Player rn = Bukkit.getPlayer(strings);
/* 289 */         rn.setFoodLevel(20);
/*     */       } 
/*     */     } 
/* 292 */     if (this.plugin.getConfig().getBoolean("dayStart")) {
/* 293 */       World world = player.getWorld();
/* 294 */       world.setTime(1000L);
/*     */     } 
/* 296 */     if (this.plugin.getConfig().getBoolean("clearStart")) {
/* 297 */       for (String names : this.plugin.hunters) {
/* 298 */         Player hunt = Bukkit.getPlayer(names);
/* 299 */         hunt.getInventory().clear();
/* 300 */         hunt.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COMPASS) });
/*     */       } 
/* 302 */       for (String names : this.plugin.speedRunners) {
/* 303 */         Player rn = Bukkit.getPlayer(names);
/* 304 */         rn.getInventory().clear();
/*     */       } 
/*     */     } 
/* 307 */     if (this.plugin.getConfig().getBoolean("achievementReset")) {
/* 308 */       for (String names : this.plugin.hunters) {
/* 309 */         Player hunter = Bukkit.getPlayer(names);
/* 310 */         Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "advancement revoke " + hunter.getDisplayName() + " everything");
/*     */       } 
/* 312 */       for (String names : this.plugin.speedRunners) {
/* 313 */         Player runner = Bukkit.getPlayer(names);
/* 314 */         Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "advancement revoke " + runner.getDisplayName() + " everything");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\gygab\Downloads\ManhuntPlus 1.4.1.jar!\me\phoenix\manhuntplus\commands\HuntPlus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */