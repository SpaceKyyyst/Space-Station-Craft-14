
package net.mcreator.ssc;

import net.mcreator.ssc.item.ReagentContainerItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

@EventBusSubscriber(modid = "ssc_14")
public class ModReagents {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, "ssc_14");
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, "ssc_14");

	public static final List<Reagent> CONFIG_REAGENTS = List.of(
	    // Напитки (Drinks)
	    new Reagent("coffee", "reagent.ssc_14.coffee", 0x664300),
	    new Reagent("cream", "reagent.ssc_14.cream", 0xDFD7AF),
	    new Reagent("cafe_latte", "reagent.ssc_14.cafe_latte", 0x664300),
	    new Reagent("green_tea", "reagent.ssc_14.green_tea", 0xC33F00),
	    new Reagent("grenadine", "reagent.ssc_14.grenadine", 0xEA1D26),
	    new Reagent("iced_coffee", "reagent.ssc_14.iced_coffee", 0x102838),
	    new Reagent("iced_green_tea", "reagent.ssc_14.iced_green_tea", 0xCE4200),
	    new Reagent("iced_tea", "reagent.ssc_14.iced_tea", 0x104038),
	    new Reagent("lemonade", "reagent.ssc_14.lemonade", 0xFFFF00),
	    new Reagent("milk", "reagent.ssc_14.milk", 0xDFDFDF),
	    new Reagent("oat_milk", "reagent.ssc_14.oat_milk", 0x302000),
	    new Reagent("soy_milk", "reagent.ssc_14.soy_milk", 0x302000),
	    new Reagent("spoiled_milk", "reagent.ssc_14.spoiled_milk", 0xFAFFBA),
	    new Reagent("nuka_cola", "reagent.ssc_14.nuka_cola", 0x100800),
	    new Reagent("soda_water", "reagent.ssc_14.soda_water", 0x619494),
	    new Reagent("soy_latte", "reagent.ssc_14.soy_latte", 0x664300),
	    new Reagent("tea", "reagent.ssc_14.tea", 0x8A5A3A),
	    new Reagent("tonic_water", "reagent.ssc_14.tonic_water", 0x0064C8),
	    new Reagent("water", "reagent.ssc_14.water", 0xC0E0FF),
	    new Reagent("ice", "reagent.ssc_14.ice", 0xBED8E6),
	    new Reagent("cola", "reagent.ssc_14.cola", 0x422912),
	    new Reagent("changeling_sting", "reagent.ssc_14.changeling_sting", 0x2E6671),
	    new Reagent("dr_gibb", "reagent.ssc_14.dr_gibb", 0x102000),
	    new Reagent("energy_drink", "reagent.ssc_14.energy_drink", 0xFFFFBF),
	    new Reagent("grape_soda", "reagent.ssc_14.grape_soda", 0xAE94A6),
	    new Reagent("lemon_lime", "reagent.ssc_14.lemon_lime", 0x878F00),
	    new Reagent("pwr_game", "reagent.ssc_14.pwr_game", 0x9385BF),
	    new Reagent("space_mountain_wind", "reagent.ssc_14.space_mountain_wind", 0x102000),
	    new Reagent("space_up", "reagent.ssc_14.space_up", 0x00FF00),
	    new Reagent("starkist", "reagent.ssc_14.starkist", 0x9F3400),
	    new Reagent("fourteen_loko", "reagent.ssc_14.fourteen_loko", 0xDEB928),
	    
	    // Соки (Juices)
	    new Reagent("apple_juice", "reagent.ssc_14.apple_juice", 0xFDAD01),
	    new Reagent("banana_juice", "reagent.ssc_14.banana_juice", 0xFFE777),
	    new Reagent("berry_juice", "reagent.ssc_14.berry_juice", 0x660099),
	    new Reagent("poison_berry_juice", "reagent.ssc_14.poison_berry_juice", 0x6600CC),
	    new Reagent("carrot_juice", "reagent.ssc_14.carrot_juice", 0xFF8820),
	    new Reagent("grape_juice", "reagent.ssc_14.grape_juice", 0x512284),
	    new Reagent("lemon_juice", "reagent.ssc_14.lemon_juice", 0xFFF690),
	    new Reagent("lime_juice", "reagent.ssc_14.lime_juice", 0x99BB43),
	    new Reagent("orange_juice", "reagent.ssc_14.orange_juice", 0xE78108),
	    new Reagent("pineapple_juice", "reagent.ssc_14.pineapple_juice", 0xFFFF00),
	    new Reagent("potato_juice", "reagent.ssc_14.potato_juice", 0x302000),
	    new Reagent("tomato_juice", "reagent.ssc_14.tomato_juice", 0x731008),
	    new Reagent("watermelon_juice", "reagent.ssc_14.watermelon_juice", 0xEF3520),
	    
	    // Алкоголь (Alcohol)
	    new Reagent("absinthe", "reagent.ssc_14.absinthe", 0x33EE00),
	    new Reagent("ale", "reagent.ssc_14.ale", 0x663100),
	    new Reagent("beer", "reagent.ssc_14.beer", 0xCFA85F),
	    new Reagent("blue_curacao", "reagent.ssc_14.blue_curacao", 0x0000CD),
	    new Reagent("cognac", "reagent.ssc_14.cognac", 0xAB3C05),
	    new Reagent("deadrum", "reagent.ssc_14.deadrum", 0x664300),
	    new Reagent("ethanol", "reagent.ssc_14.ethanol", 0xB05B3C),
	    new Reagent("gin", "reagent.ssc_14.gin", 0x664300),
	    new Reagent("kahlua", "reagent.ssc_14.kahlua", 0x664300),
	    new Reagent("melon_liquor", "reagent.ssc_14.melon_liquor", 0x138808),
	    new Reagent("nt_cahors", "reagent.ssc_14.nt_cahors", 0x7E4043),
	    new Reagent("poison_wine", "reagent.ssc_14.poison_wine", 0x000000),
	    new Reagent("rum", "reagent.ssc_14.rum", 0x664300),
	    new Reagent("sake", "reagent.ssc_14.sake", 0xDDDDDD),
	    new Reagent("tequila", "reagent.ssc_14.tequila", 0xD7D1D1),
	    new Reagent("vermouth", "reagent.ssc_14.vermouth", 0x91FF91),
	    new Reagent("vodka", "reagent.ssc_14.vodka", 0xD1D1D1),
	    new Reagent("whiskey", "reagent.ssc_14.whiskey", 0x664300),
	    new Reagent("wine", "reagent.ssc_14.wine", 0x7E4043),
	    
	    // Коктейли (Cocktails)
	    new Reagent("acidspit", "reagent.ssc_14.acidspit", 0x365000),
	    new Reagent("allies_cocktail", "reagent.ssc_14.allies_cocktail", 0x00664D),
	    new Reagent("aloe", "reagent.ssc_14.aloe", 0x192C00),
	    new Reagent("amasec", "reagent.ssc_14.amasec", 0x124DA7),
	    new Reagent("andalusia", "reagent.ssc_14.andalusia", 0x665700),
	    new Reagent("antifreeze", "reagent.ssc_14.antifreeze", 0xFF7D63),
	    new Reagent("atomic_bomb", "reagent.ssc_14.atomic_bomb", 0x666300),
	    new Reagent("b52", "reagent.ssc_14.b52", 0x664300),
	    new Reagent("bahama_mama", "reagent.ssc_14.bahama_mama", 0xFF7F3B),
	    new Reagent("banana_honk", "reagent.ssc_14.banana_honk", 0xFFFF91),
	    new Reagent("barefoot", "reagent.ssc_14.barefoot", 0x664300),
	    new Reagent("beepsky_smash", "reagent.ssc_14.beepsky_smash", 0x664300),
	    new Reagent("bilk", "reagent.ssc_14.bilk", 0x895C4C),
	    new Reagent("black_russian", "reagent.ssc_14.black_russian", 0x360000),
	    new Reagent("bloody_mary", "reagent.ssc_14.bloody_mary", 0x660000),
	    new Reagent("booger", "reagent.ssc_14.booger", 0x8CFF8C),
	    new Reagent("brave_bull", "reagent.ssc_14.brave_bull", 0x664300),
	    new Reagent("cuba_libre", "reagent.ssc_14.cuba_libre", 0x3E1B00),
	    new Reagent("demons_blood", "reagent.ssc_14.demons_blood", 0xA70000),
	    new Reagent("devils_kiss", "reagent.ssc_14.devils_kiss", 0xA68310),
	    new Reagent("doctors_delight", "reagent.ssc_14.doctors_delight", 0xFF8CFF),
	    new Reagent("driest_martini", "reagent.ssc_14.driest_martini", 0x2E6671),
	    new Reagent("erika_suprise", "reagent.ssc_14.erika_suprise", 0x2E6671),
	    new Reagent("gargle_blaster", "reagent.ssc_14.gargle_blaster", 0x9CC8B4),
	    new Reagent("gin_fizz", "reagent.ssc_14.gin_fizz", 0x664300),
	    new Reagent("gin_tonic", "reagent.ssc_14.gin_tonic", 0x004166),
	    new Reagent("goldschlager", "reagent.ssc_14.goldschlager", 0xFFFF91),
	    new Reagent("grog", "reagent.ssc_14.grog", 0x664300),
	    new Reagent("hippies_delight", "reagent.ssc_14.hippies_delight", 0x6EAA0C),
	    new Reagent("hooch", "reagent.ssc_14.hooch", 0x664E00),
	    new Reagent("iced_beer", "reagent.ssc_14.iced_beer", 0x664300),
	    new Reagent("irish_car_bomb", "reagent.ssc_14.irish_car_bomb", 0x2E6671),
	    new Reagent("irish_cream", "reagent.ssc_14.irish_cream", 0x664300),
	    new Reagent("irish_coffee", "reagent.ssc_14.irish_coffee", 0x664300),
	    new Reagent("kira_special", "reagent.ssc_14.kira_special", 0xCCCC99),
	    new Reagent("lean", "reagent.ssc_14.lean", 0x9400D3),
	    new Reagent("leanshine", "reagent.ssc_14.leanshine", 0x9D5FB8),
	    new Reagent("long_island_iced_tea", "reagent.ssc_14.long_island_iced_tea", 0x664300),
	    new Reagent("manhattan", "reagent.ssc_14.manhattan", 0x664300),
	    new Reagent("manhattan_project", "reagent.ssc_14.manhattan_project", 0x664300),
	    new Reagent("manly_dorf", "reagent.ssc_14.manly_dorf", 0x664300),
	    new Reagent("margarita", "reagent.ssc_14.margarita", 0x8CFF8C),
	    new Reagent("martini", "reagent.ssc_14.martini", 0x664300),
	    new Reagent("mead", "reagent.ssc_14.mead", 0x664300),
	    new Reagent("mojito", "reagent.ssc_14.mojito", 0x664300),
	    new Reagent("moonshine", "reagent.ssc_14.moonshine", 0xD1D7D1),
	    new Reagent("neurotoxin", "reagent.ssc_14.neurotoxin", 0x2E2E61),
	    new Reagent("patron", "reagent.ssc_14.patron", 0x585840),
	    new Reagent("red_mead", "reagent.ssc_14.red_mead", 0xC73C00),
	    new Reagent("rewriter", "reagent.ssc_14.rewriter", 0x485000),
	    new Reagent("sbiten", "reagent.ssc_14.sbiten", 0x004166),
	    new Reagent("screwdriver", "reagent.ssc_14.screwdriver", 0xA68310),
	    new Reagent("silencer", "reagent.ssc_14.silencer", 0x004666),
	    new Reagent("singulo", "reagent.ssc_14.singulo", 0x3B0C0C),
	    new Reagent("snow_white", "reagent.ssc_14.snow_white", 0xFFFFFF),
	    new Reagent("sui_dream", "reagent.ssc_14.sui_dream", 0x00A86B),
	    new Reagent("syndicate_bomb", "reagent.ssc_14.syndicate_bomb", 0x2E6660),
	    new Reagent("tequila_sunrise", "reagent.ssc_14.tequila_sunrise", 0xFFE48C),
	    new Reagent("three_mile_island", "reagent.ssc_14.three_mile_island", 0x666340),
	    new Reagent("toxins_special", "reagent.ssc_14.toxins_special", 0x665C00),
	    new Reagent("vodka_martini", "reagent.ssc_14.vodka_martini", 0x004666),
	    new Reagent("vodka_tonic", "reagent.ssc_14.vodka_tonic", 0x0064C8),
	    
	    // Химикаты (Chemicals)
	    new Reagent("potassium", "reagent.ssc_14.potassium", 0xC6C8CC),
	    new Reagent("sodium", "reagent.ssc_14.sodium", 0xC6C8CC),
	    new Reagent("lithium", "reagent.ssc_14.lithium", 0xC6C8CC),
	    new Reagent("phosphorus", "reagent.ssc_14.phosphorus", 0xEDE4E4),
	    new Reagent("sulfur", "reagent.ssc_14.sulfur", 0xFFF385),
	    new Reagent("carbon", "reagent.ssc_14.carbon", 0x22282B),
	    new Reagent("oxygen", "reagent.ssc_14.oxygen", 0x808080),
	    new Reagent("nitrogen", "reagent.ssc_14.nitrogen", 0x808080),
	    new Reagent("hydrogen", "reagent.ssc_14.hydrogen", 0x808080),
	    new Reagent("chlorine", "reagent.ssc_14.chlorine", 0xA2FF00),
	    new Reagent("fluorine", "reagent.ssc_14.fluorine", 0x808080),
	    new Reagent("aluminium", "reagent.ssc_14.aluminium", 0x848789),
	    new Reagent("copper", "reagent.ssc_14.copper", 0xB05B3C),
	    new Reagent("gold", "reagent.ssc_14.gold", 0xF7C430),
	    new Reagent("silver", "reagent.ssc_14.silver", 0xD0D0D0),
	    new Reagent("iron", "reagent.ssc_14.iron", 0x434B4D),
	    new Reagent("mercury", "reagent.ssc_14.mercury", 0x929296),
	    new Reagent("silicon", "reagent.ssc_14.silicon", 0x364266),
	    new Reagent("uranium", "reagent.ssc_14.uranium", 0x8FA191),
	    new Reagent("radium", "reagent.ssc_14.radium", 0x00FF04),
	    new Reagent("iodine", "reagent.ssc_14.iodine", 0xBC8A00),
	    
	    // Медицина (Medicine)
	    new Reagent("bicaridine", "reagent.ssc_14.bicaridine", 0xFFAA00),
	    new Reagent("kelotane", "reagent.ssc_14.kelotane", 0xBF3D19),
	    new Reagent("dermaline", "reagent.ssc_14.dermaline", 0x215263),
	    new Reagent("dylovene", "reagent.ssc_14.dylovene", 0x3A1D8A),
	    new Reagent("inaprovaline", "reagent.ssc_14.inaprovaline", 0x731024),
	    new Reagent("cryoxadone", "reagent.ssc_14.cryoxadone", 0x0091FF),
	    new Reagent("clonexadone", "reagent.ssc_14.clonexadone", 0x0666FF),
	    new Reagent("oxycodone", "reagent.ssc_14.oxycodone", 0xC4A300),
	    new Reagent("tramadol", "reagent.ssc_14.tramadol", 0x2F6ED4),
	    new Reagent("tricordrazine", "reagent.ssc_14.tricordrazine", 0x00E5FF),
	    new Reagent("omnizine", "reagent.ssc_14.omnizine", 0xFCF7F9),
	    new Reagent("spaceacillin", "reagent.ssc_14.spaceacillin", 0x9942F5),
	    new Reagent("epinephrine", "reagent.ssc_14.epinephrine", 0xD2FFFA),
	    new Reagent("dexalin", "reagent.ssc_14.dexalin", 0x0041A8),
	    new Reagent("dexalin_plus", "reagent.ssc_14.dexalin_plus", 0x4DA0BD),
	    new Reagent("hyronalin", "reagent.ssc_14.hyronalin", 0x4CB580),
	    new Reagent("arithrazine", "reagent.ssc_14.arithrazine", 0xBD5902),
	    new Reagent("alkysine", "reagent.ssc_14.alkysine", 0xFF8C00),
	    new Reagent("alkycosine", "reagent.ssc_14.alkycosine", 0x9E232B),
	    new Reagent("imidazoline", "reagent.ssc_14.imidazoline", 0xF7EF00),
	    new Reagent("inacusiate", "reagent.ssc_14.inacusiate", 0xC4C04B),
	    new Reagent("leporazine", "reagent.ssc_14.leporazine", 0xFF7DB5),
	    new Reagent("ryetalyn", "reagent.ssc_14.ryetalyn", 0x532FD4),
	    new Reagent("phalanximine", "reagent.ssc_14.phalanximine", 0xC8FF75),
	    new Reagent("hyperzine", "reagent.ssc_14.hyperzine", 0x17BD61),
	    new Reagent("synaptizine", "reagent.ssc_14.synaptizine", 0xD49A2F),
	    new Reagent("methylin", "reagent.ssc_14.methylin", 0xA700C4),
	    new Reagent("ethylredoxrazine", "reagent.ssc_14.ethylredoxrazine", 0x2D5708),
	    new Reagent("citalopram", "reagent.ssc_14.citalopram", 0x21693C),
	    new Reagent("paroxetine", "reagent.ssc_14.paroxetine", 0xFFFBAD),
	    new Reagent("chloral_hydrate", "reagent.ssc_14.chloral_hydrate", 0x18C9B1),
	    new Reagent("diphenhydramine", "reagent.ssc_14.diphenhydramine", 0x64FFE6),
	    new Reagent("cryptobiolin", "reagent.ssc_14.cryptobiolin", 0x081A80),
	    new Reagent("lipozine", "reagent.ssc_14.lipozine", 0x2690B5),
	    new Reagent("sterilizine", "reagent.ssc_14.sterilizine", 0x7CAD37),
	    new Reagent("impedrezene", "reagent.ssc_14.impedrezene", 0x215263),
	    new Reagent("ephedrine", "reagent.ssc_14.ephedrine", 0xD2FFFA),
	    new Reagent("desoxyephedrine", "reagent.ssc_14.desoxyephedrine", 0xFAFAFA),
	    
	    // Токсины (Toxins)
	    new Reagent("toxin", "reagent.ssc_14.toxin", 0xCF3600),
	    new Reagent("plasma", "reagent.ssc_14.plasma", 0x7E009E),
	    new Reagent("unstable_mutagen", "reagent.ssc_14.unstable_mutagen", 0x00FF5F),
	    new Reagent("lexorin", "reagent.ssc_14.lexorin", 0x6B0007),
	    new Reagent("mindbreaker_toxin", "reagent.ssc_14.mindbreaker_toxin", 0x77B58E),
	    new Reagent("heartbreaker_toxin", "reagent.ssc_14.heartbreaker_toxin", 0x5F959C),
	    new Reagent("soporific", "reagent.ssc_14.soporific", 0x215263),
	    new Reagent("histamine", "reagent.ssc_14.histamine", 0xFA6464),
	    new Reagent("polytrinic_acid", "reagent.ssc_14.polytrinic_acid", 0xA1000B),
	    new Reagent("fluorosulfuric_acid", "reagent.ssc_14.fluorosulfuric_acid", 0x5050FF),
	    new Reagent("sulfuric_acid", "reagent.ssc_14.sulfuric_acid", 0xBF8C00),
	    new Reagent("space_drugs", "reagent.ssc_14.space_drugs", 0x63806E),
	    
	    // Промышленные (Industrial)
	    new Reagent("welding_fuel", "reagent.ssc_14.welding_fuel", 0xA76B1C),
	    new Reagent("thermite", "reagent.ssc_14.thermite", 0x757245),
	    new Reagent("napalm", "reagent.ssc_14.napalm", 0xFA00AF),
	    new Reagent("phlogiston", "reagent.ssc_14.phlogiston", 0xD4872A),
	    new Reagent("chlorine_trifluoride", "reagent.ssc_14.chlorine_trifluoride", 0xFFC8C8),
	    new Reagent("foaming_agent", "reagent.ssc_14.foaming_agent", 0x215263),
	    new Reagent("fluorosurfactant", "reagent.ssc_14.fluorosurfactant", 0x9E6B38),
	    new Reagent("space_cleaner", "reagent.ssc_14.space_cleaner", 0xC8FF69),
	    new Reagent("space_lube", "reagent.ssc_14.space_lube", 0x77B58E),
	    new Reagent("bleach", "reagent.ssc_14.bleach", 0xA1000B),
	    new Reagent("acetone", "reagent.ssc_14.acetone", 0xAF14B7),
	    new Reagent("phenol", "reagent.ssc_14.phenol", 0xE7EA91),
	    new Reagent("ammonia", "reagent.ssc_14.ammonia", 0x77B58E),
	    new Reagent("diethylamine", "reagent.ssc_14.diethylamine", 0xA1000B),
	    
	    // Ботаника (Botany)
	    new Reagent("ez_nutrient", "reagent.ssc_14.ez_nutrient", 0x664330),
	    new Reagent("left4_zed", "reagent.ssc_14.left4_zed", 0x5B406C),
	    new Reagent("pest_killer", "reagent.ssc_14.pest_killer", 0x9E9886),
	    new Reagent("plant_bgone", "reagent.ssc_14.plant_bgone", 0x49002E),
	    new Reagent("robust_harvest", "reagent.ssc_14.robust_harvest", 0x3E901C),
	    new Reagent("weed_killer", "reagent.ssc_14.weed_killer", 0x968395),
	    
	    // Еда (Food)
	    new Reagent("flour", "reagent.ssc_14.flour", 0xFFFFFF),
	    new Reagent("sugar", "reagent.ssc_14.sugar", 0xFFFFFF),
	    new Reagent("oats", "reagent.ssc_14.oats", 0xD2B48C),
	    new Reagent("enzyme", "reagent.ssc_14.enzyme", 0x009900),
	    new Reagent("egg", "reagent.ssc_14.egg", 0xFFFFFF),
	    new Reagent("blackpepper", "reagent.ssc_14.blackpepper", 0x000000),
	    new Reagent("vinegar", "reagent.ssc_14.vinegar", 0xD2B48C),
	    new Reagent("rice", "reagent.ssc_14.rice", 0xFFFFFF),
	    new Reagent("olive_oil", "reagent.ssc_14.olive_oil", 0x808000),
	    new Reagent("oil", "reagent.ssc_14.oil", 0xB67823),
	    new Reagent("theobromine", "reagent.ssc_14.theobromine", 0xF5F5F5),
	    
	    // Прочее (Misc)
	    new Reagent("carpetium", "reagent.ssc_14.carpetium", 0x800000),
	    new Reagent("fiber", "reagent.ssc_14.fiber", 0x808080),
	    new Reagent("buzzochloric_bees", "reagent.ssc_14.buzzochloric_bees", 0xFFD35D),
	    new Reagent("ground_bee", "reagent.ssc_14.ground_bee", 0x86530E),
	    new Reagent("saxoite", "reagent.ssc_14.saxoite", 0xB8A603),
	    new Reagent("licoxide", "reagent.ssc_14.licoxide", 0xFDD023),
	    new Reagent("thc", "reagent.ssc_14.thc", 0x808080),
	    new Reagent("thc_oil", "reagent.ssc_14.thc_oil", 0xDAA520),
	    new Reagent("nicotine", "reagent.ssc_14.nicotine", 0xC0C0C0)
	);

	public static final List<Reaction> CONFIG_REACTIONS = List.of(
	    // ===== ВЗРЫВЧАТКА И ОПАСНЫЕ РЕАКЦИИ =====
	    new Reaction(Map.of("potassium", 1, "water", 1), List.of(new ExplosionEffect(3.0f))),
	    
	    // ===== НАПИТКИ (Drinks) =====
	    new Reaction(Map.of("tea", 2, "ice", 1), List.of(new ReagentGive("ice_tea", 3))),
	    new Reaction(Map.of("lemonade", 1, "beer", 1), List.of(new ReagentGive("radler", 2))),
	    new Reaction(Map.of("ice_tea", 1, "lemonade", 1), List.of(new ReagentGive("arnold_palmer", 2))),
	    new Reaction(Map.of("coffee", 1, "milk", 1), List.of(new ReagentGive("cafe_latte", 2))),
	    new Reaction(Map.of("coffee", 1, "soy_milk", 1), List.of(new ReagentGive("soy_latte", 2))),
	    new Reaction(Map.of("coffee", 2, "ice", 1), List.of(new ReagentGive("iced_coffee", 3))),
	    new Reaction(Map.of("water", 1, "sugar", 1, "lemon_juice", 1), List.of(new ReagentGive("lemonade", 3))),
	    new Reaction(Map.of("ethanol", 1, "water", 1), List.of(new ReagentGive("vodka", 2))),
	    new Reaction(Map.of("vodka", 1, "whiskey", 1, "rum", 1, "gin", 1, "lime_juice", 1), List.of(new ReagentGive("pangalactic_gargle_blaster", 5))),
	    new Reaction(Map.of("vodka", 1, "tonic_water", 2), List.of(new ReagentGive("vodka_tonic", 3))),
	    new Reaction(Map.of("vodka", 2, "kahlua", 1), List.of(new ReagentGive("black_russian", 3))),
	    new Reaction(Map.of("whiskey", 2, "cola", 1), List.of(new ReagentGive("whiskey_cola", 3))),
	    new Reaction(Map.of("beer", 1, "whiskey_cola", 1), List.of(new ReagentGive("syndicate_bomb", 2))),
	    new Reaction(Map.of("beer", 4, "vodka", 1), List.of(new ReagentGive("hooch", 5))),
	    new Reaction(Map.of("ale", 2, "beer", 1), List.of(new ReagentGive("manly_dorf", 3))),
	    new Reaction(Map.of("rum", 1, "whiskey", 1, "lime_juice", 1), List.of(new ReagentGive("andalusia", 3))),
	    new Reaction(Map.of("rum", 4, "lime_juice", 1, "sugar", 1), List.of(new ReagentGive("daiquiri", 6))),
	    new Reaction(Map.of("orange_juice", 1, "tequila", 2), List.of(new ReagentGive("tequila_sunrise", 3))),
	    new Reaction(Map.of("lime_juice", 1, "tequila", 2), List.of(new ReagentGive("margarita", 3))),
	    new Reaction(Map.of("pineapple_juice", 3, "rum", 1, "lime_juice", 1, "coconut_cream", 1), List.of(new ReagentGive("pina_colada", 6))),
	    new Reaction(Map.of("orange_juice", 2, "champagne", 1), List.of(new ReagentGive("mimosa", 3))),
	    new Reaction(Map.of("orange_juice", 2, "vodka", 1), List.of(new ReagentGive("screwdriver", 3))),
	    new Reaction(Map.of("cola", 1, "wine", 1), List.of(new ReagentGive("kalimotxo", 2))),
	    new Reaction(Map.of("cola", 2, "rum", 1), List.of(new ReagentGive("cuba_libre", 3))),
	    new Reaction(Map.of("gin", 1, "tonic_water", 2), List.of(new ReagentGive("gin_tonic", 3))),
	    new Reaction(Map.of("gin", 2, "vermouth", 1), List.of(new ReagentGive("martini", 3))),
	    new Reaction(Map.of("vermouth", 1, "vodka", 2), List.of(new ReagentGive("vodka_martini", 3))),
	    new Reaction(Map.of("vermouth", 1, "whiskey", 2), List.of(new ReagentGive("manhattan", 3))),
	    new Reaction(Map.of("carbonated_water", 1, "rum", 1, "lime_juice", 1, "sugar", 1), List.of(new ReagentGive("mojito", 4))),
	    new Reaction(Map.of("iced_tea", 1, "sugar", 1), List.of(new ReagentGive("tortuga", 2))),
	    new Reaction(Map.of("ice", 1, "green_tea", 2), List.of(new ReagentGive("iced_green_tea", 3))),
	    new Reaction(Map.of("mead", 1, "blood", 1), List.of(new ReagentGive("red_mead", 2))),
	    
	    // ===== МЕДИЦИНА (Medicine) =====
	    new Reaction(Map.of("carbon", 1, "inaprovaline", 1), List.of(new ReagentGive("bicaridine", 2))),
	    new Reaction(Map.of("oxygen", 1, "sugar", 1, "carbon", 1), List.of(new ReagentGive("inaprovaline", 3))),
	    new Reaction(Map.of("oxygen", 1, "hydrogen", 1), List.of(new ReagentGive("hydroxide", 2))),
	    new Reaction(Map.of("sodium", 1, "hydroxide", 1), List.of(new ReagentGive("sodium_hydroxide", 2))),
	    new Reaction(Map.of("silicon", 1, "carbon", 1), List.of(new ReagentGive("kelotane", 2))),
	    new Reaction(Map.of("leporazine", 1, "dermaline", 1, "carbon", 1), List.of(new ReagentGive("pyrazine", 3))),
	    new Reaction(Map.of("silicon", 1, "iron", 1), List.of(new ReagentGive("iron_silicide", 2))),
	    new Reaction(Map.of("iron_silicide", 1, "copper", 1, "plasma", 1), List.of(new ReagentGive("leporazine", 2))),
	    new Reaction(Map.of("leporazine", 1, "silicon", 1, "kelotane", 1), List.of(new ReagentGive("insuzine", 3))),
	    new Reaction(Map.of("kelotane", 1, "leporazine", 1, "silicon", 1), List.of(new ReagentGive("insuzine", 3))),
	    new Reaction(Map.of("hydroxide", 1, "bicaridine", 1), List.of(new ReagentGive("puncturaz", 2))),
	    new Reaction(Map.of("bruizine", 1, "puncturaz", 1), List.of(new ReagentGive("britvium", 1))),
	    new Reaction(Map.of("bicaridine", 1, "puncturaz", 1), List.of(new ReagentGive("britvium", 1))),
	    new Reaction(Map.of("bruizine", 1, "lacerinol", 1), List.of(new ReagentGive("britvium", 1))),
	    new Reaction(Map.of("puncturaz", 1, "lacerinol", 1), List.of(new ReagentGive("britvium", 1))),
	    new Reaction(Map.of("bicaridine", 1, "bruizine", 1), List.of(new ReagentGive("britvium", 1))),
	    new Reaction(Map.of("lithium", 1, "bicaridine", 1, "sugar", 1), List.of(new ReagentGive("bruizine", 2))),
	    new Reaction(Map.of("lithium", 1, "zinc", 1), List.of(new ReagentGive("licoxide", 1))),
	    new Reaction(Map.of("vestin", 1, "licoxide", 1), List.of(new ReagentGive("tazinid", 1))),
	    new Reaction(Map.of("vestin", 1, "ephedrine", 1, "oxygen", 2), List.of(new ReagentGive("hyperzine", 2))),
	    new Reaction(Map.of("vestin", 2, "impedrezene", 2), List.of(new ReagentGive("nocturin", 1))),
	    new Reaction(Map.of("water", 2, "mannitol", 2, "impedrezene", 1), List.of(new ReagentGive("psychodine", 4))),
	    new Reaction(Map.of("lithium", 1, "sugar", 1, "water", 1), List.of(new ReagentGive("synaptizine", 3))),
	    new Reaction(Map.of("water", 1, "synaptizine", 1, "mindbreaker_toxin", 1), List.of(new ReagentGive("pax", 3))),
	    new Reaction(Map.of("hydrogen", 1, "sugar", 1, "water", 1), List.of(new ReagentGive("mannitol", 3))),
	    new Reaction(Map.of("hydrogen", 1, "silicon", 1, "dylovene", 1), List.of(new ReagentGive("mindbreaker_toxin", 3))),
	    new Reaction(Map.of("mindbreaker_toxin", 1, "dexalin_plus", 1), List.of(new ReagentGive("heartbreaker_toxin", 2))),
	    new Reaction(Map.of("oxygen", 2, "plasma", 1), List.of(new ReagentGive("dexalin", 3))),
	    new Reaction(Map.of("iron", 1, "dexalin", 1, "carbon", 1), List.of(new ReagentGive("dexalin_plus", 3))),
	    new Reaction(Map.of("water", 1, "dexalin", 1, "oxygen", 1), List.of(new ReagentGive("cryoxadone", 3))),
	    new Reaction(Map.of("lithium", 1, "tricordrazine", 1, "cryoxadone", 1), List.of(new ReagentGive("arcryox", 3))),
	    new Reaction(Map.of("leporazine", 2, "cryoxadone", 2, "aloe", 1), List.of(new ReagentGive("aloxadone", 4))),
	    new Reaction(Map.of("cryoxadone", 1, "unstable_mutagen", 1), List.of(new ReagentGive("doxarubicsadone", 2))),
	    new Reaction(Map.of("plasma", 2, "cognizine", 1, "doxarubicsadone", 1), List.of(new ReagentGive("opporozidone", 3))),
	    new Reaction(Map.of("omnizine", 1, "cryoxadone", 2, "blood", 3), List.of(new ReagentGive("necrozol", 2))),
	    new Reaction(Map.of("haloperidol", 1, "lipolycide", 1, "necrozol", 1), List.of(new ReagentGive("necrovir", 3))),
	    new Reaction(Map.of("mercury", 1, "ephedrine", 1, "diethylamine", 1), List.of(new ReagentGive("lipolycide", 3))),
	    new Reaction(Map.of("ethanol", 1, "ammonia", 1), List.of(new ReagentGive("diethylamine", 2))),
	    new Reaction(Map.of("iodine", 1, "phosphorus", 1, "ephedrine", 1, "carbon", 1), List.of(new ReagentGive("desoxyephedrine", 4))),
	    new Reaction(Map.of("desoxyephedrine", 1, "sterilizine", 1), List.of(new ReagentGive("ethylredoxephedrine", 2))),
	    new Reaction(Map.of("coffee", 1, "methylamine", 1, "sodium_carbonate", 1), List.of(new ReagentGive("diphenylmethylamine", 3))),
	    new Reaction(Map.of("plasma", 1, "ammonia", 1, "chloral_hydrate", 1), List.of(new ReagentGive("methylamine", 2))),
	    new Reaction(Map.of("oil", 1, "potassium_iodide", 1, "aluminium", 1, "fluorine", 1, "chlorine", 1), List.of(new ReagentGive("haloperidol", 5))),
	    new Reaction(Map.of("oil", 1, "ethanol", 1, "carbon", 1, "diethylamine", 1, "table_salt", 1), List.of(new ReagentGive("diphenhydramine", 3))),
	    new Reaction(Map.of("hydroxide", 2, "blood", 1, "table_salt", 1), List.of(new ReagentGive("oculin", 4))),
	    new Reaction(Map.of("aloe", 1, "sterilizine", 1), List.of(new ReagentGive("siderlac", 2))),
	    new Reaction(Map.of("acetone", 1, "carpotoxin", 1, "siderlac", 1), List.of(new ReagentGive("cognizine", 1))),
	    new Reaction(Map.of("histamine", 2, "plasma", 1), List.of(new ReagentGive("ultravascularin", 2))),
	    new Reaction(Map.of("nitrogen", 1, "sodium", 1, "sulfuric_acid", 1), List.of(new ReagentGive("heparin", 2))),
	    new Reaction(Map.of("hydrogen", 1, "hyronalin", 1), List.of(new ReagentGive("arithrazine", 2))),
	    new Reaction(Map.of("ammonia", 1, "nitrogen", 1, "potassium", 1), List.of(new ReagentGive("ipecac", 2))),
	    new Reaction(Map.of("ammonia", 1, "dylovene", 1, "zombie_blood", 2), List.of(new ReagentGive("ambuzol", 4))),
	    new Reaction(Map.of("ambuzol", 5, "omnizine", 5), List.of(new ReagentGive("ambuzol_plus", 10))),
	    new Reaction(Map.of("ethanol", 1, "radium", 1, "table_salt", 1), List.of(new ReagentGive("lipozine", 3))),
	    new Reaction(Map.of("radium", 1, "ethanol", 1, "table_salt", 1), List.of(new ReagentGive("lipozine", 3))),
	    new Reaction(Map.of("dylovene", 1, "radium", 1), List.of(new ReagentGive("hyronalin", 2))),
	    new Reaction(Map.of("unstable_mutagen", 1, "hyronalin", 1, "ethanol", 1), List.of(new ReagentGive("phalanximine", 3))),
	    new Reaction(Map.of("chlorine", 1, "phosphorus", 1, "radium", 1), List.of(new ReagentGive("unstable_mutagen", 3))),
	    new Reaction(Map.of("sulfuric_acid", 1, "extractor", 1), List.of(new ReagentGive("stable_necroinfection_mutagen", 2))),
	    new Reaction(Map.of("unstable_mutagen", 1, "extractor", 1), List.of(new ReagentGive("unstable_necroinfection_mutagen", 2))),
	    new Reaction(Map.of("epinephrine", 1, "uranium", 1), List.of(new ReagentGive("norepinephrine_acid", 2))),
	    new Reaction(Map.of("acetone", 1, "chlorine", 1, "hydroxide", 1, "phenol", 1), List.of(new ReagentGive("epinephrine", 4))),
	    new Reaction(Map.of("benzene", 1, "hydroxide", 1), List.of(new ReagentGive("phenol", 2))),
	    new Reaction(Map.of("oil", 1, "oxygen", 1, "welding_fuel", 1), List.of(new ReagentGive("acetone", 2))),
	    new Reaction(Map.of("hydrogen", 1, "carbon", 1, "welding_fuel", 1), List.of(new ReagentGive("oil", 3))),
	    new Reaction(Map.of("hydrogen", 1, "sulfur", 1, "oxygen", 2), List.of(new ReagentGive("sulfuric_acid", 3))),
	    new Reaction(Map.of("hydrogen", 1, "potassium", 1, "fluorine", 1, "sulfuric_acid", 1), List.of(new ReagentGive("fluorosulfuric_acid", 4))),
	    new Reaction(Map.of("sulfuric_acid", 1, "fluorine", 2, "carbon", 2), List.of(new ReagentGive("fluorosurfactant", 5))),
	    new Reaction(Map.of("sulfuric_acid", 1, "potassium", 1, "plasma", 1), List.of(new ReagentGive("polytrinic_acid", 3))),
	    new Reaction(Map.of("sulfuric_acid", 1, "sugar", 1, "inaprovaline", 1), List.of(new ReagentGive("tranexamic_acid", 3))),
	    new Reaction(Map.of("soy_milk", 2, "sulfuric_acid", 1), List.of(new ReagentGive("soy_sauce", 3))),
	    new Reaction(Map.of("wine", 2, "sulfuric_acid", 1), List.of(new ReagentGive("acidspit", 3))),
	    new Reaction(Map.of("toxin", 1, "water", 4), List.of(new ReagentGive("plant_bgone", 5))),
	    new Reaction(Map.of("fluorine", 3, "chlorine", 1), List.of(new ReagentGive("chlorine_trifluoride", 4))),
	    new Reaction(Map.of("yellow_cake", 3, "fluorine", 1), List.of(new ReagentGive("uranium_hexafluoride", 3))),
	    new Reaction(Map.of("raw_uranium_powder", 2, "sulfuric_acid", 1), List.of(new ReagentGive("yellow_cake", 2))),
	    new Reaction(Map.of("aluminium", 1, "oxygen", 1, "iron", 1), List.of(new ReagentGive("thermite", 3))),
	    new Reaction(Map.of("aluminium", 1, "uranium", 1, "iron", 1), List.of(new ParticleEffect())), // Электромагнитный импульс
	    new Reaction(Map.of("aluminium", 1, "potassium", 1, "sulfur", 1), List.of(new ParticleEffect())), // Ослепительная вспышка
	    new Reaction(Map.of("silver", 10, "frost_oil", 5), List.of(new ReagentGive("silver_ingot", 1))),
	    new Reaction(Map.of("gold", 10, "frost_oil", 5), List.of(new ReagentGive("gold_ingot", 1))),
	    new Reaction(Map.of("potassium", 6, "sulfur", 2, "coal", 2), List.of(new ReagentGive("black_powder", 10))),
	    
	    // ===== БОТАНИКА (Botany) =====
	    new Reaction(Map.of("potassium", 1, "phosphorus", 1, "nitrogen", 1), List.of(new ReagentGive("ez_nutrient", 3))),
	    new Reaction(Map.of("ez_nutrient", 1, "sulfuric_acid", 1), List.of(new ReagentGive("robust_harvest", 1))),
	    new Reaction(Map.of("ez_nutrient", 1, "radium", 1), List.of(new ReagentGive("left4_zed", 1))),
	    new Reaction(Map.of("robust_harvest", 3, "cryoxadone", 1, "diethylamine", 3), List.of(new ReagentGive("semin", 1))),
	    
	    // ===== ПРОЧЕЕ =====
	    new Reaction(Map.of("water", 1), List.of(new ReagentGive("holy_water", 1))), // Освящение воды
	    new Reaction(Map.of("blood", 1), List.of(new ReagentGive("wine", 1))) // Освящение крови -> вино
	);

    private static final Map<String, Reagent> REAGENTS = new LinkedHashMap<>();
    private static final List<Reaction> REACTIONS = new ArrayList<>();

    static {
        for (Reagent r : CONFIG_REAGENTS) {
            registerReagent(r);
            if (!r.getId().equals("water")) {
                FLUID_TYPES.register(r.getId(), () -> new FluidType(FluidType.Properties.create()) {
                    // Убран @Override, так как в 1.21.8 сигнатура/модификаторы могли измениться
                    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                        consumer.accept(new IClientFluidTypeExtensions() {
                            private final ResourceLocation still = ResourceLocation.parse("ssc_14:item/container/" + r.getId() + "_still");
                            private final ResourceLocation flow = ResourceLocation.parse("ssc_14:item/container/" + r.getId() + "_flow");

                            @Override
                            public ResourceLocation getStillTexture() { return still; }
                            @Override
                            public ResourceLocation getFlowingTexture() { return flow; }
                            @Override
                            public int getTintColor() { return 0xFF000000 | r.colorRgb; }
                        });
                    }
                });

                FLUIDS.register(r.getId(), () -> new BaseFlowingFluid.Source(
                        new BaseFlowingFluid.Properties(
                                () -> FLUID_TYPES.getEntries().stream()
                                        .filter(reg -> reg.getId().getPath().equals(r.getId()))
                                        .findFirst().orElseThrow().get(),
                                null, null
                        )
                ));
            }
        }
        for (Reaction r : CONFIG_REACTIONS) registerReaction(r);
    }

    public static void register(IEventBus bus) {
        FLUID_TYPES.register(bus);
        FLUIDS.register(bus);
    }

    public static Reagent registerReagent(Reagent reagent) { REAGENTS.put(reagent.getId(), reagent); return reagent; }
    public static Optional<Reagent> getReagent(String id) { return Optional.ofNullable(REAGENTS.get(id)); }
    public static Collection<Reagent> getAllReagents() { return Collections.unmodifiableCollection(REAGENTS.values()); }
    public static void registerReaction(Reaction reaction) { REACTIONS.add(reaction); }
    public static List<Reaction> getReactions() { return Collections.unmodifiableList(REACTIONS); }

    public static class Reagent {
        private final String id; private final String translationKey; private final int colorRgb;
        public Reagent(String id, String translationKey, int colorRgb) { this.id = id; this.translationKey = translationKey; this.colorRgb = colorRgb; }
        public String getId() { return id; }
        public Component getDisplayName() { return Component.translatable(translationKey); }
        public int getColorRgb() { return colorRgb; }
    }

    public interface IReactionEffect { void apply(ItemStack stack, ServerPlayer player); }

    public static class ReagentGive implements IReactionEffect {
        private final String reagentId; private final int amount;
        public ReagentGive(String reagentId, int amount) { this.reagentId = reagentId; this.amount = amount; }
        @Override public void apply(ItemStack stack, ServerPlayer player) { addReagent(stack, reagentId, amount); }
    }

    public static class ExplosionEffect implements IReactionEffect {
        private final float power;
        public ExplosionEffect(float power) { this.power = power; }
        @Override public void apply(ItemStack stack, ServerPlayer player) {
            if (!player.level().isClientSide) {
                player.level().explode(null, null, null, player.getX(), player.getY() + 0.5, player.getZ(), this.power, false, Level.ExplosionInteraction.MOB);
            }
        }
    }

    public static class SpawnItemEffect implements IReactionEffect {
        private final String itemId; private final int count;
        public SpawnItemEffect(String itemId, int count) { this.itemId = itemId; this.count = count; }
        @Override public void apply(ItemStack stack, ServerPlayer player) {
            if (!player.level().isClientSide) {
                ResourceLocation id = ResourceLocation.tryParse(itemId);
                if (id != null) {
                    var itemHolder = player.level().registryAccess().lookupOrThrow(Registries.ITEM).get(id);
                    if (itemHolder.isPresent()) {
                        Item item = itemHolder.get().value();
                        ItemStack dropStack = new ItemStack(item, count);
                        player.level().addFreshEntity(new ItemEntity(player.level(), player.getX(), player.getY() + 1.0, player.getZ(), dropStack));
                    }
                }
            }
        }
    }

    public static class ParticleEffect implements IReactionEffect {
        @Override public void apply(ItemStack stack, ServerPlayer player) {
            if (!player.level().isClientSide) {
                ServerLevel sl = (ServerLevel) player.level();
                for (int i = 0; i < 20; i++) {
                    sl.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, player.getX() + (player.getRandom().nextDouble() - 0.5) * 2.0, player.getY() + player.getRandom().nextDouble() * 1.5, player.getZ() + (player.getRandom().nextDouble() - 0.5) * 2.0, 1, 0, 0, 0, 0.05);
                }
            }
        }
    }

    public static class Reaction {
        private final Map<String, Integer> inputs; private final List<IReactionEffect> effects;
        public Reaction(Map<String, Integer> inputs, List<IReactionEffect> effects) { this.inputs = Collections.unmodifiableMap(new LinkedHashMap<>(inputs)); this.effects = List.copyOf(effects); }
        public Map<String, Integer> getInputs() { return inputs; }
        public List<IReactionEffect> getEffects() { return effects; }
    }

    private static final String NBT_KEY_REAGENTS = "ssc_14_reagents";

    public static Map<String, Integer> getReagents(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(net.minecraft.core.component.DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.EMPTY).copyTag();
        if (!tag.contains(NBT_KEY_REAGENTS)) return Collections.emptyMap();
        CompoundTag r = tag.getCompound(NBT_KEY_REAGENTS).orElse(new CompoundTag());
        Map<String, Integer> map = new LinkedHashMap<>();
        for (String key : r.keySet()) { map.put(key, r.getInt(key).orElse(0)); }
        return Collections.unmodifiableMap(map);
    }

    public static int getAvailableSpace(ItemStack stack) {
        if (stack.getItem() instanceof ReagentContainerItem container) {
            int currentVol = getReagents(stack).values().stream().mapToInt(Integer::intValue).sum();
            return container.getMaxCapacity() - currentVol;
        }
        return Integer.MAX_VALUE;
    }

    public static boolean addReagent(ItemStack stack, String id, int amount) {
        if (amount <= 0 || getAvailableSpace(stack) < amount) return false;
        CompoundTag root = stack.getOrDefault(net.minecraft.core.component.DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.EMPTY).copyTag();
        CompoundTag r = root.getCompound(NBT_KEY_REAGENTS).orElse(new CompoundTag());
        r.putInt(id, r.getInt(id).orElse(0) + amount);
        root.put(NBT_KEY_REAGENTS, r);
        stack.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.of(root));
        return true;
    }

    public static boolean removeReagent(ItemStack stack, String id, int amount) {
        CompoundTag root = stack.getOrDefault(net.minecraft.core.component.DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.EMPTY).copyTag();
        if (!root.contains(NBT_KEY_REAGENTS)) return false;
        CompoundTag r = root.getCompound(NBT_KEY_REAGENTS).orElse(new CompoundTag());
        int prev = r.getInt(id).orElse(0);
        if (prev < amount) return false;
        if (prev == amount) r.remove(id); else r.putInt(id, prev - amount);
        root.put(NBT_KEY_REAGENTS, r);
        stack.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.of(root));
        return true;
    }

    public static void processReactions(ItemStack stack, ServerPlayer player) {
        int iter = 0; boolean changed; boolean reactionHappened = false;
        do {
            changed = false;
            for (Reaction r : REACTIONS) {
                while (canApplyReaction(stack, r)) {
                    for (Map.Entry<String, Integer> need : r.getInputs().entrySet()) { removeReagent(stack, need.getKey(), need.getValue()); }
                    for (IReactionEffect effect : r.getEffects()) { effect.apply(stack, player); }
                    changed = true; reactionHappened = true;
                    if (++iter > 1024) break;
                }
                if (iter > 1024) break;
            }
        } while (changed && iter <= 1024);
    }

    public static boolean canApplyReaction(ItemStack stack, Reaction reaction) {
        Map<String, Integer> have = getReagents(stack);
        for (Map.Entry<String, Integer> need : reaction.getInputs().entrySet()) { if (have.getOrDefault(need.getKey(), 0) < need.getValue()) return false; }
        return true;
    }

    public static class AlcoholSystem {
        public static int getAlcoholValue(String reagentId) {
            if (reagentId.equals("vodka")) return 5; if (reagentId.equals("wine")) return 3;
            if (reagentId.equals("beer")) return 1; if (reagentId.equals("whiskey")) return 6;
            return 0;
        }
        public static void giveAlcohol(ServerPlayer player, int amount) { /* Логика опьянения */ }
    }

    public static int drinkReagents(ItemStack stack, int maxAmount, ServerPlayer player) {
        Map<String, Integer> reagents = getReagents(stack);
        if (reagents.isEmpty()) return 0;
        int totalVolume = reagents.values().stream().mapToInt(Integer::intValue).sum();
        int drinkAmount = Math.min(maxAmount, totalVolume); int alcohol = 0;
        for (Map.Entry<String, Integer> entry : reagents.entrySet()) {
            int amount = (int) Math.ceil((float) entry.getValue() * drinkAmount / totalVolume);
            if (amount > 0) { alcohol += AlcoholSystem.getAlcoholValue(entry.getKey()) * amount; removeReagent(stack, entry.getKey(), amount); }
        }
        if (alcohol > 0) AlcoholSystem.giveAlcohol(player, alcohol);
        return drinkAmount;
    }

    public static class ReagentFluidHandler implements IFluidHandlerItem {
        protected final ItemStack container; protected final int capacity;
        public ReagentFluidHandler(ItemStack container, int capacity) { this.container = container; this.capacity = capacity; }
        @Override public int getTanks() { return 1; }
        @Override @NotNull public FluidStack getFluidInTank(int tank) {
            Map<String, Integer> map = getReagents(container);
            if (map.isEmpty()) return FluidStack.EMPTY;
            String id = map.keySet().iterator().next(); return createStack(id, map.get(id));
        }
        @Override public int getTankCapacity(int tank) { return capacity; }
        @Override public boolean isFluidValid(int tank, @NotNull FluidStack stack) { return getReagent(getFluidId(stack)).isPresent(); }
        @Override public int fill(FluidStack resource, FluidAction action) {
            if (resource.isEmpty()) return 0;
            String id = getFluidId(resource);
            if (getReagent(id).isEmpty()) return 0;
            int canAdd = Math.min(resource.getAmount(), getAvailableSpace(container));
            if (canAdd > 0 && action.execute()) addReagent(container, id, canAdd);
            return canAdd;
        }
        @Override @NotNull public FluidStack drain(FluidStack resource, FluidAction action) { if (resource.isEmpty()) return FluidStack.EMPTY; return drain(resource.getAmount(), action); }
        @Override @NotNull public FluidStack drain(int maxDrain, FluidAction action) {
            Map<String, Integer> map = getReagents(container);
            if (map.isEmpty()) return FluidStack.EMPTY;
            String id = map.keySet().iterator().next();
            int toDrain = Math.min(map.get(id), maxDrain);
            if (toDrain > 0 && action.execute()) removeReagent(container, id, toDrain);
            return createStack(id, toDrain);
        }
        private FluidStack createStack(String id, int amount) {
            if (id.equals("water")) return new FluidStack(Fluids.WATER, amount);
            
            // В 1.21.2+ Registry.get возвращает Optional<Holder<T>> или Optional<Reference<T>>.
            // Используем .map(h -> h.value()), чтобы достать саму жидкость, независимо от того, Holder это или Reference.
            Fluid fluid = BuiltInRegistries.FLUID.get(ResourceLocation.parse("ssc_14:" + id))
                    .map(h -> h.value())
                    .orElse(Fluids.EMPTY);
                    
            if (fluid == Fluids.EMPTY) return FluidStack.EMPTY;
            return new FluidStack(fluid, amount);
        }
        private String getFluidId(FluidStack stack) {
            ResourceLocation rl = BuiltInRegistries.FLUID.getKey(stack.getFluid());
            return rl != null ? rl.getPath() : "";
        }
        @Override @NotNull public ItemStack getContainer() { return container; }
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        Map<String, Integer> map = getReagents(event.getItemStack());
        if (!map.isEmpty()) {
            event.getToolTip().add(Component.translatable("reagent.ssc_14.reagents").withStyle(ChatFormatting.GRAY));
            map.forEach((k, v) -> getReagent(k).ifPresent(r -> event.getToolTip().add(Component.literal(" - ").append(r.getDisplayName()).append(" x " + v).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(r.getColorRgb()))))));
        }
    }

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
	    // Получаем все предметы-контейнеры из реестра
	    List<net.minecraft.world.item.Item> containerItems = new ArrayList<>();
	    
	    net.minecraft.core.registries.BuiltInRegistries.ITEM.forEach(item -> {
	        if (item instanceof ReagentContainerItem) {
	            containerItems.add(item);
	        }
	    });
	    
	    // Регистрируем capability только если нашли хотя бы один контейнер
	    if (!containerItems.isEmpty()) {
	        event.registerItem(
	            Capabilities.FluidHandler.ITEM,
	            (stack, context) -> {
	                if (stack.getItem() instanceof ReagentContainerItem item) {
	                    return new ReagentFluidHandler(stack, item.getMaxCapacity());
	                }
	                return null;
	            },
	            containerItems.toArray(new net.minecraft.world.item.Item[0])
	        );
	    }
	}
}
