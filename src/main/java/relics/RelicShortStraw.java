package relics;

import actions.AddCardModToHandAction;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import cardmods.CardModShortStraw;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicShortStraw extends CustomRelic {
    public static final String ID = "reliquary:ShortStraw";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/shortStraw.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/shortStraw.png");

    static int DAMAGE = 10;

    public RelicShortStraw() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    @Override
    public void atTurnStartPostDraw() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.relics.stream().filter(r -> r.relicId.equals(ID)).findFirst().orElse(null) != this) {
            // If we have multiple Short Straws, have the first one handle the others.
            return;
        }
        int numStraws = Math.toIntExact(p.relics.stream().filter(r -> r.relicId.equals(ID)).count());
        addToBot(new RelicAboveCreatureAction(p, this));
        addToBot(new AddCardModToHandAction(new CardModShortStraw(false),
                                            numStraws,
                                            c -> c.cost != -2));
    }

    @Override
    public void onPlayerEndTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.relics.stream().filter(r -> r.relicId.equals(ID)).findFirst().orElse(null) != this) {
            // If we have multiple Short Straws, have the first one handle the others.
            return;
        }
        boolean activated = false;
        for (AbstractCard c : p.hand.group) {
            if (CardModifierManager.hasModifier(c, CardModShortStraw.ID)) {
                if (!activated) {
                    addToBot(new RelicAboveCreatureAction(p, this));
                    activated = true;
                }
                addToBot(new LoseHPAction(p, p, DAMAGE));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DAMAGE + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicShortStraw();
    }
}
