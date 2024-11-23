package relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import basemod.helpers.CardModifierManager;
import cardmods.CardModInnateOnce;
import cards.cookie.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import util.ReliquaryLogger;
import util.TextureLoader;

import javax.smartcardio.Card;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class RelicCookieJar extends ReliquaryRelic implements CustomSavable<String>, ClickableRelic {
    public static final String ID = "reliquary:CookieJar";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/cookieJar.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/cookieJar.png");

    Queue<AbstractCard> cookiesToAdd;
    String lastAddedID;
    public static boolean previewGridOpen;

    public RelicCookieJar() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
        cookiesToAdd = new LinkedList<>();
    }

    @Override
    public void update() {
        super.update();
        if (!isObtained || AbstractDungeon.getCurrRoom() == null) {
            return;
        }
        while (!cookiesToAdd.isEmpty()) {
            AbstractCard cookie = cookiesToAdd.remove();
            boolean inCombat = CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
            if (inCombat) {
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new MakeTempCardInHandAction(cookie, true, true));
            } else {
                if (lastAddedID == null) {
                    CardModifierManager.addModifier(cookie, new CardModInnateOnce());
                }
                flash();
            }
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(cookie, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            lastAddedID = cookie.cardID;
        }

        boolean shouldCreateCard = lastAddedID == null || AbstractDungeon.player.masterDeck.group.stream().noneMatch(c -> c instanceof CardCookie && !c.upgraded);
        // Wait for queued effects to finish before actually adding anything.
        if (shouldCreateCard && AbstractDungeon.effectList.stream().noneMatch(e -> e instanceof ShowCardAndObtainEffect)) {
            // For some godforsaken reason, the ShowCardAndObtainEffect constructor puts an effect on the effect queue
            // immediately, so it triggers a concurrent modification except if performed during a Smith upgrade.
            // At least this also allows multiple Cookie Jars to trigger simultaneously.
            addRandomCookie();
        }
    }
    AbstractCard addRandomCookie() {
        CardCookie[] filtered = Arrays.stream(ALL_COOKIES).filter(c -> c.canSpawn() && c.cardID != lastAddedID && (lastAddedID != null || c.canSpawnAsFirst())).toArray(CardCookie[]::new);
        AbstractCard copy = filtered[MathUtils.random(filtered.length)].makeCopy();
        if (lastAddedID == null) {
            CardModifierManager.addModifier(copy, new CardModInnateOnce());
        }
        cookiesToAdd.add(copy);
        return copy;
    }

    @Override
    public String onSave() {
        return lastAddedID;
    }
    @Override
    public void onLoad(String s) {
        lastAddedID = s;
    }

    @Override
    public String getUpdatedDescription() {
        if (isObtained) {
            return DESCRIPTIONS[0] + DESCRIPTIONS[4];
        }
        return DESCRIPTIONS[0];
    }
    @Override
    protected void initializeTips() {
        super.initializeTips();
        tips.add(1, new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2]));
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicCookieJar();
    }

    @Override
    public void onRightClick() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            return;
        }
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        CardGroup cookieGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (CardCookie cookie : ALL_COOKIES) {
            cookieGroup.addToTop(cookie);
        }
        previewGridOpen = true;
        AbstractDungeon.gridSelectScreen.openConfirmationGrid(cookieGroup, DESCRIPTIONS[5]);
    }

    static final CardCookie[] ALL_COOKIES = new CardCookie[]{
            new CardCookieBlackAndWhite(),
            new CardCookieBrowniePoints(),
            new CardCookieHalfBakedIdea(),
            new CardCookieOmNom(),
            new CardCookieRainbowCookie(),
            new CardCookieSmartCookie(),
            new CardCookieSnackBreak(),
            new CardCookieSugarAndSpice(),
            new CardCookieSugarcoat(),
            new CardCookieSugarRush(),
            new CardCookieSweetDreams(),
            new CardCookieSweetNothings(),
            new CardCookieSweetRevenge(),
            new CardCookieSweetScience(),
            new CardCookieTheCookieCrumbles(),
            new CardCookieToughCookie(),
            new CardCookieToutSweet()
    };
}
