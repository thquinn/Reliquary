package cards.cookie;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.ReliquaryLogger;

public class CardCookieHalfBakedIdea extends CardCookie {
    public static final String ID = "reliquary:CookieHalfBakedIdea";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/cookie/halfBakedIdea.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 2;

    public CardCookieHalfBakedIdea() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardTarget.NONE);
        isSnack = true;
    }

    @Override
    public void onEaten() {
        AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.COMMON);
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}