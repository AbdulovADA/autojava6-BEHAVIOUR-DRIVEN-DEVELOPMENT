package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {

    private final String Button = "[data-test-id='action-deposit']";
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final String searchAttribute = "data-test-id";
    private SelenideElement heading = $x("//h1[contains(text(), 'Ваши карты')]");
    private ElementsCollection cards = $$(".list__item div");

    public DashboardPage() {
        heading.shouldBe(Condition.visible);

    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public int getCardBalance(DataHelper.Card selectCard) {
        String text = cards.find(Condition.attribute(searchAttribute, selectCard.getId())).getText();
        return extractBalance(text);
    }

    public TransferPage replenishCard(DataHelper.Card selectTo) {
        cards.findBy(Condition.attribute(searchAttribute, selectTo.getId())).find(Button).click();
        return new TransferPage();
    }
}