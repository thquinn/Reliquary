package powers;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import util.TextureLoader;

public class HandSizePower extends AbstractPower {
    public static final String POWER_ID = "reliquary:HandSize";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/handSize32.png");
    private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/handSize84.png");

    public HandSizePower(AbstractCreature owner, int amount) {
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        canGoNegative = true;
        updateDescription();
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
    }

    // adapted from https://github.com/JohnnyDevo/Hydrologist-Mod---Master/blob/master/src/main/java/hydrologistmod/powers/AccumulatorPower.java

    @Override
    public void onInitialApplication() {
        BaseMod.MAX_HAND_SIZE += amount;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        BaseMod.MAX_HAND_SIZE += stackAmount;
        if (amount == 0) {
            addToTop(new RemoveSpecificPowerAction(owner, owner, this.ID));
        }
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        BaseMod.MAX_HAND_SIZE -= reduceAmount;
        if (amount == 0) {
            addToTop(new RemoveSpecificPowerAction(owner, owner, this.ID));
        }
    }

    @Override
    public void onVictory() {
        BaseMod.MAX_HAND_SIZE -= amount;
    }

    @Override
    public void onRemove() {
        BaseMod.MAX_HAND_SIZE -= amount;
    }

    public void updateDescription() {
        name = amount >= 0 ? powerStrings.NAME : powerStrings.DESCRIPTIONS[0];
        description = (amount >= 0 ? powerStrings.DESCRIPTIONS[1] : powerStrings.DESCRIPTIONS[2]) + amount + powerStrings.DESCRIPTIONS[3];
    }
}
