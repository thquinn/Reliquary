package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicPlasmaLobster extends CustomRelic {
    public static final String ID = "reliquary:PlasmaLobster";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/plasmaLobster.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/plasmaLobster.png");

    private static final int BONUS = 2;

    public RelicPlasmaLobster() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        if (AbstractDungeon.player.currentBlock > 0) {
            damage += BONUS;
        }
        return damage;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BONUS + DESCRIPTIONS[1];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicPlasmaLobster();
    }
}
