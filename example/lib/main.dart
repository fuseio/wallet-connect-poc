//@dart = 2.3
import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:wallet_connect_flutter/wallet_connect_flutter.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        backgroundColor: const Color(0xff111637),
        body: Center(
          child: Container(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Text(
                  "phone version: $platformVersion",
                  style: TextStyle(color: Colors.white, fontSize: 12),
                ),
                Padding(
                  padding: const EdgeInsets.only(top: 20.0),
                  child: Text(
                    "Wallet connect Flutter",
                    style: TextStyle(color: Colors.white, fontSize: 18),
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.only(top: 40.0),
                  child: Container(
                      width: 120,
                      height: 50,
                      child: ElevatedButton(
                          onPressed: () {
                            _onConnect();
                          },
                          child: Text("Connect"))),
                ),
                Padding(
                  padding: const EdgeInsets.only(top: 30.0),
                  child: Container(
                      width: 120,
                      height: 50,
                      child: ElevatedButton(
                          onPressed: () {
                            _onDisconnect();
                          },
                          child: Text("Disconnect"))),
                ),
                Padding(
                  padding: const EdgeInsets.only(top: 30.0),
                  child: Container(
                      width: 120,
                      height: 50,
                      child: ElevatedButton(
                          onPressed: () {}, child: Text("Send TX"))),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Future<void> initPlatformState() async {
    String _platformVersion;
    try {
      _platformVersion = await WalletConnectFlutter.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    if (!mounted) return;

    setState(() {
      platformVersion = _platformVersion;
    });
  }

  _onConnect() {
    print("main: _onConnect");
    return WalletConnectFlutter.onConnect()
        .then((value) => print("value: " + value.toString()));
  }

  _onDisconnect() {
    print("_onDisconnect");
    return WalletConnectFlutter.onDisconnect;
  }
}
