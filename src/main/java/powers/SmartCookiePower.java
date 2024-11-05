package powers;

import cards.colorless.CardJustDesserts;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import util.TextureLoader;

import javax.swing.table.AbstractTableModel;

public class SmartCookiePower extends AbstractPower implements NonStackablePower {
    public static final String POWER_ID = "reliquary:SmartCookiePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/smartCookie32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/smartCookie84.png");

    AbstractCard card;

    public SmartCookiePower(AbstractCreature owner, AbstractCard card) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.owner = owner;
        type = PowerType.BUFF;
        this.card = card;
        updateDescription();
    }

    public void atStartOfTurn() {
        AbstractCard copy = card.makeStatEquivalentCopy();
        copy.purgeOnUse = true;
        addToBot(new NewQueueCardAction(copy, true, false, true));
    }

    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + FontHelper.colorString(card.name, "y") + powerStrings.DESCRIPTIONS[1];
    }
}
