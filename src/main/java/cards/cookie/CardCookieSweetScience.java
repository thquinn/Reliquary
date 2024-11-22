package cards.cookie;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import powers.SnackBreakPower;

public class CardCookieSweetScience extends CardCookie {
    public static final String ID = "reliquary:CookieSweetScience";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/cookie/sweetScience.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 0;

    public CardCookieSweetScience() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardTarget.SELF);
        baseMagicNumber = 20;
        magicNumber = 20;
        exhaust = true;
        isSnack = true;
    }
    @Override
    public boolean canSpawnAsFirst() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        int amount = Math.min(magicNumber, p.currentBlock);
        if (amount > 0) {
            addToBot(new LoseBlockAction(p, p, amount));
            AbstractDungeon.effectList.add(new RainingGoldEffect(amount * 2, true));
            addToBot(new GainGoldAction(amount));
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}