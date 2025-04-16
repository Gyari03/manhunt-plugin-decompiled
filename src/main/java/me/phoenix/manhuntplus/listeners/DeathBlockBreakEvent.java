/*     */ package me.phoenix.manhuntplus.listeners;
/*     */ import me.phoenix.manhuntplus.Main;
/*     */ import me.phoenix.manhuntplus.api.HuntEndEvent;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Particle;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class DeathBlockBreakEvent implements Listener {
/*     */   public DeathBlockBreakEvent(Main plugin) {
/*  21 */     this.plugin = plugin;
/*     */   }
/*     */   private Main plugin;
/*     */   @EventHandler
/*     */   public void onPlayerDeath(PlayerDeathEvent event) {
/*  26 */     Player player = event.getEntity();
/*  27 */     if (this.plugin.speedRunners.size() == 1 && 
/*  28 */       this.plugin.isRunner(player) && 
/*  29 */       this.plugin.inGame) {
/*  30 */       String deathMessage = ChatColor.GREEN + event.getDeathMessage();
/*  31 */       event.setDeathMessage(deathMessage);
/*  32 */       if (this.plugin.hunters.size() > 1) {
/*  33 */         Bukkit.broadcastMessage(ChatColor.GREEN + "The hunters win! Do /huntplus start to play again!");
/*     */       } else {
/*  35 */         Bukkit.broadcastMessage(ChatColor.GREEN + "The hunter wins! Do /huntplus start to play again!");
/*     */       } 
/*  37 */       if (this.plugin.deadRunners.size() > 0) {
/*  38 */         for (String name : this.plugin.deadRunners) {
/*  39 */           Player deadRunner = Bukkit.getPlayer(name);
/*  40 */           if (deadRunner.isOnline()) {
/*  41 */             this.plugin.speedRunners.add(name);
/*     */           }
/*     */         } 
/*  44 */         this.plugin.deadRunners.clear();
/*     */       } 
/*  46 */       this.plugin.inGame = false;
/*  47 */       HuntEndEvent gameEndEvent = new HuntEndEvent();
/*  48 */       Bukkit.getServer().getPluginManager().callEvent((Event)gameEndEvent);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onLethalHit(EntityDamageEvent event) {
/*  56 */     if (event.getEntity() instanceof Player) {
/*  57 */       Player player = (Player)event.getEntity();
/*  58 */       if (this.plugin.speedRunners.size() > 1 && this.plugin.inGame && 
/*  59 */         this.plugin.isRunner(player) && 
/*  60 */         event.getFinalDamage() > player.getHealth()) {
/*  61 */         event.setCancelled(true);
/*  62 */         player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1.0F, 1.0F);
/*  63 */         if (this.plugin.getConfig().getString("particleEffect") != null && !this.plugin.getConfig().getString("particleEffect").equals("NONE")) {
/*     */           try {
/*  65 */             player.getWorld().spawnParticle(Particle.valueOf(this.plugin.getConfig().getString("particleEffect")), player.getLocation(), 20);
/*  66 */           } catch (Exception e) {
/*  67 */             this.plugin.getLogger().severe("Particle type is invalid. Playing default particle. Please change it in the config file");
/*  68 */             player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 20);
/*     */           } 
/*     */         }
/*  71 */         player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.0F, 1.0F);
/*  72 */         player.setGameMode(GameMode.SPECTATOR);
/*  73 */         player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "You were killed!", ChatColor.GRAY + "It is now time to spectate!", 0, 40, 10);
/*  74 */         Bukkit.broadcastMessage(ChatColor.GREEN + player.getDisplayName() + " has been killed!");
/*  75 */         for (ItemStack i : player.getInventory().getContents()) {
/*  76 */           if (i != null) {
/*  77 */             player.getWorld().dropItemNaturally(player.getLocation(), i);
/*     */           }
/*     */         } 
/*  80 */         this.plugin.speedRunners.remove(player.getName());
/*  81 */         this.plugin.deadRunners.add(player.getName());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onDeath(PlayerDeathEvent event) {
/*  90 */     if (this.plugin.isHunter(event.getEntity())) {
/*  91 */       for (ItemStack i : event.getDrops()) {
/*  92 */         if (i.getType() == Material.COMPASS) {
/*  93 */           i.setType(Material.AIR);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDragonDeath(EntityDeathEvent event) {
/* 101 */     if (event.getEntity() instanceof org.bukkit.entity.EnderDragon && 
/* 102 */       this.plugin.inGame) {
/* 103 */       for (Player player : Bukkit.getOnlinePlayers()) {
/* 104 */         if (this.plugin.speedRunners.contains(player.getName()) || this.plugin.hunters.contains(player.getName())) {
/* 105 */           if (this.plugin.speedRunners.size() == 1) {
/* 106 */             player.sendTitle(ChatColor.AQUA + "The Dragon died!", ChatColor.LIGHT_PURPLE + "The runner wins!", 5, 100, 5); continue;
/*     */           } 
/* 108 */           player.sendTitle(ChatColor.AQUA + "The Dragon died!", ChatColor.LIGHT_PURPLE + "The runners win!", 5, 100, 5);
/*     */         } 
/*     */       } 
/* 111 */       if (this.plugin.speedRunners.size() == 1) {
/* 112 */         Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "The runner wins! Do /huntplus start to play again!");
/*     */       } else {
/* 114 */         Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "The runners win! Do /huntplus start to play again!");
/* 115 */       }  this.plugin.inGame = false;
/* 116 */       HuntEndEvent gameEndEvent = new HuntEndEvent();
/* 117 */       Bukkit.getServer().getPluginManager().callEvent((Event)gameEndEvent);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onBreak(BlockBreakEvent event) {
/* 124 */     if (this.plugin.countingDown && 
/* 125 */       this.plugin.isHunter(event.getPlayer()))
/* 126 */       event.setCancelled(true); 
/*     */   }
/*     */ }


/* Location:              C:\Users\gygab\Downloads\ManhuntPlus 1.4.1.jar!\me\phoenix\manhuntplus\listeners\DeathBlockBreakEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */