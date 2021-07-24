package relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import cardmods.CardModPaperSnowflake;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicPaperSnowflake extends CustomRelic {
    public static final String ID = "reliquary:PaperSnowflake";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/paperSnowflake.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/paperSnowflake.png");

    public RelicPaperSnowflake() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        repick();
    }
    @Override
    public void onMasterDeckChange() {
        repick();
    }
    public void repick() {
        CardGroup deck = AbstractDungeon.player.masterDeck;
        AbstractCard current = deck.group.stream().filter(c -> CardModifierManager.hasModifier(c, CardModPaperSnowflake.ID)).findFirst().orElse(null);
        CardModPaperSnowflake mod = new CardModPaperSnowflake(1);
        for (int i = deck.size() - 1; i >= 0; i--) {
            AbstractCard card = deck.group.get(i);
            if (mod.shouldApply(card)) {
                if (current != card) {
                    if (current != null) {
                        CardModifierManager.removeModifiersById(current, CardModPaperSnowflake.ID, true);
                    }
                    if (card != null) {
                        flash();
                        CardModifierManager.addModifier(card, mod);
                    }
                }
                break;
            }
        }
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.masterDeck.group.stream().filter(c -> CardModifierManager.hasModifier(c, CardModPaperSnowflake.ID))
                                                        .forEach(c -> CardModifierManager.removeModifiersById(c, CardModPaperSnowflake.ID, true));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicPaperSnowflake();
    }
}
