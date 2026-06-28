
package net.mcreator.ssc.client.screens;

import net.mcreator.ssc.block.SheathingBlock;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.MultitoolClientCache;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class MultitulUIOverlay {

	@SubscribeEvent
	public static void eventHandler(RenderGuiEvent.Pre event) {
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		Level world = mc.level;
		if (player == null || world == null) return;

		ItemStack mainHand = player.getMainHandItem();
		if (mainHand.isEmpty() || mainHand.getItem() != Ssc14ModItems.MULTITUL.get()) return;

		CustomData customData = mainHand.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		int toolMode = 1;
		if (!customData.isEmpty() && customData.getUnsafe().contains("Mode")) {
			var modeOpt = customData.getUnsafe().getDouble("Mode");
			if (modeOpt.isPresent()) {
				toolMode = modeOpt.get().intValue();
			}
		}
		if (toolMode < 1 || toolMode > 3) toolMode = 1;

		String modeLabel = switch (toolMode) {
			case 1 -> "НВ (Низкое)";
			case 2 -> "СВ (Среднее)";
			case 3 -> "ВВ (Высокое)";
			default -> "НВ (Низкое)";
		};

		HitResult hit = player.pick(5.0D, 0.0F, false);
		if (hit.getType() == HitResult.Type.BLOCK && hit instanceof BlockHitResult blockHit) {
			BlockPos pos = blockHit.getBlockPos();
			BlockState state = world.getBlockState(pos);

			if (state.getBlock() instanceof SheathingBlock) {
				int w = event.getGuiGraphics().guiWidth();
				int h = event.getGuiGraphics().guiHeight();
				var font = mc.font;

				String targetPropName = switch (toolMode) {
					case 1 -> "lv";
					case 2 -> "mv";
					case 3 -> "hv";
					default -> "lv";
				};
				
				boolean hasCable = false;
				for (Property<?> property : state.getProperties()) {
					if (property.getName().equals(targetPropName) && property instanceof BooleanProperty bp) {
						hasCable = state.getValue(bp);
						break;
					}
				}

				event.getGuiGraphics().drawString(font, "Текущий режим цепи: " + modeLabel, w / 2 + 25, h / 2 - 64, 0xFFFFFF00, false);

				if (!hasCable) {
					event.getGuiGraphics().drawString(font, "Кабель этого вольтажа отсутствует", w / 2 + 25, h / 2 - 48, 0xFFFF5555, false);
					return;
				}

				// Читаем чистые данные из пакетного клиентского кэша
				long currentPower = MultitoolClientCache.currentPower;
				long theoreticalSupply = MultitoolClientCache.theoreticalSupply;
				long idealConsumption = MultitoolClientCache.idealConsumption;
				
				long stored = (toolMode == 3) ? MultitoolClientCache.outputStored : MultitoolClientCache.inputStored;
				long max = (toolMode == 3) ? MultitoolClientCache.outputMax : MultitoolClientCache.inputMax;
				long batteryPower = MultitoolClientCache.batteryPower;
				
				if (currentPower == 0 && theoreticalSupply == 0 && idealConsumption == 0) {
					event.getGuiGraphics().drawString(font, "Кабель не подключен к узлу (0 W)", w / 2 + 25, h / 2 - 48, 0xFFFF5555, false);
					return;
				}

				String percentage = max == 0 ? "0%" : String.format("%.1f%%", ((double) stored / max) * 100);

				event.getGuiGraphics().drawString(font, "Текущее питание: " + currentPower + " W", w / 2 + 25, h / 2 - 48, -1, false);
				event.getGuiGraphics().drawString(font, "От батарей: " + (toolMode == 2 ? batteryPower : 0) + " W", w / 2 + 25, h / 2 - 32, -1, false);
				event.getGuiGraphics().drawString(font, "Теоретическое снабжение: " + theoreticalSupply + " W", w / 2 + 25, h / 2 - 16, -1, false);
				event.getGuiGraphics().drawString(font, "Идеальное потребление: " + idealConsumption + " W", w / 2 + 25, h / 2, -1, false);
				event.getGuiGraphics().drawString(font, "Запас буфера цепи: " + stored + " / " + max + " J (" + percentage + ")", w / 2 + 25, h / 2 + 16, -1, false);
			}
		}
	}
}
