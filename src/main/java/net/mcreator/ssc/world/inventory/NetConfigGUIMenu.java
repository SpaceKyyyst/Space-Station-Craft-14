
package net.mcreator.ssc.world.inventory;

import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.block.state.BlockState;
import net.mcreator.ssc.procedures.NetConfigGUI_TIC_Procedure;
import net.mcreator.ssc.procedures.NetConfigGUI_CLOSE_Procedure;
import net.mcreator.ssc.init.Ssc14ModMenus;
import net.mcreator.ssc.INetworkTrigger;
import net.mcreator.ssc.INetworkAction;
import net.mcreator.ssc.NetworkSavedData;
import net.mcreator.ssc.NetConfigGUISyncMessage;
import net.mcreator.ssc.init.Ssc14ModItems;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;

@EventBusSubscriber
public class NetConfigGUIMenu extends AbstractContainerMenu implements Ssc14ModMenus.MenuAccessor {
    public final Map<String, Object> menuState = new HashMap<>() {
        @Override
        public Object put(String key, Object value) {
            if (!this.containsKey(key) && this.size() >= 256)
                return null;
            return super.put(key, value);
        }
    };
    public final Level world;
    public final Player entity;
    public int x, y, z;
    public BlockPos sourcePos;
    public BlockPos targetPos;
    private ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private IItemHandler internal;
    private final Map<Integer, Slot> customSlots = new HashMap<>();
    private boolean bound = false;
    private Supplier<Boolean> boundItemMatcher = null;
    private Entity boundEntity = null;
    private BlockEntity boundBlockEntity = null;
    
    // Флаг для отправки синхронизации только один раз
    private boolean syncSent = false;

    public NetConfigGUIMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(Ssc14ModMenus.NET_CONFIG_GUI.get(), id);
        this.entity = inv.player;
        this.world = inv.player.level();
        this.internal = new ItemStackHandler(0);

        System.out.println("[SSC14-DEBUG] GUI открыт!");

        if (extraData != null) {
            this.targetPos = extraData.readBlockPos();

            this.x = targetPos.getX();
            this.y = targetPos.getY();
            this.z = targetPos.getZ();
            access = ContainerLevelAccess.create(world, targetPos);

            System.out.println("[SSC14-DEBUG] targetPos = " + targetPos);

            ItemStack mainHand = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
            if (Ssc14ModItems.NETWORK_CONFIGURATOR.get() == mainHand.getItem()) {
                double fx = mainHand.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("First_Target_X").orElse(0.0);
                double fy = mainHand.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("First_Target_Y").orElse(0.0);
                double fz = mainHand.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("First_Target_Z").orElse(0.0);
                this.sourcePos = BlockPos.containing(fx, fy, fz);
                System.out.println("[SSC14-DEBUG] Конфигуратор в руке! sourcePos = " + sourcePos);
            } else {
                this.sourcePos = BlockPos.ZERO;
                System.out.println("[SSC14-DEBUG] Конфигуратор НЕ в руке! sourcePos = ZERO");
            }

            populateGuiData();
            
            System.out.println("[SSC14-DEBUG] menuState размер: " + menuState.size());
            System.out.println("[SSC14-DEBUG] menuState содержимое: " + menuState);
        }
    }

    private void populateGuiData() {
        menuState.put("selected_trigger_idx", -1);

        BlockState sourceState = world.getBlockState(sourcePos);
        System.out.println("[SSC14-DEBUG] sourceState блок: " + sourceState.getBlock().getClass().getSimpleName());
        
        if (sourceState.getBlock() instanceof INetworkTrigger triggerBlock) {
            System.out.println("[SSC14-DEBUG] Блок реализует INetworkTrigger!");
            List<String> triggers = triggerBlock.getAvailableTriggers();
            System.out.println("[SSC14-DEBUG] Найдено триггеров: " + triggers.size());
            for (int i = 0; i < Math.min(triggers.size(), 8); i++) {
                String id = triggers.get(i);
                String text = triggerBlock.getTriggerName(id);
                System.out.println("[SSC14-DEBUG] Триггер " + (i+1) + ": id=" + id + ", text=" + text);
                menuState.put("L" + (i + 1) + "_id", id);
                menuState.put("L" + (i + 1) + "_text", text);
                menuState.put("L" + (i + 1) + "_active", true);
            }
        } else {
            System.out.println("[SSC14-DEBUG] ОШИБКА: Блок НЕ реализует INetworkTrigger!");
        }

        BlockState targetState = world.getBlockState(targetPos);
        System.out.println("[SSC14-DEBUG] targetState блок: " + targetState.getBlock().getClass().getSimpleName());
        
        if (targetState.getBlock() instanceof INetworkAction actionBlock) {
            System.out.println("[SSC14-DEBUG] Блок реализует INetworkAction!");
            List<String> actions = actionBlock.getAvailableActions();
            System.out.println("[SSC14-DEBUG] Найдено действий: " + actions.size());
            for (int i = 0; i < Math.min(actions.size(), 8); i++) {
                String id = actions.get(i);
                String text = actionBlock.getActionName(id);
                System.out.println("[SSC14-DEBUG] Действие " + (i+1) + ": id=" + id + ", text=" + text);
                menuState.put("R" + (i + 1) + "_id", id);
                menuState.put("R" + (i + 1) + "_text", text);
                menuState.put("R" + (i + 1) + "_active", true);
            }
        } else {
            System.out.println("[SSC14-DEBUG] ОШИБКА: Блок НЕ реализует INetworkAction!");
        }
        
        loadConnections();
    }

    private void loadConnections() {
        NetworkSavedData data = NetworkSavedData.get(world);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String triggerId = (String) menuState.get("L" + (i + 1) + "_id");
                String actionId = (String) menuState.get("R" + (j + 1) + "_id");
                if (triggerId != null && actionId != null) {
                    boolean hasConnection = data.connections.stream()
                        .anyMatch(c -> c.sourcePos.equals(sourcePos) && c.triggerId.equals(triggerId) 
                                    && c.targetPos.equals(targetPos) && c.actionId.equals(actionId));
                    menuState.put("conn_" + i + "_" + j, hasConnection);
                }
            }
        }
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.bound) {
            if (this.boundItemMatcher != null) return this.boundItemMatcher.get();
            else if (this.boundBlockEntity != null) return AbstractContainerMenu.stillValid(this.access, player, this.boundBlockEntity.getBlockState().getBlock());
            else if (this.boundEntity != null) return this.boundEntity.isAlive();
        }
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);
        NetConfigGUI_CLOSE_Procedure.execute(entity);
    }

    @Override
    public Map<Integer, Slot> getSlots() {
        return Collections.unmodifiableMap(customSlots);
    }

    @Override
    public Map<String, Object> getMenuState() {
        return menuState;
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player entity = event.getEntity();
        if (entity.containerMenu instanceof NetConfigGUIMenu menu) {
            // Отправляем синхронизацию только один раз
            if (!menu.syncSent && entity instanceof ServerPlayer serverPlayer) {
                PacketDistributor.sendToPlayer(serverPlayer, new NetConfigGUISyncMessage(menu.menuState));
                menu.syncSent = true;
                System.out.println("[SSC14-DEBUG] Отправлен пакет синхронизации из onPlayerTick!");
            }
            
            NetConfigGUI_TIC_Procedure.execute(menu.world, entity);
        }
    }
}
