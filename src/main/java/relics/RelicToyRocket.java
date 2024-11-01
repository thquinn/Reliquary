package relics;

import basemod.abstracts.CustomRelic;
import cards.colorless.CardBlastOff;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicToyRocket extends CustomRelic {
    public static final String ID = "reliquary:ToyRocket";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/toyRocket.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/toyRocket.png");

    public RelicToyRocket() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        counter = 3;
    }
    @Override
    public void atBattleStart() {
        counter = 3;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (counter > 0) {
            if (c.costForTurn == counter) {
                counter--;
                if (counter == 0) {
                    addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    addToBot(new MakeTempCardInHandAction(new CardBlastOff()));
                }
                flash();
            } else if (counter != 2 || c.costForTurn != 3) {
                counter = 3;
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicToyRocket();
    }
}
