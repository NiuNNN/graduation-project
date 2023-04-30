package niuniu.javaweb.utils.tools;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils.tools
 * @date 2023/3/21 10:12
 */
public class HouseTools {

    //定义一层最多30间房间
    static String HouseNameStr[] = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};

    /**
     * 获取房号
     *
     * @param strings
     * @return
     */
    public static String getHouseNumber(String[] strings) {
        if (strings.length == 0) {
            return HouseNameStr[0];
        } else if (strings.length == HouseNameStr.length) {
            return "";
        } else {
//            匹配房号
            for (int i = 0; i < strings.length; i++) {
                strings[i] = strings[i].substring(strings[i].length() - 2);
//                System.out.println(strings[i]);
                if (!HouseNameStr[i].equals(strings[i])) {
//                    System.out.println(HouseNameStr[i]);
                    return HouseNameStr[i];
                }
            }
            return HouseNameStr[strings.length];
        }
    }

}
