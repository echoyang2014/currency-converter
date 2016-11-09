package de.nazaruk.acceptance.scenarios;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.nazaruk.acceptance.BrowserDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.UUID;

public class RegistrationScenario {

    RegistrationContainer registrationContainer;

    @Given("^I navigate to the registration page$")
    public void I_navigate_to_regisration() throws Throwable {
        BrowserDriver.loadPage("http://localhost:8080/registration");

        registrationContainer = new RegistrationContainer();
    }

    @When("^I try to register with valid data$")
    public void I_try_to_register() throws Throwable {
        registrationContainer.getUsernameText().sendKeys(UUID.randomUUID().toString());
        registrationContainer.getPasswordText().sendKeys("test_password");
        registrationContainer.getPasswordConfirmText().sendKeys("test_password");
    }

    @And("^I press submit button")
    public void I_press_submit_button() throws Throwable {
        registrationContainer.getSubmit().click();
    }

    @Then("^I should see currency converter page with button get rate")
    public void I_should_see_currency_page() throws Throwable {
        Assert.assertNotNull(registrationContainer.getRateButton());
    }

    private  static class RegistrationContainer {

        public  WebElement getUsernameText(){
            return BrowserDriver.getCurrentDriver().findElement(By.name("username"));
        }

        public  WebElement getPasswordText(){
            return BrowserDriver.getCurrentDriver().findElement(By.name("password"));
        }

        public  WebElement getPasswordConfirmText(){
            return BrowserDriver.getCurrentDriver().findElement(By.name("passwordConfirm"));
        }

        public  WebElement getSubmit(){
            return  BrowserDriver.getCurrentDriver().findElement(By.id("submit"));
        }

        public  WebElement getRateButton(){
            return BrowserDriver.getCurrentDriver().findElement(By.id("getRate"));
        }
    }
}


