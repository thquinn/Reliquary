package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicHotPoker extends CustomRelic {
    public static final String ID = "reliquary:HotPoker";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/hotPoker.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/hotPoker.png");

    public RelicHotPoker() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (!AbstractDungeon.overlayMenu.endTurnButton.enabled) {
            // If the End Turn button is disabled, it's a start-of-turn draw.
            return;
        }
        if (drawnCard.costForTurn > 0) {
            AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
            addToBot(new RelicAboveCreatureAction(target, this));
            addToBot(new DamageAction(
                    target,
                    new DamageInfo(AbstractDungeon.player, drawnCard.cost, DamageInfo.DamageType.THORNS),
                    AbstractGameAction.AttackEffect.FIRE
            ));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicHotPoker();
    }
}
