package com.yuusyaasisutanto.bringme115.content.screen.primitivemachine;

import com.yuusyaasisutanto.bringme115.content.BM115BlockRegister;
import com.yuusyaasisutanto.bringme115.content.BM115ScreenRegister;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BM115PrimitivePaPMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final Container crystalSlot = new SimpleContainer(1);
    private final Container gunSlot = new SimpleContainer(1);


    // ClientSide
    public BM115PrimitivePaPMenu(int a, Inventory playerInv){
        this(a, playerInv, ContainerLevelAccess.NULL);
    }

    // ServerSide
    public BM115PrimitivePaPMenu(int a, Inventory playerInv, ContainerLevelAccess access) {
        super(BM115ScreenRegister.PRIMITIVE_PAP_MENU.get(), a);
        this.access = access;

        this.addSlot(new Slot(this.crystalSlot, 0, 80, 35));
        this.addSlot(new Slot(this.gunSlot, 1, 120, 35));

        setupPlayerInventory(playerInv, 8, 84);

    }

    //一旦省略、後程コンテナのID設定と同時に動作を実装するべし
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute(((level, blockPos) -> {this.clearContainer(player, this.crystalSlot);}));
        this.access.execute(((level, blockPos) -> {this.clearContainer(player, this.gunSlot);}));
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, BM115BlockRegister.PRIMITIVE_MACHINE.get());
    }

    private void setupPlayerInventory(Inventory playerInv, int startX, int startY){

        // inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInv, j + i*9 + 9, startX + j*18, startY + i*18 ));
            }
        }

        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(playerInv, k, startX + k*18, startY + 58));
        }
    }
}
