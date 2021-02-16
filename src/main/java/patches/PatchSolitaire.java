package patches;

import actions.SolitaireConjureBladeAction;
import actions.SolitaireCrescendoAction;
import actions.SolitaireForeignInfluenceAction;
import actions.SolitaireLessonLearnedAction;
import basemod.helpers.CardModifierManager;
import cardmods.CardModIncreaseMagicToLimit;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.CollectPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.watcher.CannotChangeStancePower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.stances.DivinityStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import powers.InNTurnsDeathPower;
import powers.SolitaireStudyPower;
import relics.RelicBigHammer;
import relics.RelicSolitaire;

import java.util.ArrayList;

public class PatchSolitaire {
    @SpirePatch(
            clz=AbstractCard.class,
            method="canUpgrade"
    )
    public static class PatchSolitaireCanUpgrade {
        public static SpireReturn<Boolean> Prefix(AbstractCard __instance) {
            boolean watcherCard = __instance.color == AbstractCard.CardColor.PURPLE ||
                                  __instance.cardID.equals(BecomeAlmighty.ID) ||
                                  __instance.cardID.equals(FameAndFortune.ID) ||
                                  __instance.cardID.equals(LiveForever.ID);
            if (watcherCard && __instance.timesUpgraded < 2 && AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(RelicSolitaire.ID)) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= CardCrawlGame.class,
            method="loadPlayerSave"
    )
    public static class PatchSolitaireLoad {
        @SpireInsertPatch(
                locator= Locator.class
        )
        public static void Insert(CardCrawlGame __instance, AbstractPlayer p) {
            // We must load the player's Solitaire-like relic before loading cards, since whether they can be upgraded a
            // second time depends on whether they have a Solitaire.
            if (CardCrawlGame.saveFile.relics.contains(RelicBigHammer.ID)) {
                RelicLibrary.getRelic(RelicBigHammer.ID).instantObtain(p, 0, false);
            }
            if (CardCrawlGame.saveFile.relics.contains(RelicSolitaire.ID)) {
                RelicLibrary.getRelic(RelicSolitaire.ID).instantObtain(p, 0, false);
            }
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "masterDeck");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(clz=Alpha.class, method="upgrade")
    @SpirePatch(clz=BattleHymn.class, method="upgrade")
    @SpirePatch(clz=Blasphemy.class, method="upgrade")
    @SpirePatch(clz=BowlingBash.class, method="upgrade")
    @SpirePatch(clz=Brilliance.class, method="upgrade")
    @SpirePatch(clz=CarveReality.class, method="upgrade")
    @SpirePatch(clz=Collect.class, method="upgrade")
    @SpirePatch(clz=Conclude.class, method="upgrade")
    @SpirePatch(clz=ConjureBlade.class, method="upgrade")
    @SpirePatch(clz=Consecrate.class, method="upgrade")
    @SpirePatch(clz=Crescendo.class, method="upgrade")
    @SpirePatch(clz=CrushJoints.class, method="upgrade")
    @SpirePatch(clz=CutThroughFate.class, method="upgrade")
    @SpirePatch(clz=DeceiveReality.class, method="upgrade")
    @SpirePatch(clz=Defend_Watcher.class, method="upgrade")
    @SpirePatch(clz=DeusExMachina.class, method="upgrade")
    @SpirePatch(clz=DevaForm.class, method="upgrade")
    @SpirePatch(clz=Devotion.class, method="upgrade")
    @SpirePatch(clz=Discipline.class, method="upgrade")
    @SpirePatch(clz=EmptyBody.class, method="upgrade")
    @SpirePatch(clz=EmptyFist.class, method="upgrade")
    @SpirePatch(clz=EmptyMind.class, method="upgrade")
    @SpirePatch(clz=Eruption.class, method="upgrade")
    @SpirePatch(clz=Establishment.class, method="upgrade")
    @SpirePatch(clz=Evaluate.class, method="upgrade")
    @SpirePatch(clz=Fasting.class, method="upgrade")
    @SpirePatch(clz=FearNoEvil.class, method="upgrade")
    @SpirePatch(clz=FlurryOfBlows.class, method="upgrade")
    @SpirePatch(clz=FlyingSleeves.class, method="upgrade")
    @SpirePatch(clz=FollowUp.class, method="upgrade")
    @SpirePatch(clz=ForeignInfluence.class, method="upgrade")
    @SpirePatch(clz=Foresight.class, method="upgrade")
    @SpirePatch(clz=Halt.class, method="upgrade")
    @SpirePatch(clz=Indignation.class, method="upgrade")
    @SpirePatch(clz=InnerPeace.class, method="upgrade")
    @SpirePatch(clz=Judgement.class, method="upgrade")
    @SpirePatch(clz=JustLucky.class, method="upgrade")
    @SpirePatch(clz=LessonLearned.class, method="upgrade")
    @SpirePatch(clz=LikeWater.class, method="upgrade")
    @SpirePatch(clz=MasterReality.class, method="upgrade")
    @SpirePatch(clz=Meditate.class, method="upgrade")
    @SpirePatch(clz=MentalFortress.class, method="upgrade")
    @SpirePatch(clz=Nirvana.class, method="upgrade")
    @SpirePatch(clz=Omniscience.class, method="upgrade")
    @SpirePatch(clz=Perseverance.class, method="upgrade")
    @SpirePatch(clz=Pray.class, method="upgrade")
    @SpirePatch(clz=PressurePoints.class, method="upgrade")
    @SpirePatch(clz=Prostrate.class, method="upgrade")
    @SpirePatch(clz=Protect.class, method="upgrade")
    @SpirePatch(clz=Ragnarok.class, method="upgrade")
    @SpirePatch(clz=ReachHeaven.class, method="upgrade")
    @SpirePatch(clz=Rushdown.class, method="upgrade")
    @SpirePatch(clz=Sanctity.class, method="upgrade")
    @SpirePatch(clz=SandsOfTime.class, method="upgrade")
    @SpirePatch(clz=SashWhip.class, method="upgrade")
    @SpirePatch(clz=Scrawl.class, method="upgrade")
    @SpirePatch(clz=SignatureMove.class, method="upgrade")
    @SpirePatch(clz=SimmeringFury.class, method="upgrade")
    @SpirePatch(clz=SpiritShield.class, method="upgrade")
    @SpirePatch(clz=Strike_Purple.class, method="upgrade")
    @SpirePatch(clz=Study.class, method="upgrade")
    @SpirePatch(clz=Swivel.class, method="upgrade")
    @SpirePatch(clz=TalkToTheHand.class, method="upgrade")
    @SpirePatch(clz=Tantrum.class, method="upgrade")
    @SpirePatch(clz=ThirdEye.class, method="upgrade")
    @SpirePatch(clz=Tranquility.class, method="upgrade")
    @SpirePatch(clz=Vault.class, method="upgrade")
    @SpirePatch(clz=Vigilance.class, method="upgrade")
    @SpirePatch(clz=Wallop.class, method="upgrade")
    @SpirePatch(clz=WaveOfTheHand.class, method="upgrade")
    @SpirePatch(clz=Weave.class, method="upgrade")
    @SpirePatch(clz=WheelKick.class, method="upgrade")
    @SpirePatch(clz=WindmillStrike.class, method="upgrade")
    @SpirePatch(clz=Wish.class, method="upgrade")
    @SpirePatch(clz=BecomeAlmighty.class, method="upgrade")
    @SpirePatch(clz=FameAndFortune.class, method="upgrade")
    @SpirePatch(clz=LiveForever.class, method="upgrade")
    @SpirePatch(clz=Worship.class, method="upgrade")
    @SpirePatch(clz=WreathOfFlame.class, method="upgrade")
    public static class PatchSolitaireUpgrade {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player == null) {
                return SpireReturn.Continue();
            }
            RelicSolitaire solitaire = (RelicSolitaire) AbstractDungeon.player.getRelic(RelicSolitaire.ID);
            if (__instance.timesUpgraded == 1 && solitaire != null) {
                solitaire.upgradeCard(__instance);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz=Blasphemy.class,
            method="use"
    )
    public static class PatchSolitaireBlasphemy {
        public static SpireReturn Prefix(Blasphemy __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(DivinityStance.STANCE_ID));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InNTurnsDeathPower(p, __instance.magicNumber)));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz=Collect.class,
            method="use"
    )
    public static class PatchSolitaireCollect {
        public static void Postfix(Collect __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CollectPower(p, 1), 1));
            }
        }
    }

    @SpirePatch(
            clz=ConjureBlade.class,
            method="use"
    )
    public static class PatchSolitaireConjureBlade {
        public static SpireReturn Prefix(ConjureBlade __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new SolitaireConjureBladeAction(AbstractDungeon.player, __instance.freeToPlayOnce, __instance.energyOnUse + 1));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz=Crescendo.class,
            method="use"
    )
    public static class PatchSolitaireCrescendo {
        public static SpireReturn Prefix(Crescendo __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new SolitaireCrescendoAction(__instance.magicNumber));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz=Eruption.class,
            method="use"
    )
    public static class PatchSolitaireEruption {
        public static SpireReturn Prefix(Eruption __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                int damage = __instance.damage;
                if (!p.stance.ID.equals(WrathStance.STANCE_ID) && !p.hasPower(CannotChangeStancePower.POWER_ID)) {
                    damage = MathUtils.floor(new WrathStance().atDamageGive(damage, __instance.damageTypeForTurn));
                }
                AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(WrathStance.STANCE_ID));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, __instance.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz=FlyingSleeves.class,
            method="use"
    )
    public static class PatchSolitaireFlyingSleeves {
        public static void Prefix(FlyingSleeves __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                if (m != null) {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIFF_1", 0.2F));
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_FAST", 0.2F));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(m.hb.cX, m.hb.cY - 30.0F * Settings.scale, 500.0F, -200.0F, 250.0F, 3.0F, Color.VIOLET, Color.PINK)));
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, __instance.damage, __instance.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
            }
        }
    }

    @SpirePatch(
            clz=ForeignInfluence.class,
            method="use"
    )
    public static class PatchSolitaireForeignInfluence {
        public static SpireReturn Prefix(ForeignInfluence __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new SolitaireForeignInfluenceAction(__instance.magicNumber, __instance.upgraded));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz=LessonLearned.class,
            method="use"
    )
    public static class PatchSolitaireLessonLearned {
        public static SpireReturn Prefix(LessonLearned __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new SolitaireLessonLearnedAction(m, new DamageInfo(p, __instance.damage, __instance.damageTypeForTurn)));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz=SandsOfTime.class,
            method="onRetained"
    )
    public static class PatchSolitaireSandsOfTime {
        public static void Prefix(SandsOfTime __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ReduceCostAction(__instance));
            }
        }
    }

    @SpirePatch(
            clz=Scrawl.class,
            method="use"
    )
    public static class PatchSolitaireScrawl {
        public static void Prefix(Scrawl __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ScryAction(__instance.magicNumber));
            }
        }
    }

    @SpirePatch(
            clz=Study.class,
            method="use"
    )
    public static class PatchSolitaireStudy {
        public static SpireReturn Prefix(Study __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SolitaireStudyPower(p, __instance.magicNumber), __instance.magicNumber));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz=Swivel.class,
            method="use"
    )
    public static class PatchSolitaireSwivel {
        public static void Postfix(Swivel __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VigorPower(p, __instance.magicNumber), __instance.magicNumber));
            }
        }
    }

    @SpirePatch(
            clz=Vault.class,
            method="use"
    )
    public static class PatchSolitaireVault {
        public static void Postfix(Vault __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, __instance.magicNumber), __instance.magicNumber));
            }
        }
    }

    @SpirePatch(
            clz=Wish.class,
            method="use"
    )
    public static class PatchSolitaireWish {
        @SpireInsertPatch(
                locator= Locator.class,
                localvars = {"stanceChoices"}
        )
        public static void Insert(Wish __instance, AbstractPlayer p, AbstractMonster m, ArrayList<AbstractCard> stanceChoices) {
            if (__instance.timesUpgraded == 2) {
                for (AbstractCard c : stanceChoices) {
                    c.upgrade();
                }
            }
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.MethodCallMatcher(Wish.class, "addToBot");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch(
            clz=AbstractCard.class,
            method="onRetained"
    )
    public static class PatchSolitaireWorship {
        public static int INCREASE = 1;
        public static int LIMIT = 9;
        public static void Prefix(AbstractCard __instance) {
            if (__instance.cardID.equals(Worship.ID) && __instance.timesUpgraded == 2) {
                CardModifierManager.addModifier(__instance, new CardModIncreaseMagicToLimit(INCREASE, LIMIT));
            }
        }
    }
}