package cards.colorless.vapor;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CardVaporEntropicBrew extends CardVapor {
    public static final String ID = "reliquary:VaporEntropicBrew";
    private static final String IMG_PATH = "reliquaryAssets/images/cards/colorless/vapors/entropicBrew.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;

    public CardVaporEntropicBrew() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        selfRetain = true;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        List<Integer> indices = IntStream.range(0, p.potionSlots).boxed().collect(Collectors.toList());
        Collections.shuffle(indices);
        for (int index : indices) {
            AbstractPotion oldPotion = p.potions.get(index);
            if (oldPotion instanceof PotionSlot) {
                continue;
            }
            AbstractPotion newPotion;
            do {
                newPotion = AbstractDungeon.returnRandomPotion(true);
            } while (oldPotion.ID.equals(newPotion.ID));
            p.removePotion(oldPotion);
            p.obtainPotion(newPotion);
            if (!upgraded) {
                return;
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}