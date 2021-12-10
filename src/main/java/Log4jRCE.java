public class Log4jRCE {

    static {
        System.out.println("I am Log4jRCE from remote!!!");
//        try {
//            String[] cmd = {"code"};
//            java.lang.Runtime.getRuntime().exec(cmd).waitFor();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public Log4jRCE(){
        System.out.println("I am Log4jRCE from remote222!!!");
    }
}
