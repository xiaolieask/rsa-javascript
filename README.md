# rsa-javascript
rsa-javascript
# NPM 下载包
npm i rsa-javascript --save-dev
# js使用 （加密）
```javascript
import RSA from 'rsa-javascript'
var rsa_m = "b582bfab21f62568d50d2e216c2bf2a76f9e2742b0914d130e76e8d3c24a6a28ba38a4d623d92b66af25844de17138216d0adffb8b14d4ad4d911868e420e9580b7d78e21d1d24a0ed3d7c3dfa61725b55705afb5fa2249478a4c46f3d6d48782111c744bd5abaaa5490ba488b00465a6e2c1f104df44d0ea0f7bf57e985e19d"
var rsa_e = "10001"
RSA.setMaxDigits(130) //131 => n的十六进制位数/2+3
cont key = new RSA.RSAKeyPair(rsa_e, '', rsa_m) //e,m是从通过后台Java穿过来的
var password = "123456";
password = RSA.encryptedString(key, password); // 对你的密码加密处理 ，不支持汉字
// 将password传递到后台，在后台使用Java解密
```
# java使用 （解密）
```javascript
Java生成秘钥及存储私钥进Redis
//解密
try {
    PrivateKey privateKey = SecurityKeyUtil.getPrivateKey(UserAspect.privateKeyString);
    aco = SecurityKeyUtil.decrypt(privateKey, aco);
    pwd = SecurityKeyUtil.decrypt(privateKey, pwd);
} catch (Exception e) {
    e.printStackTrace();
    throw new PersonUserException(PersonUserEnum.SESSION_TIMEOUT);
}
```
For detailed explanation on how things work, checkout the [docs for rsa-javascript](https://github.com/xiaolieask/rsa-js-java).
