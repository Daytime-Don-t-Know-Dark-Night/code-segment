package boluo.basic

import java.util.Properties

/**
 * @Author dingc
 * @Date 2022-10-03 23:41
 * @Description
 */
object S08_LoadConfig {

    // env = dev
    // env = prod
    def main(args: Array[String]): Unit = {
        val prop_dev = load("dev")
        val prop_prod = load("prod")

        println("dev properties: ")
        println("HOST: " + prop_dev.getProperty(Constants.JDBC_URL))
        println("DRIVER: " + prop_dev.getProperty(Constants.DRIVER_NAME))
        println("USERNAME: " + prop_dev.getProperty(Constants.USERNAME))
        println("PASSWORD: " + prop_dev.getProperty(Constants.PASSWORD))

        println("prod properties: ")
        println("HOST: " + prop_prod.getProperty(Constants.JDBC_URL))
        println("DRIVER: " + prop_prod.getProperty(Constants.DRIVER_NAME))
        println("USERNAME: " + prop_prod.getProperty(Constants.USERNAME))
        println("PASSWORD: " + prop_prod.getProperty(Constants.PASSWORD))

    }

    def load(env: String): Properties = {
        val prop = new Properties()

        var url: String = null
        if (Constants.DEV.equals(env)) {
            url = Constants.APPLICATION_DEV
        }
        if (Constants.PROD.equals(env)) {
            url = Constants.APPLICATION_PROD
        }

        prop.load(getClass.getResourceAsStream(url))
        prop
    }

}
