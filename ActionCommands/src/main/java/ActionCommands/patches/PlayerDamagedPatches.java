package ActionCommands.patches;

import ActionCommands.ActionCommands;
import ActionCommands.interfaces.PlayerWithActionCommands;
import ActionCommands.util.MouseActionListener;
import ActionCommands.vfx.WordPopupEffect;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;

public class PlayerDamagedPatches {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ActionCommands.makeID("TimingText"));
    public static final String[] TEXT = uiStrings.TEXT;

    //private static AbstractCreature activeCreature;
    private static AbstractCreature lastSeen;
    private static final int BASE_FRAMES = 10;
    private static final int SUPER_BLOCK_FRAMES = 3;
    private static int frameReduction;

    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class ActivateListener {
        @SpireInsertPatch(locator = MonsterTurnLocator.class, localvars = "m")
        public static void withoutCrashingHopefully(GameActionManager __instance, AbstractMonster m) {
            //activeCreature = m;
            lastSeen = null;
            frameReduction = 0;
        }
        private static class MonsterTurnLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "takeTurn");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class DamageReduction {
        @SpirePrefixPatch
        public static void withoutCrashingHopefully(AbstractPlayer __instance, DamageInfo info) {
            if (__instance instanceof PlayerWithActionCommands && ((PlayerWithActionCommands) __instance).useBlockCommands() && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                if (lastSeen == info.owner && frameReduction < 7) {
                    frameReduction++;
                }
                if (info.owner != null && info.owner != __instance) {
                    lastSeen = info.owner;
                }
                if (MouseActionListener.getLatestClick() != null) {
                    if (MouseActionListener.mouseClicks.size() == 1 || MouseActionListener.allOthersSuccessful()) {
                        if (MouseActionListener.clickedInFrameLimit(BASE_FRAMES-frameReduction, SUPER_BLOCK_FRAMES)) {
                            //TODO remove the click from the list
                            MouseActionListener.setClickSuccessful();
                            info.output -= ((PlayerWithActionCommands) __instance).getBlockCommandDamageReduction(!MouseActionListener.getLatestClick().isLeftClick());
                            if (info.output < 0) {
                                info.output = 0;
                            }
                            AbstractDungeon.effectList.add(new WordPopupEffect(AbstractDungeon.player.hb.cX+AbstractDungeon.player.hb_w, AbstractDungeon.player.hb.cY, TEXT[MouseActionListener.calculateTimingIndex(BASE_FRAMES-frameReduction, TEXT.length)].toUpperCase(), Color.WHITE.cpy()));
                            ((PlayerWithActionCommands) __instance).onSuccessfulBlockCommand(!MouseActionListener.getLatestClick().isLeftClick(), info.owner);
                        }
                    }
                }
            }
        }
    }
}
