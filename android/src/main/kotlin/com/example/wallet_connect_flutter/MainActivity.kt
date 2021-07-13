package com.example.wallet_connect_flutter
import java.net.URLEncoder

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.walletconnect.Session
import org.walletconnect.*
import com.example.wallet_connect_flutter.ExampleApplication

class MainActivity : Activity(), Session.Callback {
    private var txRequest: Long? = null
    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onMethodCall(call: Session.MethodCall) {}
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState) }
    override fun onStart() { super.onStart() }

    override fun onStatus(status: Session.Status) {
        when(status) {
            Session.Status.Approved -> sessionApproved()
            Session.Status.Closed -> sessionClosed()
            Session.Status.Connected,
            Session.Status.Disconnected,
            is Session.Status.Error -> {
                println("error of session")
            }
        }
    }

    fun init(){
        val session = nullOnThrow { ExampleApplication.session } ?: return
        session.addCallback(this)
        sessionApproved()
    }
    fun connect(qr: String){
        ExampleApplication.resetSession(qr)
        ExampleApplication.session.addCallback(this)
    }

    fun disconnect(){
        ExampleApplication.session.kill()
    }

    fun sendTX(){
        val from = ExampleApplication.session.approvedAccounts()?.first()
            ?: return Unit //@setOnClickListener
        val txRequest = System.currentTimeMillis()
        ExampleApplication.session.performMethodCall(
            Session.MethodCall.SendTransaction(
                txRequest,
                from,
                "0x24EdA4f7d0c466cc60302b9b5e9275544E5ba552",
                null,
                null,
                null,
                "0x5AF3107A4000",
                ""
                ),
        ::handleResponse
        )
        this.txRequest = txRequest
    }
    private fun sessionApproved() {
        var _res="";
        uiScope.launch {
            _res = "Connected: ${ExampleApplication.session.approvedAccounts().toString()}"
        }
        println("res: " + _res)
    }

    private fun sessionClosed() {
        uiScope.launch {
            var res = "Disconnected"
        }
    }
    private fun handleResponse(resp: Session.MethodCall.Response) {
        if (resp.id == txRequest) {
            txRequest = null
            uiScope.launch {
                //screen_main_response.visibility = View.VISIBLE
                ("Last response: " + ((resp.result as? String) ?: "Unknown response")).also {
                    //screen_main_response.text = it
                }
            }
        }
    }

    override fun onDestroy() {
        ExampleApplication.session.removeCallback(this)
        super.onDestroy()
    }
}
