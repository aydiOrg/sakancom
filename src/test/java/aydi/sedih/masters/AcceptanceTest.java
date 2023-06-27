package aydi.sedih.masters;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions (
        features = "features",
        plugin = {"summary","html:target/cucumber/report.html"},
        monochrome = true,
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        glue = "aydi.sedih.masters"
)

public class AcceptanceTest {
}
