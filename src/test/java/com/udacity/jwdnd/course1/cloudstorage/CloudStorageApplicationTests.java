package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@Autowired
	private CredentialService credentialService;

	private EncryptionService encryptionService;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}


	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}


	public void getSignuPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}




	public void getHomePageError() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	@Order(1)
	public void unauthorizedUserCanOnlyAccessLoginAndSinupPage(){
		this.getLoginPage();
		this.getSignuPage();
		this.getHomePageError();
	}

	@Test
	@Order(2)
	public void userSignupLoginLogoutSuccessTestCase() throws InterruptedException {

		String username = "netcha";
		String firstName = "Nelson";
		String lastName = "oliveira";
		String password = "netchas123";

		driver.get("http://localhost:" + port + "/signup");
		SignUpPage signupPage = new SignUpPage(driver);
		signupPage.signUp(firstName, lastName, username, password);

		Thread.sleep(3000);

		this.login(username, password);

		Thread.sleep(3000);

		HomePage homePage = new HomePage(driver);
		homePage.getHomePageTitle();
		homePage.logout();

		this.getHomePageError();

	}

	private void login(String username, String password) {
		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage =  new LoginPage(driver);
		loginPage.login(username, password);
	}

	@Test
	@Order(3)
	public void createNoteSuccesstestCase() throws InterruptedException {
		String title = "noteTeste";
		String description = "descriptionTeste";

		this.login("netcha", "netchas123");

		NotePage notePage = new NotePage(driver);

		notePage.clickAddNoteButton();
		notePage.fillNoteData(title,description);
		notePage.clickSubmitNoteButton();

		ResultPage resultPage = new ResultPage(driver);
		resultPage.goToHomePage();

		notePage.goToNavTab();

		notePage.clickEditNoteButton();

		Assertions.assertEquals(title, notePage.getNoteTitleDisplayed());
		Assertions.assertEquals(description, notePage.getNoteDescriptionDisplayed());

		HomePage homePage = new HomePage(this.driver);

		homePage.logout();
	}

	@Test
	@Order(4)
	public void editNoteSuccesstestCase() throws InterruptedException {
		String title = "noteEditTeste";
		String description = "descriptionEditTeste";

		this.login("netcha", "netchas123");

		NotePage notePage = new NotePage(this.driver);

		notePage.clickEditNoteButton();
		notePage.fillNoteData(title,description);
		notePage.clickSubmitNoteButton();

		ResultPage resultPage = new ResultPage(driver);
		resultPage.goToHomePage();

		notePage.goToNavTab();

		notePage.clickEditNoteButton();

		Assertions.assertEquals(title, notePage.getNoteTitleDisplayed());
		Assertions.assertEquals(description, notePage.getNoteDescriptionDisplayed());

		HomePage homePage = new HomePage(driver);
		homePage.logout();
	}

	@Test
	@Order(5)
	public void deleteNoteSuccesstestCase() throws InterruptedException {

		this.login("netcha", "netchas123");

		NotePage notePage = new NotePage(this.driver);

		notePage.deleteNote();

		ResultPage resultPage = new ResultPage(driver);
		resultPage.goToHomePage();

		notePage.goToNavTab();

		assertThrows(TimeoutException.class, ()->{ notePage.deleteNote(); });

		HomePage homePage = new HomePage(driver);

		homePage.logout();
	}
	@Test
	@Order(6)
	public void createCredentialAndVerifyDisplayWithPasswordEncryptedSuccesstestCase() throws InterruptedException {

		this.login("netcha", "netchas123");

		encryptionService = new EncryptionService();

		CredentialPage credentialPage = new CredentialPage(this.driver);

		//insert 4 credentials;
		for (int i = 1; i < 5; i++){

			credentialPage.clickAddCredentialButton();
			credentialPage.fillCredentialData("urlTeste"+i, "urlusername"+i, "12345"+i);
			credentialPage.clickCredentialSubmitButton();

			ResultPage resultPage = new ResultPage(driver);
			resultPage.goToHomePage();

			Credential credential = credentialService.getCredentialById(i);

			credentialPage.goToCredentialTab();

			Assertions.assertEquals("urlTeste"+i, credentialPage.geturlDisplayed(i));
			Assertions.assertEquals("urlusername"+i, credentialPage.getUsernameDisplayed(i));
			Assertions.assertEquals(encryptionService.encryptValue("12345"+i, credential.getKey()),
					credentialPage.getEncryptedPassword(i));

		}

		HomePage homePage = new HomePage(driver);

		homePage.logout();
	}

	@Test
	@Order(7)
	public void editCredentialSuccesstestCase() throws InterruptedException {

		this.login("netcha", "netchas123");

		CredentialPage credentialPage = new CredentialPage(this.driver);

		encryptionService = new EncryptionService();


		for (int i = 1; i < 5; i++){

			credentialPage.clickEditCredentialButton(i);

			credentialPage.fillCredentialData("urlEditTeste"+i, "urlEditusername"+i, "1234567"+i);
			credentialPage.clickCredentialSubmitButton();

			ResultPage resultPage = new ResultPage(driver);
			resultPage.goToHomePage();

			Credential credential = credentialService.getCredentialById(i);

			credentialPage.goToCredentialTab();

			credentialPage.clickEditCredentialButton(i);


			Assertions.assertEquals("urlEditTeste"+i, credentialPage.getUrlDisplayedInModel());
			Assertions.assertEquals("urlEditusername"+i, credentialPage.getUsernameDisplayedInModel());

			Assertions.assertEquals(encryptionService.decryptValue(credentialPage.getEncryptedPassword(i),
					credential.getKey()), "1234567"+i);

		}

		HomePage homePage = new HomePage(driver);

		homePage.logout();
	}

	@Test
	@Order(8)
	public void deleteCredentialSuccesstestCase() throws InterruptedException {

		this.login("netcha", "netchas123");

		CredentialPage credentialPage = new CredentialPage(this.driver);

		for (int i = 1; i < 5; i++){

			credentialPage.deleteCredential();

			ResultPage resultPage = new ResultPage(driver);
			resultPage.goToHomePage();

			credentialPage.goToCredentialTab();

		}

		assertThrows(TimeoutException.class, ()->{credentialPage.deleteCredential();});

		HomePage homePage = new HomePage(driver);

		homePage.logout();
	}

}
