package com.yuusyaasisutanto.bringme115.content.screen.primitivemachine;

import com.tacz.guns.init.ModItems;
import com.yuusyaasisutanto.bringme115.content.register.BM115BlockRegister;
import com.yuusyaasisutanto.bringme115.content.register.BM115ItemRegister;
import com.yuusyaasisutanto.bringme115.content.register.BM115ScreenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class BM115PrimitivePaPMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final Container primitivePaPSlot = new SimpleContainer(2);

    //借用 from
    //github.com/Tutorials-By-Kaupenjoe/Forge-Tutorial-1.20.X/blob/30-blockEntity/src/main/java/net/kaupenjoe/tutorialmod/screen/GemPolishingStationMenu.java
    //MIT license | github.com/Tutorials-By-Kaupenjoe/Forge-Tutorial-1.20.X/blob/30-blockEntity/LICENSE

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 2;  // must be the number of slots you have!
    // 借用ここまで

    // ClientSide
    public BM115PrimitivePaPMenu(int a, Inventory playerInv){
        this(a, playerInv, ContainerLevelAccess.NULL);
    }

    // ServerSide
    public BM115PrimitivePaPMenu(int a, Inventory playerInv, ContainerLevelAccess access) {
        super(BM115ScreenRegister.PRIMITIVE_PAP_MENU.get(), a);
        this.access = access;
        checkContainerSize(playerInv, 2);
        setupPlayerInventory(playerInv, 8, 84);

        //gunslot
        this.addSlot(new Slot(this.primitivePaPSlot, 0, 80, 35){
            @Override
            public boolean mayPlace(@NotNull ItemStack inputItem) {
                ResourceLocation itemName = ForgeRegistries.ITEMS.getKey(inputItem.getItem());
                return inputItem != null && itemName.getNamespace().equals("tacz") && itemName.getPath().equals("modern_kinetic_gun");
            }
        });

        //crystalslot
        this.addSlot(new Slot(this.primitivePaPSlot, 1, 120, 35){
                         @Override
                         public boolean mayPlace(@NotNull ItemStack inputItem) {
                             return inputItem.is(BM115ItemRegister.AETHERIUM_CRYSTAL.get());
                         }
                     }
        );



    }

    //借用 from
    //github.com/Tutorials-By-Kaupenjoe/Forge-Tutorial-1.20.X/blob/30-blockEntity/src/main/java/net/kaupenjoe/tutorialmod/screen/GemPolishingStationMenu.java
    //MIT license | github.com/Tutorials-By-Kaupenjoe/Forge-Tutorial-1.20.X/blob/30-blockEntity/LICENSE

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    // 借用ここまで

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute(((level, blockPos) -> {this.clearContainer(player, this.primitivePaPSlot);}));
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
