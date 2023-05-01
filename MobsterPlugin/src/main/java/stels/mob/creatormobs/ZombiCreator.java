package stels.mob.creatormobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import stels.mob.MobsterPlugin;

public class ZombiCreator implements Listener {

    private static MobsterPlugin plugin;

    public ZombiCreator(MobsterPlugin plugin) {
        this.plugin = plugin;
    }

    public static void createCraftZombi(Location location) {
        Zombie zomb = location.getWorld().spawn(location, Zombie.class);
        zomb.setCustomName(ChatColor.DARK_GREEN + "MOBSTER ZOMBI");
        zomb.setCustomNameVisible(true);

        //zomb.setAI(true);

        Attributable zombArt = zomb;
        AttributeInstance attributeInstance = zombArt.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attributeInstance.setBaseValue(10);
        zomb.setHealth(10);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!zomb.isDead()) {
                    if (zomb.getTarget() == null) {
                        for (Entity entity : zomb.getNearbyEntities(10, 10, 10)) {
                            Player player = (Player) entity;
                            zomb.setTarget(player);
                        }
                    } else {
                        LivingEntity target = zomb.getTarget();
                        if (target.getLocation().distanceSquared(zomb.getLocation()) > 25) {
                            zomb.getWorld().playSound(zomb.getLocation(), Sound.ENTITY_WITHER_SHOOT, 10, 10);

                            //Анимация
                            zomb.getWorld().spawnParticle(Particle.WATER_BUBBLE, zomb.getLocation(), 20);
                            //Добавляем прыжок
                            zomb.setVelocity(target.getLocation().add(0, 2, 0).subtract(zomb.getLocation()).toVector().multiply(0.275));
                        }
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);

    }

    //Обработчик событий для Zombi
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Zombie) {
            if (event.getDamager().getCustomName() != null && event.getDamager().getCustomName().equals(ChatColor.DARK_GREEN + "MOBSTER ZOMBI")) {
                if (event.getEntity() instanceof Player) {
                    //Добавляем эффет увядания угрока
                    Player player = (Player) event.getEntity();
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 0));
                }
            }
        }
    }
}
