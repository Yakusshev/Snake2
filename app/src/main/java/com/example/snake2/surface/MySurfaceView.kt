package com.example.snake2.surface

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView


class MySurfaceView(context: Context, attrs: AttributeSet?, defStyle: Int) :
    SurfaceView(context, attrs, defStyle), SurfaceHolder.Callback {
    private var mMyThread: MyThread? = null

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) { //вызывается, когда surfaceView появляется на экране
        mMyThread = MyThread(getHolder())
        mMyThread!!.setRunning(true)
        mMyThread!!.start() //запускает процесс в отдельном потоке
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        //когда view меняет свой размер
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) { //когда view исчезает из поля зрения
        var retry = true
        mMyThread?.setRunning(false) //останавливает процесс
        while (retry) {
            try {
                mMyThread?.join() //ждет окончательной остановки процесса
                retry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()//не более чем формальность
            }
        }
    }
}