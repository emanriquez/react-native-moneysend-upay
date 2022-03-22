import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-moneysend-reactnative' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const MoneySendUPAY = NativeModules.MoneySendUPAY
  ? NativeModules.MoneysendReactnative
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function MoneySendLink(senderid: string, url: string, apikey: string) {
  return new Promise((resolve, reject) => {
   // console.log('CALL MONEYSENDLINK');
    try {
      MoneySendUPAY.MoneySendUPAY(
        senderid,
        url,
        apikey,
        (error: any, resultId: any) => {
          if (error) {
            reject(error);
          } else {
            resolve(resultId);
          }
        }
      );
    } catch (e) {
      console.log('ERROR INIT MONEYSEND');
      reject(e);
    }
  });
}