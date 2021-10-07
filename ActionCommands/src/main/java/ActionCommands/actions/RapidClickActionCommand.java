package ActionCommands.actions;

import ActionCommands.ActionCommands;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class RapidClickActionCommand extends AbstractGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ActionCommands.makeID("SpamClickText"));
    public static final String[] TEXT = uiStrings.TEXT;
    public static final float DEFAULT_TIME = 2;
    public static final int DEFAULT_CLICKS = 10;
    public final AbstractGameAction onSuccessAction;
    public final AbstractGameAction onFailureAction;
    public final int clicks;

    public RapidClickActionCommand(AbstractGameAction onSuccessAction, AbstractGameAction onFailureAction) {
        this(DEFAULT_TIME, DEFAULT_CLICKS, onSuccessAction, onFailureAction);
    }

    public RapidClickActionCommand(float time, int clicks, AbstractGameAction onSuccessAction, AbstractGameAction onFailureAction) {
        this.duration = this.startDuration = time;
        this.clicks = clicks;
        this.onSuccessAction = onSuccessAction;
        this.onFailureAction = onFailureAction;
    }

    @Override
    public void update() {
        this.isDone = true;
    }
}
