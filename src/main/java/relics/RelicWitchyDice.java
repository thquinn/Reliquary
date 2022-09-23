package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import util.TextureLoader;

public class RelicWitchyDice extends CustomRelic {
    public static final String ID = "reliquary:WitchyDice";
    private static final Texture IMG = TextureLoader.getTexture("reliquaryAssets/images/relics/witchyDice.png");
    private static final Texture OUTLINE  = TextureLoader.getTexture("reliquaryAssets/images/relics/outline/witchyDice.png");

    public RelicWitchyDice() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new RelicAboveCreatureAction(p, this));
        AbstractCard.CardRarity rarity;
        int roll = AbstractDungeon.cardRandomRng.random(99);
        if (roll < 55) {
            rarity = AbstractCard.CardRarity.COMMON;
        } else if (roll < 85) {
            rarity = AbstractCard.CardRarity.UNCOMMON;
        } else {
            rarity = AbstractCard.CardRarity.RARE;
        }
        AbstractCard card = CardLibrary.getAnyColorCard(rarity);
        card.purgeOnUse = true;
        p.limbo.addToTop(card);
        card.current_x = Settings.WIDTH / 2.0F;
        card.current_y = Settings.HEIGHT / 2.0F;
        card.target_x = Settings.WIDTH / 2.0F;
        card.target_y = Settings.HEIGHT / 2.0F;
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.transparency = 0;
        card.targetTransparency = 1;
        card.applyPowers();
        AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        addToTop(new NewQueueCardAction(card, target, false, true));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new RelicWitchyDice();
    }
}
