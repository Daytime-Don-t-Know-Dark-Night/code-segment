package boluo.log;

import org.apache.log4j.Logger;

/**
 * @Author dingc
 * @Date 2022-10-13 21:39
 * @Description
 */
public class LogTest {

    private static final Logger log = Logger.getLogger(LogTest.class);

    public static void main(String[] args) {
        log.info("this is a log test: info!");
        log.debug("this is a log test: debug!");
        log.error("this is a log test: error!");
    }
}
