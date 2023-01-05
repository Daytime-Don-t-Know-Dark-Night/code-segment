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
        println("HOST: " + prop_dev.getProperty(S08_Constants.JDBC_URL))
        println("DRIVER: " + prop_dev.getProperty(S08_Constants.DRIVER_NAME))
        println("USERNAME: " + prop_dev.getProperty(S08_Constants.USERNAME))
        println("PASSWORD: " + prop_dev.getProperty(S08_Constants.PASSWORD))

        println("prod properties: ")
        println("HOST: " + prop_prod.getProperty(S08_Constants.JDBC_URL))
        println("DRIVER: " + prop_prod.getProperty(S08_Constants.DRIVER_NAME))
        println("USERNAME: " + prop_prod.getProperty(S08_Constants.USERNAME))
        println("PASSWORD: " + prop_prod.getProperty(S08_Constants.PASSWORD))

    }

    def load(env: String): Properties = {
        val prop = new Properties()

        var url: String = null
        if (S08_Constants.DEV.equals(env)) {
            url = S08_Constants.APPLICATION_DEV
        }
        if (S08_Constants.PROD.equals(env)) {
            url = S08_Constants.APPLICATION_PROD
        }

        prop.load(getClass.getResourceAsStream(url))
        prop
    }

}
