/*    */ package me.phoenix.manhuntplus.api;
/*    */ 
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class TeamRandomizeEvent
/*    */   extends Event {
/*  8 */   private static final HandlerList handlers = new HandlerList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 15 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 19 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\gygab\Downloads\ManhuntPlus 1.4.1.jar!\me\phoenix\manhuntplus\api\TeamRandomizeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */