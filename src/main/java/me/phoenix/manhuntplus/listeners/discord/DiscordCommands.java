/*    */ package me.phoenix.manhuntplus.listeners.discord;
/*    */ 
/*    */ import me.phoenix.manhuntplus.Main;
/*    */ import net.dv8tion.jda.api.entities.TextChannel;
/*    */ import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
/*    */ import net.dv8tion.jda.api.hooks.ListenerAdapter;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class DiscordCommands
/*    */   extends ListenerAdapter {
/*    */   private Main plugin;
/*    */   
/*    */   public DiscordCommands(Main plugin) {
/* 15 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
/* 20 */     if (!event.getAuthor().isBot()) {
/* 21 */       TextChannel channel = event.getChannel();
/* 22 */       if (event.getMessage().getContentRaw().equals("!huntstatus")) {
/* 23 */         if (!this.plugin.inGame) {
/* 24 */           channel.sendMessage("No hunt is active").queue();
/*    */         } else {
/* 26 */           channel.sendMessage("A hunt is active. List of players:").queue();
/* 27 */           for (String name : this.plugin.hunters) {
/* 28 */             Player hunter = Bukkit.getPlayer(name);
/* 29 */             channel.sendMessage("**" + hunter.getDisplayName() + "** is a **hunter**").queue();
/*    */           } 
/* 31 */           for (String names : this.plugin.speedRunners) {
/* 32 */             Player runner = Bukkit.getPlayer(names);
/* 33 */             channel.sendMessage("**" + runner.getDisplayName() + "** is a **runner**").queue();
/*    */           } 
/*    */         } 
/*    */       }
/* 37 */       if (event.getMessage().getContentRaw().equals("!online")) {
/* 38 */         if (this.plugin.getServer().getOnlinePlayers().size() == 0) {
/* 39 */           channel.sendMessage("Currently no players online").queue();
/*    */           return;
/*    */         } 
/* 42 */         channel.sendMessage("List of all players online:").queue();
/* 43 */         for (Player online : Bukkit.getOnlinePlayers())
/* 44 */           channel.sendMessage("**" + online.getDisplayName() + "** is online").queue(); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\gygab\Downloads\ManhuntPlus 1.4.1.jar!\me\phoenix\manhuntplus\listeners\discord\DiscordCommands.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */