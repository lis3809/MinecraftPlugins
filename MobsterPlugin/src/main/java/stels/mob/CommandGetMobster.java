package stels.mob;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import stels.mob.creatormobs.NecromanCreator;
import stels.mob.creatormobs.VillagerShahterCreator;
import stels.mob.creatormobs.ZombiCreator;

public class CommandGetMobster implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            if (command.getName().equalsIgnoreCase("getzombi")) {
                //Создаем нашего моба, вызывая соответствующий метод класса MobsterCreator
                ZombiCreator.createCraftZombi(((Player) sender).getPlayer().getLocation());
            }

            if (command.getName().equalsIgnoreCase("getnecroman")) {
                //Создаем нашего моба, вызывая соответствующий метод класса MobsterCreator
                NecromanCreator.createNecroman(((Player) sender).getPlayer().getLocation());
            }


            if (command.getName().equalsIgnoreCase("getvillager")) {
                //Создаем нашего Шахтёра, вызывая соответствующий метод
                VillagerShahterCreator.createVillagerShahterCreator(((Player) sender).getPlayer().getLocation().add(((Player) sender).getPlayer().getLocation().getDirection()));
            }

            if (command.getName().equalsIgnoreCase("killmobster")) {
                //Создаем нашего моба, вызывая соответствующий метод класса MobsterCreator
                for (Entity entity : ((Player) sender).getPlayer().getNearbyEntities(15, 15, 15)) {
                    entity.remove();
                }
            }


        } else {
            sender.sendMessage("Эта команда только для игроков");
        }


        //Напоминалка: Bukkit.getPlayer(sender.getName()).getWorld();

        //Entity entity = Bukkit.getPlayer(sender.getName()).getWorld().spawnEntity(Bukkit.getPlayer(sender.getName()).getLocation(), EntityType.PLAYER);


        //Игрок, вызвавший команду
        //Player player = sender.getServer().getPlayer(sender.getName());
        //World world = player.getWorld();

        //Entity entity = sender.getServer().getPlayer(sender.getName()).getWorld().spawnEntity(sender.getServer().getPlayer(sender.getName()).getLocation(), EntityType.ZOMBIE);
        //Zombie playerMobster = (Mobster) entity;

        //HumanEntity mobster =  sender.getServer().getPlayer(sender.getName()).getWorld().spawn(player.getLocation(), HumanEntity.class);


        //playerMobster.setTexturePack("plugins/husk.png");

        //mobster.setCustomName("MOBSTER");
        //mobster.setCustomNameVisible(true);
        //mobster.setHealth(1);
        //mobster.setAI(true);


        //LivingEntity mob = (LivingEntity)p. getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
        //Zombie z = (Зомби) толпа;

        //z.setCustomName("§cZombie Custom");
        //z.setCustomNameVisible (истина);
        //z.setBaby (ложь); // зомби это ребенок? = ложь
        //z.setHealth (50);

        sender.sendMessage("Cоздан пользовательский Mob!");


        return true;
    }
}
