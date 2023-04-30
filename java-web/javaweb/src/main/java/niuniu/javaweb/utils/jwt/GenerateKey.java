package niuniu.javaweb.utils.jwt;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils.jwt
 * @date 2023/2/7 17:55
 */
public class GenerateKey {

    //公钥
    private static final String pubKeyPath = "E:\\graduation-project\\file\\local\\Rsa\\rsa.pub";

    //私钥
    private static final String priKeyPath = "E:\\graduation-project\\file\\local\\Rsa\\rsa.pri";


    public static void main(String[] args) throws Exception {
        GenerateKey jwtTest = new GenerateKey();
        jwtTest.testRsa();
    }

//    生成公钥和私钥
    public void testRsa() throws Exception {
        RSAUtil.generateKey(pubKeyPath, priKeyPath, "NiuNiu");
    }


}