/*    */ package me.phoenix.manhuntplus.listeners;
/*    */ 
/*    */ import me.phoenix.manhuntplus.Main;
/*    */ import net.dv8tion.jda.api.entities.TextChannel;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*    */ 
/*    */ public class MCChatEvent
/*    */   implements Listener {
/*    */   private Main plugin;
/*    */   
/*    */   public MCChatEvent(Main plugin) {
/* 17 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onChat(AsyncPlayerChatEvent event) {
/* 22 */     Player player = event.getPlayer();
/* 23 */     if (this.plugin.getConfig().getBoolean("discordMcChat")) {
/* 24 */       TextChannel chatChannel = this.plugin.bot.getTextChannelById(this.plugin.getConfig().getString("discordMcChannel"));
/* 25 */       if (chatChannel != null) {
/* 26 */         chatChannel.sendMessage("**" + player.getDisplayName() + "** » " + event.getMessage()).queue();
/*    */       } else {
/* 28 */         this.plugin.getLogger().warning("Discord channel is null. Please provide a valid channel ID");
/*    */       } 
/*    */     } 
/* 31 */     if (this.plugin.isHunter(player)) {
/* 32 */       if (this.plugin.getConfig().getString("teamchatPrefix") == null) {
/* 33 */         player.sendMessage(ChatColor.RED + "No teamchat prefix is set in the configuration file. Please set in order to use teamchat");
/*    */         return;
/*    */       } 
/* 36 */       if (event.getMessage().startsWith(this.plugin.getConfig().getString("teamchatPrefix"))) {
/* 37 */         event.setCancelled(true);
/* 38 */         String original = this.plugin.getConfig().getString("hunterTeamchat");
/* 39 */         String message = original.replaceAll("%player%", player.getDisplayName()).replaceAll("%message%", event.getMessage().substring(1));
/* 40 */         String normal = "&c&l[Hunter Chat] &c%player% »&6&l%message%";
/* 41 */         String msg = normal.replaceAll("%player%", player.getDisplayName()).replaceAll("%message%", event.getMessage().substring(1));
/* 42 */         for (String names : this.plugin.hunters) {
/* 43 */           Player hunter = Bukkit.getPlayer(names);
/* 44 */           if (original.equals("%default%")) {
/* 45 */             hunter.sendMessage(ChatColor.translateAlternateColorCodes('&', msg)); continue;
/*    */           } 
/* 47 */           hunter.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
/*    */         }
/*    */       
/*    */       } 
/* 51 */     } else if (this.plugin.isRunner(player)) {
/* 52 */       if (this.plugin.getConfig().getString("teamchatPrefix") == null) {
/* 53 */         player.sendMessage(ChatColor.RED + "No teamchat prefix is set in the configuration file. Please set in order to use teamchat");
/*    */         return;
/*    */       } 
/* 56 */       if (event.getMessage().startsWith(this.plugin.getConfig().getString("teamchatPrefix"))) {
/* 57 */         event.setCancelled(true);
/* 58 */         String original = this.plugin.getConfig().getString("runnerTeamchat");
/* 59 */         String message = original.replaceAll("%player%", player.getDisplayName()).replaceAll("%message%", event.getMessage().substring(1));
/* 60 */         String normal = "&a&l[Runner Chat] &a%player% »&b&l%message%";
/* 61 */         String msg = normal.replaceAll("%player%", player.getDisplayName()).replaceAll("%message%", event.getMessage().substring(1));
/* 62 */         for (String names : this.plugin.speedRunners) {
/* 63 */           Player runner = Bukkit.getPlayer(names);
/* 64 */           if (original.equals("%default%")) {
/* 65 */             runner.sendMessage(ChatColor.translateAlternateColorCodes('&', msg)); continue;
/*    */           } 
/* 67 */           runner.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
/*    */         }
/*    */       
/*    */       } 
/* 71 */     } else if (this.plugin.deadRunners.contains(player.getName()) && this.plugin.inGame) {
/* 72 */       event.setCancelled(true);
/* 73 */       for (String names : this.plugin.deadRunners) {
/* 74 */         Player dead = Bukkit.getPlayer(names);
/* 75 */         dead.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[Spectator Chat] " + player.getDisplayName() + " » " + event.getMessage()));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\gygab\Downloads\ManhuntPlus 1.4.1.jar!\me\phoenix\manhuntplus\listeners\MCChatEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */