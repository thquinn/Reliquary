package cards.cookie;

import actions.CookieCardBiteAction;
import basemod.abstracts.CustomSavable;
import basemod.helpers.TooltipInfo;
import cards.ReliquaryCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import relics.RelicCookieJar;
import util.TextureLoader;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public abstract class CardCookie extends ReliquaryCard implements CustomSavable<Integer> {
    static String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(RelicCookieJar.ID).DESCRIPTIONS;
    static TextureAtlas.AtlasRegion[] SILHOUETTES_ATTACK, SILHOUETTES_POWER, SILHOUETTES_SKILL;
    int displayBites;

    public CardCookie(String id,
                     String name,
                     String img,
                     int cost,
                     String rawDescription,
                     AbstractCard.CardType type,
                     AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, CookieStatics.RELIQUARY_COOKIE, CardRarity.SPECIAL, target);
        if (SILHOUETTES_ATTACK == null) {
            SILHOUETTES_ATTACK = new TextureAtlas.AtlasRegion[] {
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_attack.png"),
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_attack_bite.png"),
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_attack_bite2.png"),
            };
            SILHOUETTES_POWER = new TextureAtlas.AtlasRegion[] {
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_power.png"),
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_power_bite.png"),
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_power_bite2.png"),
            };
            SILHOUETTES_SKILL = new TextureAtlas.AtlasRegion[] {
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_skill.png"),
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_skill_bite.png"),
                    TextureLoader.asAtlasRegion("reliquaryAssets/images/cardui/cookie/silhouette_skill_bite2.png"),
            };
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new CookieCardBiteAction(this));
    }
    public void setBites(int bites) {
        if (upgraded) {
            bites = 0;
        }
        bites = Math.min(bites, 2);
        CardCookie.CookieBiteField.bites.set(this, bites);
        FleetingField.fleeting.set(this, bites >= 2);
    }

    public void onEaten() { }

    @Override
    public void update() {
        int bites = CookieBiteField.bites.get(this);
        if (bites != displayBites) {
            displayBites = bites;
            String[] smallBacks = CookieStatics.CARD_BACKS_SKILL_SMALL, largeBacks = CookieStatics.CARD_BACKS_SKILL_LARGE;
            if (type == CardType.ATTACK) {
                smallBacks = CookieStatics.CARD_BACKS_ATTACK_SMALL;
                largeBacks = CookieStatics.CARD_BACKS_ATTACK_LARGE;
            } else if (type == CardType.POWER) {
                smallBacks = CookieStatics.CARD_BACKS_POWER_SMALL;
                largeBacks = CookieStatics.CARD_BACKS_POWER_LARGE;
            }
            setBackgroundTexture(smallBacks[displayBites], largeBacks[displayBites]);
        }
        super.update();
    }

    @Override
    public TextureAtlas.AtlasRegion getCardBgAtlas() {
        if (type == CardType.ATTACK) {
            return SILHOUETTES_ATTACK[displayBites];
        } else if (type == CardType.POWER) {
            return SILHOUETTES_POWER[displayBites];
        }
        return SILHOUETTES_SKILL[displayBites];
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        return Arrays.asList(new TooltipInfo(DESCRIPTIONS[1], DESCRIPTIONS[3]));
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        int bites = CookieBiteField.bites.get(this);
        CardCookie copy = (CardCookie) super.makeStatEquivalentCopy();
        copy.setBites(bites);
        return copy;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
        setBites(0);
    }

    @Override
    public Integer onSave() {
        return CookieBiteField.bites.get(this);
    }
    @Override
    public void onLoad(Integer bites) {
        setBites(bites);
    }
    @Override
    public Type savedType() {
        // Needed for the CustomSavable implementation to make it to subclasses.
        return Integer.class;
    }

    @SpirePatch(clz = CardCookie.class, method = SpirePatch.CLASS)
    public static class CookieBiteField {
        public static SpireField<Integer> bites = new SpireField<>(() -> 0);
    }
}
