package cards.colorless.vapor;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BackAttackPower;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;

public class CardVaporSmokeBomb extends CustomCard {
    public static final String ID = "reliquary:VaporSmokeBomb";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/colorless/vapors/blockPotion.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;

    public CardVaporSmokeBomb() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        baseMagicNumber = 100;
        magicNumber = 100;
        selfRetain = true;
        exhaust = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }
        if (p.gold < magicNumber) {
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.hasPower(BackAttackPower.POWER_ID)) {
                cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[1];
                return false;
            }
            if (mo.type == AbstractMonster.EnemyType.BOSS) {
                cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[AbstractDungeon.getCurrRoom().monsters.monsters.size() == 1 ? 2 : 3];
                return false;
            }
        }
        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        p.loseGold(magicNumber);
        AbstractDungeon.getCurrRoom().smoked = true;
        addToBot(new VFXAction(new SmokeBombEffect(p.hb.cX, p.hb.cY)));
        AbstractDungeon.effectList.add(new RainingGoldEffect(magicNumber, true));
        p.hideHealthBar();
        p.isEscaping = true;
        p.flipHorizontal = !p.flipHorizontal;
        AbstractDungeon.overlayMenu.endTurnButton.disable();
        p.escapeTimer = 2.5F;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(-50);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CardVaporSmokeBomb();
    }
}