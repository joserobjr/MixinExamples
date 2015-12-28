package br.com.gamemods.mixinexamples;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class MixinExampleMod extends DummyModContainer
{
    public MixinExampleMod()
    {
        super(new ModMetadata());
        ModMetadata metadata = getMetadata();
        metadata.authorList.add("joserobjr");
        metadata.name="Mixin Example Mod";
        metadata.modId="MixinExample";
        metadata.version="1.0-SNAPSHOT";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        return true;
    }
}
