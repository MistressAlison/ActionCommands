//package ActionCommands.actions;
//
//import ActionCommands.ActionCommands;
//import ActionCommands.interfaces.PlayerWithActionCommands;
//import ActionCommands.util.MouseActionListener;
//import ActionCommands.vfx.WordPopupEffect;
//import com.badlogic.gdx.graphics.Color;
//import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.cards.DamageInfo;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.helpers.input.InputHelper;
//import com.megacrit.cardcrawl.localization.UIStrings;
//
//public class PreDamageActionCommand extends AbstractGameAction {
//
//    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ActionCommands.makeID("TimingText"));
//    public static final String[] TEXT = uiStrings.TEXT;
//    private static final int REDUCTION = 2;
//
//    public final DamageInfo linkedInfo;
//    boolean inputDown;
//    boolean inputUp;
//    boolean attempted;
//    int timingIndex;
//
//    public PreDamageActionCommand(int pseudoFrames, DamageInfo linkedInfo) {
//        this(pseudoFrames/60f, linkedInfo);
//    }
//
//    public PreDamageActionCommand(float timingWindow, DamageInfo linkedInfo) {
//        this.startDuration = this.duration = timingWindow;
//        this.linkedInfo = linkedInfo;
//    }
//
//    public int calculateTimingIndex(float currentDuration) {
//        int steps = 2*TEXT.length - 1;
//        float step = startDuration/steps;
//        float t = 0;
//        int i = 0;
//        while (i < steps) {
//            t += step;
//            if (t >= currentDuration) {
//                break;
//            }
//            i++;
//        }
//        if (i < TEXT.length) {
//            return i;
//        } else {
//            return 2*(TEXT.length-1)-i;
//        }
//    }
//
//    @Override
//    public void update() {
//        if (duration == startDuration) {
//            MouseActionListener.isActive = false;
//        }
//        if (MouseActionListener.fudgedClick) {
//            this.isDone = true;
//            return;
//        }
//        if (!attempted) {
//            if (InputHelper.justClickedLeft) {
//                inputDown = true;
//            } else if (InputHelper.justReleasedClickLeft) {
//                inputUp = true;
//                attempted = true;
//                if (inputDown) {
//                    timingIndex = calculateTimingIndex(duration);
//                }
//                this.isDone = true;
//            }
//        }
//        tickDuration();
//        if (isDone && inputUp && inputDown) {
//            AbstractDungeon.effectList.add(new WordPopupEffect(AbstractDungeon.player.hb.cX+AbstractDungeon.player.hb_w, AbstractDungeon.player.hb.cY, TEXT[timingIndex].toUpperCase(), Color.WHITE.cpy()));
//            linkedInfo.output -= REDUCTION;
//            if (linkedInfo.output < 0) {
//                linkedInfo.output = 0;
//            }
//            if (AbstractDungeon.player instanceof PlayerWithActionCommands) {
//                ((PlayerWithActionCommands) AbstractDungeon.player).onSuccessfulBlockCommand();
//            }
//        }
//    }
//}
