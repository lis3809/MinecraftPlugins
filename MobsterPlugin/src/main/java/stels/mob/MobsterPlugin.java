package stels.mob;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import stels.mob.creatormobs.NecromanCreator;
import stels.mob.creatormobs.VillagerShahterCreator;
import stels.mob.creatormobs.ZombiCreator;


public final class MobsterPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("================MobsterPlugin enable===============");

        //регистрируем классы-создатели мобов
        getServer().getPluginManager().registerEvents(new ZombiCreator(this), this);
        getServer().getPluginManager().registerEvents(new NecromanCreator(this), this);
        getServer().getPluginManager().registerEvents(new VillagerShahterCreator(this), this);

        //регистратор событий плагина
        getServer().getPluginManager().registerEvents(this,this);

        //Регистрируем команды
        getServer().getPluginCommand("getzombi").setExecutor(new CommandGetMobster());
        getServer().getPluginCommand("getnecroman").setExecutor(new CommandGetMobster());
        getServer().getPluginCommand("killmobster").setExecutor(new CommandGetMobster());
        getServer().getPluginCommand("getvillager").setExecutor(new CommandGetMobster());

    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent inventoryCloseEvent){
        inventoryCloseEvent.getPlayer().sendMessage("Вы закрыли ивентарь");

        Vector vector = inventoryCloseEvent.getPlayer().getLocation().getDirection();


        Location location1 = inventoryCloseEvent.getPlayer().getLocation().add(vector);

        if (location1.getBlock().getType().equals(Material.AIR)){
            inventoryCloseEvent.getPlayer().sendMessage("Это воздух");
        } else {
            inventoryCloseEvent.getPlayer().sendMessage("Что то твердое");
            location1.getBlock().breakNaturally();
        }


    }

}
