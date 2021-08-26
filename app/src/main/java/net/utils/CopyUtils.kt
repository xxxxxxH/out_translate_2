package net.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils
import android.widget.Toast

open class CopyUtils {
    fun copy(context: Context, content: String) {
        if (TextUtils.isEmpty(content) or TextUtils.equals(content, "No Data")) {
            return
        }
        val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Label", content)
        manager.setPrimaryClip(clipData)
        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
    }
}