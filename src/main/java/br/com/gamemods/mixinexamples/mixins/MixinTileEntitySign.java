package br.com.gamemods.mixinexamples.mixins;

import br.com.gamemods.mixinexamples.CustomizedSign;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntitySign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(TileEntitySign.class)
public class MixinTileEntitySign extends TileEntitySign implements CustomizedSign
{
    private NBTBase customData;

    @Inject(method = "writeToNBT", at = @At("RETURN"))
    private void appendDataToNBT(NBTTagCompound signCompound, CallbackInfo ci)
    {
        if(customData == null)
            return;

        signCompound.setTag("Custom", customData);
    }

    @Inject(method = "readFromNBT", at = @At("RETURN"))
    private void readCustomDataFromNBT(NBTTagCompound signCompound, CallbackInfo ci)
    {
        customData = signCompound.getTag("Custom");
    }

    @Override
    public NBTBase getCustomData()
    {
        return customData;
    }

    @Override
    public void setCustomData(NBTBase customData)
    {
        this.customData = customData;
    }

    @Override
    public List<String> getLines()
    {
        // Using a custom implementation instead of Arrays.asList(signText)
        // to enforce the 15 char limit that exists on the signs
        return new SignText(signText);

        /*
         * We can't create classes on mixin packages, this inner class won't work because it's a new class.
         *
        return new AbstractList<String>()
        {
            @Override
            public String get(int index)
            {
                return signText[index];
            }

            @Override
            public int size()
            {
                return signText.length;
            }

            @Override
            public String set(int index, String element)
            {
                String previous = signText[index];
                if(element.length() > 15)
                    signText[index] = element.substring(0, 15);
                else
                    signText[index] = element;
                return previous;
            }

            @Override
            public Object[] toArray()
            {
                return signText.clone();
            }
        };
        */
    }
}
