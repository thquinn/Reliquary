package relics;

import actions.DiscoveryCustomAction;
import basemod.abstracts.CustomRelic;
import cards.colorless.vapor.*;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SacredBark;
import com.megacrit.cardcrawl.relics.Sozu;
import util.TextureLoader;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RelicBoilingFlask extends CustomRelic {
    public static final String ID = "reliquary:BoilingFlask";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/boilingFlask.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/boilingFlask.png");

    public RelicBoilingFlask() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public boolean canSpawn() {
        boolean hasEmptySlot = AbstractDungeon.player.potions.stream().anyMatch(p -> p instanceof PotionSlot);
        if (AbstractDungeon.player.hasRelic(Sozu.ID) && hasEmptySlot) {
            return false;
        }
        return true;
    }

    public void atBattleStartPreDraw() {
        CardGroup vapors = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractPotion potion : AbstractDungeon.player.potions) {
            if (potion instanceof PotionSlot) {
                return;
            }
            AbstractCard vapor = POTION_TO_VAPOR.getOrDefault(potion.ID, UNKNOWN_VAPOR);
            if (vapors.group.stream().anyMatch(c -> c.cardID.equals(vapor.cardID))) {
                continue;
            }
            if (AbstractDungeon.player.hasRelic(SacredBark.ID)) {
                vapor.upgrade();
            }
            vapors.addToTop(vapor);
        }
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        if (vapors.size() == 1) {
            addToBot(new MakeTempCardInHandAction(vapors.getTopCard()));
        } else {
            addToBot(new DiscoveryCustomAction(vapors));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicBoilingFlask();
    }

    public static final Map<String, AbstractCard> POTION_TO_VAPOR = Stream.of(new Object[][] {
            { Ambrosia.POTION_ID, new CardVaporAmbrosia() },
            { AncientPotion.POTION_ID, new CardVaporAncientPotion() },
            { AttackPotion.POTION_ID, new CardVaporAttackPotion() },
            { BlessingOfTheForge.POTION_ID, new CardVaporBlessingOfTheForge() },
            { BlockPotion.POTION_ID, new CardVaporBlockPotion() },
            { BloodPotion.POTION_ID, new CardVaporBloodPotion() },
            { BottledMiracle.POTION_ID, new CardVaporBottledMiracle() },
            { ColorlessPotion.POTION_ID, new CardVaporColorlessPotion() },
            { CultistPotion.POTION_ID, new CardVaporCultistPotion() },
            { CunningPotion.POTION_ID, new CardVaporCunningPotion() },
            { DexterityPotion.POTION_ID, new CardVaporDexterityPotion() },
            { DistilledChaosPotion.POTION_ID, new CardVaporDistilledChaos() },
            { DuplicationPotion.POTION_ID, new CardVaporDuplicationPotion() },
            { Elixir.POTION_ID, new CardVaporElixir() },
            { EnergyPotion.POTION_ID, new CardVaporEnergyPotion() },
            { EntropicBrew.POTION_ID, new CardVaporEntropicBrew() },
            { EssenceOfDarkness.POTION_ID, new CardVaporEssenceOfDarkness() },
            { EssenceOfSteel.POTION_ID, new CardVaporEssenceOfSteel() },
            { ExplosivePotion.POTION_ID, new CardVaporExplosivePotion() },
            { FairyPotion.POTION_ID, new CardVaporFairyInABottle() },
            { FearPotion.POTION_ID, new CardVaporFearPotion() },
            { FirePotion.POTION_ID, new CardVaporFirePotion() },
            { SteroidPotion.POTION_ID, new CardVaporFlexPotion() },
            { FocusPotion.POTION_ID, new CardVaporFocusPotion() },
            { FruitJuice.POTION_ID, new CardVaporFruitJuice() },
            { GamblersBrew.POTION_ID, new CardVaporGamblersBrew() },
            { GhostInAJar.POTION_ID, new CardVaporGhostInAJar() },
            { HeartOfIron.POTION_ID, new CardVaporHeartOfIron() },
            { LiquidBronze.POTION_ID, new CardVaporLiquidBronze() },
            { LiquidMemories.POTION_ID, new CardVaporLiquidMemories() },
            { PoisonPotion.POTION_ID, new CardVaporPoisonPotion() },
            { PotionOfCapacity.POTION_ID, new CardVaporPotionOfCapacity() },
            { PowerPotion.POTION_ID, new CardVaporPowerPotion() },
            { RegenPotion.POTION_ID, new CardVaporRegenPotion() },
            { SkillPotion.POTION_ID, new CardVaporSkillPotion() },
            { SmokeBomb.POTION_ID, new CardVaporSmokeBomb() },
            { SneckoOil.POTION_ID, new CardVaporSneckoOil() },
            { SpeedPotion.POTION_ID, new CardVaporSpeedPotion() },
            { StancePotion.POTION_ID, new CardVaporStancePotion() },
            { StrengthPotion.POTION_ID, new CardVaporStrengthPotion() },
            { SwiftPotion.POTION_ID, new CardVaporSwiftPotion() },
            { WeakenPotion.POTION_ID, new CardVaporWeakPotion() }
    }).collect(Collectors.toMap(data -> (String)data[0], data -> (AbstractCard)data[1]));
    public static final AbstractCard UNKNOWN_VAPOR = new CardVaporUnknownPotion();
}
