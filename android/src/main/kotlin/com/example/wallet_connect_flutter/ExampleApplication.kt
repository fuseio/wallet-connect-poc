package com.example.wallet_connect_flutter
import androidx.multidex.MultiDexApplication
import com.squareup.moshi.*
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
     override fun onCreate() {
        super.onCreate()
        initMoshi()
        initClient()
        initBridge()
    }
    companion object {
        private lateinit var client: OkHttpClient
        private lateinit var moshi: Moshi
        private lateinit var bridge: BridgeServer
        private lateinit var storage: WCSessionStore
        lateinit var config: Session.Config
        lateinit var session: Session
        private fun initClient() {
            client = OkHttpClient.Builder().build()
        }
    
        private fun initMoshi() {
            moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        }
    
    
        private fun initBridge() {
            bridge = BridgeServer(moshi)
            bridge.start()
        }
    
      
        fun resetSession(qr: String) {
            initMoshi()
            initClient()
            initBridge()
            val file = File.createTempFile("yoFGF", "yo2GFDGFD.json")
            storage = FileWCSessionStore(file,moshi).apply {  }
            nullOnThrow { session }?.clearCallbacks()
            //val key = ByteArray(32).also { Random().nextBytes(it) }.toNoPrefixHexString()
            config = Session.Config("9297bfab-8fa9-40a0-a15a-c809270ae82e", "https://bridge.walletconnect.org", "bf2ee021fba079b0bf367df053fbb588d3ae574f275bc8120984d0def0b5f473")
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

