package com.superthai.delight;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// A simple food mod: every texture in the "food" set becomes an edible item.
@Mod(SuperThaiDelight.MODID)
public class SuperThaiDelight {
    // The mod id, matching the entry in neoforge.mods.toml (via ${mod_id}).
    public static final String MODID = "superthai_delight";

    // Registers all of this mod's items under the "superthai_delight" namespace.
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Registers this mod's creative tab.
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Every food we register is collected here so the creative tab can list them all.
    private static final List<DeferredItem<Item>> FOODS = new ArrayList<>();

    // --- The dishes. Values are (nutrition, saturationModifier). ---
    public static final DeferredItem<Item> BOILED_RICE = food("boiled_rice", 3, 0.3f);
    public static final DeferredItem<Item> BUA_LOY = food("bua_loy", 4, 0.4f);
    public static final DeferredItem<Item> CRISPY_PORK_WITH_BASIL = food("crispy_pork_with_basil", 8, 0.8f);
    public static final DeferredItem<Item> FOI_THONG = food("foi_thong", 4, 0.4f);
    public static final DeferredItem<Item> FRIED_CHICKEN = food("fried_chicken", 8, 0.8f);
    public static final DeferredItem<Item> GRILLED_PORK_ON_A_STICK = food("grilled_pork_on_a_stick", 6, 0.7f);
    public static final DeferredItem<Item> GRILLED_PORK_WITH_STICKY_RICE = food("grilled_pork_with_sticky_rice", 8, 0.9f);
    public static final DeferredItem<Item> ISAN_STYLE_MEAT_SALAD = food("isan_style_meat_salad", 7, 0.8f);
    public static final DeferredItem<Item> MANGO_STICKY_RICE = food("mango_sticky_rice", 6, 0.6f);
    public static final DeferredItem<Item> OLIANG = food("oliang", 2, 0.1f);
    public static final DeferredItem<Item> PAPAYA_SALAD = food("papaya_salad", 5, 0.6f);
    public static final DeferredItem<Item> SPICY_MINCED_MEAT_SALAD = food("spicy_minced_meat_salad", 7, 0.8f);
    public static final DeferredItem<Item> THAI_TEA = food("thai_tea", 3, 0.2f);
    public static final DeferredItem<Item> THONG_YOD = food("thong_yod", 4, 0.4f);

    // A creative tab holding every dish, placed right after the vanilla Food & Drinks tab.
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FOOD_TAB =
            CREATIVE_MODE_TABS.register("food_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MODID))
                    .withTabsBefore(CreativeModeTabs.FOOD_AND_DRINKS)
                    .icon(() -> FRIED_CHICKEN.get().getDefaultInstance())
                    .displayItems((parameters, output) -> FOODS.forEach(food -> output.accept(food.get())))
                    .build());

    public SuperThaiDelight(IEventBus modEventBus, ModContainer modContainer) {
        // Hook our registries onto the mod event bus so the game picks them up during loading.
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }

    // Registers a simple edible item and remembers it for the creative tab.
    private static DeferredItem<Item> food(String name, int nutrition, float saturationModifier) {
        DeferredItem<Item> item = ITEMS.registerSimpleItem(name, new Item.Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(nutrition)
                        .saturationModifier(saturationModifier)
                        .build()));
        FOODS.add(item);
        return item;
    }
}
