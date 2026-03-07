//public class Primal_Stone() extends Item {
//    public Primal_Stone(Properties properties) {
//        super(properties);
//    }
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
//        ItemStack itemStack = player.getItemInHand(usedHand);
//        if (!level.isClientSide) {
//            player.sendSystemMessage(Component.literal("Сосать Америка"));
//        }
//        itemStack.hurtAndShrink(1, player);
//        return InteractionResultHolder.consume(itemStack);
//    }
//}
