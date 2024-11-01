package relics;

import basemod.abstracts.CustomRelic;
import cards.cookie.CardCookie;
import cards.cookie.CardCookieTest;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import util.TextureLoader;

public class RelicCookieJar extends CustomRelic {
    public static final String ID = "reliquary:CookieJar";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/cookieJar.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/cookieJar.png");

    public RelicCookieJar() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void onEquip() {
        getRandomCookie(null);
    }
    public void onPurgeCookie(CardCookie cookie) {
        getRandomCookie(cookie);
    }
    public void onUpgradeCookie(CardCookie cookie) {
        getRandomCookie(cookie);
    }

    void getRandomCookie(CardCookie except) {
        CardCookie cookie = new CardCookieTest();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(cookie, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        boolean inCombat = CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
        if (inCombat) {
            addToBot(new MakeTempCardInHandAction(cookie, true, true));
        }
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
