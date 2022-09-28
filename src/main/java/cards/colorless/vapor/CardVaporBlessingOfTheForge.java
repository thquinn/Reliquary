package cards.colorless.vapor;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CardVaporBlessingOfTheForge extends CardVapor {
    public static final String ID = "reliquary:VaporBlessingOfTheForge";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/colorless/vapors/blessingOfTheForge.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;

    public CardVaporBlessingOfTheForge() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        selfRetain = true;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = upgraded ? (int)Math.ceil(p.hand.size() / 2f) : 1;
        CardGroup upgradeable = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : p.hand.group) {
            if (c.canUpgrade() && c.type != CardType.STATUS)
                upgradeable.addToTop(c);
        }
        amount = Math.min(amount, upgradeable.size());
        upgradeable.shuffle();
        for (int i = 0; i < amount; i++) {
            upgradeable.group.get(i).upgrade();
            upgradeable.group.get(i).superFlash();
            upgradeable.group.get(i).applyPowers();
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}