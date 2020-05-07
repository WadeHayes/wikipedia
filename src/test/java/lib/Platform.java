package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Platform {
    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_IOS = "iOS";
    private static final String PLATFORM_MOBILE_WEB = "mobile_web";
    private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    private static Platform instance;
    private Platform(){}
    public static Platform getInstance(){
        if (instance == null){
            instance = new Platform();
        }
        return instance;
    }

    public RemoteWebDriver getDriver() throws Exception {
        URL url = new URL(APPIUM_URL);
        if (isAndroid()){
            return new AndroidDriver(url, this.getAndroidDesiredCapabililies());
        }else if (isIOS()){
            return new IOSDriver(url, this.getIOSDesiredCapabililies());
        }else if (isMw()){
            return new ChromeDriver(this.getMvChromeOptions() );
        } else {
            throw new Exception("Cannot detected type of platform. Platform: " + this.getPlatformVariables());
        }
    }

    public boolean isAndroid(){
        return this.isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS(){
        return this.isPlatform(PLATFORM_IOS);
    }

    public boolean isMw(){
        return this.isPlatform(PLATFORM_MOBILE_WEB);
    }

    private DesiredCapabilities getAndroidDesiredCapabililies(){
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus5X");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, "/Users/vladislav/IdeaProjects/JavaAppium/apks/org.wikipedia.apk");
        return desiredCapabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabililies(){
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.0");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone SE");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, "/Users/vladislav/IdeaProjects/JavaAppium/apks/Wikipedia.app");
        return desiredCapabilities;
    }

    private ChromeOptions getMvChromeOptions(){
        Map<String, Object> deviceMetrics = new HashMap<String, Object>();
        deviceMetrics.put("width", 360);
        deviceMetrics.put("heigth", 640);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<String, Object>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=340,640");
        return chromeOptions;
    }

    private boolean isPlatform(String myPlatform){
        String platform = this.getPlatformVariables();
        return myPlatform.equals(platform);
    }

    public String getPlatformVariables(){
        return System.getenv("PLATFORM");
    }
}
