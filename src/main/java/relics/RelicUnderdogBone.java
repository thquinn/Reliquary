package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicUnderdogBone extends ReliquaryRelic {
    public static final String ID = "reliquary:UnderdogBone";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/underdogBone.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/underdogBone.png");

    public RelicUnderdogBone() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (!c.upgraded) {
            AbstractPlayer p = AbstractDungeon.player;
            if (p.drawPile.group.stream().anyMatch(d -> d.cardID.equals(c.cardID) && d.upgraded)) {
                addToTop(new RelicAboveCreatureAction(p, this));
                addToTop(new FetchAction(p.drawPile, d -> d.cardID.equals(c.cardID) && d.upgraded, 1));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicUnderdogBone();
    }
}
