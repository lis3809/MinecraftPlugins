package stels.mob.creatormobs;

import org.bukkit.*;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import stels.mob.MobsterPlugin;

import java.util.Random;

public class NecromanCreator implements Listener {

    private static MobsterPlugin plugin;

    public NecromanCreator(MobsterPlugin plugin) {
        this.plugin = plugin;
    }

    public static void createNecroman(Location location) {
        Skeleton skeleton = location.getWorld().spawn(location, Skeleton.class);
        skeleton.setCustomName(ChatColor.DARK_BLUE + "NECROMAN");
        skeleton.setCustomNameVisible(true);

        //Устанавливаем различные параметры
        Attributable skeletonAt = skeleton;
        //Здоровье
        AttributeInstance attributeHelth = skeletonAt.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attributeHelth.setBaseValue(10);
        skeleton.setHealth(10);
        //Устанавливааем скорость движения
        AttributeInstance attributeSpeed = skeletonAt.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        attributeSpeed.setBaseValue(0.1);

        //Устанавливаем инвентарь
        skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SPADE));
        skeleton.getEquipment().setHelmet(new ItemStack(Material.GOLD_HELMET));
        skeleton.getEquipment().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));


        new BukkitRunnable() {
            int i = 0;

            public void run() {
                if (!skeleton.isDead()) {
                    if (skeleton.getTarget() != null) {
                        if (i % 2 == 0) {
                            FallingBlock fallingBlock = skeleton.getWorld().spawnFallingBlock(skeleton.getLocation().add(0, 2, 0), Material.SNOW_BLOCK, (byte) 0);
                            fallingBlock.setCustomName("Necroman Orb");
                            fallingBlock.setDropItem(false);
                            fallingBlock.setVelocity(skeleton.getTarget().getLocation().add(0, 1, 0).subtract(fallingBlock.getLocation()).toVector().multiply(0.5));
                            fallingBlock.getWorld().playSound(fallingBlock.getLocation(), Sound.ENTITY_WITHER_SHOOT, 10, 10);

                            //Запускаем проверку куда попал блок
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (!fallingBlock.isDead()) {
                                        fallingBlock.getWorld().spawnParticle(Particle.FLAME, fallingBlock.getLocation(), 1);

                                        //Перебираем всех Entity, которые находятся рядом с упавшим блоком в радиусе 2
                                        for (Entity entity : fallingBlock.getNearbyEntities(2, 2, 2)) {
                                            //Находим игрока
                                            if (entity instanceof Player) {
                                                //Если игрок оказался совсем рядом (ближе 1 блока) - наносим ему урон
                                                if (fallingBlock.getLocation().distanceSquared(entity.getLocation()) < 1) {
                                                    Player player = (Player) entity;
                                                    player.damage(1, skeleton);
                                                    fallingBlock.remove();
                                                    cancel();
                                                }
                                            }
                                        }
                                    } else {
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(plugin, 0, 2L);
                        }
                        if (i % 10 == 0) {
                            Random r = new Random();
                            skeleton.getWorld().playSound(skeleton.getLocation(), Sound.ENTITY_EVOCATION_ILLAGER_PREPARE_SUMMON, 10, 10);
                            for (int j = 0; j < 4; j++) {
                                Zombie zombie = skeleton.getWorld().spawn(skeleton.getLocation().add(r.nextInt(1 + 1) - 1, 0, r.nextInt(1 + 1) - 1), Zombie.class);

                                //Одеваем зомби шлем
                                zombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
                                //Устанавливаем для зомби цель как у скелетона
                                zombie.setTarget(skeleton.getTarget());
                                //немного эффектов
                                zombie.getWorld().spawnParticle(Particle.FLAME, zombie.getLocation(), 1);
                            }
                        }
                        i++;
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    //Обработчик событий для падающих блоков
    @EventHandler
    public void onChange(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock) {
            if (event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equals("Necroman Orb")) {
                //Удаляем упавший блок
                event.setCancelled(true);
                event.getEntity().remove();
            }
        }
    }

    }
