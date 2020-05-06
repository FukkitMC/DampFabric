package io.github.fukkitmc.legacy.extra;

import net.minecraft.server.ScoreboardTeamBase;

public interface ScoreboardTeamExtra {

    boolean canSeeFriendlyInvisibles();

    ScoreboardTeamBase.EnumNameTagVisibility getNameTagVisibility();

}
