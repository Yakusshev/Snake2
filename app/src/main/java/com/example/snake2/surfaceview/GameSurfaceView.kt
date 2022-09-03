package com.example.snake2.surfaceview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceView
import com.example.domain.config.Direction
import com.example.domain.config.GLOW
import com.example.domain.config.SNAKE_SIZE_DEFAULT
import com.example.domain.models.Field
import com.example.domain.models.Snake
import com.example.snake2.R
import com.example.snake2.activity.ViewModelObserver
import java.lang.NullPointerException


class GameSurfaceView(context: Context, attrs: AttributeSet?, defStyle: Int) :
    SurfaceView(context, attrs, defStyle), ViewModelObserver {
    //private val paint = Paint()
    private val backgroundColor = context.getColor(R.color.background_surface_view)
    private var snakeColor = context.getColor(R.color.snake)
    private var appleColor = context.getColor(R.color.apple)

    private val paintSnake = Paint()
    private val paintSnakeBlur = Paint()

    private val paintApple = Paint()
    private val paintAppleBlur = Paint()

    val path = Path()

//    private val preferences: SharedPreferences = context.getSharedPreferences("preferences", 0)
//    private val glow = preferences.getBoolean(context.getString(R.string.glow_key), false)

    init {
        //paintSnake.isAntiAlias = true
        //paintSnake.isDither = true
        paintSnake.style = Paint.Style.FILL
        paintSnake.color = snakeColor
        //paintSnake.setShadowLayer(50f, 0f, 0f, snakeColor)

        //RadialGradient()

        paintSnakeBlur.set(paintSnake)
        //paintSnakeBlur.setShadowLayer(30f, 0, 0,)
        //paintSnakeBlur.style = Paint.Style.STROKE
        //paintSnakeBlur.strokeWidth = 10f
        paintSnakeBlur.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.SOLID)

        paintApple.set(paintSnake)
        paintApple.color = appleColor
        //paintApple.setShadowLayer(50f, 0f, 0f, appleColor)

        paintAppleBlur.set(paintSnakeBlur)
        paintAppleBlur.color = appleColor
    }

    override fun draw(gameModel: Field) {
        drawFrame(gameModel)
    }

    fun drawFrame(gameModel: Field) {
        //Log.d("threads", "drawFrame: ${Looper.myLooper()}")
        try {
            val canvas: Canvas = holder.lockCanvas()
            drawFrame(canvas, gameModel)
            holder.unlockCanvasAndPost(canvas)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    private fun drawFrame(canvas: Canvas, gameModel: Field) {
        canvas.drawColor(backgroundColor)
            //canvas.drawColor(Color.BLACK)

        for (apple in gameModel.apples){
            if (GLOW) {
                canvas.drawRect(apple.rect, paintAppleBlur)
                canvas.drawRect(apple.rect, paintAppleBlur)
                canvas.drawRect(apple.rect, paintAppleBlur)
                canvas.drawRect(apple.rect, paintAppleBlur)
            }
            canvas.drawRect(apple.rect, paintApple)
        }

        /*try {
        } catch (e: Exception) {
            e.printStackTrace()
        }*/ //IndexOutOfBoundsException, ConcurrentModificationException //TODO resolve ConcurrentModificationException

        //canvas.drawPath(path, paintSnakeBlur)

        if (GLOW) {
            path.reset()
            for (s in gameModel.snake) {
                path.addRect(RectF(s.rect), Path.Direction.CW)
                canvas.drawRect(s.rect, paintSnake)
            }
            canvas.drawPath(path, paintSnakeBlur)
        } else {
            for (s in gameModel.snake)
                canvas.drawRect(s.rect, paintSnake)
        }


        //path.
        //canvas.drawPath(path, paintSnake)
        //path.

        /*try {
        } catch (e: ConcurrentModificationException) {
            e.printStackTrace()
        }*/
    }

    private fun Snake.blur() : Rect {
        val newRect = rect.copy()
        if (direction == Direction.Top || direction == Direction.Bottom) {
            with (newRect) {
                top = rect.top
                bottom = rect.bottom
                left = rect.left - SNAKE_SIZE_DEFAULT / 2
                right = rect.right + SNAKE_SIZE_DEFAULT / 2
            }
        } else {
            with (newRect) {
                top = rect.top - SNAKE_SIZE_DEFAULT / 2
                bottom = rect.bottom + SNAKE_SIZE_DEFAULT / 2
                left = rect.left
                right = rect.right
            }
        }
        return newRect
    }

    private fun Rect.copy() : Rect {
        return Rect(left, top, right, bottom)
    }

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    //private val gestureListener = GestureDetectorCompat(this.context, MyGestureListener())

    /* override fun onTouchEvent(event: MotionEvent?): Boolean {
         gestureListener.onTouchEvent(event)
         performClick()
         return super.onTouchEvent(event)
     }

     override fun performClick(): Boolean {
         super.performClick()
         //doSomething()
         return true
     }*/
}