package de.nazaruk.acceptance;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.concurrent.TimeUnit;

/**
 * https://www.ibm.com/developerworks/library/a-automating-ria/ :)
 * Created by nazaruk on 11/9/16.
 */
public class BrowserDriver {

    private static WebDriver mDriver;
    private final static Logger logger = Logger.getLogger(BrowserDriver.class);

    static {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
    }

    public static synchronized WebDriver getCurrentDriver() {
        if (mDriver == null) {
            try {
                mDriver = new ChromeDriver();
                mDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            } finally {
                Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
            }
        }
        return mDriver;
    }

    public static void loadPage(String url) {
        logger.info("Directing browser to:" + url);
        getCurrentDriver().get(url);
    }

    public static void close() {
        try {
            getCurrentDriver().quit();
            mDriver = null;
            BrowserDriver.logger.info("closing the browser");
        } catch (UnreachableBrowserException e) {
            BrowserDriver.logger.info("cannot close browser: unreachable browser");
        }
    }


    private static class BrowserCleanup implements Runnable {
        public void run() {
            BrowserDriver.logger.info("Closing the browser");
            close();
        }
    }
}

