#import "MoneysendReactnative.h"
#import <React/RCTLog.h>
#import <UIKit/UIKit.h>
#import "moneysend"

@implementation MoneysendReactnative

RCTResponseSenderBlock requestPermissionsResolve;

RCT_EXPORT_MODULE()
// Example metho
// See // https://reactnative.dev/docs/native-modules-ios
RCT_EXPORT_METHOD(MoneySendUPAY
                  :(NSString *)senderid
                  :(NSString *)urlmoney
                  :(NSString *)apikey
                  :(RCTResponseSenderBlock)callback
                 )
{
  requestPermissionsResolve = callback;

  RCTLogInfo(@"INIT NATIVE MONEYSEND");
  NSError *e = nil;
   [BioCaller MoneyCreateLink:[[UIApplication sharedApplication] delegate] senderID:senderid urlMoney:urlMoney, apikey: apikey];
   [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(callBackApp) name:@"MSNNotification" object:nil];
}

- (void)callBackApp{
        if (BioCaller.getResultData  != nil){
            [[NSNotificationCenter defaultCenter] removeObserver:self];
            requestPermissionsResolve(@[[NSNull null],BioCaller.getResultData]);
        }else{
          [[NSNotificationCenter defaultCenter] removeObserver:self];
            requestPermissionsResolve(@"Cerrado",@[[NSNull null]]);
        }
}



@end
