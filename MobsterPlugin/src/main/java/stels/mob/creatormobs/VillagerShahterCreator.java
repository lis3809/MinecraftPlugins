package stels.mob.creatormobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import stels.mob.MobsterPlugin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class VillagerShahterCreator implements Listener {
    private static MobsterPlugin plugin;
    private static final Location[][][] locationsArray = new Location[10][10][10];

    public VillagerShahterCreator(MobsterPlugin plugin) {
        this.plugin = plugin;
    }

    public static void createVillagerShahterCreator(Location location) {
        Villager villager = location.getWorld().spawn(location, Villager.class);
        villager.setCustomName(ChatColor.GREEN + "Шахтёр");
        villager.setCustomNameVisible(true);
        villager.setAI(false);
        villager.setProfession(Villager.Profession.NITWIT);

        villager.getLocation().setDirection(location.getDirection());

        //Устанавливаем различные параметры
        Attributable villagerAttr = villager;
        //Здоровье
        AttributeInstance attributeInstance = villagerAttr.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attributeInstance.setBaseValue(10);
        villager.setHealth(10);

        //Устанавливаем инвентарь
        villager.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SPADE));

        //=====Инициализируем массив координат будущей шахты=====
        //initLocationArray(location);

        //villager.getLocation().getBlock().


        new BukkitRunnable() {

            @Override
            public void run() {
                if (!villager.isDead()) {



                    Vector vector = villager.getLocation().getDirection();

                    Block block = villager.getLocation().add(vector).getBlock();
                    if (block.getType().equals(Material.AIR)) {
                        Bukkit.getLogger().info("Block is AIR");
                    } else {
                        block.breakNaturally();
                        Bukkit.getLogger().info("Block is delete");
                    }



                    //zomb.setVelocity(target.getLocation().add(0, 2, 0).subtract(zomb.getLocation()).toVector().multiply(0.275));
                    villager.setVelocity(vector.add(new Vector(1,1,0)));


                    //Проходимся по всем елементам трехмерного массива и разрушаем их
                    //for (int y = 0; y < locationsArray.length; y++) {
                    //                        for (int x = 0; x < locationsArray[y].length; x++) {
                    //                            for (int z = 0; z < locationsArray[y][x].length; z++) {
                    //
                    //                                if (!locationsArray[x][y][z].getBlock().isEmpty()){
                    //
                    //                                    //Перемещаем Шахтёра к блоку
                    //                                    villager.setVelocity(locationsArray[x][y][z].toVector());
                    //                                    locationsArray[x][y][z].getBlock().breakNaturally();
                    //
                    //
                    //                                    //fallingBlock.setVelocity(skeleton.getTarget().getLocation().add(0, 1, 0).subtract(fallingBlock.getLocation()).toVector().multiply(0.5));
                    //                                }
                    //                            }
                    //                        }
                    //                    }

                } else {
                    cancel();
                    Bukkit.getLogger().info("Villager is dead");
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);


    }

    //==============Метод инициализации массива шахты в зависимости от начального положения
    private static void initLocationArray(Location entityLocation) {
        int glubina = 0;
        for (int y = 0; y < locationsArray.length; y++) {
            for (int x = 0; x < locationsArray[y].length; x++) {
                for (int z = 0; z < locationsArray[y][x].length; z++) {
                    locationsArray[x][y][z] = entityLocation.add(x, glubina, z);
                    Bukkit.getLogger().info("Location added");
                    Bukkit.getLogger().info(locationsArray[x][y][z].toString());

                }
            }
            --glubina;
        }
    }


    //for (int y = 0; y < locationsArray.length ; y++) {
    //                System.out.println();
    //                for (int x = 0; x < locationsArray[y].length; x++) {
    //                    System.out.println();
    //                    for (int z = 0; z < locationsArray[y][x].length; z++) {
    //                        System.out.print(locationsArray[x][y][z] + "\t");
    //                    }
    //                }
    //            }

    //Обработчик событий для падающих блоков
    @EventHandler
    public void onChange(EntityChangeBlockEvent event) {

    }


}
