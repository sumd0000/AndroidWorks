package com.test.androidserver.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.EditText
import com.test.androidserver.R


class LinedEditText// we need this constructor for LayoutInflater
(context: Context, attrs: AttributeSet) : EditText(context, attrs) {
    private val mRect: Rect
    private val mPaint: Paint

    init {

        mRect = Rect()
        mPaint = Paint()
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE)
        mPaint.setColor(R.color.blue) //SET YOUR OWN COLOR HERE
    }

    protected override fun onDraw(canvas: Canvas) {
        //int count = getLineCount();

        val height = height
        val line_height = lineHeight

        var count = height / line_height

        if (lineCount > count)
            count = lineCount//for long text with scrolling

        val r = mRect
        val paint = mPaint
        var baseline = getLineBounds(0, r)//first line

        for (i in 0 until count) {

            canvas.drawLine(r.left.toFloat(), (baseline + 1).toFloat(), r.right.toFloat(), (baseline + 1).toFloat(), paint)
            baseline += lineHeight//next line
        }

        super.onDraw(canvas)
    }
}