package com.example.wallet_connect_flutter
import androidx.multidex.MultiDexApplication
import com.squareup.moshi.Moshi
import com.example.wallet_connect_flutter.server.BridgeServer
import org.walletconnect.*

import okhttp3.OkHttpClient
import org.komputing.khex.extensions.toNoPrefixHexString
import org.walletconnect.Session
import org.walletconnect.impls.*
import org.walletconnect.nullOnThrow
import java.io.File
import java.util.*

class ExampleApplication : MultiDexApplication() {
    override fun onCreate() {super.onCreate()}
    companion object {
        private lateinit var client: OkHttpClient
        private lateinit var moshi: Moshi
        private lateinit var bridge: BridgeServer
        private lateinit var storage: WCSessionStore
        lateinit var config: Session.FullyQualifiedConfig
        lateinit var session: Session
        private fun initClient() {
            client = OkHttpClient.Builder().build()
        }
    
        private fun initMoshi() {
            moshi = Moshi.Builder().build()
        }
    
    
        private fun initBridge() {
            bridge = BridgeServer(moshi)
            bridge.start()
        }
    
      
        fun resetSession(qr: String) {
            initMoshi()
            initClient()
            initBridge()
            nullOnThrow { session }?.clearCallbacks()
            val key = ByteArray(32).also { Random().nextBytes(it) }.toNoPrefixHexString()
            config = Session.FullyQualifiedConfig(UUID.randomUUID().toString(), "http://localhost:${BridgeServer.PORT}", key)
            session = WCSession(
                config,
                MoshiPayloadAdapter(moshi),
                storage,
                OkHttpTransport.Builder(client, moshi),
                Session.PeerMeta(name = "Example App")
            )
            session.offer()
        }
    }
}

