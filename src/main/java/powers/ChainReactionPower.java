package powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.CombustPower;
import util.ReliquaryLogger;
import util.TextureLoader;

import java.lang.reflect.Field;

public class ChainReactionPower extends AbstractPower {
    public static final String POWER_ID = "reliquary:ChainReaction";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture tex32 = TextureLoader.getTexture("reliquaryAssets/images/powers/chainReaction32.png");
private static final Texture tex84 = TextureLoader.getTexture("reliquaryAssets/images/powers/chainReaction84.png");

    public ChainReactionPower(AbstractCreature owner, int amount) {
        name = powerStrings.NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToTop(new ApplyPowerAction(owner, owner, new CombustPower(owner, 0, amount), amount));
        // Combust's stackPower always increments HP loss. Let's counteract that...
        CombustPower combust = (CombustPower) owner.getPower(CombustPower.POWER_ID);
        if (combust != null) {
            try {
                Field hpLossField = CombustPower.class.getDeclaredField("hpLoss");
                hpLossField.setAccessible(true);
                hpLossField.set(combust, (int) hpLossField.get(combust) - 1);
            } catch (Exception e) {
                ReliquaryLogger.error("reflection failed in ChainReactionPower: " + e);
            }
        }
    }

    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }
}
