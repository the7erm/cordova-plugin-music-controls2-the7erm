//
//  MainViewController+MusicControls.m
//
//
//  Created by Juan Gonzalez on 12/17/16.
//
//

#import <Foundation/Foundation.h>


#import "CDVViewController+MusicControls.h"

@implementation CDVViewController (MusicControls)

- (void) remoteControlReceivedWithEvent: (UIEvent *) receivedEvent {
    [[NSNotificationCenter defaultCenter] postNotificationName:@"musicControlsEventNotification" object:receivedEvent];
}

@end
