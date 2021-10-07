//package ActionCommands.patches;
//
//import ActionCommands.actions.PreDamageActionCommand;
//import ActionCommands.interfaces.PlayerWithActionCommands;
//import ActionCommands.util.MouseActionListener;
//import basemod.ReflectionHacks;
//import com.evacipated.cardcrawl.modthespire.lib.*;
//import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.GameActionManager;
//import com.megacrit.cardcrawl.actions.common.DamageAction;
//import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
//import com.megacrit.cardcrawl.cards.DamageInfo;
//import com.megacrit.cardcrawl.core.AbstractCreature;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.rooms.AbstractRoom;
//import javassist.CtBehavior;
//
//public class AddDefendCommand {
//
//    private static AbstractCreature activeCreature;
//    private static AbstractCreature lastSeen;
//    private static int frameReduce;
//
//    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
//    public static class ActivateListener {
//        @SpireInsertPatch(locator = Locator.class, localvars = "m")
//        public static void withoutCrashingHopefully(GameActionManager __instance, AbstractMonster m) {
//            activeCreature = m;
//            lastSeen = null;
//            frameReduce = 0;
//        }
//        private static class Locator extends SpireInsertLocator {
//            @Override
//            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "takeTurn");
//                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
//            }
//        }
//    }
//
//    @SpirePatch(clz = GameActionManager.class, method = "addToBottom")
//    public static class AddCommandsBeforeBottom {
//        @SpirePrefixPatch
//        public static void withoutCrashingHopefully(GameActionManager __instance, AbstractGameAction action) {
//            addAction(__instance, action, false);
//        }
//    }
//
//    @SpirePatch(clz = GameActionManager.class, method = "addToTop")
//    public static class AddCommandsAfterTop {
//        @SpirePostfixPatch
//        public static void withoutCrashingHopefully(GameActionManager __instance, AbstractGameAction action) {
//            addAction(__instance, action, true);
//        }
//    }
//
//    public static void addAction(GameActionManager gam, AbstractGameAction action, boolean top) {
//        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.player instanceof PlayerWithActionCommands && ((PlayerWithActionCommands) AbstractDungeon.player).useBlockCommands()) {
//            if (gam.turnHasEnded && action.source == activeCreature && action.target == AbstractDungeon.player) {
//                DamageInfo info = null;
//                if (action instanceof DamageAction) {
//                    info = ReflectionHacks.getPrivate(action, DamageAction.class, "info");
//                } else if (action instanceof VampireDamageAction) {
//                    info = ReflectionHacks.getPrivate(action, VampireDamageAction.class, "info");
//                }
//                if (info != null) {
//                    if (lastSeen == activeCreature && frameReduce < 7) {
//                        frameReduce++;
//                    }
//                    lastSeen = activeCreature;
//                    PreDamageActionCommand pdac = new PreDamageActionCommand(10-frameReduce, info);
//                    if (top) {
//                        gam.actions.add(0, pdac);
//                    } else {
//                        gam.actions.add(pdac);
//                    }
//                }
//            }
//        }
//    }
//}
