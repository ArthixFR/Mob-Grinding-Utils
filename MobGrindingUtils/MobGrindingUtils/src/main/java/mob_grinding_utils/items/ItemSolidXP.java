package mob_grinding_utils.items;

import java.util.List;

import javax.annotation.Nullable;

import mob_grinding_utils.tile.TileEntitySinkTank;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemSolidXP extends Item {
	public int xpValue = 50; // just a basic 

	public ItemSolidXP(Properties properties, int value) {
		super(properties);
		xpValue = value;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
		list.add(new TranslationTextComponent("tooltip.solid_xp").appendString(Integer.toString(xpValue)).mergeStyle(TextFormatting.YELLOW));
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
		if (entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entity;
			if (xpValue > 0)
				if (!world.isRemote) {
					TileEntitySinkTank.addPlayerXP(player, xpValue);
					world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5F, 0.8F + world.rand.nextFloat() * 0.4F);
				}
		}
		return super.onItemUseFinish(stack, world, entity);
	}
}