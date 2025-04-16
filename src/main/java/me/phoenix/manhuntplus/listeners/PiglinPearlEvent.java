/*    */ package me.phoenix.manhuntplus.listeners;
/*    */ 
/*    */ import java.util.Random;
/*    */ import me.phoenix.manhuntplus.Main;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Piglin;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.EntityDropItemEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class PiglinPearlEvent
/*    */   implements Listener
/*    */ {
/*    */   private Main plugin;
/*    */   
/*    */   public PiglinPearlEvent(Main plugin) {
/* 19 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onTrade(EntityDropItemEvent event) {
/* 24 */     if (!(event.getEntity() instanceof Piglin))
/* 25 */       return;  if (event.getItemDrop().getItemStack().getType() == Material.ENDER_PEARL)
/* 26 */       return;  if (this.plugin.inGame && this.plugin.getConfig().getBoolean("piglinPearlBoost")) {
/* 27 */       Piglin piglin = (Piglin)event.getEntity();
/* 28 */       Random random = new Random();
/* 29 */       int chance = random.nextInt(99);
/* 30 */       if (chance >= 10)
/* 31 */         return;  Random pearlChance = new Random();
/* 32 */       int quantityDropped = pearlChance.nextInt(2) + 2;
/* 33 */       Location itemLoc = event.getItemDrop().getLocation();
/* 34 */       event.getItemDrop().setItemStack(new ItemStack(Material.ENDER_PEARL));
/* 35 */       event.getItemDrop().getItemStack().setAmount(quantityDropped);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\gygab\Downloads\ManhuntPlus 1.4.1.jar!\me\phoenix\manhuntplus\listeners\PiglinPearlEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */