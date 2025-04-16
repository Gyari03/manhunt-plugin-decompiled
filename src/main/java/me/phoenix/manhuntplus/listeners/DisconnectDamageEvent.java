/*    */ package me.phoenix.manhuntplus.listeners;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import me.phoenix.manhuntplus.Main;
/*    */ import me.phoenix.manhuntplus.api.HuntStartEvent;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ 
/*    */ public class DisconnectDamageEvent
/*    */   implements Listener
/*    */ {
/*    */   private Main plugin;
/* 22 */   private List<String> wasRunner = new ArrayList<>();
/* 23 */   private List<String> wasHunter = new ArrayList<>();
/*    */   
/*    */   public DisconnectDamageEvent(Main plugin) {
/* 26 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onLeave(PlayerQuitEvent event) {
/* 31 */     Player player = event.getPlayer();
/* 32 */     if (this.plugin.isHunter(player)) {
/* 33 */       this.plugin.hunters.remove(player.getName());
/* 34 */       this.wasHunter.add(player.getName());
/* 35 */     } else if (this.plugin.isRunner(player)) {
/* 36 */       this.plugin.speedRunners.remove(player.getName());
/* 37 */       this.wasRunner.add(player.getName());
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onJoin(PlayerJoinEvent event) {
/* 43 */     Player player = event.getPlayer();
/* 44 */     if (this.wasRunner.contains(player.getName())) {
/* 45 */       this.plugin.speedRunners.add(player.getName());
/* 46 */       this.wasRunner.remove(player.getName());
/*    */     }
/* 48 */     else if (this.wasHunter.contains(player.getName())) {
/* 49 */       this.plugin.hunters.add(player.getName());
/* 50 */       this.wasHunter.remove(player.getName());
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerHit(EntityDamageByEntityEvent event) {
/* 56 */     if (event.getEntity() instanceof Player && event.getDamager() instanceof Player && 
/* 57 */       this.plugin.waitingRunner) {
/* 58 */       Player runner = (Player)event.getDamager();
/* 59 */       Player hunter = (Player)event.getEntity();
/* 60 */       if (this.plugin.isRunner(runner) && this.plugin.isHunter(hunter)) {
/* 61 */         Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "The hunt has begun!");
/* 62 */         this.plugin.waitingRunner = false;
/* 63 */         this.plugin.inGame = true;
/*    */ 
/*    */         
/* 66 */         HuntStartEvent gameStartEvent = new HuntStartEvent();
/* 67 */         Bukkit.getServer().getPluginManager().callEvent((Event)gameStartEvent);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerDamage(EntityDamageEvent event) {
/* 75 */     if (event.getEntity() instanceof Player) {
/* 76 */       Player player = (Player)event.getEntity();
/* 77 */       if (this.plugin.isHunter(player) && this.plugin.countingDown)
/* 78 */         event.setCancelled(true); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\gygab\Downloads\ManhuntPlus 1.4.1.jar!\me\phoenix\manhuntplus\listeners\DisconnectDamageEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */