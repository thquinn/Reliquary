package patches;

import actions.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import powers.*;
import relics.RelicSkeletonKey;

public class PatchSkeletonKey {
    static String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(RelicSkeletonKey.ID).DESCRIPTIONS;

    @SpirePatch(
            clz=AbstractCard.class,
            method="canUpgrade"
    )
    public static class PatchSkeletonKeyCanUpgrade {
        public static SpireReturn<Boolean> Prefix(AbstractCard __instance) {
            boolean silentCard = __instance.color == AbstractCard.CardColor.GREEN;
            if (silentCard && __instance.timesUpgraded < 2 && AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(RelicSkeletonKey.ID)) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz=AThousandCuts.class, method="upgrade")
    @SpirePatch(clz=Accuracy.class, method="upgrade")
    @SpirePatch(clz=Acrobatics.class, method="upgrade")
    @SpirePatch(clz=Adrenaline.class, method="upgrade")
    @SpirePatch(clz=AfterImage.class, method="upgrade")
    @SpirePatch(clz=Alchemize.class, method="upgrade")
    @SpirePatch(clz=AllOutAttack.class, method="upgrade")
    @SpirePatch(clz=Backflip.class, method="upgrade")
    @SpirePatch(clz=Backstab.class, method="upgrade")
    @SpirePatch(clz=Bane.class, method="upgrade")
    @SpirePatch(clz=BladeDance.class, method="upgrade")
    @SpirePatch(clz=Blur.class, method="upgrade")
    @SpirePatch(clz=BouncingFlask.class, method="upgrade")
    @SpirePatch(clz=BulletTime.class, method="upgrade")
    @SpirePatch(clz=Burst.class, method="upgrade")
    @SpirePatch(clz=CalculatedGamble.class, method="upgrade")
    @SpirePatch(clz=Caltrops.class, method="upgrade")
    @SpirePatch(clz=Catalyst.class, method="upgrade")
    @SpirePatch(clz=Choke.class, method="upgrade")
    @SpirePatch(clz=CloakAndDagger.class, method="upgrade")
    @SpirePatch(clz=Concentrate.class, method="upgrade")
    @SpirePatch(clz=CorpseExplosion.class, method="upgrade")
    @SpirePatch(clz=CripplingPoison.class, method="upgrade")
    @SpirePatch(clz=DaggerSpray.class, method="upgrade")
    @SpirePatch(clz=DaggerThrow.class, method="upgrade")
    @SpirePatch(clz=Dash.class, method="upgrade")
    @SpirePatch(clz=DeadlyPoison.class, method="upgrade")
    @SpirePatch(clz=Defend_Green.class, method="upgrade")
    @SpirePatch(clz=Deflect.class, method="upgrade")
    @SpirePatch(clz=DieDieDie.class, method="upgrade")
    @SpirePatch(clz=Distraction.class, method="upgrade")
    @SpirePatch(clz=DodgeAndRoll.class, method="upgrade")
    @SpirePatch(clz=Doppelganger.class, method="upgrade")
    @SpirePatch(clz=EndlessAgony.class, method="upgrade")
    @SpirePatch(clz=Envenom.class, method="upgrade")
    @SpirePatch(clz=EscapePlan.class, method="upgrade")
    @SpirePatch(clz=Eviscerate.class, method="upgrade")
    @SpirePatch(clz=Expertise.class, method="upgrade")
    @SpirePatch(clz=Finisher.class, method="upgrade")
    @SpirePatch(clz=Flechettes.class, method="upgrade")
    @SpirePatch(clz=FlyingKnee.class, method="upgrade")
    @SpirePatch(clz=Footwork.class, method="upgrade")
    @SpirePatch(clz=GlassKnife.class, method="upgrade")
    @SpirePatch(clz=GrandFinale.class, method="upgrade")
    @SpirePatch(clz=HeelHook.class, method="upgrade")
    @SpirePatch(clz=InfiniteBlades.class, method="upgrade")
    @SpirePatch(clz=LegSweep.class, method="upgrade")
    @SpirePatch(clz=Malaise.class, method="upgrade")
    @SpirePatch(clz=MasterfulStab.class, method="upgrade")
    @SpirePatch(clz=Neutralize.class, method="upgrade")
    @SpirePatch(clz=Nightmare.class, method="upgrade")
    @SpirePatch(clz=NoxiousFumes.class, method="upgrade")
    @SpirePatch(clz=Outmaneuver.class, method="upgrade")
    @SpirePatch(clz=PhantasmalKiller.class, method="upgrade")
    @SpirePatch(clz=PiercingWail.class, method="upgrade")
    @SpirePatch(clz=PoisonedStab.class, method="upgrade")
    @SpirePatch(clz=Predator.class, method="upgrade")
    @SpirePatch(clz=Prepared.class, method="upgrade")
    @SpirePatch(clz=QuickSlash.class, method="upgrade")
    @SpirePatch(clz=Reflex.class, method="upgrade")
    @SpirePatch(clz=RiddleWithHoles.class, method="upgrade")
    @SpirePatch(clz=Setup.class, method="upgrade")
    @SpirePatch(clz=Shiv.class, method="upgrade")
    @SpirePatch(clz=Skewer.class, method="upgrade")
    @SpirePatch(clz=Slice.class, method="upgrade")
    @SpirePatch(clz=SneakyStrike.class, method="upgrade")
    @SpirePatch(clz=StormOfSteel.class, method="upgrade")
    @SpirePatch(clz=Strike_Green.class, method="upgrade")
    @SpirePatch(clz=SuckerPunch.class, method="upgrade")
    @SpirePatch(clz=Survivor.class, method="upgrade")
    @SpirePatch(clz=Tactician.class, method="upgrade")
    @SpirePatch(clz=Terror.class, method="upgrade")
    @SpirePatch(clz=ToolsOfTheTrade.class, method="upgrade")
    @SpirePatch(clz=Unload.class, method="upgrade")
    @SpirePatch(clz=WellLaidPlans.class, method="upgrade")
    @SpirePatch(clz=WraithForm.class, method="upgrade")
    public static class PatchSkeletonKeyUpgrade {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player == null) {
                return SpireReturn.Continue();
            }
            RelicSkeletonKey key = (RelicSkeletonKey) AbstractDungeon.player.getRelic(RelicSkeletonKey.ID);
            if (__instance.timesUpgraded == 1 && key != null) {
                key.upgradeCard(__instance);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= AThousandCuts.class,
            method="use"
    )
    public static class PatchSkeletonKeyAThousandCuts {
        public static void Postfix(AThousandCuts __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CutAbovePower(p, 1), 1));
            }
        }
    }
    @SpirePatch(
            clz= ThousandCutsPower.class,
            method="onAfterCardPlayed"
    )
    public static class PatchSkeletonKeyAThousandCutsPower {
        public static boolean TRIGGER = true;
        public static void Postfix(ThousandCutsPower __instance, AbstractCard card) {
            AbstractPower power = AbstractDungeon.player.getPower(CutAbovePower.POWER_ID);
            if (power == null || card.type != AbstractCard.CardType.SKILL || !PatchSkeletonKeyAThousandCutsPower.TRIGGER) {
                return;
            }
            power.flash();
            PatchSkeletonKeyAThousandCutsPower.TRIGGER = false;
            for (int i = 0; i < power.amount; i++) {
                __instance.onAfterCardPlayed(card);
            }
            PatchSkeletonKeyAThousandCutsPower.TRIGGER = true;
        }
    }

    @SpirePatch(
            clz= Accuracy.class,
            method="use"
    )
    public static class PatchSkeletonKeyAccuracy {
        public static void Postfix(Accuracy __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv(), 1));
            }
        }
    }

    @SpirePatch(
            clz= Adrenaline.class,
            method="use"
    )
    public static class PatchSkeletonKeyAdrenaline {
        @SpireInsertPatch(
                locator= PatchSkeletonKey.PatchSkeletonKeyAdrenaline.Locator.class
        )
        public static SpireReturn Insert(Adrenaline __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(3));
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(2));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(Adrenaline.class, "upgraded");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= AfterImage.class,
            method="use"
    )
    public static class PatchSkeletonKeyAfterImage {
        public static void Postfix(AfterImage __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AfterGlowPower(p, 1), 1));
            }
        }
    }
    @SpirePatch(
            clz= AfterImagePower.class,
            method="onUseCard"
    )
    public static class PatchSkeletonKeyAfterImagePower {
        public static boolean TRIGGER = true;
        public static void Postfix(AfterImagePower __instance, AbstractCard card, UseCardAction action) {
            AbstractPower power = AbstractDungeon.player.getPower(AfterGlowPower.POWER_ID);
            if (power == null || card.type != AbstractCard.CardType.SKILL || !PatchSkeletonKeyAfterImagePower.TRIGGER) {
                return;
            }
            power.flash();
            PatchSkeletonKeyAfterImagePower.TRIGGER = false;
            for (int i = 0; i < power.amount; i++) {
                __instance.onUseCard(card, action);
            }
            PatchSkeletonKeyAfterImagePower.TRIGGER = true;
        }
    }

    @SpirePatch(
            clz= Alchemize.class,
            method="use"
    )
    public static class PatchSkeletonKeyAlchemize {
        public static SpireReturn Prefix(Alchemize __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new SkeletonKeyObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= AllOutAttack.class,
            method="use"
    )
    public static class PatchSkeletonKeyAllOutAttack {
        @SpireInsertPatch(
                locator= PatchSkeletonKey.PatchSkeletonKeyAllOutAttack.Locator.class
        )
        public static SpireReturn Insert(AllOutAttack __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new BetterDiscardAction(1, true, new SkeletonKeyAllOutAttackAction(__instance)));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.NewExprMatcher(DiscardAction.class);
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= Backstab.class,
            method="use"
    )
    public static class PatchSkeletonKeyBackstab {
        public static void Postfix(Backstab __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2 && GameActionManager.turn > 1) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, __instance.damage, __instance.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
            }
        }
    }

    @SpirePatch(
            clz= Bane.class,
            method="use"
    )
    public static class PatchSkeletonKeyBane {
        public static void Postfix(Bane __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new BaneAction(m, new DamageInfo(p, __instance.damage, __instance.damageTypeForTurn)));
            }
        }
    }

    @SpirePatch(
            clz= Blur.class,
            method="use"
    )
    public static class PatchSkeletonKeyBlur {
        @SpireInsertPatch(
                locator= PatchSkeletonKey.PatchSkeletonKeyBlur.Locator.class
        )
        public static SpireReturn Insert(Blur __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SkeletonKeyBlurPower(p, 1)));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.NewExprMatcher(BlurPower.class);
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
    @SpirePatch(
            clz= GameActionManager.class,
            method="getNextAction"
    )
    public static class PatchSkeletonKeyBlurPower {
        @SpireInsertPatch(
                locator= PatchSkeletonKey.PatchSkeletonKeyBlurPower.Locator.class
        )
        public static SpireReturn Insert(GameActionManager __instance) {
            AbstractPlayer p = AbstractDungeon.player;
            if (!p.hasPower(SkeletonKeyBlurPower.POWER_ID)) {
                return SpireReturn.Continue();
            }
            p.addBlock(p.currentBlock);
            if (!AbstractDungeon.getCurrRoom().isBattleOver) {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(null, p.gameHandSize, true));
                p.applyStartOfTurnPostDrawRelics();
                p.applyStartOfTurnPostDrawPowers();
                AbstractDungeon.actionManager.addToBottom(new EnableEndTurnButtonAction());
            }
            return SpireReturn.Return();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasPower");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= BulletTime.class,
            method="use"
    )
    public static class PatchSkeletonKeyBulletTime {
        public static SpireReturn Prefix(BulletTime __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyBulletTimeAction());
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= CalculatedGamble.class,
            method="use"
    )
    public static class PatchSkeletonKeyCalculatedGamble {
        public static SpireReturn Prefix(CalculatedGamble __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new GamblingChipAction(p, true));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Caltrops.class,
            method="use"
    )
    public static class PatchSkeletonKeyCaltrops {
        public static void Postfix(Caltrops __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AreaDenialPower(p)));
            }
        }
    }
    @SpirePatch(
            clz= ThornsPower.class,
            method="onAttacked"
    )
    public static class PatchSkeletonKeyCaltropsPower {
        @SpireInsertPatch(
                locator= PatchSkeletonKey.PatchSkeletonKeyCaltropsPower.Locator.class
        )
        public static SpireReturn Insert(ThornsPower __instance, DamageInfo info, int damageAmount) {
            if (!__instance.owner.hasPower(AreaDenialPower.POWER_ID)) {
                return SpireReturn.Continue();
            }
            AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(__instance.owner, DamageInfo.createDamageMatrix(__instance.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
            return SpireReturn.Return(damageAmount);
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.NewExprMatcher(DamageAction.class);
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= Catalyst.class,
            method="use"
    )
    public static class PatchSkeletonKeyCatalyst {
        public static SpireReturn Prefix(Catalyst __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new MultiplyPoisonAction(p, m, 4));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Choke.class,
            method="use"
    )
    public static class PatchSkeletonKeyChoke {
        public static void Postfix(Choke __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new DoubleWindsorPower(m, 1), 1));
            }
        }
    }
    @SpirePatch(
            clz= ChokePower.class,
            method="onUseCard"
    )
    public static class PatchSkeletonKeyChokePower {
        public static void Postfix(ChokePower __instance, AbstractCard card) {
            AbstractPower power = __instance.owner.getPower(DoubleWindsorPower.POWER_ID);
            if (power == null || card.type != AbstractCard.CardType.SKILL) {
                return;
            }
            for (int i = 0; i < power.amount; i++) {
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(__instance.owner, null, __instance.amount));
            }
        }
    }

    @SpirePatch(
            clz= Concentrate.class,
            method="use"
    )
    public static class PatchSkeletonKeyConcentrate {
        @SpireInsertPatch(
                locator= PatchSkeletonKey.PatchSkeletonKeyConcentrate.Locator.class
        )
        public static SpireReturn Insert(Concentrate __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(3));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.NewExprMatcher(GainEnergyAction.class);
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= CripplingPoison.class,
            method="use"
    )
    public static class PatchSkeletonKeyCripplingPoison {
        public static SpireReturn Prefix(CripplingPoison __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                    if (!m.isDead && !m.isDying) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, (AbstractPower)new PoisonPower(m, p, __instance.magicNumber), __instance.magicNumber));
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 3, false), 3));
                    }
                }
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= DaggerSpray.class,
            method="use"
    )
    public static class PatchSkeletonKeyDaggerSpray {
        public static void Postfix(DaggerSpray __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                for (int i = 0; i < __instance.magicNumber - 2; i++) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, __instance.multiDamage, __instance.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
                }
            }
        }
    }

    @SpirePatch(
            clz= DaggerThrow.class,
            method="use"
    )
    public static class PatchSkeletonKeyDaggerThrow {
        @SpireInsertPatch(
                locator= PatchSkeletonKey.PatchSkeletonKeyDaggerThrow.Locator.class
        )
        public static SpireReturn Insert(DaggerThrow __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, __instance.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, __instance.magicNumber, false));
                return SpireReturn.Return(null);
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
            clz= Distraction.class,
            method="use"
    )
    public static class PatchSkeletonKeyDistraction {
        public static SpireReturn Prefix(Distraction __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();
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
            clz= Doppelganger.class,
            method="use"
    )
    public static class PatchSkeletonKeyDoppelganger {
        public static SpireReturn Prefix(Doppelganger __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new DoppelgangerAction(p, false, __instance.freeToPlayOnce, __instance.energyOnUse + 2));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= EndlessAgony.class,
            method="triggerWhenDrawn"
    )
    public static class PatchSkeletonKeyEndlessAgony {
        public static void Postfix(EndlessAgony __instance) {
            if (__instance.timesUpgraded == 2) {
                for (int i = 0; i < __instance.magicNumber - 1; i++) {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(__instance.makeStatEquivalentCopy()));
                }
            }
        }
    }

    @SpirePatch(
            clz= Envenom.class,
            method="use"
    )
    public static class PatchSkeletonKeyEnvenom {
        public static SpireReturn Prefix(Envenom __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnvenomPower(p, __instance.magicNumber), __instance.magicNumber));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Eviscerate.class,
            method="use"
    )
    public static class PatchSkeletonKeyEviscerate {
        public static void Postfix(Eviscerate __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                for (int i = 0; i < __instance.magicNumber - 3; i++) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, __instance.damage, __instance.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                }
            }
        }
    }

    @SpirePatch(
            clz= GlassKnife.class,
            method="use"
    )
    public static class PatchSkeletonKeyGlassKnife {
        @SpireInsertPatch(
                locator= PatchSkeletonKey.PatchSkeletonKeyGlassKnife.Locator.class
        )
        public static SpireReturn Insert(GlassKnife __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ModifyDamageAction(__instance.uuid, -__instance.magicNumber));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.NewExprMatcher(ModifyDamageAction.class);
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= GrandFinale.class,
            method="canUse"
    )
    public static class PatchSkeletonKeyGrandFinale {
        @SpireInsertPatch(
                locator= PatchSkeletonKey.PatchSkeletonKeyGrandFinale.Locator.class
        )
        public static SpireReturn<Boolean> Insert(GrandFinale __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded != 2) {
                return SpireReturn.Continue();
            }
            if (p.drawPile.size() > __instance.magicNumber) {
                __instance.cantUseMessage = DESCRIPTIONS[RelicSkeletonKey.DESC_INDEX_GRAND_FINALE + 1] + __instance.magicNumber + DESCRIPTIONS[RelicSkeletonKey.DESC_INDEX_GRAND_FINALE + 2];
                return SpireReturn.Return(false);
            }
            return SpireReturn.Return(true);
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "drawPile");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= HeelHook.class,
            method="use"
    )
    public static class PatchSkeletonKeyHeelHook {
        public static SpireReturn Prefix(HeelHook __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new SkeletonKeyHeelHookAction(m, new DamageInfo(p, __instance.damage, __instance.damageTypeForTurn)));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= InfiniteBlades.class,
            method="use"
    )
    public static class PatchSkeletonKeyInfiniteBlades {
        public static SpireReturn Prefix(InfiniteBlades __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InfiniteBladesPower(p, __instance.magicNumber), __instance.magicNumber));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Malaise.class,
            method="use"
    )
    public static class PatchSkeletonKeyMalaise {
        public static SpireReturn Prefix(Malaise __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new MalaiseAction(p, m, false, __instance.freeToPlayOnce, __instance.energyOnUse * 2));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Nightmare.class,
            method="use"
    )
    public static class PatchSkeletonKeyNightmare {
        public static SpireReturn Prefix(Nightmare __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new SkeletonKeyNightmareAction(__instance.magicNumber));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Outmaneuver.class,
            method="use"
    )
    public static class PatchSkeletonKeyOutmaneuver {
        public static SpireReturn Prefix(Outmaneuver __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnergizedPower(p, 4), 4));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= PhantasmalKiller.class,
            method="use"
    )
    public static class PatchSkeletonKeyPhantasmalKiller {
        public static SpireReturn Prefix(PhantasmalKiller __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PhantasmalPower(p, __instance.magicNumber), __instance.magicNumber));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Predator.class,
            method="use"
    )
    public static class PatchSkeletonKeyPredator {
        @SpireInsertPatch(
                locator= PatchSkeletonKey.PatchSkeletonKeyPredator.Locator.class
        )
        public static SpireReturn Insert(Predator __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, __instance.magicNumber), __instance.magicNumber));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.NewExprMatcher(DrawCardNextTurnPower.class);
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz= RiddleWithHoles.class,
            method="use"
    )
    public static class PatchSkeletonKeyRiddleWithHoles {
        public static void Postfix(RiddleWithHoles __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                for (int i = 0; i < __instance.magicNumber - 5; i++) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, __instance.damage, __instance.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                }
            }
        }
    }

    @SpirePatch(
            clz= Setup.class,
            method="use"
    )
    public static class PatchSkeletonKeySetup {
        public static void Postfix(Setup __instance) {
            if (__instance.timesUpgraded == 2) {
                for (int i = 0; i < __instance.magicNumber - 1; i++) {
                    AbstractDungeon.actionManager.addToBottom(new SetupAction());
                }
            }
        }
    }

    @SpirePatch(
            clz= Skewer.class,
            method="use"
    )
    public static class PatchSkeletonKeyPhantasmalSkewer {
        public static SpireReturn Prefix(Skewer __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2 && __instance.energyOnUse >= 5) {
                AbstractDungeon.actionManager.addToBottom(new SkewerAction(p, m, __instance.damage, __instance.damageTypeForTurn, __instance.freeToPlayOnce, __instance.energyOnUse + 2));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= StormOfSteel.class,
            method="use"
    )
    public static class PatchSkeletonKeyStormOfSteel {
        public static SpireReturn Prefix(StormOfSteel __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new SkeletonKeyStormOfSteelAction());
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Terror.class,
            method="use"
    )
    public static class PatchSkeletonKeyTerror {
        public static void Postfix(Terror __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2 && !m.hasPower(SlowPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new SlowPower(m, 0), 0));
            }
        }
    }

    @SpirePatch(
            clz= ToolsOfTheTrade.class,
            method="use"
    )
    public static class PatchSkeletonKeyToolsOfTheTrade {
        public static SpireReturn Prefix(ToolsOfTheTrade __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ToolsOfTheTradePower(p, __instance.magicNumber), __instance.magicNumber));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= Unload.class,
            method="use"
    )
    public static class PatchSkeletonKeyUnload {
        @SpireInsertPatch(
                locator= PatchSkeletonKey.PatchSkeletonKeyUnload.Locator.class
        )
        public static void Insert(Unload __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(__instance.magicNumber));
            }
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.NewExprMatcher(UnloadAction.class);
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
}