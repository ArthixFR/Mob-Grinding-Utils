package mob_grinding_utils.itemblocks;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MGUBlockItem extends BlockItem {
    public MGUBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
        block = blockIn;
    }
    private final Block block;

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (I18n.hasKey(block.getTranslationKey() + ".tooltip_1")) {
            tooltip.add(new TranslationTextComponent(block.getTranslationKey() + ".tooltip_1").mergeStyle(TextFormatting.YELLOW));
            if (I18n.hasKey(block.getTranslationKey() + ".tooltip_2")) {
                tooltip.add(new TranslationTextComponent(block.getTranslationKey() + ".tooltip_2").mergeStyle(TextFormatting.YELLOW));
                if (I18n.hasKey(block.getTranslationKey() + ".tooltip_3")) {
                    tooltip.add(new TranslationTextComponent(block.getTranslationKey() + ".tooltip_3").mergeStyle(TextFormatting.YELLOW));
                    if (I18n.hasKey(block.getTranslationKey() + ".tooltip_4")) {
                        tooltip.add(new TranslationTextComponent(block.getTranslationKey() + ".tooltip_4").mergeStyle(TextFormatting.YELLOW));
                        if (I18n.hasKey(block.getTranslationKey() + ".tooltip_5")) {
                            tooltip.add(new TranslationTextComponent(block.getTranslationKey() + ".tooltip_5").mergeStyle(TextFormatting.YELLOW));
                            if (I18n.hasKey(block.getTranslationKey() + ".tooltip_6")) {
                                tooltip.add(new TranslationTextComponent(block.getTranslationKey() + ".tooltip_6").mergeStyle(TextFormatting.YELLOW));
                            }
                        }
                    }
                }
            }
        }
    }
}
