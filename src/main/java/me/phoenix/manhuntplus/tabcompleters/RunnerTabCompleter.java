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
/*    */ public class RunnerTabCompleter
/*    */   implements TabCompleter
/*    */ {
/* 14 */   List<String> arguments = new ArrayList<>();
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (sender == null || command == null || label == null || args == null) {
        return null;
    }

/* 19 */     if (this.arguments.isEmpty()) {
/* 20 */       this.arguments.add("add");
/* 21 */       this.arguments.add("remove");
/* 22 */       this.arguments.add("list");
/* 23 */       this.arguments.add("clear");
/* 24 */       this.arguments.add("help");
/*    */     } 
/* 26 */     List<String> result = new ArrayList<>();
/* 27 */     if (args.length == 1) {
/* 28 */       for (String subcommand : this.arguments) {
/* 29 */         if (subcommand.toLowerCase().startsWith(args[0].toLowerCase())) {
/* 30 */           result.add(subcommand);
/*    */         }
/*    */       } 
/* 33 */       return result;
/*    */     } 
/* 35 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\gygab\Downloads\ManhuntPlus 1.4.1.jar!\me\phoenix\manhuntplus\tabcompleters\RunnerTabCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */