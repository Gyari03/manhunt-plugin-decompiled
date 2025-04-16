/*    */ package me.phoenix.manhuntplus.listeners;
/*    */ 
/*    */ import me.phoenix.manhuntplus.Main;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerDropItemEvent;
/*    */ import org.bukkit.event.player.PlayerRespawnEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class DropRespawnEvent
/*    */   implements Listener {
/*    */   private Main plugin;
/*    */   
/*    */   public DropRespawnEvent(Main plugin) {
/* 17 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onRespawn(PlayerRespawnEvent event) {
/* 23 */     Player player = event.getPlayer();
/* 24 */     if (this.plugin.isHunter(player)) {
/* 25 */       player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COMPASS) });
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onDrop(PlayerDropItemEvent event) {
/* 31 */     if (this.plugin.isHunter(event.getPlayer()) && event.getItemDrop().getItemStack().getType() == Material.COMPASS)
/* 32 */       event.setCancelled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\gygab\Downloads\ManhuntPlus 1.4.1.jar!\me\phoenix\manhuntplus\listeners\DropRespawnEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */