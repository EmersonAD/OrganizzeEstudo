package com.example.organizze.Helper

import android.os.Build
import android.util.Base64
import java.util.*
class Base64Custom {


    fun EncodeBase64(text:String): ByteArray? {
        return Base64.encode(text.toByteArray(), Base64.DEFAULT)
    }
    fun DecodeBase64(textDecode:String): String {
        return String(Base64.decode(textDecode, Base64.DEFAULT))
    }

}