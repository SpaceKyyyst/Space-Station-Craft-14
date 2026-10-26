package net.ssc14.content.item.food;

/** Canonical food descriptors shared by registrations and compatibility facades. */
public final class Ssc14FoodDefinitions {
    public static final FoodDefinition BREAD = food(3, "Nom. На вкус как хлеб.");
    public static final FoodDefinition TOFU = food(3, "Nom. На вкус пресно и немного соево.");
    public static final FoodDefinition BUN = food(1, "Nom. На вкус как хлеб.");
    public static final FoodDefinition BREAD_SLICE = food(1, "Nom. На вкус как хлеб.");
    public static final FoodDefinition TOFU_SLICE = food(1, "Nom. На вкус пресно и немного соево.");
    public static final FoodDefinition TOFU_BURGER = food(1, "Nom. На вкус как хлеб с тофу.");
    public static final FoodDefinition CHEESE = food(1, "Nom. На вкус сырно.");
    public static final FoodDefinition CUTLET = food(1, "Nom. На вкус как сырое перемолотое мясо.");
    public static final FoodDefinition FRIED_CUTLET = food(1, "Nom. На вкус как котлета.");
    public static final FoodDefinition MEAT = food(1, "Nom. На вкус как сырое мясо.");
    public static final FoodDefinition FRIED_MEAT = food(1, "Nom. На вкус как стейк.");
    public static final FoodDefinition ROTTEN_MEAT = food(1, "Nom. На вкус как гниль и плесень.");

    private Ssc14FoodDefinitions() { }

    private static FoodDefinition food(int maxBites, String tasteMessage) {
        return FoodDefinition.withoutTrash(maxBites, tasteMessage);
    }
}
