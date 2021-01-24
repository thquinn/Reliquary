package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

import java.util.Optional;

public class RelicBuckler extends CustomRelic {
    public static final String ID = "reliquary:Buckler";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/buckler.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/buckler.png");

    public RelicBuckler() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        counter = 2;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        AbstractPlayer p = AbstractDungeon.player;
        if (counter == 0) return;
        if (c.canUpgrade()) {
            Optional<AbstractCard> deckCard = p.masterDeck.group.stream().filter(d -> d.cardID.equals(c.cardID) && d.timesUpgraded == c.timesUpgraded).findAny();
            if (deckCard.isPresent()) {
                c.upgrade();
                c.superFlash();
                deckCard.get().upgrade();
                addToTop(new RelicAboveCreatureAction(p, this));
                flash();
                counter--;
                if (counter == 0) {
                    grayscale = true;
                }
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
        return new RelicBuckler();
    }
}
