# rsa-js-java
rsa-js-java
# NPM 下载包
npm i rsa-js-java --save-dev
# js使用
```javascript
import RSA from 'rsa-js-java'
RSA.setMaxDigits(130)
key = RSA.RSAKeyPair(exponent,'',module)
RSA.encryptedString(key, 'register_pwd')
```
# java使用
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
For detailed explanation on how things work, checkout the [guide](http://vuejs-templates.github.io/webpack/) and [docs for rsa-js-java](https://github.com/xiaolieask/rsa-js-java).