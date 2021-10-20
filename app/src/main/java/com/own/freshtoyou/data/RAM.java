package com.own.freshtoyou.data;

public class RAM {
    private static String t;
    private static String h;
    private static String set_ts_t;

    public static synchronized String getSet_ts_t() {
        return RAM.set_ts_t;
    }

    public static synchronized void setSet_ts_t(String set_ts_t) {
        RAM.set_ts_t = set_ts_t;
    }

    public static synchronized String getT() {
        return "温度："+RAM.t+"℃";
    }

    public static synchronized void setT(String t) {
        RAM.t = t;
    }

    public static synchronized String getH() {
        return "湿度："+RAM.h+"%";
    }

    public static synchronized void setH(String h) {
        RAM.h = h;
    }


//    private static RAM ram;
//
//    public static RAM getInstance (){
//        if (ram == null){
//            synchronized (RAM.class){
//                if(ram == null){
//                    ram = new RAM();
//                    t = "10";
//                    h = "70";
//                    set_ts_t = "10";
//                }
//            }
//        }
//        return ram;
//    }
}
