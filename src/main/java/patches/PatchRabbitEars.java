package patches;

import actions.FrostImpulseAction;
import actions.RabbitEarsAggregateAction;
import actions.RabbitEarsBarrageAction;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.DarkImpulseAction;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.BiasPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import orbs.OrbBias;
import orbs.OrbData;
import powers.MrofOhcePower;
import powers.RabbitEarsCreativeAIPower;
import relics.RelicRabbitEars;

public class PatchRabbitEars {
    static String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(RelicRabbitEars.ID).DESCRIPTIONS;

    @SpirePatch(
            clz=AbstractCard.class,
            method="canUpgrade"
    )
    public static class PatchRabbitEarsCanUpgrade {
        public static SpireReturn<Boolean> Prefix(AbstractCard __instance) {
            boolean defectCard = __instance.color == AbstractCard.CardColor.BLUE;
            if (defectCard && __instance.timesUpgraded < 2 && AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(RelicRabbitEars.ID)) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz= Aggregate.class, method="upgrade")
    @SpirePatch(clz= AllForOne.class, method="upgrade")
    @SpirePatch(clz= Amplify.class, method="upgrade")
    @SpirePatch(clz= AutoShields.class, method="upgrade")
    @SpirePatch(clz= BallLightning.class, method="upgrade")
    @SpirePatch(clz= Barrage.class, method="upgrade")
    @SpirePatch(clz= BeamCell.class, method="upgrade")
    @SpirePatch(clz= BiasedCognition.class, method="upgrade")
    @SpirePatch(clz= Blizzard.class, method="upgrade")
    @SpirePatch(clz= BootSequence.class, method="upgrade")
    @SpirePatch(clz= Buffer.class, method="upgrade")
    @SpirePatch(clz= Capacitor.class, method="upgrade")
    @SpirePatch(clz= Chaos.class, method="upgrade")
    @SpirePatch(clz= Chill.class, method="upgrade")
    @SpirePatch(clz= Claw.class, method="upgrade")
    @SpirePatch(clz= ColdSnap.class, method="upgrade")
    @SpirePatch(clz= CompileDriver.class, method="upgrade")
    @SpirePatch(clz= ConserveBattery.class, method="upgrade")
    @SpirePatch(clz= Consume.class, method="upgrade")
    @SpirePatch(clz= Coolheaded.class, method="upgrade")
    @SpirePatch(clz= CoreSurge.class, method="upgrade")
    @SpirePatch(clz= CreativeAI.class, method="upgrade")
    @SpirePatch(clz= Darkness.class, method="upgrade")
    @SpirePatch(clz= Defend_Blue.class, method="upgrade")
    @SpirePatch(clz= Defragment.class, method="upgrade")
    @SpirePatch(clz= DoomAndGloom.class, method="upgrade")
    @SpirePatch(clz= DoubleEnergy.class, method="upgrade")
    @SpirePatch(clz= Dualcast.class, method="upgrade")
    @SpirePatch(clz= EchoForm.class, method="upgrade")
    @SpirePatch(clz= Electrodynamics.class, method="upgrade")
    @SpirePatch(clz= Equilibrium.class, method="upgrade")
    @SpirePatch(clz= Fission.class, method="upgrade")
    @SpirePatch(clz= ForceField.class, method="upgrade")
    @SpirePatch(clz= FTL.class, method="upgrade")
    @SpirePatch(clz= Fusion.class, method="upgrade")
    @SpirePatch(clz= GeneticAlgorithm.class, method="upgrade")
    @SpirePatch(clz= Glacier.class, method="upgrade")
    @SpirePatch(clz= GoForTheEyes.class, method="upgrade")
    @SpirePatch(clz= Heatsinks.class, method="upgrade")
    @SpirePatch(clz= HelloWorld.class, method="upgrade")
    @SpirePatch(clz= Hologram.class, method="upgrade")
    @SpirePatch(clz= Hyperbeam.class, method="upgrade")
    @SpirePatch(clz= Leap.class, method="upgrade")
    @SpirePatch(clz= LockOn.class, method="upgrade")
    @SpirePatch(clz= Loop.class, method="upgrade")
    @SpirePatch(clz= MachineLearning.class, method="upgrade")
    @SpirePatch(clz= Melter.class, method="upgrade")
    @SpirePatch(clz= MeteorStrike.class, method="upgrade")
    @SpirePatch(clz= MultiCast.class, method="upgrade")
    @SpirePatch(clz= Overclock.class, method="upgrade")
    @SpirePatch(clz= Rainbow.class, method="upgrade")
    @SpirePatch(clz= Reboot.class, method="upgrade")
    @SpirePatch(clz= Rebound.class, method="upgrade")
    @SpirePatch(clz= Recursion.class, method="upgrade")
    @SpirePatch(clz= Recycle.class, method="upgrade")
    @SpirePatch(clz= ReinforcedBody.class, method="upgrade")
    @SpirePatch(clz= Reprogram.class, method="upgrade")
    @SpirePatch(clz= RipAndTear.class, method="upgrade")
    @SpirePatch(clz= Scrape.class, method="upgrade")
    @SpirePatch(clz= Seek.class, method="upgrade")
    @SpirePatch(clz= SelfRepair.class, method="upgrade")
    @SpirePatch(clz= Skim.class, method="upgrade")
    @SpirePatch(clz= Stack.class, method="upgrade")
    @SpirePatch(clz= StaticDischarge.class, method="upgrade")
    @SpirePatch(clz= SteamBarrier.class, method="upgrade")
    @SpirePatch(clz= Storm.class, method="upgrade")
    @SpirePatch(clz= Streamline.class, method="upgrade")
    @SpirePatch(clz= Strike_Blue.class, method="upgrade")
    @SpirePatch(clz= Sunder.class, method="upgrade")
    @SpirePatch(clz= SweepingBeam.class, method="upgrade")
    @SpirePatch(clz= Tempest.class, method="upgrade")
    @SpirePatch(clz= ThunderStrike.class, method="upgrade")
    @SpirePatch(clz= Turbo.class, method="upgrade")
    @SpirePatch(clz= WhiteNoise.class, method="upgrade")
    @SpirePatch(clz= Zap.class, method="upgrade")
    public static class PatchRabbitEarsUpgrade {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player == null) {
                return SpireReturn.Continue();
            }
            RelicRabbitEars ears = (RelicRabbitEars) AbstractDungeon.player.getRelic(RelicRabbitEars.ID);
            if (__instance.timesUpgraded == 1 && ears != null) {
                ears.upgradeCard(__instance);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Aggregate.class,
            method="use"
    )
    public static class PatchRabbitEarsAggregate {
        public static SpireReturn Prefix(Aggregate __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new RabbitEarsAggregateAction(__instance.magicNumber));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Barrage.class,
            method="use"
    )
    public static class PatchRabbitEarsBarrage {
        public static SpireReturn Prefix(Barrage __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new RabbitEarsBarrageAction(m, new DamageInfo(p, __instance.damage, DamageInfo.DamageType.NORMAL), new DamageInfo(p, __instance.magicNumber, DamageInfo.DamageType.NORMAL)));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= BiasedCognition.class,
            method="use"
    )
    public static class PatchRabbitEarsBiasedCognition {
        @SpireInsertPatch(
                locator= PatchRabbitEars.PatchRabbitEarsBiasedCognition.Locator.class
        )
        public static SpireReturn Insert(BiasedCognition __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new OrbBias()));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.NewExprMatcher(BiasPower.class);
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= Chill.class,
            method="use"
    )
    public static class PatchRabbitEarsChill {
        public static void Postfix(Chill __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Frost()));
            }
        }
    }

    @SpirePatch(
            clz= ColdSnap.class,
            method="use"
    )
    public static class PatchRabbitEarsColdSnap {
        public static void Postfix(ColdSnap __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new FrostImpulseAction());
            }
        }
    }

    @SpirePatch(
            clz= ConserveBattery.class,
            method="use"
    )
    public static class PatchRabbitEarsConserveBattery {
        public static void Postfix(ConserveBattery __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, 1)));
            }
        }
    }

    @SpirePatch(
            clz= CreativeAI.class,
            method="use"
    )
    public static class PatchRabbitEarsCreativeAI {
        public static SpireReturn Prefix(CreativeAI __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RabbitEarsCreativeAIPower(p, 1)));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Darkness.class,
            method="use"
    )
    public static class PatchRabbitEarsDarkness {
        public static void Postfix(Darkness __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new DarkImpulseAction());
            }
        }
    }

    @SpirePatch(
            clz= DoomAndGloom.class,
            method="use"
    )
    public static class PatchRabbitEarsDoomAndGloom {
        public static void Postfix(DoomAndGloom __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Dark()));
            }
        }
    }

    @SpirePatch(
            clz= Dualcast.class,
            method="use"
    )
    public static class PatchRabbitEarsDualcast {
        public static void Prefix(Dualcast __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new AnimateOrbAction(1));
                AbstractDungeon.actionManager.addToBottom(new EvokeWithoutRemovingOrbAction(1));
            }
        }
    }

    @SpirePatch(
            clz= EchoForm.class,
            method="use"
    )
    public static class PatchRabbitEarsEchoForm {
        public static void Postfix(EchoForm __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MrofOhcePower(p, 1)));
            }
        }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="updateInput"
    )
    public static class PatchRabbitEarsEchoFormDelayEndTurn {
        @SpireInsertPatch(
                locator= PatchRabbitEars.PatchRabbitEarsEchoFormDelayEndTurn.Locator.class
        )
        public static SpireReturn Insert(AbstractPlayer __instance) {
            MrofOhcePower mrof = (MrofOhcePower) AbstractDungeon.player.getPower(MrofOhcePower.POWER_ID);
            if (mrof == null) {
                return SpireReturn.Continue();
            }
            if (!AbstractDungeon.actionManager.actions.isEmpty()) {
                return SpireReturn.Return();
            }
            return mrof.beforeEndOfTurn() ? SpireReturn.Return() : SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "endTurnQueued");
                int[] matches = LineFinder.findAllInOrder(ctMethodToPatch, matcher);
                return new int[]{matches[matches.length - 1]};
            }
        }
    }

    @SpirePatch(
            clz= GameActionManager.class,
            method="getNextAction"
    )
    public static class PatchRabbitEarsEchoFormTrackTargets {
        @SpireInsertPatch(
                locator= PatchRabbitEars.PatchRabbitEarsEchoFormTrackTargets.Locator.class
        )
        public static void Insert(GameActionManager __instance) {
            MrofOhcePower.cardsPlayedThisTurnTargets.add(AbstractDungeon.actionManager.cardQueue.get(0).monster);
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(GameActionManager.class, "cardsPlayedThisTurn");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= GameActionManager.class,
            method="getNextAction"
    )
    public static class PatchRabbitEarsEchoFormClearTargets {
        @SpireInsertPatch(
                locator= PatchRabbitEars.PatchRabbitEarsEchoFormClearTargets.Locator.class
        )
        public static void Insert(GameActionManager __instance) {
            MrofOhcePower.cardsPlayedThisTurnTargets.clear();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(GameActionManager.class, "orbsChanneledThisTurn");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
    @SpirePatch(
            clz= GameActionManager.class,
            method="clear"
    )
    public static class PatchRabbitEarsEchoFormClearTargets2 {
        @SpireInsertPatch(
                locator= PatchRabbitEars.PatchRabbitEarsEchoFormClearTargets.Locator.class
        )
        public static void Insert(GameActionManager __instance) {
            MrofOhcePower.cardsPlayedThisTurnTargets.clear();
        }
    }

    @SpirePatch(
            clz= Rainbow.class,
            method="use"
    )
    public static class PatchRabbitEarsRainbow {
        public static void Prefix(Rainbow __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new OrbData()));
            }
        }
    }
}