package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnRemoveCardFromMasterDeckRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import util.TextureLoader;
import vfx.DelayEffect;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RelicCrucible extends ReliquaryRelic implements OnRemoveCardFromMasterDeckRelic {
    public static final String ID = "reliquary:Crucible";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/crucible.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/crucible.png");

    static final int N = 2;
    boolean trigger = false;

    public RelicCrucible() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    @Override
    public void onRemoveCardFromMasterDeck(AbstractCard removed) {
        if (removed.upgraded) {
            // Can't actually perform the upgrade in here, since that causes a concurrent modification exception with Peace Pipe.
            trigger = true;
        }
    }

    @Override
    public void update() {
        super.update();
        if (!trigger) {
            return;
        }
        trigger = false;
        AbstractPlayer p = AbstractDungeon.player;
        List<AbstractCard> upgradeableCards = p.masterDeck.group.stream().filter(c -> c.canUpgrade()).collect(Collectors.toList());
        if (upgradeableCards.isEmpty()) {
            return;
        }
        flash();
        if (upgradeableCards.size() > 1) {
            Collections.shuffle(upgradeableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        }
        for (int i = 0; i < N && i < upgradeableCards.size(); i++) {
            AbstractCard card = upgradeableCards.get(i);
            card.upgrade();
            p.bottledCardUpgradeCheck(upgradeableCards.get(0));
            float xOffset = upgradeableCards.size() == 1 ? 0 :  -AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale;
            if (i == 1) {
                xOffset *= -1;
            }
            AbstractDungeon.effectList.add(new DelayEffect(
                    new ShowCardBrieflyEffect(card.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F + xOffset, Settings.HEIGHT / 2.0F),
                    2
            ));
        }
        AbstractDungeon.effectList.add(new DelayEffect(
                new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F),
                2
        ));
    }

    @Override
    public String getUpdatedDescription() {
        return N == 1 ? DESCRIPTIONS[0] : DESCRIPTIONS[1] + N + DESCRIPTIONS[2];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicCrucible();
    }
}
