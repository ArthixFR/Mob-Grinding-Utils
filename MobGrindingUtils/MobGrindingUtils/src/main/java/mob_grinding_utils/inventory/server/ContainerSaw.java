package mob_grinding_utils.inventory.server;

import mob_grinding_utils.ModContainers;
import mob_grinding_utils.ModItems;
import mob_grinding_utils.tile.TileEntitySaw;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class ContainerSaw extends Container {
	private final int numRows = 2;
	public TileEntitySaw saw;
	
	public ContainerSaw(final int windowId, final PlayerInventory playerInventory, PacketBuffer extra) {
		super(ModContainers.SAW.get(), windowId);
		
		BlockPos tilePos = extra.readBlockPos();
		TileEntity tile = playerInventory.player.getEntityWorld().getTileEntity(tilePos);
		if (!(tile instanceof TileEntitySaw))
			return;
		saw = (TileEntitySaw) tile;
		int i = (numRows - 4) * 18;

		addSlot(new SlotRestriction((IInventory) tile, 0, 18, 18, new ItemStack(ModItems.SAW_UPGRADE_SHARPNESS.get(), 1), 10));
		addSlot(new SlotRestriction((IInventory) tile, 1, 43, 18, new ItemStack(ModItems.SAW_UPGRADE_LOOTING.get(), 1), 10));
		addSlot(new SlotRestriction((IInventory) tile, 2, 68, 18, new ItemStack(ModItems.SAW_UPGRADE_FIRE.get(), 1), 10));
		addSlot(new SlotRestriction((IInventory) tile, 3, 93, 18, new ItemStack(ModItems.SAW_UPGRADE_SMITE.get(), 1), 10));
		addSlot(new SlotRestriction((IInventory) tile, 4, 118, 18, new ItemStack(ModItems.SAW_UPGRADE_ARTHROPOD.get(), 1), 10));
		addSlot(new SlotRestriction((IInventory) tile, 5, 143, 18, new ItemStack(ModItems.SAW_UPGRADE_BEHEADING.get(), 1), 10));

		for (int j = 0; j < 3; j++)
			for (int k = 0; k < 9; k++)
				addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 86 + j * 18 + i));
		for (int j = 0; j < 9; j++)
			addSlot(new Slot(playerInventory, j, 8 + j * 18, 144 + i));
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int slotIndex) {

		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) inventorySlots.get(slotIndex);
		if (slot != null && slot.getHasStack()) {
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			if (slotIndex > 5) {
				if (stack1.getItem() == ModItems.SAW_UPGRADE_SHARPNESS.get())
					if (!mergeItemStack(stack1, 0, 1, false))
						return ItemStack.EMPTY;
				if (stack1.getItem() == ModItems.SAW_UPGRADE_LOOTING.get())
					if (!mergeItemStack(stack1, 1, 2, false))
						return ItemStack.EMPTY;
				if (stack1.getItem() == ModItems.SAW_UPGRADE_FIRE.get())
					if (!mergeItemStack(stack1, 2, 3, false))
						return ItemStack.EMPTY;
				if (stack1.getItem() == ModItems.SAW_UPGRADE_SMITE.get())
					if (!mergeItemStack(stack1, 3, 4, false))
						return ItemStack.EMPTY;
				if (stack1.getItem() == ModItems.SAW_UPGRADE_ARTHROPOD.get())
					if (!mergeItemStack(stack1, 4, 5, false))
						return ItemStack.EMPTY;
				if (stack1.getItem() == ModItems.SAW_UPGRADE_BEHEADING.get())
					if (!mergeItemStack(stack1, 5, 6, false))
						return ItemStack.EMPTY;
			} else if (!mergeItemStack(stack1, 6, inventorySlots.size(), false))
				return ItemStack.EMPTY;
			if (stack1.isEmpty())
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();
			if (stack1.getCount() != stack.getCount())
				slot.onTake(player, stack1);
			else
				return ItemStack.EMPTY;
		}

		return stack;
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
		boolean merged = false;
		int slotIndex = startIndex;

		if (reverseDirection)
			slotIndex = endIndex - 1;

		Slot slot;
		ItemStack slotstack;

		if (stack.isStackable()) {
			while (stack.getCount() > 0 && (!reverseDirection && slotIndex < endIndex || reverseDirection && slotIndex >= startIndex)) {
				slot = (Slot) this.inventorySlots.get(slotIndex);
				slotstack = slot.getStack();

				if (!slotstack.isEmpty() && slotstack.getItem() == stack.getItem() && stack.getDamage() == slotstack.getDamage() && ItemStack.areItemStackTagsEqual(stack, slotstack) && slotstack.getCount() < slot.getSlotStackLimit()) {
					int mergedStackSize = stack.getCount() + getSmaller(slotstack.getCount(), slot.getSlotStackLimit());

					if (mergedStackSize <= stack.getMaxStackSize() && mergedStackSize <= slot.getSlotStackLimit()) {
						stack.setCount(0);
						slotstack.setCount(mergedStackSize);
						slot.onSlotChanged();
						merged = true;
					} else if (slotstack.getCount() < stack.getMaxStackSize() && slotstack.getCount() < slot.getSlotStackLimit()) {
						if (slot.getSlotStackLimit() >= stack.getMaxStackSize()) {
							stack.shrink(stack.getMaxStackSize() - slotstack.getCount());
							slotstack.setCount(stack.getMaxStackSize());
							slot.onSlotChanged();
							merged = true;
						}
						else if (slot.getSlotStackLimit() < stack.getMaxStackSize()) {
							stack.shrink(slot.getSlotStackLimit() - slotstack.getCount());
							slotstack.setCount(slot.getSlotStackLimit());
							slot.onSlotChanged();
							merged = true;
						}
					}
				}

				if (reverseDirection)
					--slotIndex;
				else
					++slotIndex;
			}
		}

		if (stack.getCount() > 0) {
			if (reverseDirection)
				slotIndex = endIndex - 1;
			else
				slotIndex = startIndex;

			while (!reverseDirection && slotIndex < endIndex || reverseDirection && slotIndex >= startIndex) {
				slot = (Slot) this.inventorySlots.get(slotIndex);
				slotstack = slot.getStack();
				if (slotstack.isEmpty() && slot.isItemValid(stack) && slot.getSlotStackLimit() < stack.getCount()) {
					ItemStack copy = stack.copy();
					copy.setCount(slot.getSlotStackLimit());
					stack.shrink(slot.getSlotStackLimit());
					slot.putStack(copy);
					slot.onSlotChanged();
					merged = true;
					break;
				} else if (slotstack.isEmpty() && slot.isItemValid(stack)) {
					slot.putStack(stack.copy());
					slot.onSlotChanged();
					stack.setCount(0);
					merged = true;
					break;
				}

				if (reverseDirection)
					--slotIndex;
				else
					++slotIndex;
			}
		}

		return merged;
	}

	protected int getSmaller(int stackSize1, int stackSize2) {
		if (stackSize1 < stackSize2)
			return stackSize1;
		else
			return stackSize2;
	}

}