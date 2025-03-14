package com.boluo.command;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author dingc
 * @Date 2022-09-19 23:09
 * @Description
 */
public class CommandLineTest {

    /*
    -env=prod
    -js=2022-12-21/01:01:01
    -cp=/usr/conf
    * */
    public static void main(String[] args) throws ParseException {

        CommandLine cli = new GnuParser().parse(new Options()
                .addOption("env", "environment", true, "")
                .addOption("js", "job_start_time", true, "")
                .addOption("cp", "config_path", true, ""), args);

        // 变量
        String env = cli.getOptionValue("env", "dev");
        String jobStartTime = cli.getOptionValue("js", "");
        String configPath = cli.getOptionValue("cp");

        LocalDateTime datetime = LocalDateTime.parse(jobStartTime, DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss"));

        System.out.println("env: " + env);
        System.out.println("jobStartTime: " + jobStartTime);
        System.out.println("configPath: " + configPath);

    }

}
