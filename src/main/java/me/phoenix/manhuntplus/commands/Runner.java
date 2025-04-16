/*     */ package me.phoenix.manhuntplus.commands;
/*     */ 
/*     */ import me.phoenix.manhuntplus.Main;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class Runner
/*     */   implements CommandExecutor {
/*     */   private Main plugin;
/*     */   
/*     */   public Runner(Main plugin) {
/*  18 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/*  23 */     if (label.equalsIgnoreCase("runner")) {
/*  24 */       if (!(sender instanceof Player)) {
/*  25 */         sender.sendMessage("[Manhunt+] You must be a player to run this command!");
/*  26 */         return true;
/*     */       } 
/*  28 */       Player player = (Player)sender;
/*  29 */       if (player.hasPermission("manhuntplus.speedrunner")) {
/*  30 */         if (args.length == 0) {
/*  31 */           player.sendMessage(ChatColor.GREEN + "Please use '/runner help' to see list of commands");
/*  32 */           return true;
/*     */         } 
/*  34 */         if (args.length == 1) {
/*  35 */           switch (args[0].toLowerCase())
/*     */           { case "list":
/*  37 */               if (this.plugin.speedRunners.size() == 0) {
/*  38 */                 player.sendMessage(ChatColor.GREEN + "No speedrunners have been set");
/*  39 */                 return true;
/*     */               } 
/*  41 */               player.sendMessage(ChatColor.GREEN + "Here are the runners:");
/*  42 */               for (String name : this.plugin.speedRunners) {
/*  43 */                 Player runnerName = Bukkit.getPlayer(name);
/*  44 */                 player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &a" + runnerName.getDisplayName()));
/*     */               } 
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
/*  66 */               return true;case "help": player.sendMessage(ChatColor.YELLOW + "/runner add <player>: " + ChatColor.GREEN + "Add a speedrunner"); player.sendMessage(ChatColor.YELLOW + "/runner remove <player>: " + ChatColor.GREEN + "Remove a speedrunner"); player.sendMessage(ChatColor.YELLOW + "/runner list: " + ChatColor.GREEN + "Lists all the speedrunners"); player.sendMessage(ChatColor.YELLOW + "/runner clear: " + ChatColor.GREEN + "Removes all speedrunners"); player.sendMessage(ChatColor.YELLOW + "/runner help: " + ChatColor.GREEN + "Displays this page"); return true;case "clear": if (this.plugin.speedRunners.size() == 0) { player.sendMessage(ChatColor.RED + "There are no speedrunners to clear!"); return true; }  player.sendMessage(ChatColor.GREEN + "All speedrunners have been cleared"); this.plugin.speedRunners.clear(); return true; }  player.sendMessage(ChatColor.RED + "Please use '/runner help' for commands"); return true;
/*     */         } 
/*  68 */         if (args.length == 2) {
/*  69 */           if (args[0].equalsIgnoreCase("add")) {
/*  70 */             Player target = Bukkit.getPlayer(args[1]);
/*  71 */             if (target == null) {
/*  72 */               player.sendMessage(ChatColor.RED + "Could not find player!");
/*  73 */               return true;
/*     */             } 
/*  75 */             if (!this.plugin.speedRunners.contains(target.getName())) {
/*  76 */               if (target == player) {
/*  77 */                 player.sendMessage(ChatColor.GREEN + "You have been added as a speedrunner");
/*  78 */                 this.plugin.speedRunners.add(player.getName());
/*  79 */                 this.plugin.hunters.remove(player.getName());
/*  80 */                 player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.COMPASS) });
/*  81 */                 return true;
/*     */               } 
/*  83 */               target.sendMessage(ChatColor.GREEN + "You have been added as a speedrunner");
/*  84 */               player.sendMessage(ChatColor.GREEN + target.getDisplayName() + " is now a speedrunner");
/*  85 */               this.plugin.speedRunners.add(target.getName());
/*  86 */               this.plugin.hunters.remove(target.getName());
/*  87 */               player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.COMPASS) });
/*  88 */               return true;
/*     */             } 
/*  90 */             if (target == player) {
/*  91 */               player.sendMessage(ChatColor.RED + "You are already a speedrunner!");
/*  92 */               return true;
/*     */             } 
/*  94 */             player.sendMessage(ChatColor.RED + target.getDisplayName() + " is already a speedrunner!");
/*  95 */             return true;
/*     */           } 
/*  97 */           if (args[0].equalsIgnoreCase("remove")) {
/*  98 */             Player target = Bukkit.getPlayer(args[1]);
/*  99 */             if (target == null) {
/* 100 */               player.sendMessage(ChatColor.RED + "Could not find player!");
/* 101 */               return true;
/*     */             } 
/* 103 */             if (this.plugin.isRunner(target)) {
/* 104 */               if (target == player) {
/* 105 */                 player.sendMessage(ChatColor.GREEN + "You are no longer a speedrunner");
/* 106 */                 this.plugin.speedRunners.remove(player.getName());
/* 107 */                 return true;
/*     */               } 
/* 109 */               target.sendMessage(ChatColor.GREEN + "You are no longer a speedrunner");
/* 110 */               player.sendMessage(ChatColor.GREEN + target.getDisplayName() + " is no longer a speedrunner");
/* 111 */               this.plugin.speedRunners.remove(target.getName());
/* 112 */               return true;
/*     */             } 
/* 114 */             if (target == player) {
/* 115 */               player.sendMessage(ChatColor.RED + "You are not a speedrunner!");
/* 116 */               return true;
/*     */             } 
/* 118 */             player.sendMessage(ChatColor.RED + target.getDisplayName() + " is not a speedrunner!");
/* 119 */             return true;
/*     */           } 
/*     */         } 
/* 122 */         player.sendMessage(ChatColor.RED + "Please use '/runner help' for commands");
/* 123 */         return true;
/*     */       } 
/*     */     } 
/* 126 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\gygab\Downloads\ManhuntPlus 1.4.1.jar!\me\phoenix\manhuntplus\commands\Runner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */