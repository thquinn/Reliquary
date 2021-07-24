package patches;

import actions.ApplyPowerIfAbsentAction;
import actions.BigHammerWhirlwindAction;
import actions.MultiplyBlockAction;
import actions.MultiplyStrengthAction;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BerserkPower;
import com.megacrit.cardcrawl.powers.BrutalityPower;
import com.megacrit.cardcrawl.powers.HeatsinkPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import javassist.*;
import powers.ChainReactionPower;
import powers.HandSizePower;
import powers.HydratedPower;
import powers.VulnerableNextTurnPower;
import relics.RelicBigHammer;

public class PatchBigHammer {
    static String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(RelicBigHammer.ID).DESCRIPTIONS;

    @SpirePatch(
            clz=AbstractCard.class,
            method="canUpgrade"
    )
    public static class PatchBigHammerCanUpgrade {
        public static SpireReturn<Boolean> Prefix(AbstractCard __instance) {
            boolean ironcladCard = __instance.color == AbstractCard.CardColor.RED;
            if (ironcladCard && __instance.timesUpgraded < 2 && AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(RelicBigHammer.ID)) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz=Anger.class, method="upgrade")
    @SpirePatch(clz=Armaments.class, method="upgrade")
    @SpirePatch(clz=Barricade.class, method="upgrade")
    @SpirePatch(clz=Bash.class, method="upgrade")
    @SpirePatch(clz=BattleTrance.class, method="upgrade")
    @SpirePatch(clz=Berserk.class, method="upgrade")
    @SpirePatch(clz=BloodForBlood.class, method="upgrade")
    @SpirePatch(clz=Bloodletting.class, method="upgrade")
    @SpirePatch(clz=Bludgeon.class, method="upgrade")
    @SpirePatch(clz=BodySlam.class, method="upgrade")
    @SpirePatch(clz=Brutality.class, method="upgrade")
    @SpirePatch(clz=BurningPact.class, method="upgrade")
    @SpirePatch(clz=Carnage.class, method="upgrade")
    @SpirePatch(clz=Clash.class, method="upgrade")
    @SpirePatch(clz=Cleave.class, method="upgrade")
    @SpirePatch(clz=Clothesline.class, method="upgrade")
    @SpirePatch(clz=Combust.class, method="upgrade")
    @SpirePatch(clz=Corruption.class, method="upgrade")
    @SpirePatch(clz=DarkEmbrace.class, method="upgrade")
    @SpirePatch(clz=Defend_Red.class, method="upgrade")
    @SpirePatch(clz=DemonForm.class, method="upgrade")
    @SpirePatch(clz=Disarm.class, method="upgrade")
    @SpirePatch(clz=DoubleTap.class, method="upgrade")
    @SpirePatch(clz=Dropkick.class, method="upgrade")
    @SpirePatch(clz=DualWield.class, method="upgrade")
    @SpirePatch(clz=Entrench.class, method="upgrade")
    @SpirePatch(clz=Evolve.class, method="upgrade")
    @SpirePatch(clz=Exhume.class, method="upgrade")
    @SpirePatch(clz=Feed.class, method="upgrade")
    @SpirePatch(clz=FeelNoPain.class, method="upgrade")
    @SpirePatch(clz=FiendFire.class, method="upgrade")
    @SpirePatch(clz=FireBreathing.class, method="upgrade")
    @SpirePatch(clz=FlameBarrier.class, method="upgrade")
    @SpirePatch(clz=Flex.class, method="upgrade")
    @SpirePatch(clz=GhostlyArmor.class, method="upgrade")
    @SpirePatch(clz=Havoc.class, method="upgrade")
    @SpirePatch(clz=Headbutt.class, method="upgrade")
    @SpirePatch(clz=HeavyBlade.class, method="upgrade")
    @SpirePatch(clz=Hemokinesis.class, method="upgrade")
    @SpirePatch(clz=Immolate.class, method="upgrade")
    @SpirePatch(clz=Impervious.class, method="upgrade")
    @SpirePatch(clz=InfernalBlade.class, method="upgrade")
    @SpirePatch(clz=Inflame.class, method="upgrade")
    @SpirePatch(clz=Intimidate.class, method="upgrade")
    @SpirePatch(clz=IronWave.class, method="upgrade")
    @SpirePatch(clz=Juggernaut.class, method="upgrade")
    @SpirePatch(clz=LimitBreak.class, method="upgrade")
    @SpirePatch(clz=Metallicize.class, method="upgrade")
    @SpirePatch(clz=Offering.class, method="upgrade")
    @SpirePatch(clz=PerfectedStrike.class, method="upgrade")
    @SpirePatch(clz=PommelStrike.class, method="upgrade")
    @SpirePatch(clz=PowerThrough.class, method="upgrade")
    @SpirePatch(clz=Pummel.class, method="upgrade")
    @SpirePatch(clz=Rage.class, method="upgrade")
    @SpirePatch(clz=Rampage.class, method="upgrade")
    @SpirePatch(clz=Reaper.class, method="upgrade")
    @SpirePatch(clz=RecklessCharge.class, method="upgrade")
    @SpirePatch(clz=Rupture.class, method="upgrade")
    @SpirePatch(clz=SecondWind.class, method="upgrade")
    @SpirePatch(clz=SeeingRed.class, method="upgrade")
    @SpirePatch(clz=Sentinel.class, method="upgrade")
    @SpirePatch(clz=SeverSoul.class, method="upgrade")
    @SpirePatch(clz=Shockwave.class, method="upgrade")
    @SpirePatch(clz=ShrugItOff.class, method="upgrade")
    @SpirePatch(clz=SpotWeakness.class, method="upgrade")
    @SpirePatch(clz=Strike_Red.class, method="upgrade")
    @SpirePatch(clz=SwordBoomerang.class, method="upgrade")
    @SpirePatch(clz=ThunderClap.class, method="upgrade")
    @SpirePatch(clz=TrueGrit.class, method="upgrade")
    @SpirePatch(clz=TwinStrike.class, method="upgrade")
    @SpirePatch(clz=Uppercut.class, method="upgrade")
    @SpirePatch(clz=Warcry.class, method="upgrade")
    @SpirePatch(clz=Whirlwind.class, method="upgrade")
    @SpirePatch(clz=WildStrike.class, method="upgrade")
    public static class PatchBigHammerUpgrade {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player == null) {
                return SpireReturn.Continue();
            }
            RelicBigHammer hammer = (RelicBigHammer) AbstractDungeon.player.getRelic(RelicBigHammer.ID);
            if (__instance.timesUpgraded == 1 && hammer != null) {
                hammer.upgradeCard(__instance);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz=Anger.class,
            method=SpirePatch.CONSTRUCTOR
    )
    public static class PatchBigHammerAnger {
        public static void Raw(CtBehavior ctMethodToPatch) throws NotFoundException, CannotCompileException {
            CtClass ctClass = ctMethodToPatch.getDeclaringClass();
            ClassPool pool = ctClass.getClassPool();
            CtClass ctAnger = pool.get(Anger.class.getName());
            CtClass ctAbstractMonster = pool.get(AbstractMonster.class.getName());

            CtMethod method = CtNewMethod.make(
                    CtClass.voidType, // Return
                    "calculateCardDamage", // Method name
                    new CtClass[]{ ctAbstractMonster },
                    null, // Exceptions
                    "{" +
                        "int realBaseDamage = baseDamage;" +
                        "baseDamage += magicNumber * " + PatchBigHammerAnger.class.getName() + ".discardedAngerCount();" +
                        "super.calculateCardDamage($1);" +
                        "baseDamage = realBaseDamage;" +
                        "isDamageModified = damage != baseDamage;" +
                    "}",
                    ctClass
            );
            ctClass.addMethod(method);
        }
        public static int discardedAngerCount() {
            return (int) AbstractDungeon.player.discardPile.group.stream().filter(c -> c.cardID.equals(Anger.ID)).count();
        }
    }

    @SpirePatch(
            clz= Armaments.class,
            method="use"
    )
    public static class PatchBigHammerArmaments {
        public static void Postfix(Armaments __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(true));
            }
        }
    }

    @SpirePatch(
            clz= Berserk.class,
            method="use"
    )
    public static class PatchBigHammerBerserk {
        public static SpireReturn Prefix(Berserk __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerableNextTurnPower(p, __instance.magicNumber), __instance.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BerserkPower(p, 1), 1));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    public static class PatchBigHammerBodySlam {
        public static final int PERCENTAGE = 150;
        private static final String UPGRADE_DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(BodySlam.ID).UPGRADE_DESCRIPTION;

        @SpirePatch(
                clz= BodySlam.class,
                method="use"
        )
        public static class PatchBigHammerBodySlamUse {
            public static SpireReturn Prefix(BodySlam __instance, AbstractPlayer p, AbstractMonster m) {
                if (__instance.timesUpgraded != 2) {
                    return SpireReturn.Continue();
                }
                __instance.baseDamage = p.currentBlock * PERCENTAGE / 100;
                __instance.calculateCardDamage(m);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, __instance.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                __instance.rawDescription = String.format(DESCRIPTIONS[RelicBigHammer.DESC_INDEX_BODY_SLAM], PERCENTAGE);
                __instance.initializeDescription();
                return SpireReturn.Return(null);
            }
        }

        @SpirePatch(
                clz= BodySlam.class,
                method="applyPowers"
        )
        public static class PatchBigHammerBodySlamApplyPowers {
            @SpireInsertPatch(
                    locator= Locator.class
            )
            public static void Insert(BodySlam __instance) {
                if (__instance.timesUpgraded == 2) {
                    __instance.baseDamage = AbstractDungeon.player.currentBlock * PERCENTAGE / 100;
                }
            }
            private static class Locator extends SpireInsertLocator {
                public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                    Matcher matcher = new Matcher.MethodCallMatcher(AbstractCard.class, "applyPowers");
                    return LineFinder.findInOrder(ctMethodToPatch, matcher);
                }
            }
            public static void Postfix(BodySlam __instance) {
                if (__instance.timesUpgraded == 2) {
                    __instance.rawDescription = String.format(DESCRIPTIONS[RelicBigHammer.DESC_INDEX_BODY_SLAM], PERCENTAGE);
                    __instance.rawDescription += UPGRADE_DESCRIPTION;
                    __instance.initializeDescription();
                }
            }
        }

        @SpirePatch(
                clz= BodySlam.class,
                method="onMoveToDiscard"
        )
        public static class PatchBigHammerBodySlamOnMoveToDiscard {
            public static SpireReturn Prefix(BodySlam __instance) {
                if (__instance.timesUpgraded != 2) {
                    return SpireReturn.Continue();
                }
                __instance.rawDescription = String.format(DESCRIPTIONS[RelicBigHammer.DESC_INDEX_BODY_SLAM], PERCENTAGE);
                __instance.initializeDescription();
                return SpireReturn.Return(null);
            }
        }

        @SpirePatch(
                clz= BodySlam.class,
                method="calculateCardDamage"
        )
        public static class PatchBigHammerBodySlamCalculateCardDamage {
            public static void Postfix(BodySlam __instance) {
                if (__instance.timesUpgraded == 2) {
                    __instance.rawDescription = String.format(DESCRIPTIONS[RelicBigHammer.DESC_INDEX_BODY_SLAM], PERCENTAGE);
                    __instance.rawDescription += UPGRADE_DESCRIPTION;
                    __instance.initializeDescription();
                }
            }
        }
    }

    @SpirePatch(
            clz= Brutality.class,
            method="use"
    )
    public static class PatchBigHammerBrutality {
        public static SpireReturn Prefix(Brutality __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded != 2) {
                return SpireReturn.Continue();
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BrutalityPower(p, __instance.magicNumber), __instance.magicNumber));
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(
            clz= Combust.class,
            method="use"
    )
    public static class PatchBigHammerCombust {
        public static int GAIN_PER_TURN = 2;
        public static void Postfix(Combust __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ChainReactionPower(p, GAIN_PER_TURN), GAIN_PER_TURN));
            }
        }
    }

    @SpirePatch(
            clz= DarkEmbrace.class,
            method="use"
    )
    public static class PatchBigHammerDarkEmbrace {
        public static void Postfix(DarkEmbrace __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HeatsinkPower(p, 1), 1));
            }
        }
    }

    @SpirePatch(
            clz= Dropkick.class,
            method="use"
    )
    public static class PatchBigHammerDropkick {
        public static void Postfix(Dropkick __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerIfAbsentAction(m, p, new VulnerablePower(m, 1, false)));
            }
        }
    }

    @SpirePatch(
            clz= Entrench.class,
            method="use"
    )
    public static class PatchBigHammerEntrench {
        public static SpireReturn Prefix(Entrench __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded != 2) {
                return SpireReturn.Continue();
            }
            AbstractDungeon.actionManager.addToBottom(new MultiplyBlockAction(p, 3));
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(
            clz= Evolve.class,
            method="use"
    )
    public static class PatchBigHammerEvolve {
        public static int HAND_SIZE_INCREASE = 2;
        public static void Postfix(Evolve __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HandSizePower(p, HAND_SIZE_INCREASE)));
            }
        }
    }

    @SpirePatch(
            clz= Exhume.class,
            method="use"
    )
    public static class PatchBigHammerExhume {
        public static SpireReturn Prefix(Exhume __instance) {
            if (__instance.timesUpgraded != 2) {
                return SpireReturn.Continue();
            }
            AbstractDungeon.actionManager.addToBottom(new ExhumeAction(true));
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(
            clz= Flex.class,
            method="use"
    )
    public static class PatchBigHammerFlex {
        public static int HYDRATION = 1;
        public static void Postfix(Flex __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HydratedPower(p, HYDRATION)));
            }
        }
    }

    @SpirePatch(
            clz= Havoc.class,
            method="use"
    )
    public static class PatchBigHammerHavoc {
        public static void Postfix(Havoc __instance) {
            for (int i = 1; i < __instance.magicNumber; i++) {
                AbstractDungeon.actionManager.addToBottom(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), true));
            }
        }
    }

    @SpirePatch(
            clz= InfernalBlade.class,
            method="use"
    )
    public static class PatchBigHammerInfernalBlade {
        public static SpireReturn Prefix(InfernalBlade __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();
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
            clz= LimitBreak.class,
            method="use"
    )
    public static class PatchBigHammerLimitBreak {
        public static SpireReturn Prefix(LimitBreak __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded != 2) {
                return SpireReturn.Continue();
            }
            AbstractDungeon.actionManager.addToBottom(new MultiplyStrengthAction(p, 3));
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(
            clz= Offering.class,
            method="use"
    )
    public static class PatchBigHammerOffering {
        public static void Postfix(Offering __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            }
        }
    }

    @SpirePatch(
            clz= RecklessCharge.class,
            method="use"
    )
    public static class PatchBigHammerRecklessCharge {
        public static SpireReturn Prefix(RecklessCharge __instance, AbstractPlayer p, AbstractMonster m) {
            if (__instance.timesUpgraded != 2) {
                return SpireReturn.Continue();
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, __instance.damage, __instance.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), 1));
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(
            clz= SeeingRed.class,
            method="use"
    )
    public static class PatchBigHammerSeeingRed {
        public static void Postfix(SeeingRed __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            }
        }
    }

    @SpirePatch(
            clz= Sentinel.class,
            method="triggerOnExhaust"
    )
    public static class PatchBigHammerSentinel {
        public static void Postfix(Sentinel __instance) {
            if (__instance.timesUpgraded == 2) {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(__instance.magicNumber));
            }
        }
    }

    @SpirePatch(
            clz= ThunderClap.class,
            method="use"
    )
    public static class PatchBigHammerThunderClap {
        @SpireInsertPatch(
                locator=Locator.class
        )
        public static SpireReturn Insert(ThunderClap __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded != 2) {
                return SpireReturn.Continue();
            }
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new VulnerablePower(mo, __instance.magicNumber, false), __instance.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
            return SpireReturn.Return(null);
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractRoom.class, "monsters");
                int[] matches = LineFinder.findAllInOrder(ctMethodToPatch, matcher);
                return new int[]{matches[matches.length - 1]};
            }
        }
    }

    @SpirePatch(
            clz= TrueGrit.class,
            method="use"
    )
    public static class PatchBigHammerTrueGrit {
        @SpireInsertPatch(
                locator=Locator.class
        )
        public static SpireReturn Insert(TrueGrit __instance) {
            if (__instance.timesUpgraded != 2) {
                return SpireReturn.Continue();
            }
            AbstractDungeon.actionManager.addToBottom(new ExhaustAction(__instance.magicNumber, false, true, false));
            return SpireReturn.Return(null);
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(TrueGrit.class, "upgraded");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }
    @SpirePatch(
            clz = HandCardSelectScreen.class,
            method= "refreshSelectedCards"
    )
    public static class HandCardSelectScreenPatch {
        // This is a fix for the basegame behavior (thanks to Github user Rita-Bernstein for an example).
        public static void Postfix(HandCardSelectScreen __instance, boolean ___anyNumber) {
            if (__instance.selectedCards.size() >= 1 && ___anyNumber && !__instance.canPickZero){
                __instance.button.enable();
            }
        }
    }

    @SpirePatch(
            clz= Whirlwind.class,
            method="use"
    )
    public static class PatchBigHammerWhirlwind {
        public static int EXTRA = 1;
        public static SpireReturn Prefix(Whirlwind __instance, AbstractPlayer p) {
            if (__instance.timesUpgraded != 2) {
                return SpireReturn.Continue();
            }
            AbstractDungeon.actionManager.addToBottom(new BigHammerWhirlwindAction(p, __instance.multiDamage, __instance.damageTypeForTurn, __instance.freeToPlayOnce, __instance.energyOnUse, EXTRA));
            return SpireReturn.Return(null);
        }
    }
}