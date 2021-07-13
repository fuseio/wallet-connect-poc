import 'dart:async';

import 'package:flutter/services.dart';

class WalletConnectFlutter {
  static const MethodChannel _channel =
      const MethodChannel('wallet_connect_flutter');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String?> onConnect(String qr) async {
    print("qr: " + qr);
    final String? res = await _channel.invokeMethod('onConnect', {'qr': qr});
    return res;
  }

  static Future<String?> onDisconnect() async {
    final String? res = await _channel.invokeMethod('onDisconnect');
    return res;
  }

  static Future<String?> onSendTX() async {
    final String? res = await _channel.invokeMethod('onSendTX');
    return res;
  }
}
