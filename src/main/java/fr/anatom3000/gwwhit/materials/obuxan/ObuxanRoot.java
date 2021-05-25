package fr.anatom3000.gwwhit.materials.obuxan;
import net.minecraft.util.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.item.BlockItem;
import fr.anatom3000.gwwhit.CustomItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorMaterial;
import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
public class ObuxanRoot  {
public static final ObuxanIngot OBUXAN_INGOT = new ObuxanIngot();
public static final ObuxanBlock OBUXAN_BLOCK = new ObuxanBlock();
public static final ObuxanOre OBUXAN_ORE = new ObuxanOre();
public static final ObuxanShovel OBUXAN_SHOVEL = new ObuxanShovel(ObuxanMaterial.INSTANCE);
public static final ObuxanPickaxe OBUXAN_PICKAXE = new ObuxanPickaxe(ObuxanMaterial.INSTANCE);
public static final ObuxanAxe OBUXAN_AXE = new ObuxanAxe(ObuxanMaterial.INSTANCE);
public static final ObuxanHoe OBUXAN_HOE = new ObuxanHoe(ObuxanMaterial.INSTANCE);
public static final ArmorMaterial obuxanArmorMaterial = new ObuxanArmorMaterial();
public static final ObuxanHelmet OBUXAN_HELMET = new ObuxanHelmet(obuxanArmorMaterial);
public static final ObuxanChestplate OBUXAN_CHESTPLATE = new ObuxanChestplate(obuxanArmorMaterial);
public static final ObuxanLeggings OBUXAN_LEGGINGS = new ObuxanLeggings(obuxanArmorMaterial);
public static final ObuxanBoots OBUXAN_BOOTS = new ObuxanBoots(obuxanArmorMaterial);

public void onInitialize() {
Registry.register(Registry.ITEM, new Identifier("gwwhit","obuxan_ingot"),OBUXAN_INGOT);
Registry.register(Registry.BLOCK, new Identifier("gwwhit", "obuxan_block"), OBUXAN_BLOCK);
Registry.register(Registry.ITEM, new Identifier("gwwhit", "obuxan_block"), new BlockItem(OBUXAN_BLOCK, new FabricItemSettings().group(CustomItemGroups.GWWHITGroup)));
Registry.register(Registry.BLOCK, new Identifier("gwwhit", "obuxan_ore"), OBUXAN_ORE);
Registry.register(Registry.ITEM, new Identifier("gwwhit", "obuxan_ore"), new BlockItem(OBUXAN_ORE, new FabricItemSettings().group(CustomItemGroups.GWWHITGroup)));
Registry.register(Registry.ITEM, new Identifier("gwwhit","obuxan_shovel"),OBUXAN_SHOVEL);
Registry.register(Registry.ITEM, new Identifier("gwwhit","obuxan_pickaxe"),OBUXAN_PICKAXE);
Registry.register(Registry.ITEM, new Identifier("gwwhit","obuxan_axe"),OBUXAN_AXE);
Registry.register(Registry.ITEM, new Identifier("gwwhit","obuxan_hoe"),OBUXAN_HOE);
Registry.register(Registry.ITEM, new Identifier("gwwhit","obuxan_helmet"),OBUXAN_HELMET);
Registry.register(Registry.ITEM, new Identifier("gwwhit","obuxan_chestplate"),OBUXAN_CHESTPLATE);
Registry.register(Registry.ITEM, new Identifier("gwwhit","obuxan_leggings"),OBUXAN_LEGGINGS);
Registry.register(Registry.ITEM, new Identifier("gwwhit","obuxan_boots"),OBUXAN_BOOTS);
RegistryKey<ConfiguredFeature<?,?>> ore = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, new Identifier("gwwhit","ore_obuxan"));
BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.UNDERGROUND_ORES, ore);
}
public void onInitializeClient() {

}
}