package powers;

import cards.colorless.CardJustDesserts;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import util.TextureLoader;

public class SweetRevengePower extends AbstractPower implements OnLoseBlockPower {
    public static final String POWER_ID = "reliquary:SweetRevengePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/sweetRevenge32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/sweetRevenge84.png");

    int blocked;

    public SweetRevengePower(AbstractCreature owner) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.owner = owner;
        type = PowerType.BUFF;
        amount = 1;
        updateDescription();
    }

    @Override
    public int onLoseBlock(DamageInfo damageInfo, int damageAmount) {
        int blockLost = Math.min(damageAmount, owner.currentBlock);
        blocked += blockLost;
        return damageAmount;
    }
    public void atStartOfTurn() {
        if (blocked > 0) {
            CardJustDesserts justDesserts = new CardJustDesserts();
            justDesserts.setDamage(blocked);
            addToBot(new MakeTempCardInHandAction(justDesserts, amount));
        }
        blocked = 0;
    }

    public void updateDescription() {
        if (amount == 1) {
            description = powerStrings.DESCRIPTIONS[0];
        } else {
            description = powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2];
        }
    }
}
