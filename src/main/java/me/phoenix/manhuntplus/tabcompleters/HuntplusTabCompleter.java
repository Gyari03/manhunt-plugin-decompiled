/*    */ package me.phoenix.manhuntplus.tabcompleters;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.TabCompleter;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public class HuntplusTabCompleter
/*    */   implements TabCompleter
/*    */ {
/* 14 */   List<String> arguments = new ArrayList<>();
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
/* 19 */     if (sender == null) $$$reportNull$$$0(0);  if (command == null) $$$reportNull$$$0(1);  if (label == null) $$$reportNull$$$0(2);  if (args == null) $$$reportNull$$$0(3);  if (this.arguments.isEmpty()) {
/* 20 */       this.arguments.add("start");
/* 21 */       this.arguments.add("stop");
/* 22 */       this.arguments.add("info");
/* 23 */       this.arguments.add("randomize");
/* 24 */       this.arguments.add("reload");
/* 25 */       this.arguments.add("rules");
/* 26 */       this.arguments.add("help");
/*    */     } 
/* 28 */     List<String> result = new ArrayList<>();
/* 29 */     if (args.length == 1) {
/* 30 */       for (String subcommand : this.arguments) {
/* 31 */         if (subcommand.toLowerCase().startsWith(args[0].toLowerCase())) {
/* 32 */           result.add(subcommand);
/*    */         }
/*    */       } 
/* 35 */       return result;
/*    */     } 
/* 37 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\gygab\Downloads\ManhuntPlus 1.4.1.jar!\me\phoenix\manhuntplus\tabcompleters\HuntplusTabCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */