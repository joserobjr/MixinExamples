package br.com.gamemods.mixinexamples;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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

    public class ItemProgressOnLevel
    {
        @SubscribeEvent
        public void onTick(TickEvent.PlayerTickEvent event)
        {
            if(event.phase == TickEvent.Phase.END && event.player instanceof CustomizedPlayer)
            {
                CustomizedPlayer custom = (CustomizedPlayer) event.player;
                int itemUse = custom.getItemInUseCountOnServer();
                int max = custom.getCustomInt();
                if(itemUse == 0)
                {
                    if(max > 0)
                    {
                        custom.setCustomInt(0);
                        event.player.addExperienceLevel(-5000);
                    }
                    return;
                }

                ItemStack currentItem = event.player.inventory.getCurrentItem();
                if(currentItem.getItem() == Items.bow)
                {
                    itemUse -= 71980;
                    if(itemUse < 0) itemUse = 0;
                }

                if(itemUse>max)
                    custom.setCustomInt(max=itemUse);

                event.player.addExperienceLevel(-5000);
                event.player.experienceLevel = (int)(((max-itemUse)/(float)max)*100);
                //event.player.addExperienceLevel(1);
            }
        }
    }

    @Subscribe
    public void onInitEvent(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(new ItemProgressOnLevel());
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

        event.registerServerCommand(new CommandBase()
        {
            @Override
            public String getCommandName()
            {
                return "closescreen";
            }

            @Override
            public boolean canCommandSenderUseCommand(ICommandSender sender)
            {
                return sender instanceof EntityPlayer;
            }

            @Override
            public String getCommandUsage(ICommandSender sender)
            {
                return "/closescreen";
            }

            @Override
            public void processCommand(ICommandSender sender, String[] args)
            {
                ((EntityPlayer)sender).closeScreen();
            }
        });
    }
}
