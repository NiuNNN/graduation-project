package niuniu.javaweb.utils.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.utils.jwt
 * @date 2023/2/7 17:55
 */
@Component
public class JWTUtil {

    //公钥
    private static PublicKey publicKey;

    //私钥
    private static PrivateKey privateKey;

    //过期时间
    private static Long ExpirationTime;


    @Value("${jwt.pubKeyPath}")
    public void pubKeyPath(String pubKeyPath) throws Exception {
        this.publicKey = RSAUtil.getPublicKey(pubKeyPath);
    }

    @Value("${jwt.priKeyPath}")
    public void priKeyPath(String priKeyPath) throws Exception {
        this.privateKey = RSAUtil.getPrivateKey(priKeyPath);
    }

    @Value("${jwt.expiration}")
    public void setExpirationTime(Long expiration){
        this.ExpirationTime = expiration;
    }


    /**
     * 私钥生成Token
     * @param oriInfo
     * @return
     * @throws Exception
     */
    public static String generateToken(String oriInfo)  {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + ExpirationTime;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .claim("info",oriInfo)
                .setIssuer("NiuNiu") //签发者
                .setIssuedAt(now) //签发时间
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.RS256,privateKey)
                .compact();
    }

    /**
     * 从token中获取原始信息
     * @param token
     * @return
     * @throws Exception
     */
    public static String parseJWT(String token)  {
        return  Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(token)
                .getBody().get("info")+"";
    }
}
