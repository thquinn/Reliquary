package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import util.TextureLoader;

public class RelicSoularoid extends CustomRelic {
    public static final String ID = "reliquary:Soularoid";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/soularoid.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/soularoid.png");

    public RelicSoularoid() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster target) {
        if (card.type == AbstractCard.CardType.STATUS) {
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        if (p.masterDeck.group.stream().noneMatch(c -> c.cardID.equals(card.cardID))) {
            flash();
            AbstractDungeon.effectsQueue.add(new FastCardObtainEffect(card.makeStatEquivalentCopy(), card.current_x, card.current_y));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicSoularoid();
    }
}
