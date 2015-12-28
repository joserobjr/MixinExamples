package br.com.gamemods.mixinexamples.mixins;

import br.com.gamemods.mixinexamples.CustomizedPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityPlayer implements CustomizedPlayer
{
    @Shadow
    private int itemInUseCount;

    private int customInt = 2;

    public MixinEntityPlayer(World world, GameProfile gameProfile)
    {
        super(world, gameProfile);
    }

    @Override
    public int getCustomInt()
    {
        return customInt;
    }

    @Override
    public void setCustomInt(int customInt)
    {
        this.customInt = customInt;
    }

    @Override
    public int getItemInUseCountOnServer()
    {
        return this.itemInUseCount;
    }
}
