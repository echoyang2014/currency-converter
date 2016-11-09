package de.nazaruk.acceptance;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by nazaruk on 11/9/16.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = {"de.nazaruk.acceptance.scenarios"}
)
public class RunAcceptanceTests {

}
