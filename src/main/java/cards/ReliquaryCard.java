package cards;

import basemod.abstracts.CustomCard;

public abstract class ReliquaryCard extends CustomCard {
    public ReliquaryCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }
}
