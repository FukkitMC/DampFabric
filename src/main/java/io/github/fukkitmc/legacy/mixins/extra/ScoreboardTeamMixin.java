package io.github.fukkitmc.legacy.mixins.extra;


import io.github.fukkitmc.legacy.extra.ScoreboardTeamExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.ScoreboardTeam;
import net.minecraft.server.ScoreboardTeamBase;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ScoreboardTeam.class)
public class ScoreboardTeamMixin implements ScoreboardTeamExtra {
    @Override
    public boolean canSeeFriendlyInvisibles() {
        return false;
    }

    @Override
    public ScoreboardTeamBase.EnumNameTagVisibility getNameTagVisibility() {
        return null;
    }
}
