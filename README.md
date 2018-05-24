# rsa-js-java
rsa-javascript
# NPM 下载包
npm i rsa-javascript --save-dev
# js使用 （加密）
```javascript
import RSA from 'rsa-javascript'
RSA.setMaxDigits(130)
key = new RSA.RSAKeyPair(10001,'','1e2qwe2we32qw35e4qw3e43qwe4qw3e')
RSA.encryptedString(key, 'pwd') // 对你的密码加密处理 
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