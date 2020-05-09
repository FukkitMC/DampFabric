package io.github.fukkitmc.legacy.mixins.craftbukkit;

import net.minecraft.server.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(value = EntityLiving.class, remap = false)
public abstract class EntityLivingMixin {

    @Shadow
    public int as;
    @Shadow
    public AttributeMapBase c;
    @Shadow
    public Map<Integer, MobEffect> effects;
    @Shadow
    public int hurtTicks;
    @Shadow
    public int hurtTimestamp;
    @Shadow
    public int deathTicks;

    @Shadow
    public abstract AttributeMapBase getAttributeMap();

    @Shadow
    public abstract ItemStack[] as();

    @Shadow
    public abstract float getHealth();

    @Shadow
    public abstract float getAbsorptionHearts();

    public void b(NBTTagCompound nBTTagCompound) {
        nBTTagCompound.setFloat("HealF", this.getHealth());
        nBTTagCompound.setShort("Health", (short) ((int) Math.ceil(this.getHealth())));
        nBTTagCompound.setShort("HurtTime", (short) this.hurtTicks);
        nBTTagCompound.setInt("HurtByTimestamp", this.hurtTimestamp);
        nBTTagCompound.setShort("DeathTime", (short) this.deathTicks);
        nBTTagCompound.setFloat("AbsorptionAmount", this.getAbsorptionHearts());
        ItemStack[] itemStacks2 = this.as();
        int k = itemStacks2.length;

        int l;
        ItemStack itemStack2;
        for (l = 0; l < k; ++l) {
            itemStack2 = itemStacks2[l];
            if (itemStack2 != null) {
                this.c.a(itemStack2.B());
            }
        }

        nBTTagCompound.set("Attributes", GenericAttributes.a(this.getAttributeMap()));
        itemStacks2 = this.as();
        k = itemStacks2.length;

        for (l = 0; l < k; ++l) {
            itemStack2 = itemStacks2[l];
            if (itemStack2 != null) {
                this.c.b(itemStack2.B());
            }
        }

        if (!this.effects.isEmpty()) {
            NBTTagList nBTTagList = new NBTTagList();

            for (MobEffect mobEffect : this.effects.values()) {
                nBTTagList.add(mobEffect.a(new NBTTagCompound()));
            }

            nBTTagCompound.set("ActiveEffects", nBTTagList);
        }

    }
}
