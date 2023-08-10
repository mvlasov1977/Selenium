package selenium;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static javax.swing.UIManager.put;


public class SelenoidOptions {
    private String name;
    private String timeout;
    private String timezone;
    private String isTrashButtonAdd;
    private String typeVideoRecording;

    public SelenoidOptions(String name, String timeout, String timezone, String isTrashButtonAdd, String typeVideoRecording) {
        this.name = name;
        this.timeout = timeout;
        this.timezone = timezone;
        this.isTrashButtonAdd = isTrashButtonAdd;
        this.typeVideoRecording = typeVideoRecording;
    }
    private HashMap<String, Object> makeOptions(String name, String timeout, String timezone, String isTrashButtonAdd, String typeVideoRecording){
        HashMap<String, Object> myHashMap = new HashMap<>();
        /* How to add test badge */
        put("name", name);

        /* How to set session timeout */
        put("sessionTimeout", timeout);

        /* How to set timezone */
        put("env", new ArrayList<String>() {{
            add(timezone);
        }});

        /* How to add "trash" button */
        put("labels", new HashMap<String, Object>() {{
            put("manual", isTrashButtonAdd);
        }});

        /* How to enable video recording */
        put(typeVideoRecording, true);

        return myHashMap;
    }
    public RemoteWebDriver getChromeDriver(String browserVersion, String selenoidURL) throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("browserVersion", browserVersion);
        options.setCapability("selenoid:options",makeOptions(name, timeout, timezone, isTrashButtonAdd, typeVideoRecording));
        RemoteWebDriver chromeDriver = new RemoteWebDriver(new URL(selenoidURL), options);
        return chromeDriver;
    }
}
