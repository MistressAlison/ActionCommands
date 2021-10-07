package ActionCommands.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public interface PlayerWithActionCommands {
    default boolean useBlockCommands() {
        return true;
    }

    default int getBlockCommandDamageReduction(boolean superBlock) {
        return superBlock ? 5 : 2;
    }

    default void onSuccessfulBlockCommand(boolean wasSuperBlock, AbstractCreature attacker) {}

    default void onSuccessfulAttackCommand(AbstractCard card) {}
}
