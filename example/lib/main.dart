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

  Future<void> initPlatformState() async {
    String platformVersion;
    try {
      platformVersion = await WalletConnectFlutter.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    if (!mounted) return;

    setState(() {
      platformVersion = platformVersion;
    });
  }



  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        backgroundColor: const Color(0xff111637),
        body: Center(
          child: Container(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Text("Wallet connect Flutter",style: TextStyle(color: Colors.white,fontSize: 18),),
                Padding(
                  padding: const EdgeInsets.only(top: 40.0),
                  child: Container(width: 120,height: 50, child: ElevatedButton(onPressed: (){}, child: Text("Connect"))),
                ),
                Padding(
                  padding: const EdgeInsets.only(top: 30.0),
                  child: Container(width: 120,height: 50,child: ElevatedButton(onPressed: (){}, child: Text("Disconnect"))),
                ),
                Padding(
                  padding: const EdgeInsets.only(top: 30.0),
                  child: Container(width: 120,height: 50, child: ElevatedButton(onPressed: (){}, child: Text("Send TX"))),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
