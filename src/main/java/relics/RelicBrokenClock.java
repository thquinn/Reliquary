package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import patches.PatchSkipPlayerTurn;
import util.TextureLoader;

public class RelicBrokenClock extends CustomRelic {
    public static final String ID = "reliquary:BrokenClock";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/brokenClock.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/brokenClock.png");

    private int turn = 0;

    public RelicBrokenClock() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        turn = 0;
        addToBot(new SkipEnemiesTurnAction());
    }

    @Override
    public void onPlayerEndTurn() {
        turn++;
        if (turn == 1) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        } else if (turn == 2) {
            PatchSkipPlayerTurn.SkipPlayerTurn.skipPlayerTurn.set(AbstractDungeon.getCurrRoom(), true);
        }
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.cardID.equals("Vault")) {
            // Don't count a Vault turn as one of your extra turns.
            turn--;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicBrokenClock();
    }
}
