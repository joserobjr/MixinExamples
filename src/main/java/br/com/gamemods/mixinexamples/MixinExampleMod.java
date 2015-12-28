package br.com.gamemods.mixinexamples;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

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
        bus.register(this);
        return true;
    }

    @Subscribe
    public void onServerStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandBase()
        {
            @Override
            public String getCommandName()
            {
                return "getcustomint";
            }

            @Override
            public String getCommandUsage(ICommandSender sender)
            {
                return "/getcustomint";
            }

            @Override
            public boolean canCommandSenderUseCommand(ICommandSender sender)
            {
                return true;
            }

            @Override
            public void processCommand(ICommandSender sender, String[] args)
            {
                if(sender instanceof CustomizedPlayer)
                    sender.addChatMessage(new ChatComponentText("Your custom int value is "+((CustomizedPlayer) sender).getCustomInt())
                            .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
                else
                    sender.addChatMessage(new ChatComponentText("You don't have a custom int value")
                            .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            }
        });

        event.registerServerCommand(new CommandBase()
        {
            @Override
            public String getCommandName()
            {
                return "setcustomint";
            }

            @Override
            public String getCommandUsage(ICommandSender sender)
            {
                return "/setcustomint value";
            }

            @Override
            public boolean canCommandSenderUseCommand(ICommandSender sender)
            {
                return true;
            }

            @Override
            public void processCommand(ICommandSender sender, String[] args)
            {
                int x = -1;
                boolean success;
                try
                {
                    if (args.length != 1)
                        success = false;
                    else
                    {
                        x = Integer.valueOf(args[0]);
                        success = true;
                    }
                }
                catch (NumberFormatException e)
                {
                    success = false;
                }

                if(!success)
                {
                    sender.addChatMessage(new ChatComponentText(getCommandUsage(sender))
                            .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED))
                    );
                }
                else if(sender instanceof CustomizedPlayer)
                {
                    ((CustomizedPlayer) sender).setCustomInt(x);
                    sender.addChatMessage(new ChatComponentText("Your custom int value is now "+x)
                            .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN))
                    );
                }
                else
                    sender.addChatMessage(new ChatComponentText("You don't have a custom int value")
                            .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            }
        });
    }
}
