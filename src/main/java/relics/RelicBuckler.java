package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

import java.util.Optional;

public class RelicBuckler extends CustomRelic {
    public static final String ID = "reliquary:Buckler";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/buckler.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/buckler.png");

    static int USES = 2;

    public RelicBuckler() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        counter = USES;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        AbstractPlayer p = AbstractDungeon.player;
        if (counter <= 0) return;
        if (c.canUpgrade()) {
            Optional<AbstractCard> deckCard = p.masterDeck.group.stream().filter(d -> d.uuid == c.uuid).findAny();
            if (deckCard.isPresent()) {
                c.upgrade();
                c.superFlash();
                deckCard.get().upgrade();
                addToTop(new RelicAboveCreatureAction(p, this));
                flash();
                setCounter(counter - 1);
            }
        }
    }

    @Override
    public void setCounter(int counter) {
        this.counter = counter;
        if (counter <= 0) {
            usedUp();
            description = DESCRIPTIONS[3];
        } else {
            description = counter == 1 ? DESCRIPTIONS[2] : DESCRIPTIONS[0] + counter + DESCRIPTIONS[1];
        }
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + USES + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicBuckler();
    }
}
