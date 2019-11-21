package edu.washington.widgetexample

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.provider.ContactsContract.Intents.Insert.ACTION
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.content.getSystemService
import kotlinx.android.synthetic.main.layout_floating_widget.view.*

class WidgetService : Service() {

    var widgetView: View? = null
    var windowManager: WindowManager? = null


    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        widgetView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            0,
            0,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager?.addView(widgetView, params)

        widgetView?.findViewById<ImageView>(R.id.iv_collapsed_close)?.setOnClickListener {
            stopSelf()
        }

        widgetView?.findViewById<ImageView>(R.id.iv_expanded_close)?.setOnClickListener {
            widgetView?.canvas_expanded?.visibility = View.GONE
            widgetView?.canvas_collapsed?.visibility = View.VISIBLE

        }

        widgetView?.canvas_root?.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_UP) {
                    widgetView?.canvas_collapsed?.visibility = View.GONE
                    widgetView?.canvas_expanded?.visibility = View.VISIBLE
                }
                return true
            }
        })

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        windowManager?.removeView(widgetView)
        super.onDestroy()
    }
}
