package shedar.mods.ic2.nuclearcontrol.api.cards;

import shedar.mods.ic2.nuclearcontrol.api.PanelString;
import shedar.mods.ic2.nuclearcontrol.utils.DataSorter;

import java.util.ArrayList;
import java.util.List;

public class CardData {

    private PanelString cardTitle;
    private List<PanelString> cardData;
    private List<PanelString> fullCardData;

    public CardData(String cardTitle, List<PanelString> cardData, List<PanelString> fullCardData){
        this.cardTitle = new PanelString();
        this.cardTitle.textCenter = cardTitle;
        this.fullCardData = fullCardData;
        this.cardData = cardData;
    }

    public List<PanelString> getCardDataWithTitle(){
        if (cardData == null) return null;

        if (cardTitle.textCenter.isEmpty()) return cardData;

        List<PanelString> dataWithTitle = new ArrayList<>();
        dataWithTitle.add(cardTitle);
        dataWithTitle.addAll(cardData);
        return dataWithTitle;
    }

    public void sortByPrefix(DataSorter sorter){
        sorter.sortListByPrefix(this.cardData, this.fullCardData);
    }
}
