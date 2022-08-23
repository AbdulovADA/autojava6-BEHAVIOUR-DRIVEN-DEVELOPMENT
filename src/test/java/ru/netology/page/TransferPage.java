package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class TransferPage {

    private SelenideElement heading = $x("//h1[contains(text(), 'Пополнение карты')]");
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement confirmButton = $("[data-test-id=action-transfer]");
    private SelenideElement errorMassage = $(".notification__title");


    public TransferPage() {
        heading.shouldBe(Condition.visible);
    }

    public DashboardPage transferMoney(String cardFrom, int sum) {
        amountField.sendKeys(Keys.COMMAND + "A",BACK_SPACE);
        amountField.setValue(String.valueOf(sum));
        fromField.sendKeys(Keys.COMMAND + "A",BACK_SPACE);
        fromField.setValue(cardFrom);
        confirmButton.click();
        return new DashboardPage();
    }
    public void errorMessage (){
        errorMassage.shouldHave(exactText("Ошибка!"))
                .shouldBe(Condition.visible);
    }
}