package com.vytrack.pages.fleet;

import com.vytrack.utilities.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VehiclesPage extends BasePage {
    @FindBy(xpath = "//label[text()='Page:']/following-sibling::ul//input")
    public WebElement pageNumber;
    public Integer getPageNumber(){
        return Integer.valueOf(pageNumber.getAttribute("value"));
    }
}
