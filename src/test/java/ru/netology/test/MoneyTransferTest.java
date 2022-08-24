package ru.netology.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {

    @BeforeEach
    void shouldOpen() {
        Selenide.open("http://localhost:9999");
    }

    @Test
    void ShouldTransferMoneyFromSecondToFirstCard() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int beforeBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo(authInfo));
        int beforeBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo(authInfo));
        var transferPage = dashboardPage.replenishCard(DataHelper.getFirstCardInfo(authInfo));
        transferPage.transferMoney(
                DataHelper.getSecondCardInfo(authInfo).getCardNumber(), 100);
        int afterBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo(authInfo));
        int afterBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo(authInfo));
        Assertions.assertEquals(beforeBalanceFirstCard + 100, afterBalanceFirstCard);
        Assertions.assertEquals(beforeBalanceSecondCard - 100, afterBalanceSecondCard);
    }

    @Test
    void ShouldTransferMoneyFromFirstToSecondCard() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int beforeBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo(authInfo));
        int beforeBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo(authInfo));
        var transferPage = dashboardPage.replenishCard(DataHelper.getSecondCardInfo(authInfo));
        transferPage.transferMoney(
                DataHelper.getFirstCardInfo(authInfo).getCardNumber(), 100);
        int afterBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo(authInfo));
        int afterBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo(authInfo));
        Assertions.assertEquals(beforeBalanceFirstCard - 100, afterBalanceFirstCard);
        Assertions.assertEquals(beforeBalanceSecondCard + 100, afterBalanceSecondCard);
    }

    @Test
    void ShouldTransferZeroSumFromFirstToSecondCard() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int beforeBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo(authInfo));
        int beforeBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo(authInfo));
        var transferPage = dashboardPage.replenishCard(DataHelper.getSecondCardInfo(authInfo));
        transferPage.transferMoney(
                DataHelper.getFirstCardInfo(authInfo).getCardNumber(), 0);
        int afterBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo(authInfo));
        int afterBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo(authInfo));
        Assertions.assertEquals(beforeBalanceFirstCard, afterBalanceFirstCard);
        Assertions.assertEquals(beforeBalanceSecondCard, afterBalanceSecondCard);
    }

    @Test
    void ShouldTransferFromFirstToSecondCardBiggerAmountThanOnCard() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var transferPage = dashboardPage.replenishCard(DataHelper.getSecondCardInfo(authInfo));
        transferPage.transferMoney(
                DataHelper.getFirstCardInfo(authInfo).getCardNumber(), 12000);
        transferPage.errorMessage();
    }
}