package relics;

import basemod.abstracts.CustomRelic;
import cards.cookie.CardCookie;
import cards.cookie.CardCookieRainbowCookie;
import cards.cookie.CardCookieSnackBreak;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import util.TextureLoader;

import java.util.LinkedList;
import java.util.Queue;

public class RelicCookieJar extends CustomRelic {
    public static final String ID = "reliquary:CookieJar";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/cookieJar.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/cookieJar.png");

    Queue<CardCookie> cookiesToAdd;

    public RelicCookieJar() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
        cookiesToAdd = new LinkedList<>();
    }

    @Override
    public void onEquip() {
        cookiesToAdd.add(getRandomCookie(null));
    }

    @Override
    public void update() {
        super.update();
        if (!isObtained || AbstractDungeon.getCurrRoom() == null) {
            return;
        }
        while (!cookiesToAdd.isEmpty()) {
            CardCookie cookie = cookiesToAdd.remove();
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(cookie, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            boolean inCombat = CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
            if (inCombat) {
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new MakeTempCardInHandAction(cookie, true, true));
            } else {
                flash();
            }
        }
        // Wait for queued effects to finish before checking for the absence of unupgraded cookies.
        if (AbstractDungeon.effectList.stream().noneMatch(e -> e instanceof ShowCardAndObtainEffect) && AbstractDungeon.player.masterDeck.group.stream().noneMatch(c -> c instanceof CardCookie && !c.upgraded)) {
            // For some godforsaken reason, the ShowCardAndObtainEffect constructor puts an effect on the effect queue
            // immediately, so it triggers a concurrent modification except if performed during a Smith upgrade.
            cookiesToAdd.add(getRandomCookie(null));
        }
    }
    CardCookie getRandomCookie(CardCookie except) {
        return new CardCookieRainbowCookie();
    }

    @Override
    public String getUpdatedDescription() {
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
}
