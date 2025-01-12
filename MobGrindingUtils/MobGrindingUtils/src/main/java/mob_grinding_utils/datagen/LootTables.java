package mob_grinding_utils.datagen;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import mob_grinding_utils.ModBlocks;
import mob_grinding_utils.Reference;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class LootTables extends LootTableProvider {
    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(Blocks::new, LootParameterSets.BLOCK));
    }

    private static class Blocks extends BlockLootTables {
        @Override
        protected void addTables() {
            registerDropSelfLootTable(ModBlocks.ABSORPTION_HOPPER.getBlock());
            registerDropSelfLootTable(ModBlocks.DARK_OAK_STONE.getBlock());
            registerDropSelfLootTable(ModBlocks.DRAGON_MUFFLER.getBlock());
            registerDropSelfLootTable(ModBlocks.WITHER_MUFFLER.getBlock());
            registerDropSelfLootTable(ModBlocks.ENTITY_CONVEYOR.getBlock());
            registerDropSelfLootTable(ModBlocks.FAN.getBlock());
            registerDropSelfLootTable(ModBlocks.SAW.getBlock());
            registerDropSelfLootTable(ModBlocks.SPIKES.getBlock());
            registerDropSelfLootTable(ModBlocks.XP_TAP.getBlock());
            registerDropSelfLootTable(ModBlocks.ENDER_INHIBITOR_ON.getBlock());
            registerDropping(ModBlocks.ENDER_INHIBITOR_OFF.getBlock(), ModBlocks.ENDER_INHIBITOR_ON.getItem());
            registerDropSelfLootTable(ModBlocks.TINTED_GLASS.getBlock());
            registerLootTable(ModBlocks.DREADFUL_DIRT.getBlock(), (block) -> droppingWithSilkTouch(block, Items.DIRT));
            registerLootTable(ModBlocks.DELIGHTFUL_DIRT.getBlock(), (block) -> droppingWithSilkTouch(block, Items.DIRT));
            registerDropSelfLootTable(ModBlocks.SOLID_XP_BLOCK.getBlock());
            registerDropSelfLootTable(ModBlocks.ENTITY_SPAWNER.getBlock());
        }

        @Nonnull
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getValues().stream()
                    .filter(b -> b.getRegistryName().getNamespace().equals(Reference.MOD_ID))
                    .filter(b -> !b.getRegistryName().getPath().equals("tank"))
                    .filter(b -> !b.getRegistryName().getPath().equals("tank_sink"))
                    .filter(b -> !b.getRegistryName().getPath().equals("jumbo_tank"))
                    .filter(b -> !b.getRegistryName().getPath().equals("xpsolidifier"))
                    .filter(b -> !b.getRegistryName().getPath().equals("fluid_xp"))
                    .collect(Collectors.toList());
        }
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationTracker validationtracker) {
        map.forEach((name, table) -> LootTableManager.validateLootTable(validationtracker, name, table));
    }
}
