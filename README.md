# react-native-moneysend-upay

Bridge MoneySend

## Installation

1. Clonar proyecto en tu sistema
2. en Package.json dejar nueva dependencia vinculada a proyecto descargado

3. Ejecuta yarn o npm install
4. Linkea proyecto para Android y IOS
react-native link react-native-moneysend-upay

6. Linkear manualmente en setting.gradle en carpeta Android otras librerias requeridas
include ':moneysend'
project(':moneysend').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-moneysend-upay/libs/moneysend')



7. En Android archivo local.properties agregar:

org.gradle.jvmargs=-XX\:MaxHeapSize\=512m -Xmx512m

8. en Android archivo gradle.properties agregar:

org.gradle.parallel=false
org.gradle.caching=false
org.gradle.configureondemand=false

org.gradle.jvmargs=-Xmx4096m -XX:MaxPermSize=4096m -XX:+HeapDumpOnOutOfMemoryError


## AndroidManifest.xml

```
 <uses-permission android:name="android.permission.INTERNET" />
```



## Usage

```js
import { MoneySendLink } from "react-native-moneysend-upay";

// ...
MoneySendLink(senderid, urlmoney, apikey)
  .then((result) => {
    if (result) {
      SucessText(result);
    }
  })
  .catch((err) => {
    console.log('ERROR MONEYSEND');
  });
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
