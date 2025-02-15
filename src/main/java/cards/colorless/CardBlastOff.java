package cards.colorless;

import cards.ReliquaryCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

public class CardBlastOff extends ReliquaryCard {
    public static final String ID = "reliquary:BlastOff";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/colorless/blastOff.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 0;

    public CardBlastOff() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        baseDamage = 25;
        baseMagicNumber = 1;
        magicNumber = 1;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped())
                addToBot(new VFXAction(new ExplosionSmallEffect(mo.hb.cX, mo.hb.cY), 0.1F));
        }
        addToBot(new WaitAction(0.5F));
        addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, 1, false)));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(10);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CardBlastOff();
    }
}