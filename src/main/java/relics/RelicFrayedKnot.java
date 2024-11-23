package relics;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnAnyPowerAppliedRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.DoubleYourBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.WrathNextTurnPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.WrathStance;
import powers.SkeletonKeyBlurPower;
import powers.VulnerableNextTurnPower;
import util.TextureLoader;

public class RelicFrayedKnot extends ReliquaryRelic implements OnReceivePowerRelic {
    public static final String ID = "reliquary:FrayedKnot";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/frayedKnot.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/frayedKnot.png");

    public RelicFrayedKnot() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT, RelicType.GREEN);
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature source) {
        AbstractPlayer p = AbstractDungeon.player;

        if (power.ID.equals(EnergizedPower.POWER_ID) || power.ID.equals(EnergizedBluePower.POWER_ID)) {
            addToBot(new GainEnergyAction(power.amount));
        } else if (power.ID.equals(NextTurnBlockPower.POWER_ID)) {
            addToBot(new GainBlockAction(p, power.amount));
        } else if (power.ID.equals(DrawCardNextTurnPower.POWER_ID)) {
            addToBot(new DrawCardAction(source, power.amount));
        } else if (power.ID.equals(NightmarePower.POWER_ID)) {
            AbstractCard card = ReflectionHacks.getPrivate(power, NightmarePower.class, "card");
            addToBot(new MakeTempCardInHandAction(card, power.amount));
        } else if (power.ID.equals(PhantasmalPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new DoubleDamagePower(p, 1, false), power.amount));
        } else if (power.ID.equals(WrathNextTurnPower.POWER_ID)) {
            addToBot(new ChangeStanceAction(WrathStance.STANCE_ID));
        } else if (power.ID.equals(VulnerableNextTurnPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, power.amount, false)));
        } else if (power.ID.equals(SkeletonKeyBlurPower.POWER_ID)) {
            addToBot(new DoubleYourBlockAction(p));
            addToBot(new ApplyPowerAction(p, p, new BlurPower(p, power.amount)));
        } else {
            return true;
        }
        return false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicFrayedKnot();
    }
}
