package com.example.wallet_connect_flutter
import com.example.wallet_connect_flutter.server.BridgeServer
import com.example.wallet_connect_flutter.MainActivity
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.walletconnect.Session
import org.walletconnect.nullOnThrow

/** WalletConnectFlutterPlugin */
class WalletConnectFlutterPlugin: FlutterPlugin, MethodCallHandler, Activity() {
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "wallet_connect_flutter")
    channel.setMethodCallHandler(this)
  }
  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      MainActivity().init()
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if(call.method == "onConnect"){
      MainActivity().init()
      val qr: String? = call.argument<String>("qr")
      MainActivity().connect(qr.toString())
      result.success("Finish")
    }else if(call.method == "onDisconnect"){
      MainActivity().disconnect()
      result.success("Success disconnected")
    }else if(call.method == "onSendTX"){
      MainActivity().sendTX()
      result.success("Success send TX")
     } else{
      result.notImplemented()
    }
  }

}
