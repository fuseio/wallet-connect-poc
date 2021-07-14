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
         fun initClient() {
            client = OkHttpClient.Builder().build()
        }
    
         fun initMoshi() {
            moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        }
    
    
         fun initBridge() {
            bridge = BridgeServer(moshi)
            bridge.start()
        }
    
      
        fun resetSession(qr: String) {
            val file = File.createTempFile("yoFGF", "session_store.json")
            println(file.path)
            storage = FileWCSessionStore(file,moshi).apply {  }
            nullOnThrow { session }?.clearCallbacks()

            val key = ByteArray(32).also { Random().nextBytes(it) }.toNoPrefixHexString()
            config = Session.Config(UUID.randomUUID().toString(), "http://localhost:${BridgeServer.PORT}", key,"wc",1)
            session = WCSession(
                config,
                MoshiPayloadAdapter(moshi),
                storage,
                OkHttpTransport.Builder(client, moshi),
                Session.PeerMeta(name = "Example App")
            )
            session.offer()
            print("client: " + client.cache)
            print("client: " +  MoshiPayloadAdapter(moshi),
            )

        }
    }
}

