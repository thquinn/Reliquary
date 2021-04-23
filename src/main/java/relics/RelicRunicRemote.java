package relics;

import basemod.abstracts.CustomRelic;
import cards.colorless.CardVim;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.*;
import com.megacrit.cardcrawl.monsters.city.*;
import com.megacrit.cardcrawl.monsters.ending.SpireShield;
import com.megacrit.cardcrawl.monsters.ending.SpireSpear;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import util.ReliquaryLogger;
import util.TextureLoader;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RelicRunicRemote extends CustomRelic {
    public static final String ID = "reliquary:RunicRemote";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/runicRemote.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/runicRemote.png");

    public boolean perfect = true;
    Set<String> dropIDs = new HashSet<>();
    static String RANDOM_COLORLESS = "random_colorless";

    public RelicRunicRemote() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public boolean canSpawn() {
        // Only spawn in the first two acts, and not in custom acts since we don't have cards for the monsters there.
        return Settings.isEndless || AbstractDungeon.id.equals(Exordium.ID) || AbstractDungeon.id.equals(TheCity.ID);
    }

    @Override
    public void atBattleStart() {
        ReliquaryLogger.log(AbstractDungeon.id);
        perfect = true;
        dropIDs.clear();
        Optional<AbstractMonster> slimeBoss = AbstractDungeon.getMonsters().monsters.stream().filter(m -> m.id.equals(SlimeBoss.ID)).findAny();
        if (slimeBoss.isPresent()) {
            onMonsterDeath(slimeBoss.get());
        }
        beginLongPulse();
    }
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > AbstractDungeon.player.currentBlock) {
            perfect = false;
            stopPulse();
        }
        return damageAmount;
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        String[] cardIDs = ENEMY_TO_CARDS.get(m.id);
        if (cardIDs == null) {
            dropIDs.add(RANDOM_COLORLESS);
            return;
        }
        for (String id : cardIDs) {
            String lookupID = id.endsWith("+") ? id.substring(0, id.length() - 1) : id;
            AbstractCard card = CardLibrary.getCopy(lookupID);
            if (card != null) {
                dropIDs.add(id);
                return;
            }
        }
    }

    @Override
    public void onVictory() {
        stopPulse();
        if (!perfect || dropIDs.isEmpty()) {
            return;
        }
        flash();
        RewardItem reward = new RewardItem();
        reward.relic = this;
        reward.text = DESCRIPTIONS[1];
        reward.cards.clear();
        for (String id : dropIDs) {
            if (id.equals(RANDOM_COLORLESS)) {
                // The player has killed an unknown enemy.
                float rareChance, upgradeChance;
                if (AbstractDungeon.actNum == 1) {
                    rareChance = .1f;
                    upgradeChance = .1f;
                } else if (AbstractDungeon.actNum == 2) {
                    rareChance = .2f;
                    upgradeChance = .2f;
                } else if (AbstractDungeon.actNum == 3) {
                    rareChance = .3f;
                    upgradeChance = .3f;
                } else {
                    rareChance = .4f;
                    upgradeChance = .4f;
                }
                AbstractCard card;
                float selector = MathUtils.random();
                if (selector < rareChance) {
                    card = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE);
                } else {
                    card = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.UNCOMMON);
                }
                card = card.makeCopy();
                if (MathUtils.random() < upgradeChance) {
                    card.upgrade();
                }
                reward.cards.add(card);
            } else {
                String lookupID = id.endsWith("+") ? id.substring(0, id.length() - 1) : id;
                AbstractCard card = CardLibrary.getCopy(lookupID);
                if (id.endsWith("+")) {
                    card.upgrade();
                }
                reward.cards.add(card);
            }
        }
        AbstractDungeon.getCurrRoom().addCardReward(reward);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicRunicRemote();
    }

    public static final Map<String, String[]> ENEMY_TO_CARDS = Stream.of(new Object[][]{
            // Exordium
            { AcidSlime_L.ID, new String[]{ DeadlyPoison.ID } },
            { AcidSlime_M.ID, new String[]{ DeadlyPoison.ID } },
            { AcidSlime_S.ID, new String[]{ DeadlyPoison.ID } },
            { Cultist.ID, new String[]{ Flex.ID } },
            { FungiBeast.ID, new String[]{ BeamCell.ID } },
            { GremlinFat.ID, new String[]{ Clothesline.ID } },
            { GremlinNob.ID, new String[]{ Crescendo.ID + '+' } }, // append a '+' to card IDs to specify the upgraded version
            { GremlinThief.ID, new String[]{ Slice.ID } },
            { GremlinTsundere.ID, new String[]{ SteamBarrier.ID } },
            { GremlinWarrior.ID, new String[]{ Eruption.ID } },
            { GremlinWizard.ID, new String[]{ HeavyBlade.ID } },
            { Hexaghost.ID, new String[]{ Ragnarok.ID } },
            { JawWorm.ID, new String[]{ IronWave.ID } },
            { Lagavulin.ID, new String[]{ WellLaidPlans.ID } },
            { Looter.ID, new String[]{ HandOfGreed.ID } },
            { LouseDefensive.ID, new String[]{ SashWhip.ID } },
            { LouseNormal.ID, new String[]{ Inflame.ID } },
            { Sentry.ID, new String[]{ CoreSurge.ID + '+' } },
            { SlaverBlue.ID, new String[]{ LegSweep.ID } },
            { SlaverRed.ID, new String[]{ Setup.ID } },
            { SlimeBoss.ID, new String[]{ Bludgeon.ID } },
            { SpikeSlime_L.ID, new String[]{ Melter.ID } },
            { SpikeSlime_M.ID, new String[]{ Melter.ID } },
            { SpikeSlime_S.ID, new String[]{ Melter.ID } },
            { TheGuardian.ID, new String[]{ Buffer.ID } },
            // City
            { BanditBear.ID, new String[]{ Footwork.ID + '+' } },
            { BanditLeader.ID, new String[]{ Neutralize.ID + '+' } },
            { BanditPointy.ID, new String[]{ RiddleWithHoles.ID + '+' } },
            { BookOfStabbing.ID, new String[]{ Whirlwind.ID + '+' } },
            { BronzeAutomaton.ID, new String[]{ Hyperbeam.ID } },
            { BronzeOrb.ID, new String[]{ Omniscience.ID } },
            { Byrd.ID, new String[]{ ReinforcedBody.ID } },
            { Centurion.ID, new String[]{ Sentinel.ID } },
            { Champ.ID, new String[]{ DemonForm.ID } },
            { Chosen.ID, new String[]{ Foresight.ID } },
            { GremlinLeader.ID, new String[]{ LimitBreak.ID } },
            { Healer.ID, new String[]{ BandageUp.ID } },
            { Mugger.ID, new String[]{ HandOfGreed.ID + '+' } },
            { ShelledParasite.ID, new String[]{ Metallicize.ID } },
            { SnakePlant.ID, new String[]{ TalkToTheHand.ID } },
            { Snecko.ID, new String[]{ BouncingFlask.ID + '+' } },
            { SphericGuardian.ID, new String[]{ Barricade.ID } },
            { Taskmaster.ID, new String[]{ Evolve.ID + '+' } },
            { TheCollector.ID, new String[]{ Malaise.ID } },
            { TorchHead.ID, new String[]{ Immolate.ID } },
            // Beyond
            { AwakenedOne.ID, new String[]{ CreativeAI.ID + '+' } },
            { Darkling.ID, new String[]{ Reaper.ID } },
            { Deca.ID, new String[]{ CardVim.ID } },
            { Donu.ID, new String[]{ Wish.ID + '+' } },
            { Exploder.ID, new String[]{ Hemokinesis.ID } },
            { GiantHead.ID, new String[]{ BulletTime.ID } },
            { Maw.ID, new String[]{ Amplify.ID } },
            { Nemesis.ID, new String[]{ WraithForm.ID } },
            { OrbWalker.ID, new String[]{ FiendFire.ID } },
            { Reptomancer.ID, new String[]{ ToolsOfTheTrade.ID + '+' } },
            { Repulsor.ID, new String[]{ WhiteNoise.ID } },
            { SnakeDagger.ID, new String[]{ Envenom.ID + '+' } },
            { Spiker.ID, new String[]{ Caltrops.ID } },
            { SpireGrowth.ID, new String[]{ Choke.ID + '+' } },
            { TimeEater.ID, new String[]{ Vault.ID + '+' } },
            { Transient.ID, new String[]{ Nightmare.ID } },
            { WrithingMass.ID, new String[]{ EchoForm.ID } },
            // Ending
            { SpireShield.ID, new String[]{ Impervious.ID + '+' } },
            { SpireSpear.ID, new String[]{ PhantasmalKiller.ID + '+' } },
            // Gensokyo Act 1
            { "Gensokyo:Aya", new String[]{ AfterImage.ID } },
            { "Gensokyo:Cirno", new String[]{ Chill.ID + '+' } },
            { "Gensokyo:CorruptedTreant", new String[]{ PoisonedStab.ID } },
            { "Gensokyo:EarthOrb", new String[]{ } },
            { "Gensokyo:FireOrb", new String[]{ } },
            { "Gensokyo:MetalOrb", new String[]{ } },
            { "Gensokyo:GreaterFairy", new String[]{ Flex.ID } },
            { "Gensokyo:GreyKodama", new String[]{ CloakAndDagger.ID } },
            { "Gensokyo:Gryphon", new String[]{ GoForTheEyes.ID } },
            { "Gensokyo:Kitsune", new String[]{ TrueGrit.ID } },
            { "Gensokyo:Kokoro", new String[]{ Mayhem.ID } },
            { "Gensokyo:Komachi", new String[]{ Reaper.ID } },
            { "Gensokyo:LivingMonolith", new String[]{ ConserveBattery.ID } },
            { "Gensokyo:MaidFairy", new String[]{ Deflect.ID } },
            { "Gensokyo:Mamizou", new String[]{ "Gensokyo:MenInBlack" } },
            { "Gensokyo:MoonRabbit", new String[]{ "Gensokyo:BrilliantDragonBullet" } },
            { "Gensokyo:Patchouli", new String[]{ Rainbow.ID + '+' } },
            { "Gensokyo:Python", new String[]{ DeadlyPoison.ID } },
            { "Gensokyo:RedKodama", new String[]{ Overclock.ID } },
            { "Gensokyo:Reimu", new String[]{ "Gensokyo:GapWoman" } },
            { "Gensokyo:Sumireko", new String[]{ "Gensokyo:Doppelganger+" } },
            { "Gensokyo:SunflowerFairy", new String[]{ ThunderClap.ID } },
            { "Gensokyo:VengefulSpirit", new String[]{ FireBreathing.ID } },
            { "Gensokyo:WaterOrb", new String[]{ } },
            { "Gensokyo:WhiteKodama", new String[]{ ShrugItOff.ID } },
            { "Gensokyo:WoodOrb", new String[]{ } },
            { "Gensokyo:YellowKodama", new String[]{ BallLightning.ID } },
            { "Gensokyo:YinYangOrb", new String[]{ } },
            { "Gensokyo:Yukari", new String[]{ "Gensokyo:TekeTeke" } },
            { "Gensokyo:ZombieFairy", new String[]{ SuckerPunch.ID } },
            // Gensokyo Act 2
            { "Gensokyo:AngelMirror", new String[]{ FlameBarrier.ID } },
            { "Gensokyo:BigMudSlime", new String[]{ Rampage.ID } },
            { "Gensokyo:Byakuren", new String[]{ "Gensokyo:TurboGranny" } },
            { "Gensokyo:Chomper", new String[]{ Choke.ID } },
            { "Gensokyo:CosmicMonolith", new String[]{ Meditate.ID } },
            { "Gensokyo:Eiki", new String[]{ Judgement.ID } },
            { "Gensokyo:Gloop", new String[]{ Shockwave.ID } },
            { "Gensokyo:Kaguya", new String[]{ "Gensokyo:RisingWorld" } },
            { "Gensokyo:Koishi", new String[]{ "Gensokyo:MissMary" } },
            { "Gensokyo:Kune", new String[]{ "Gensokyo:SalamanderShield" } },
            { "Gensokyo:Miko", new String[]{ "Gensokyo:RedCapeBlueCape" } },
            { "Gensokyo:Mirror", new String[]{ Hologram.ID + '+' } },
            { "Gensokyo:Reisen", new String[]{ "Gensokyo:Kunekune" } },
            { "Gensokyo:SlimeBunny", new String[]{ Panacea.ID } },
            { "Gensokyo:Swordslinger", new String[]{ Tantrum.ID } },
            { "Gensokyo:TanukiDog", new String[]{ Pummel.ID + '+' } },
            { "Gensokyo:Tenshi", new String[]{ "Gensokyo:HAARP" } },
            { "Gensokyo:Wraith", new String[]{ Apparition.ID } },
            // Gensokyo Act 3
            { "Gensokyo:Alice", new String[]{ ForeignInfluence.ID + '+' } },
            { "Gensokyo:AncientGuardian", new String[]{ Entrench.ID + '+' } },
            { "Gensokyo:AtlasGolem", new String[]{ Wallop.ID + '+' } },
            { "Gensokyo:BlueSoul", new String[]{ Tranquility.ID + '+' } },
            { "Gensokyo:Doll", new String[]{ } },
            { "Gensokyo:Doremy", new String[]{ Nightmare.ID } },
            { "Gensokyo:Duskaxe", new String[]{ Inflame.ID } },
            { "Gensokyo:FeralBat", new String[]{ NoxiousFumes.ID } },
            { "Gensokyo:Flandre", new String[]{ "Gensokyo:FourOfAKind+" } },
            { "Gensokyo:Kasen", new String[]{ "Gensokyo:MonkeysPaw" } },
            { "Gensokyo:Kume", new String[]{ BowlingBash.ID } },
            { "Gensokyo:LoudBat", new String[]{ PiercingWail.ID } },
            { "Gensokyo:MadBoulder", new String[]{ Perseverance.ID } },
            { "Gensokyo:Marisa", new String[]{ "Gensokyo:SevenSchoolMysteries" } },
            { "Gensokyo:Mokou", new String[]{ "Gensokyo:SpontaneousHumanCombustion" } },
            { "Gensokyo:PurpleSoul", new String[]{ Crescendo.ID + '+' } },
            { "Gensokyo:Rafflesia", new String[]{ CripplingPoison.ID + '+' } },
            { "Gensokyo:Remilia", new String[]{ } },
            { "Gensokyo:Sariel", new String[]{ Worship.ID + '+' } },
            { "Gensokyo:SeedOfUnknown", new String[]{ WellLaidPlans.ID } },
            { "Gensokyo:Sharpion", new String[]{ Evolve.ID } },
            { "Gensokyo:Shinki", new String[]{ DemonForm.ID + '+' } },
            { "Gensokyo:VampireBat", new String[]{ Bite.ID } },
            { "Gensokyo:Yumeko", new String[]{ StaticDischarge.ID + '+' } },
            { "Gensokyo:Yuyuko", new String[]{ WraithForm.ID + '+' } },
    }).collect(Collectors.toMap(data -> (String)data[0], data -> (String[])data[1]));
}
