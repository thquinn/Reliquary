package patches;

import actions.BetterDiscardAction;
import actions.SkeletonKeyAllOutAttackAction;
import actions.SkeletonKeyObtainPotionAction;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.BaneAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.Berserk;
import com.megacrit.cardcrawl.cards.red.TrueGrit;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AfterImagePower;
import com.megacrit.cardcrawl.powers.BerserkPower;
import com.megacrit.cardcrawl.powers.ThousandCutsPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import powers.AfterGlowPower;
import powers.CutAbovePower;
import powers.VulnerableNextTurnPower;
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
}