package mob_grinding_utils.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mob_grinding_utils.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TileEntityTank extends TileEntity  implements ITickableTileEntity {
	public FluidTank tank = new FluidTank(FluidAttributes.BUCKET_VOLUME *  32);
    private final LazyOptional<IFluidHandler> tank_holder = LazyOptional.of(() -> tank);
	public int prevTankAmount;

	public TileEntityTank() {
		super(ModBlocks.TANK.getTileEntityType());
	}

	public TileEntityTank(TileEntityType<TileEntitySinkTank> TANK_SINK_TILE) {
		super(TANK_SINK_TILE);
	}

	public TileEntityTank(TileEntityType<TileEntityJumboTank> JUMBO_TANK_TILE, FluidTank tankIn) {
		super(JUMBO_TANK_TILE);
		this.tank = tankIn;
	}

	@Override
	public void tick() {
		if (getWorld().isRemote)
			return;
		if(prevTankAmount != tank.getFluidAmount())
			updateBlock();
		prevTankAmount = tank.getFluidAmount();
	}

	public void updateBlock() {
		getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos), 3);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
		super.onDataPacket(net, packet);
		read(getBlockState(), packet.getNbtCompound());
		return;
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbt = new CompoundNBT();
		write(nbt);
		return new SUpdateTileEntityPacket(getPos(), 0, nbt);
	}

	@Override
    public CompoundNBT getUpdateTag() {
		CompoundNBT nbt = new CompoundNBT();
        return write(nbt);
    }

	@Override
	public void read(BlockState state, CompoundNBT tagCompound) {
		super.read(state, tagCompound);
		tank.readFromNBT(tagCompound);
	}

	@Override
	public CompoundNBT write(CompoundNBT tagCompound) {
		super.write(tagCompound);
		tank.writeToNBT(tagCompound);
		return tagCompound;
	}

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return tank_holder.cast();
        return super.getCapability(capability, facing);
    }

    public FluidTank getTank(){
        return this.tank;
    }

	public int getScaledFluid(int scale) {
		return tank.getFluid() != null ? (int) ((float) tank.getFluidAmount() / (float) tank.getCapacity() * scale) : 0;
	}
}
