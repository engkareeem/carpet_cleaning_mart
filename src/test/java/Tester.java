import io.cucumber.java.bs.A;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features
        = "feature",
        plugin = {"summary","html:target/cucumber/wikipedia.html"},

        monochrome = true,
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        glue = {""}
)

public class Tester {
    static AuthTest authTest = new AuthTest();

    public static void main(String[] args) throws InterruptedException {
        authTest.testSignUpCustomer();
        authTest.testLogIn();
        authTest.testIsLoggedIn();
        authTest.testLogOut();

    }
}