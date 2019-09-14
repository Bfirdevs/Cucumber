package com.vytrack.pages;

import com.vytrack.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HRAppDeptEmpPage {
    private WebDriver driver;
    public HRAppDeptEmpPage(){
        this.driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }
    @FindBy(id="pt1:ot1")
    public WebElement departmentID;
    @FindBy(id="pt1:ot2")
    public WebElement departmentName;
    @FindBy(id="pt1:ot3")
    public WebElement managerID;
    @FindBy(id="pt1:ot4")
    public WebElement locationID;
    @FindBy(id="pt1:cb3")
    public WebElement Next;
}
