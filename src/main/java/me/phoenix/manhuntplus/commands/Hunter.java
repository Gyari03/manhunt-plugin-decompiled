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
/*     */ public class Hunter
/*     */   implements CommandExecutor
/*     */ {
/*     */   private Main plugin;
/*     */   
/*     */   public Hunter(Main plugin) {
/*  19 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/*  24 */     if (sender instanceof Player) {
/*  25 */       Player player = (Player)sender;
/*  26 */       if (label.equalsIgnoreCase("hunter") && 
/*  27 */         player.hasPermission("manhuntplus.hunter")) {
/*  28 */         if (args.length == 0) {
/*  29 */           player.sendMessage(ChatColor.GREEN + "Please use '/hunter help' to see list of commands");
/*  30 */           return true;
/*     */         } 
/*  32 */         if (args.length == 1) {
/*  33 */           switch (args[0].toLowerCase())
/*     */           { case "help":
/*  35 */               player.sendMessage(ChatColor.YELLOW + "/hunter add <player>: " + ChatColor.GREEN + "Add a hunter");
/*  36 */               player.sendMessage(ChatColor.YELLOW + "/hunter remove <player>: " + ChatColor.GREEN + "Remove a hunter");
/*  37 */               player.sendMessage(ChatColor.YELLOW + "/hunter list: " + ChatColor.GREEN + "Lists all the hunters");
/*  38 */               player.sendMessage(ChatColor.YELLOW + "/hunter clear: " + ChatColor.GREEN + "Removes all hunters");
/*  39 */               player.sendMessage(ChatColor.YELLOW + "/hunter help: " + ChatColor.GREEN + "Displays this page");
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
/*  68 */               return true;case "list": if (this.plugin.hunters.size() == 0) { player.sendMessage(ChatColor.GREEN + "No hunters have been set"); return true; }  player.sendMessage(ChatColor.GREEN + "Here are the hunters:"); for (String name : this.plugin.hunters) { Player hunterName = Bukkit.getPlayer(name); player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &a" + hunterName.getDisplayName())); }  return true;case "clear": if (this.plugin.hunters.size() == 0) { player.sendMessage(ChatColor.RED + "There are no hunters to clear!"); return true; }  player.sendMessage(ChatColor.GREEN + "All hunters have been cleared"); for (String name : this.plugin.hunters) { Player hunter = Bukkit.getPlayer(name); hunter.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.COMPASS) }); }  this.plugin.hunters.clear(); return true; }  player.sendMessage(ChatColor.RED + "Please use '/hunter help' for commands"); return true;
/*     */         } 
/*  70 */         if (args.length == 2) {
/*  71 */           if (args[0].equalsIgnoreCase("add")) {
/*  72 */             Player target = Bukkit.getPlayer(args[1]);
/*  73 */             if (target == null) {
/*  74 */               player.sendMessage(ChatColor.RED + "Could not find player!");
/*  75 */               return true;
/*     */             } 
/*  77 */             if (!this.plugin.isHunter(target)) {
/*  78 */               if (target == player) {
/*  79 */                 player.sendMessage(ChatColor.GREEN + "You have been added as a hunter");
/*  80 */                 player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COMPASS) });
/*  81 */                 this.plugin.hunters.add(player.getName());
/*  82 */                 this.plugin.speedRunners.remove(player.getName());
/*  83 */                 this.plugin.huntersNumber.put(player.getName(), Integer.valueOf(0));
/*  84 */                 return true;
/*     */               } 
/*  86 */               target.sendMessage(ChatColor.GREEN + "You have been added as a hunter");
/*  87 */               target.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COMPASS) });
/*  88 */               player.sendMessage(ChatColor.GREEN + target.getDisplayName() + " has been added as a hunter");
/*  89 */               this.plugin.hunters.add(target.getName());
/*  90 */               this.plugin.speedRunners.remove(target.getName());
/*  91 */               this.plugin.huntersNumber.put(target.getName(), Integer.valueOf(0));
/*  92 */               return true;
/*     */             } 
/*  94 */             if (target == player) {
/*  95 */               player.sendMessage(ChatColor.RED + "You are already a hunter!");
/*  96 */               return true;
/*     */             } 
/*  98 */             player.sendMessage(ChatColor.RED + target.getDisplayName() + " is already a hunter!");
/*  99 */             return true;
/*     */           } 
/* 101 */           if (args[0].equalsIgnoreCase("remove")) {
/* 102 */             Player target = Bukkit.getPlayer(args[1]);
/* 103 */             if (target == null) {
/* 104 */               player.sendMessage(ChatColor.RED + "Could not find player!");
/* 105 */               return true;
/*     */             } 
/* 107 */             if (this.plugin.isHunter(target)) {
/* 108 */               if (target == player) {
/* 109 */                 player.sendMessage(ChatColor.GREEN + "You are no longer a hunter");
/* 110 */                 player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.COMPASS) });
/* 111 */                 this.plugin.hunters.remove(player.getName());
/* 112 */                 return true;
/*     */               } 
/* 114 */               target.sendMessage(ChatColor.GREEN + "You are no longer a hunter");
/* 115 */               target.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.COMPASS) });
/* 116 */               player.sendMessage(ChatColor.GREEN + target.getDisplayName() + " is no longer a hunter");
/* 117 */               this.plugin.hunters.remove(target.getName());
/* 118 */               return true;
/*     */             } 
/* 120 */             if (target == player) {
/* 121 */               player.sendMessage(ChatColor.RED + "You are not a hunter!");
/* 122 */               return true;
/*     */             } 
/* 124 */             player.sendMessage(ChatColor.RED + target.getDisplayName() + " is not a hunter!");
/* 125 */             return true;
/*     */           } 
/*     */         } 
/* 128 */         player.sendMessage(ChatColor.RED + "Please use '/hunter help' for commands");
/* 129 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     sender.sendMessage("[Manhunt+] You must be a player to run this command!");
/* 134 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\gygab\Downloads\ManhuntPlus 1.4.1.jar!\me\phoenix\manhuntplus\commands\Hunter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */