package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ReboundPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicBoomerang extends CustomRelic {
    public static final String ID = "reliquary:Boomerang";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/boomerang.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/boomerang.png");

    static int NUMBER = 4;

    public RelicBoomerang() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atTurnStart() {
        counter = 0;
        stopPulse();
        if (NUMBER == 1) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            ReboundPower reboundPower = new ReboundPower(AbstractDungeon.player);
            reboundPower.onAfterUseCard(null, null); // the rebound power ignores the first card so it doesn't trigger from the card that creates it: Rebound
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, reboundPower));
        }
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (counter < NUMBER) {
            counter++;
        }
        if (counter == NUMBER - 1) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ReboundPower(AbstractDungeon.player)));
            beginLongPulse();
        } else if (counter == NUMBER) {
            stopPulse();
        }
    }

    @Override
    public String getUpdatedDescription() {
        int descriptionIndex = NUMBER - 1;
        if (descriptionIndex < 0 || descriptionIndex >= DESCRIPTIONS.length) {
            return "<ERROR: Contact developer.>";
        }
        return DESCRIPTIONS[descriptionIndex];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicBoomerang();
    }
}
