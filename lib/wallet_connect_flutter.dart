
import 'dart:async';

import 'package:flutter/services.dart';

class WalletConnectFlutter {
  static const MethodChannel _channel =
      const MethodChannel('wallet_connect_flutter');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
