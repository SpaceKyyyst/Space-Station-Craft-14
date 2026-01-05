/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import org.lwjgl.glfw.GLFW;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import net.mcreator.ssc.network.RotateMessage;
import net.mcreator.ssc.network.PulltheObjectMessage;
import net.mcreator.ssc.network.DCMopenMessage;

@EventBusSubscriber(Dist.CLIENT)
public class Ssc14ModKeyMappings {
	public static final KeyMapping DC_MOPEN = new KeyMapping("key.ssc_14.dc_mopen", GLFW.GLFW_KEY_T, "key.categories.ssc14") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				ClientPacketDistributor.sendToServer(new DCMopenMessage(0, 0));
				DCMopenMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping ROTATE = new KeyMapping("key.ssc_14.rotate", GLFW.GLFW_KEY_R, "key.categories.gameplay") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				ClientPacketDistributor.sendToServer(new RotateMessage(0, 0));
				RotateMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping PULLTHE_OBJECT = new KeyMapping("key.ssc_14.pullthe_object", GLFW.GLFW_KEY_F, "key.categories.gameplay") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				ClientPacketDistributor.sendToServer(new PulltheObjectMessage(0, 0));
				PulltheObjectMessage.pressAction(Minecraft.getInstance().player, 0, 0);
				PULLTHE_OBJECT_LASTPRESS = System.currentTimeMillis();
			} else if (isDownOld != isDown && !isDown) {
				int dt = (int) (System.currentTimeMillis() - PULLTHE_OBJECT_LASTPRESS);
				ClientPacketDistributor.sendToServer(new PulltheObjectMessage(1, dt));
				PulltheObjectMessage.pressAction(Minecraft.getInstance().player, 1, dt);
			}
			isDownOld = isDown;
		}
	};
	private static long PULLTHE_OBJECT_LASTPRESS = 0;

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(DC_MOPEN);
		event.register(ROTATE);
		event.register(PULLTHE_OBJECT);
	}

	@EventBusSubscriber(Dist.CLIENT)
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(ClientTickEvent.Post event) {
			if (Minecraft.getInstance().screen == null) {
				DC_MOPEN.consumeClick();
				ROTATE.consumeClick();
				PULLTHE_OBJECT.consumeClick();
			}
		}
	}
}