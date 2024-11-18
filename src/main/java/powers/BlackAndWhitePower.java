package powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import util.TextureLoader;

public class BlackAndWhitePower extends AbstractPower {
    public static final String POWER_ID = "reliquary:BlackAndWhitePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/blackAndWhite32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/blackAndWhite84.png");

    boolean playedAttack, playedSkill;

    public BlackAndWhitePower(AbstractCreature owner, int amount) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        playedAttack = false;
        playedSkill = false;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster _) {
        if (playedAttack && playedSkill) {
            return;
        }
        playedAttack |= card.type == AbstractCard.CardType.ATTACK;
        playedSkill |= card.type == AbstractCard.CardType.SKILL;
        if (playedAttack && playedSkill) {
            flash();
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped() && !m.halfDead && m.getIntentBaseDmg() > 0) {
                    addToBot(new DamageAction(m, new DamageInfo(owner, m.getIntentBaseDmg() * amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
                }
            }
        }
    }

    public void updateDescription() {
        if (amount == 1) {
            description = powerStrings.DESCRIPTIONS[0];
        } else {
            description = powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2];
        }
    }
}
