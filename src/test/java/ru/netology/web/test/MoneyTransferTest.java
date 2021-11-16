package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.Card.getFirstCard;
import static ru.netology.web.data.DataHelper.Card.getSecondCard;


class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        Configuration.browser = "chrome";
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        int amount = 1000;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val firstCardInfo = getFirstCard();
        val secondCardInfo = getSecondCard();
        val expectedResultFirstCard = dashboardPage.getFirstCardBalance() - amount;
        val expectedResultSecondCard = dashboardPage.getSecondCardBalance() + amount;
        val transferPage = dashboardPage.chooseCard(secondCardInfo);
        transferPage.transferFromCardToCard(firstCardInfo, amount);
        val actualResultsFirstCard = dashboardPage.getFirstCardBalance();
        val actualResultsSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(expectedResultFirstCard, actualResultsFirstCard);
        assertEquals(expectedResultSecondCard, actualResultsSecondCard);
    }
}
