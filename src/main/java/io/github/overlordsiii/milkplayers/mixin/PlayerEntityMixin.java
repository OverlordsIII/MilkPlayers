package io.github.overlordsiii.milkplayers.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

	@Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "interact", at = @At("HEAD"))
	private void onInteract(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		if (!this.world.isClient && entity instanceof PlayerEntity) {
			ItemStack stackInHand = this.getStackInHand(hand);
			if (stackInHand.isOf(Items.BUCKET)) {
				this.playSound(SoundEvents.ENTITY_COW_MILK, 1.0f, 1.0f);
				ItemStack itemStack2 = ItemUsage.exchangeStack(stackInHand, (PlayerEntity) (Object) this, Items.MILK_BUCKET.getDefaultStack());
				this.setStackInHand(hand, itemStack2);
			}
		}
	}
}
