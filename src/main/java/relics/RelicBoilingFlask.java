package relics;

import actions.DiscoveryCustomAction;
import basemod.abstracts.CustomRelic;
import cards.colorless.vapor.*;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.widepotions.potions.WidePotion;
import com.evacipated.cardcrawl.mod.widepotions.potions.WidePotionRightHalf;
import com.evacipated.cardcrawl.mod.widepotions.potions.WidePotionSlot;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SacredBark;
import com.megacrit.cardcrawl.relics.Sozu;
import util.TextureLoader;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RelicBoilingFlask extends ReliquaryRelic {
    public static final String ID = "reliquary:BoilingFlask";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/boilingFlask.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/boilingFlask.png");

    public RelicBoilingFlask() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public boolean canSpawn() {
        long emptySlots = AbstractDungeon.player.potions.stream().filter(p -> p instanceof PotionSlot).count();
        if (AbstractDungeon.player.hasRelic(Sozu.ID) && emptySlots > 0) {
            return false;
        }
        if (emptySlots > 0 && AbstractDungeon.floorNum > 45 - emptySlots * 2) {
            return false;
        }
        return true;
    }

    public void atBattleStartPreDraw() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.potions.size() == 0) {
            return;
        }
        ArrayList<AbstractPotion> potions = new ArrayList<>(p.potions);
        boolean wideLoad = Loader.isModLoaded("widepotions");
        if (wideLoad) {
            potions.addAll(WidePotionSlot.Field.widepotions.get(p));
        }

        CardGroup vapors = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractPotion potion : potions) {
            if (potion instanceof PotionSlot) {
                return;
            }
            if (wideLoad && potion instanceof WidePotionRightHalf) {
                continue;
            }
            CardVapor vapor = (CardVapor) POTION_TO_VAPOR.getOrDefault(potion.ID, UNKNOWN_VAPOR).makeCopy();
            if (vapors.group.stream().anyMatch(c -> c.cardID.equals(vapor.cardID))) {
                continue;
            }
            if (AbstractDungeon.player.hasRelic(SacredBark.ID)) {
                vapor.upgrade();
            }
            if (wideLoad && potion instanceof WidePotion) {
                vapor.widen(true);
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

    public static final Map<String, CardVapor> POTION_TO_VAPOR = Stream.of(new Object[][] {
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
    }).collect(Collectors.toMap(data -> (String)data[0], data -> (CardVapor)data[1]));
    public static final CardVapor UNKNOWN_VAPOR = new CardVaporUnknownPotion();
}
