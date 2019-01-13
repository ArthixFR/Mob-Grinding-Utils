package mob_grinding_utils.client.render;

import mob_grinding_utils.ModBlocks;
import mob_grinding_utils.blocks.BlockSaw;
import mob_grinding_utils.models.ModelSawBase;
import mob_grinding_utils.models.ModelSawBlade;
import mob_grinding_utils.tile.TileEntitySaw;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TileEntitySawRenderer extends TileEntityRenderer<TileEntitySaw> {
	private static final ResourceLocation BASE_TEXTURE = new ResourceLocation("mob_grinding_utils:textures/tiles/saw_base.png");
	private static final ResourceLocation BLADE_TEXTURE = new ResourceLocation("mob_grinding_utils:textures/tiles/saw_blade.png");
	private final ModelSawBase saw_base = new ModelSawBase();
	private final ModelSawBlade saw_blade = new ModelSawBlade();

	public void renderTile(TileEntitySaw tile, double x, double y, double z, float partialTick, int destroyStage) {
		IBlockState state = tile.getWorld().getBlockState(tile.getPos());

		if(state == null || state.getBlock() != ModBlocks.SAW)
			return;

		EnumFacing facing = state.getValue(BlockSaw.FACING);

		bindTexture(BASE_TEXTURE);
		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		GlStateManager.scaled(-1, -1, 1);

		switch (facing) {
		case UP:
			GlStateManager.rotatef(0F, 0.0F, 1F, 0F);
			break;
		case DOWN:
			GlStateManager.rotatef(180F, 1F, 0F, 0F);
			break;
		case NORTH:
			GlStateManager.rotatef(90F, 1F, 0F, 0F);
			break;
		case SOUTH:
			GlStateManager.rotatef(-90F, 1.0F, 0F, 0F);
			break;
		case WEST:
			GlStateManager.rotatef(90F, 0.0F, 0F, 1F);
			break;
		case EAST:
			GlStateManager.rotatef(-90F, 0.0F, 0F, 1F);
			break;
		}
		GlStateManager.translatef(0F, -1F, 0F);
		saw_base.render();

		GlStateManager.pushMatrix();

		float ticks = tile.animationTicks + (tile.animationTicks - tile.prevAnimationTicks)  * partialTick;

		GlStateManager.rotatef(ticks, 0.0F, 1F, 0F);
		saw_base.renderAxle();
		
		GlStateManager.pushMatrix();
		GlStateManager.rotatef(45F, 0.0F, 1F, 0F);
		saw_base.renderMace();
		GlStateManager.popMatrix();
		
		GlStateManager.pushMatrix();
		GlStateManager.rotatef(165F, 0.0F, 1F, 0F);
		saw_base.renderMace();
		GlStateManager.popMatrix();
		
		GlStateManager.pushMatrix();
		GlStateManager.rotatef(285F, 0.0F, 1F, 0F);
		saw_base.renderMace();
		GlStateManager.popMatrix();
		
		bindTexture(BLADE_TEXTURE);
		GlStateManager.pushMatrix();
		GlStateManager.translatef(0F, 0.2F, -0.16F);
		GlStateManager.rotatef(8F, 1.0F, 0F, 0F);
		saw_blade.render();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translatef(0F, 0.00F, 0.16F);
		GlStateManager.rotatef(-8F, 1.0F, 0F, 0F);
		saw_blade.render();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translatef(0F, -0.2F, -0.16F);
		GlStateManager.rotatef(8F, 1.0F, 0F, 0F);
		saw_blade.render();
		GlStateManager.popMatrix();

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();

	}

	@Override
	public void render(TileEntitySaw te, double x, double y, double z, float partialTicks, int destroyStage) {
		if(te == null || !te.hasWorld()) {
			renderTileAsItem(x, y, z);
			return;
		}
		renderTile(te, x, y, z, partialTicks, destroyStage);
	}

	private void renderTileAsItem(double x, double y, double z) {
		GlStateManager.pushMatrix();
		bindTexture(BASE_TEXTURE);
		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.scaled(-1D, -1D, 1D);
		saw_base.render();
		saw_base.renderAxle();
		
		GlStateManager.pushMatrix();
		GlStateManager.rotatef(45F, 0.0F, 1F, 0F);
		saw_base.renderMace();
		GlStateManager.popMatrix();
		
		GlStateManager.pushMatrix();
		GlStateManager.rotatef(165F, 0.0F, 1F, 0F);
		saw_base.renderMace();
		GlStateManager.popMatrix();
		
		GlStateManager.pushMatrix();
		GlStateManager.rotatef(285F, 0.0F, 1F, 0F);
		saw_base.renderMace();
		GlStateManager.popMatrix();
		
		bindTexture(BLADE_TEXTURE);
		GlStateManager.pushMatrix();
		GlStateManager.translatef(0F, 0.2F, -0.16F);
		GlStateManager.rotatef(8F, 1.0F, 0F, 0F);
		saw_blade.render();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translatef(0F, 0F, 0.16F);
		GlStateManager.rotatef(-8F, 1.0F, 0F, 0F);
		saw_blade.render();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translatef(0F, -0.2F, -0.16F);
		GlStateManager.rotatef(8F, 1.0F, 0F, 0F);
		saw_blade.render();
		GlStateManager.popMatrix();

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}

}