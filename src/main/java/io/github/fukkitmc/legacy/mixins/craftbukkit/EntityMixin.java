package io.github.fukkitmc.legacy.mixins.craftbukkit;

import net.minecraft.server.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public DataWatcher datawatcher;

    @Shadow public abstract int getAirTicks();

    @Shadow public int fireTicks;

    @Shadow public float fallDistance;

    @Shadow public float yaw;

    @Shadow public float pitch;

    @Shadow public abstract NBTTagList a(double... ds);

    @Shadow public abstract String getCustomName();

    @Shadow public CommandObjectiveExecutor au;

    @Shadow public double R;

    @Shadow public abstract boolean R();

    @Shadow public abstract boolean getCustomNameVisible();

    @Shadow public World world;

    @Shadow public abstract UUID getUniqueID();

    @Shadow public int portalCooldown;

    @Shadow public boolean invulnerable;

    @Shadow public int dimension;

    @Shadow public boolean onGround;

    @Shadow public abstract NBTTagList a(float... fs);

    @Shadow public Entity vehicle;

    @Shadow public abstract void appendEntityCrashDetails(CrashReportSystemDetails crashReportSystemDetails);

    @Shadow public double locX;

    @Shadow public double locY;

    @Shadow public double locZ;

    @Shadow public double motZ;

    @Shadow public double motY;

    @Shadow public double motX;

    @Shadow public abstract void b(NBTTagCompound nBTTagCompound);

    /**
     * @author fukkit
     */
    @Overwrite
    public void e(NBTTagCompound nbttagcompound) {
        try {
            nbttagcompound.set("Pos", this.a(this.locX, this.locY, this.locZ));
            nbttagcompound.set("Motion", this.a(this.motX, this.motY, this.motZ));

            // CraftBukkit start - Checking for NaN pitch/yaw and resetting to zero
            // TODO: make sure this is the best way to address this.
            if (Float.isNaN(this.yaw)) {
                this.yaw = 0;
            }

            if (Float.isNaN(this.pitch)) {
                this.pitch = 0;
            }
            // CraftBukkit end
            nbttagcompound.set("Rotation", this.a(this.yaw, this.pitch));
            nbttagcompound.setFloat("FallDistance", this.fallDistance);
            nbttagcompound.setShort("Fire", (short) this.fireTicks);
            nbttagcompound.setShort("Air", (short) this.getAirTicks());
            nbttagcompound.setBoolean("OnGround", this.onGround);
            nbttagcompound.setInt("Dimension", this.dimension);
            nbttagcompound.setBoolean("Invulnerable", this.invulnerable);
            nbttagcompound.setInt("PortalCooldown", this.portalCooldown);
            nbttagcompound.setLong("UUIDMost", this.getUniqueID().getMostSignificantBits());
            nbttagcompound.setLong("UUIDLeast", this.getUniqueID().getLeastSignificantBits());
            // CraftBukkit start
            nbttagcompound.setLong("WorldUUIDLeast", this.world.getDataManager().getUUID().getLeastSignificantBits());
            nbttagcompound.setLong("WorldUUIDMost", this.world.getDataManager().getUUID().getMostSignificantBits());
            nbttagcompound.setInt("Bukkit.updateLevel", 2);
            // CraftBukkit end
            if (this.getCustomName() != null && this.getCustomName().length() > 0) {
                nbttagcompound.setString("CustomName", this.getCustomName());
                nbttagcompound.setBoolean("CustomNameVisible", this.getCustomNameVisible());
            }
            this.au.b(nbttagcompound);
            if (this.R()) {
                nbttagcompound.setBoolean("Silent", this.R());
            }
            this.b(nbttagcompound);
            if (this.vehicle != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                if (this.vehicle.c(nbttagcompound1)) {
                    nbttagcompound.set("Riding", nbttagcompound1);
                }
            }

        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Saving entity NBT");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being saved");

            this.appendEntityCrashDetails(crashreportsystemdetails);
            throw new RuntimeException(throwable);
        }
    }

}
