package me.phoenix.manhuntplus.timers;
import me.phoenix.manhuntplus.Main;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.plugin.Plugin;

public class Timer {
    private int seconds=0;
    private boolean running= false;
    private Thread timerThread;

    private Main plugin;

    public Timer(Main plugin) {
        this.plugin = plugin;

    }

    public void startTimer(){
        running = true;
        timerThread = new Thread(()->{
            while(true){
                try {
                    tick();
                    if(seconds % 600 == 0)
                        sendTime();
                } catch (InterruptedException e) {
                    running = false;
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        timerThread.start();
    }

    private void tick() throws InterruptedException {
        Thread.sleep(1000);
        seconds++;
    }

    public int getSeconds() {
        return seconds;
    }

    public void stopTimer(){
        running = false;
        //if(timerThread != null){
        //    timerThread.interrupt();
        //}
    }

    public void sendTime(){
        int minutes = seconds/60;
        int remainingSeconds = seconds % 60;
        String timeMessage = String.format("%d minutes and %d seconds!", minutes, remainingSeconds);
        if(!plugin.deadRunners.isEmpty()){
            plugin.getServer().getOnlinePlayers().forEach(player -> {
                player.sendMessage(Color.AQUA +  plugin.deadRunners.getFirst() + " has survived for "  + timeMessage);
            });
        }
        if(!plugin.speedRunners.isEmpty()) {
            plugin.getServer().getOnlinePlayers().forEach(player -> {

                player.sendMessage(Color.AQUA + plugin.speedRunners.getFirst() + " has been on the run for " + timeMessage);
            });
        }
    }
}
