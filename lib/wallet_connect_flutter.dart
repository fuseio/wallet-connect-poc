import 'dart:async';

import 'package:flutter/services.dart';

class WalletConnectFlutter {
  static const MethodChannel _channel =
      const MethodChannel('wallet_connect_flutter');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String?> onConnect() async {
    final String? res = await _channel.invokeMethod('onConnect');
    return res;
  }

  static Future<String?> onDisconnect() async {
    final String? res = await _channel.invokeMethod('onDisconnect');
    return res;
  }
}
