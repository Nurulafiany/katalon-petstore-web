import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

TestData loginData = findTestData('LoginData')

for (int row = 1; row <= loginData.getRowNumbers(); row++) {
    WebUI.openBrowser('')

    WebUI.navigateToUrl(GlobalVariable.signInUrl)

    WebUI.setText(findTestObject('Petstore/Page_SignIn/input_Username'), loginData.getValue('username', row))

    WebUI.setText(findTestObject('Petstore/Page_SignIn/input_Password'), loginData.getValue('password', row))

    WebUI.click(findTestObject('Petstore/Page_SignIn/input_Signon'))

    if (loginData.getValue('expected', row) == 'valid') {
        WebUI.verifyElementPresent(findTestObject('Petstore/Page_SignIn/a_SignOut'), 10)
    } else {
        WebUI.verifyElementPresent(findTestObject('Petstore/Page_SignIn/ul_Messages'), 10)

        String messageText = WebUI.getText(findTestObject('Petstore/Page_SignIn/ul_Messages'))

        assert messageText.toLowerCase().contains('invalid')
    }

    WebUI.closeBrowser()
}
