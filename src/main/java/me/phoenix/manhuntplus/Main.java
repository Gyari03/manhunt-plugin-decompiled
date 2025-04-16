/*     */ package me.phoenix.manhuntplus;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import me.phoenix.manhuntplus.commands.HuntPlus;
/*     */ import me.phoenix.manhuntplus.commands.Hunter;
/*     */ import me.phoenix.manhuntplus.commands.Runner;
/*     */ import me.phoenix.manhuntplus.listeners.*;
/*     */
/*     */ import me.phoenix.manhuntplus.tabcompleters.HunterTabCompleter;
import me.phoenix.manhuntplus.tabcompleters.HuntplusTabCompleter;
/*     */ import me.phoenix.manhuntplus.tabcompleters.RunnerTabCompleter;
/*     */ import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.TabCompleter;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.BookMeta;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public final class Main extends JavaPlugin {
/*  30 */   public List<String> hunters = new ArrayList<>();
/*  31 */   public List<String> speedRunners = new ArrayList<>();
/*  32 */   public List<String> deadRunners = new ArrayList<>();
/*     */   
/*  34 */   public HashMap<String, Integer> huntersNumber = new HashMap<>();
/*     */   
/*     */   public boolean inGame = false;
/*     */   
/*     */   public boolean countingDown = false;
/*     */   
/*     */   public boolean waitingRunner = false;
/*  42 */   File config = new File(getDataFolder() + File.separator + "config.yml");
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  46 */     logs();
/*  47 */     checkConfig();
/*  48 */     registerCommands();
/*  49 */     registerEvents();
/*     */   }
/*     */   
/*     */   private void registerCommands() {
/*  54 */     getCommand("hunter").setExecutor((CommandExecutor)new Hunter(this));
/*  55 */     getCommand("runner").setExecutor((CommandExecutor)new Runner(this));
/*  56 */     getCommand("huntplus").setExecutor((CommandExecutor)new HuntPlus(this));
/*  57 */     getCommand("hunter").setTabCompleter((TabCompleter)new HunterTabCompleter());
/*  58 */     getCommand("runner").setTabCompleter((TabCompleter)new RunnerTabCompleter());
/*  59 */     getCommand("huntplus").setTabCompleter((TabCompleter)new HuntplusTabCompleter());
/*     */   }
/*     */   
/*     */   private void registerEvents() {
/*  63 */     PluginManager pm = getServer().getPluginManager();
/*     */     
/*  65 */     pm.registerEvents((Listener)new CompassPortalEvent(this), (Plugin)this);
/*  66 */     pm.registerEvents((Listener)new DropRespawnEvent(this), (Plugin)this);
/*  67 */     pm.registerEvents((Listener)new DisconnectDamageEvent(this), (Plugin)this);
/*  68 */     pm.registerEvents((Listener)new DeathBlockBreakEvent(this), (Plugin)this);
/*  70 */     pm.registerEvents((Listener)new PiglinPearlEvent(this), (Plugin)this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void logs() {
/*  75 */     Logger log = getLogger();
/*  76 */     log.info("Author: UmbralPhoenix");
/*  77 */     log.info("Version: " + getDescription().getVersion());
/*  78 */     if (Bukkit.getVersion().contains("1.16.1")) {
/*  79 */       log.warning("Detected server version 1.16.1. Please beware this version does not support nether tracking. Issues may occur if you try tracking in the nether.");
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkConfig() {
/*  84 */     if (!this.config.exists()) {
/*  85 */       getLogger().info("Config not found. Creating...");
/*  86 */       saveDefaultConfig();
/*  87 */       getLogger().info("Creation successful!");
/*     */     } 
/*     */   }
/*     */
/*     */   
/*     */   public ItemStack ruleBook() {
/* 111 */     ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
/* 112 */     BookMeta bookMeta = (BookMeta)book.getItemMeta();
/* 113 */     bookMeta.addPage(new String[] { ChatColor.translateAlternateColorCodes('&', "&6&lOfficial Manhunt Rules\n\n&a1. &0No portal trapping. When the runner(s) comes through an end or nether portal, they must be able to move and get out from the portal regardless of the trap set by the hunter(s)") });
/* 114 */     bookMeta.addPage(new String[] { ChatColor.translateAlternateColorCodes('&', "&62. &0No nether portal traveling or breaking. Only the runner(s) is allowed to break their own portal, and the runner(s) is not allowed to create a new portal in the nether to travel far away in the overworld.") });
/* 115 */     bookMeta.addPage(new String[] { ChatColor.translateAlternateColorCodes('&', "&63. &0No over powered items or glitches allowed. For example, towering with obsidian in the end to kill the dragon is not allowed. Instant harming potions are not allowed. Rules like this can vary depending on the players choices.") });
/* 116 */     bookMeta.setTitle("Manhunt Rules");
/* 117 */     bookMeta.setAuthor("Umbral Phoenix");
/* 118 */     book.setItemMeta((ItemMeta)bookMeta);
/* 119 */     return book;
/*     */   }
/*     */   
/*     */   public boolean isHunter(Player player) {
/* 123 */     return this.hunters.contains(player.getName());
/*     */   }
/*     */   
/*     */   public boolean isRunner(Player player) {
/* 127 */     return this.speedRunners.contains(player.getName());
/*     */   }
/*     */ }


/* Location:              C:\Users\gygab\Downloads\ManhuntPlus 1.4.1.jar!\me\phoenix\manhuntplus\Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */