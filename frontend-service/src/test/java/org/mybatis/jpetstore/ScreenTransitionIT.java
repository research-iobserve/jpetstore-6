/**
 *    Copyright (C) 2010-2017 the original author or authors.
 *                  2017 iObserve Project (https://www.iobserve-devops.net)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.jpetstore;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit.ScreenShooter;

/**
 * Integration tests for screen transition.
 *
 * @author Kazuki Shimizu
 */
public class ScreenTransitionIT {

  /**
   * screen shooter.
   */
  @Rule
  public ScreenShooter screenShooter = ScreenShooter.failedTests();

  @BeforeClass
  public static void setupSelenide() {
    Configuration.browser = WebDriverRunner.HTMLUNIT;
    Configuration.timeout = TimeUnit.SECONDS.toMillis(10);
    Configuration.baseUrl = "http://localhost:8080/jpetstore";
  }

  @After
  public void logout() {
    final SelenideElement element = Selenide.$(By.linkText("Sign Out"));
    if (element.exists()) {
      element.click();
    }
  }

  @Test
  public void testOrder() {

    // Open the home page
    Selenide.open("/");
    Assertions.assertThat(Selenide.title()).isEqualTo("JPetStore Demo");

    // Move to the top page
    Selenide.$(By.linkText("Enter the Store")).click();
    Selenide.$(By.id("WelcomeContent")).shouldBe(Condition.text(""));

    // Move to sign in page & sign
    Selenide.$(By.linkText("Sign In")).click();
    Selenide.$(By.name("username")).setValue("j2ee");
    Selenide.$(By.name("password")).setValue("j2ee");
    Selenide.$(By.name("signon")).click();
    Selenide.$(By.id("WelcomeContent")).shouldBe(Condition.text("Welcome ABC!"));

    // Search items
    Selenide.$(By.name("keyword")).setValue("fish");
    Selenide.$(By.name("searchProducts")).click();
    Selenide.$$(By.cssSelector("#Catalog table tr")).shouldHaveSize(4);

    // Select item
    Selenide.$(By.linkText("Fresh Water fish from China")).click();
    Selenide.$(By.cssSelector("#Catalog h2")).shouldBe(Condition.text("Goldfish"));

    // Add a item to the cart
    Selenide.$(By.linkText("Add to Cart")).click();
    Selenide.$(By.cssSelector("#Catalog h2")).shouldBe(Condition.text("Shopping Cart"));

    // Checkout cart items
    Selenide.$(By.linkText("Proceed to Checkout")).click();
    Assertions.assertThat(Selenide.title()).isEqualTo("JPetStore Demo");

    // Confirm order information
    Selenide.$(By.name("newOrder")).click();
    Selenide.$(By.cssSelector("#Catalog"))
        .shouldBe(Condition.text("Please confirm the information below and then press continue..."));

    // Submit order
    Selenide.$(By.linkText("Confirm")).click();
    Selenide.$(By.cssSelector(".messages li")).shouldBe(Condition.text("Thank you, your order has been submitted."));
    final String orderId = ScreenTransitionIT.extractOrderId(Selenide.$(By.cssSelector("#Catalog table tr")).text());

    // Show profile page
    Selenide.$(By.linkText("My Account")).click();
    Selenide.$(By.cssSelector("#Catalog h3")).shouldBe(Condition.text("User Information"));

    // Show orders
    Selenide.$(By.linkText("My Orders")).click();
    Selenide.$(By.cssSelector("#Content h2")).shouldBe(Condition.text("My Orders"));

    // Show order detail
    Selenide.$(By.linkText(orderId)).click();
    Assertions.assertThat(ScreenTransitionIT.extractOrderId(Selenide.$(By.cssSelector("#Catalog table tr")).text()))
        .isEqualTo(orderId);

    // Sign out
    Selenide.$(By.linkText("Sign Out")).click();
    Selenide.$(By.id("WelcomeContent")).shouldBe(Condition.text(""));

  }

  @Test
  public void testUpdateProfile() {
    // Open the home page
    Selenide.open("/");
    Assertions.assertThat(Selenide.title()).isEqualTo("JPetStore Demo");

    // Move to the top page
    Selenide.$(By.linkText("Enter the Store")).click();
    Selenide.$(By.id("WelcomeContent")).shouldBe(Condition.text(""));

    // Move to sign in page & sign
    Selenide.$(By.linkText("Sign In")).click();
    Selenide.$(By.name("username")).setValue("j2ee");
    Selenide.$(By.name("password")).setValue("j2ee");
    Selenide.$(By.name("signon")).click();
    Selenide.$(By.id("WelcomeContent")).shouldBe(Condition.text("Welcome ABC!"));

    // Show profile page
    Selenide.$(By.linkText("My Account")).click();
    Selenide.$(By.cssSelector("#Catalog h3")).shouldBe(Condition.text("User Information"));
    Selenide.$$(By.cssSelector("#Catalog table td")).get(1).shouldBe(Condition.text("j2ee"));

    // Edit account
    Selenide.$(By.name("editAccount")).click();
    Selenide.$(By.cssSelector("#Catalog h3")).shouldBe(Condition.text("User Information"));
    Selenide.$$(By.cssSelector("#Catalog table td")).get(1).shouldBe(Condition.text("j2ee"));
  }

  @Test
  public void testRegistrationUser() {
    // Open the home page
    Selenide.open("/");
    Assertions.assertThat(Selenide.title()).isEqualTo("JPetStore Demo");

    // Move to the top page
    Selenide.$(By.linkText("Enter the Store")).click();
    Selenide.$(By.id("WelcomeContent")).shouldBe(Condition.text(""));

    // Move to sign in page & sign
    Selenide.$(By.linkText("Sign In")).click();
    Selenide.$(By.cssSelector("#Catalog p")).shouldBe(Condition.text("Please enter your username and password."));

    // Move to use registration page
    Selenide.$(By.linkText("Register Now!")).click();
    Selenide.$(By.cssSelector("#Catalog h3")).shouldBe(Condition.text("User Information"));

    // Create a new user
    final String userId = String.valueOf(System.currentTimeMillis());
    Selenide.$(By.name("username")).setValue(userId);
    Selenide.$(By.name("password")).setValue("password");
    Selenide.$(By.name("repeatedPassword")).setValue("password");
    Selenide.$(By.name("account.firstName")).setValue("Jon");
    Selenide.$(By.name("account.lastName")).setValue("MyBatis");
    Selenide.$(By.name("account.email")).setValue("jon.mybatis@test.com");
    Selenide.$(By.name("account.phone")).setValue("09012345678");
    Selenide.$(By.name("account.address1")).setValue("Address1");
    Selenide.$(By.name("account.address2")).setValue("Address2");
    Selenide.$(By.name("account.city")).setValue("Minato-Ku");
    Selenide.$(By.name("account.state")).setValue("Tokyo");
    Selenide.$(By.name("account.zip")).setValue("0001234");
    Selenide.$(By.name("account.country")).setValue("Japan");
    Selenide.$(By.name("account.languagePreference")).selectOption("japanese");
    Selenide.$(By.name("account.favouriteCategoryId")).selectOption("CATS");
    Selenide.$(By.name("account.listOption")).setSelected(true);
    Selenide.$(By.name("account.bannerOption")).setSelected(true);
    Selenide.$(By.name("newAccount")).click();
    Selenide.$(By.id("WelcomeContent")).shouldBe(Condition.text(""));

    // Move to sign in page & sign
    Selenide.$(By.linkText("Sign In")).click();
    Selenide.$(By.name("username")).setValue(userId);
    Selenide.$(By.name("password")).setValue("password");
    Selenide.$(By.name("signon")).click();
    Selenide.$(By.id("WelcomeContent")).shouldBe(Condition.text("Welcome Jon!"));

  }

  @Test
  public void testSelectItems() {
    // Open the home page
    Selenide.open("/");
    Assertions.assertThat(Selenide.title()).isEqualTo("JPetStore Demo");

    // Move to the top page
    Selenide.$(By.linkText("Enter the Store")).click();
    Selenide.$(By.id("WelcomeContent")).shouldBe(Condition.text(""));

    // Move to category
    Selenide.$(By.cssSelector("#SidebarContent a")).click();
    Selenide.$(By.cssSelector("#Catalog h2")).shouldBe(Condition.text("Fish"));

    // Move to items
    Selenide.$(By.linkText("FI-SW-01")).click();
    Selenide.$(By.cssSelector("#Catalog h2")).shouldBe(Condition.text("Angelfish"));

    // Move to item detail
    Selenide.$(By.linkText("EST-1")).click();
    Selenide.$$(By.cssSelector("#Catalog table tr td")).get(2).shouldBe(Condition.text("Large Angelfish"));

    // Back to items
    Selenide.$(By.linkText("Return to FI-SW-01")).click();
    Selenide.$(By.cssSelector("#Catalog h2")).shouldBe(Condition.text("Angelfish"));

    // Back to category
    Selenide.$(By.linkText("Return to FISH")).click();
    Selenide.$(By.cssSelector("#Catalog h2")).shouldBe(Condition.text("Fish"));

    // Back to the top page
    Selenide.$(By.linkText("Return to Main Menu")).click();
    Selenide.$(By.id("WelcomeContent")).shouldBe(Condition.text(""));

  }

  @Test
  public void testViewCart() {

    // Open the home page
    Selenide.open("/");
    Assertions.assertThat(Selenide.title()).isEqualTo("JPetStore Demo");

    // Move to the top page
    Selenide.$(By.linkText("Enter the Store")).click();
    Selenide.$(By.id("WelcomeContent")).shouldBe(Condition.text(""));

    // Move to cart
    Selenide.$(By.name("img_cart")).click();
    Selenide.$(By.cssSelector("#Catalog h2")).shouldBe(Condition.text("Shopping Cart"));

  }

  @Test
  public void testViewHelp() {

    // Open the home page
    Selenide.open("/");
    Assertions.assertThat(Selenide.title()).isEqualTo("JPetStore Demo");

    // Move to the top page
    Selenide.$(By.linkText("Enter the Store")).click();
    Selenide.$(By.id("WelcomeContent")).shouldBe(Condition.text(""));

    // Move to help
    Selenide.$(By.linkText("?")).click();
    Selenide.$(By.cssSelector("#Content h1")).shouldBe(Condition.text("JPetStore Demo"));

  }

  private static String extractOrderId(final String target) {
    final Matcher matcher = Pattern.compile("Order #(\\d{4}) .*").matcher(target);
    String orderId = "";
    if (matcher.find()) {
      orderId = matcher.group(1);
    }
    return orderId;
  }

}
