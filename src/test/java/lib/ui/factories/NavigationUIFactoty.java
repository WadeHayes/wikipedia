package lib.ui.factories;

import lib.Platform;
import lib.ui.NavigationUI;
import lib.ui.android.AndroidNavigationUI;
import lib.ui.ios.IOSNavigationUI;
import lib.ui.mobile_web.MwNavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NavigationUIFactoty {
    public static NavigationUI get(RemoteWebDriver driver){
        if (Platform.getInstance().isAndroid()){
            return new AndroidNavigationUI(driver);
        }else if (Platform.getInstance().isIOS()){
            return new IOSNavigationUI(driver);
        }else {
            return new MwNavigationUI(driver);
        }
    }
}
