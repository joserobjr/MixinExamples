package br.com.gamemods.mixinexamples.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerMP.class)
public abstract class MixinEntityPlayerMP extends EntityPlayerMP
{
    public MixinEntityPlayerMP(MinecraftServer minecraft, WorldServer server, GameProfile profile, ItemInWorldManager itemInWorldManager)
    {
        super(minecraft,server,profile,itemInWorldManager);
    }

    @Inject(method = "mountEntity", at = @At(value = "HEAD"), cancellable = true)
    private void onMount(Entity entity, CallbackInfo ci)
    {
        addChatMessage(new ChatComponentText("You can't mount on "+entity.getCommandSenderName()));
        ci.cancel();
    }
}
