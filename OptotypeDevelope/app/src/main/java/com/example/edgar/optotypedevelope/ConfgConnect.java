package com.example.edgar.optotypedevelope;

/**
 * Created by Edgar on 22/04/2018.
 */

public class ConfgConnect {

    private static String ipWebService;
    private static String ipShowTest;
    private static String  portConecction;

    public ConfgConnect() {

        ipWebService = "192.168.43.108";
        ipShowTest = "192.168.1.44";
        portConecction = "5000";

    }

    public static String getIpWebService() {
        return ipWebService;
    }

    public static void setIpWebService(String ipWebService) {
        ConfgConnect.ipWebService = ipWebService;
    }

    public static String getIpShowTest() {
        return ipShowTest;
    }

    public static void setIpShowTest(String ipShowTest) {
        ConfgConnect.ipShowTest = ipShowTest;
    }

    public static String getPortConecction() {
        return portConecction;
    }

    public static void setPortConecction(String portConecction) {
        ConfgConnect.portConecction = portConecction;
    }
}
