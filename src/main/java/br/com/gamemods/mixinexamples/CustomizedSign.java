package br.com.gamemods.mixinexamples;

import net.minecraft.nbt.NBTBase;

import java.util.AbstractList;
import java.util.List;

public interface CustomizedSign
{
    NBTBase getCustomData();

    void setCustomData(NBTBase customData);

    List<String> getLines();

    class SignText extends AbstractList<String>
    {
        private String[] signText;

        public SignText(String[] signText)
        {
            this.signText = signText;
        }

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
}
