package patches;

import actions.*;
import basemod.helpers.CardModifierManager;
import basemod.helpers.SuperclassFinder;
import cardmods.CardModSolitairized;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.unique.TempestAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.*;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import orbs.OrbBias;
import orbs.OrbData;
import powers.*;
import relics.RelicRabbitEars;
import util.ReliquaryLogger;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class PatchRabbitEars {
    static String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(RelicRabbitEars.ID).DESCRIPTIONS;
    static final CardStrings STACK_STRINGS = CardCrawlGame.languagePack.getCardStrings(Stack.ID);

    @SpirePatch(
            clz=AbstractCard.class,
            method="canUpgrade"
    )
    public static class PatchRabbitEarsCanUpgrade {
        public static SpireReturn<Boolean> Prefix(AbstractCard __instance) {
            boolean defectCard = __instance.color == AbstractCard.CardColor.BLUE;
            boolean hasSolitaire = AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(RelicRabbitEars.ID);
            boolean isSolitairized = CardModifierManager.hasModifier(__instance, CardModSolitairized.ID);
            if (defectCard && __instance.timesUpgraded == 1 && (hasSolitaire || isSolitairized)) {
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
            if (__instance.timesUpgraded != 1) {
                return SpireReturn.Continue();
            }
            RelicRabbitEars rabbitEars = AbstractDungeon.player == null ? null : (RelicRabbitEars) AbstractDungeon.player.getRelic(RelicRabbitEars.ID);
            if (rabbitEars == null && !CardModifierManager.hasModifier(__instance, CardModSolitairized.ID)) {
                return SpireReturn.Continue();
            }
            if (rabbitEars == null) {
                rabbitEars = (RelicRabbitEars) RelicLibrary.getRelic(RelicRabbitEars.ID).makeCopy();
            }
            rabbitEars.upgradeCard(__instance);
            return SpireReturn.Return(null);
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
                AbstractDungeon.actionManager.addToBottom(new OrbImpulseAction(Frost.ORB_ID));
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
            clz= Electrodynamics.class,
            method="use"
    )
    public static class PatchRabbitEarsElectrodynamics {
        public static void Postfix(Electrodynamics __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractPlayer p = AbstractDungeon.player;
                AbstractMonster m = AbstractDungeon.getRandomMonster();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerToRandomEnemyAction(p, new LockOnPower(m, 99)));
            }
        }
    }

    @SpirePatch(
            clz= Fusion.class,
            method="use"
    )
    public static class PatchRabbitEarsFusion {
        public static void Prefix(Fusion __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new MoveOrbsLeftAction(Plasma.ORB_ID));
            }
        }
    }

    @SpirePatch(
            clz= GoForTheEyes.class,
            method="use"
    )
    public static class PatchRabbitEarsGoForTheEyes {
        public static void Postfix(GoForTheEyes __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new RabbitEarsForTheEyesAction(1, m));
            }
        }
    }

    @SpirePatch(
            clz= Heatsinks.class,
            method="use"
    )
    public static class PatchRabbitEarsHeatsinks {
        public static void Postfix(Heatsinks __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThermalPastePower(p)));
            }
        }
    }

    @SpirePatch(
            clz= HeatsinkPower.class,
            method="onUseCard"
    )
    public static class PatchRabbitEarsHeatsinksThermalPaste {
        @SpireInsertPatch(
                locator= PatchRabbitEars.PatchRabbitEarsHeatsinksThermalPaste.Locator.class
        )
        public static SpireReturn Insert(HeatsinkPower __instance) {
            if (__instance.owner.hasPower(ThermalPastePower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new PreferentialDrawCardAction(__instance.amount, c -> c.type == AbstractCard.CardType.POWER));
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.NewExprMatcher(DrawCardAction.class);
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= HelloWorld.class,
            method="use"
    )
    public static class PatchRabbitEarsHelloWorld {
        public static SpireReturn Prefix(HelloWorld __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RabbitEarsHelloPower(p, 1)));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Hologram.class,
            method="use"
    )
    public static class PatchRabbitEarsHologram {
        @SpireInsertPatch(
                locator= PatchRabbitEars.PatchRabbitEarsHologram.Locator.class
        )
        public static SpireReturn Insert(Hologram __instance) {
            if (__instance.timesUpgraded == 2) {
                Consumer<AbstractCard> consumer = c -> c.retain = true;
                AbstractDungeon.actionManager.addToBottom(new BetterDiscardPileToHandWithConsumerAction(1, false, consumer));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.NewExprMatcher(BetterDiscardPileToHandAction.class);
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= LockOn.class,
            method="use"
    )
    public static class PatchRabbitEarsLockOn {
        public static void Postfix(LockOn __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                for (AbstractMonster otherM : AbstractDungeon.getMonsters().monsters) {
                    if (otherM != m && !otherM.isDying && !otherM.isDead) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(otherM, p, new LockOnPower(otherM, 1), 1));
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz= Melter.class,
            method="use"
    )
    public static class PatchRabbitEarsMelter {
        public static SpireReturn Prefix(Melter __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new StealAllBlockAction(m, p));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, __instance.damage, __instance.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= MeteorStrike.class,
            method="use"
    )
    public static class PatchRabbitEarsMeteorStrike {
        public static void Postfix(MeteorStrike __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ChannelUntilFullAction(new Plasma()));
            }
        }
    }

    @SpirePatch(
            clz= MultiCast.class,
            method="use"
    )
    public static class PatchRabbitEarsMultiCast {
        public static SpireReturn Prefix(MultiCast __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new VariableMulticastAction(2, __instance.energyOnUse, __instance.freeToPlayOnce));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
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

    @SpirePatch(
            clz= Rebound.class,
            method="use"
    )
    public static class PatchRabbitEarsRebound {
        @SpireInsertPatch(
                locator= PatchRabbitEars.PatchRabbitEarsRebound.Locator.class
        )
        public static SpireReturn Insert(Rebound __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RabbitEarsReboundPower(p, 1)));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.NewExprMatcher(ReboundPower.class);
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
    @SpirePatch(
            clz= UseCardAction.class,
            method="update"
    )
    public static class PatchRabbitEarsReboundReturnToHandProper {
        @SpireInsertPatch(
                locator= PatchRabbitEars.PatchRabbitEarsReboundReturnToHandProper.Locator.class
        )
        public static SpireReturn Insert(UseCardAction __instance, AbstractCard ___targetCard) {
            if (__instance.returnToHand) {
                // For some godforsaken reason, only the card's returnToHand is checked, not the UseCardAction's.
                AbstractDungeon.player.hand.moveToHand(___targetCard);
                AbstractDungeon.player.onCardDrawOrDiscard();
                ___targetCard.exhaustOnUseOnce = false;
                ___targetCard.dontTriggerOnUseCard = false;
                AbstractDungeon.actionManager.addToBottom(new HandCheckAction());
                __instance.isDone = true;
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(UseCardAction.class, "reboundCard");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= Recursion.class,
            method="use"
    )
    public static class PatchRabbitEarsRecursion {
        public static SpireReturn Prefix(Recursion __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new RabbitEarsRedoAction());
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Recycle.class,
            method="use"
    )
    public static class PatchRabbitEarsRecycle {
        public static SpireReturn Prefix(Recycle __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new RabbitEarsRecycleAction(__instance.magicNumber));
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Stack.class,
            method="use"
    )
    public static class PatchRabbitEarsStack {
        public static SpireReturn Prefix(Stack __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, __instance.block));
                __instance.rawDescription = DESCRIPTIONS[RelicRabbitEars.DESC_INDEX_STACK];
                __instance.initializeDescription();
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(
            clz= Stack.class,
            method="applyPowers"
    )
    public static class PatchRabbitEarsStackApplyPowers {
        public static SpireReturn Prefix(Stack __instance) {
            if (__instance.timesUpgraded == 2) {
                __instance.baseBlock = AbstractDungeon.player.discardPile.size() * 2;
                try {
                    Method superApplyPowers = SuperclassFinder.getSuperClassMethod(__instance.getClass(), "applyPowersToBlock");
                    superApplyPowers.setAccessible(true);
                    superApplyPowers.invoke(__instance);
                } catch (Exception e) {
                    ReliquaryLogger.error("PatchRabbitEarsStackApplyPowers failed to call applyPowersToBlock: " + e);
                }
                __instance.rawDescription = DESCRIPTIONS[RelicRabbitEars.DESC_INDEX_STACK] + STACK_STRINGS.EXTENDED_DESCRIPTION[0];
                __instance.initializeDescription();
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Storm.class,
            method="use"
    )
    public static class PatchRabbitEarsStorm {
        public static SpireReturn Prefix(Storm __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RabbitEarsStormPower(p, 1)));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Sunder.class,
            method="use"
    )
    public static class PatchRabbitEarsSunder {
        @SpireInsertPatch(
                locator= PatchRabbitEars.PatchRabbitEarsSunder.Locator.class
        )
        public static SpireReturn Insert(Sunder __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new SunderAction(m, new DamageInfo(p, __instance.damage, __instance.damageTypeForTurn), 4));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.NewExprMatcher(SunderAction.class);
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= SweepingBeam.class,
            method="use"
    )
    public static class PatchRabbitEarsSweepingBeam {
        @SpireInsertPatch(
                locator= PatchRabbitEars.PatchRabbitEarsSweepingBeam.Locator.class
        )
        public static SpireReturn Insert(SweepingBeam __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new RabbitEarsSweepingBeamAction(p, __instance.multiDamage, __instance.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, __instance.magicNumber));
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.NewExprMatcher(DamageAllEnemiesAction.class);
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= Tempest.class,
            method="use"
    )
    public static class PatchRabbitEarsTempest {
        public static SpireReturn Prefix(Tempest __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new TempestAction(p, __instance.energyOnUse + 2, false, __instance.freeToPlayOnce));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= WhiteNoise.class,
            method="use"
    )
    public static class PatchRabbitEarsWhiteNoise {
        public static SpireReturn Prefix(WhiteNoise __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER).makeCopy();
                c.upgrade();
                c.upgrade();
                c.setCostForTurn(0);
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Zap.class,
            method="use"
    )
    public static class PatchRabbitEarsZap {
        public static void Postfix(Zap __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HalfLightningPower(p, 1)));
            }
        }
    }
}