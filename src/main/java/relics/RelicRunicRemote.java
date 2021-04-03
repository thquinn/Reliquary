package relics;

import actions.AddCardModToHandAction;
import basemod.abstracts.CustomRelic;
import cardmods.CardModRetain;
import cards.colorless.CardVim;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.*;
import com.megacrit.cardcrawl.monsters.city.*;
import com.megacrit.cardcrawl.monsters.ending.SpireShield;
import com.megacrit.cardcrawl.monsters.ending.SpireSpear;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import util.TextureLoader;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RelicRunicRemote extends CustomRelic {
    public static final String ID = "reliquary:RunicRemote";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/runicRemote.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/runicRemote.png");

    public boolean perfect = true;
    Set<String> dropIDs = new HashSet<String>();

    public RelicRunicRemote() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    @Override
    public void atBattleStart() {
        perfect = true;
        dropIDs.clear();
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
            String lookupID = id.endsWith("+") ? id.substring(0, id.length() - 1) : id;
            AbstractCard card = CardLibrary.getCopy(lookupID);
            if (id.endsWith("+")) {
                card.upgrade();
            }
            reward.cards.add(card);
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
            { Deca.ID, new String[]{ CardVim.ID + '+' } },
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
    }).collect(Collectors.toMap(data -> (String)data[0], data -> (String[])data[1]));
}
