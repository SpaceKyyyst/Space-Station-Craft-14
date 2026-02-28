package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class Digestion20PrProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS) ? _livingEntity0.getAttribute(Ssc14ModAttributes.NUTRIENTS).getValue() : 0) > 150) {
			Ssc14Mod.queueServerWork(320, () -> {
				if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS))
					_livingEntity2.getAttribute(Ssc14ModAttributes.NUTRIENTS)
							.setBaseValue((5 + (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS) ? _livingEntity1.getAttribute(Ssc14ModAttributes.NUTRIENTS).getValue() : 0)));
				if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
					_livingEntity4.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).setBaseValue(Math.round(
							(entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES) ? _livingEntity3.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue() : 0)
									- 1));
			});
		} else if ((entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS) ? _livingEntity6.getAttribute(Ssc14ModAttributes.NUTRIENTS).getValue() : 0) <= 150
				&& (entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS) ? _livingEntity7.getAttribute(Ssc14ModAttributes.NUTRIENTS).getValue() : 0) > 100) {
			Ssc14Mod.queueServerWork(400, () -> {
				if (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS))
					_livingEntity9.getAttribute(Ssc14ModAttributes.NUTRIENTS)
							.setBaseValue((5 + (entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS) ? _livingEntity8.getAttribute(Ssc14ModAttributes.NUTRIENTS).getValue() : 0)));
				if (entity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
					_livingEntity11.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).setBaseValue(Math.round(
							(entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES) ? _livingEntity10.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue() : 0)
									- 1));
			});
		} else if ((entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS) ? _livingEntity13.getAttribute(Ssc14ModAttributes.NUTRIENTS).getValue() : 0) <= 100
				&& (entity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS) ? _livingEntity14.getAttribute(Ssc14ModAttributes.NUTRIENTS).getValue() : 0) > 50) {
			Ssc14Mod.queueServerWork(480, () -> {
				if (entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS))
					_livingEntity16.getAttribute(Ssc14ModAttributes.NUTRIENTS).setBaseValue(
							(5 + (entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS) ? _livingEntity15.getAttribute(Ssc14ModAttributes.NUTRIENTS).getValue() : 0)));
				if (entity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
					_livingEntity18.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).setBaseValue(Math.round(
							(entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES) ? _livingEntity17.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue() : 0)
									- 1));
			});
		} else if ((entity instanceof LivingEntity _livingEntity20 && _livingEntity20.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS) ? _livingEntity20.getAttribute(Ssc14ModAttributes.NUTRIENTS).getValue() : 0) <= 50) {
			Ssc14Mod.queueServerWork(560, () -> {
				if (entity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS))
					_livingEntity22.getAttribute(Ssc14ModAttributes.NUTRIENTS).setBaseValue(
							(5 + (entity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS) ? _livingEntity21.getAttribute(Ssc14ModAttributes.NUTRIENTS).getValue() : 0)));
				if (entity instanceof LivingEntity _livingEntity24 && _livingEntity24.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
					_livingEntity24.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).setBaseValue(Math.round(
							(entity instanceof LivingEntity _livingEntity23 && _livingEntity23.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES) ? _livingEntity23.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).getValue() : 0)
									- 1));
			});
		}
	}
}