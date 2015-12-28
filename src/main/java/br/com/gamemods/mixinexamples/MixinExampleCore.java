package br.com.gamemods.mixinexamples;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.Map;

@IFMLLoadingPlugin.Name("MixinExample")
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class MixinExampleCore implements IFMLLoadingPlugin
{
    public MixinExampleCore()
    {
        MixinBootstrap.init();

        MixinEnvironment.getDefaultEnvironment()
                .addConfiguration("mixins-examples.json");
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[0];
    }

    @Override
    public String getModContainerClass()
    {
        return "br.com.gamemods.mixinexamples.MixinExampleMod";
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {

    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
