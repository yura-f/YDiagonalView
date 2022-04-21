/**
 * @author Yura F (yura-f.github.io)
 */
class YDiagonalView: View {
    private var bgPaint = Paint()
    private var linePaint = Paint()

    private var heightDiagonal: Float = 0f
    private var offsetStroke: Float = 0f
    private var lineStartY:Float = 0f
    private var lineFinishY:Float = 0f
    private var diagonalPoint:Float = 0f

    private var pathRect: Path? = null

    constructor(context: Context):super(context){
        initUI()
    }

    constructor(context: Context, attributeSet: AttributeSet):super(context, attributeSet){
        setupFields(attributeSet)

        initUI()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int):super(context, attributeSet, defStyle){
        setupFields(attributeSet)

        initUI()
    }

    private fun initUI() {
        bgPaint.flags = Paint.ANTI_ALIAS_FLAG
        bgPaint.style = Paint.Style.FILL
        bgPaint.color = ContextCompat.getColor(context, android.R.color.white)

        linePaint.flags = Paint.ANTI_ALIAS_FLAG
        linePaint.color = ContextCompat.getColor(context, android.R.color.darker_gray)
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeCap = Paint.Cap.SQUARE
        linePaint.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24f, context.resources.displayMetrics)
        linePaint.isDither = true

        offsetStroke = linePaint.strokeWidth / 2
    }

    private fun setupFields(attrs: AttributeSet) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.YDiagonalView, 0, 0)
        if (attributes.hasValue(R.styleable.YDiagonalView_heightDiagonal)) {
            heightDiagonal = attributes.getDimension(R.styleable.YDiagonalView_heightDiagonal, 0f)
        }

        attributes.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val wF = w.toFloat()
        val hF = h.toFloat()

        diagonalPoint = hF
        if (heightDiagonal > 0f) diagonalPoint = hF - heightDiagonal

        pathRect = Path()
        pathRect?.lineTo(wF, 0f)
        pathRect?.lineTo(wF, diagonalPoint)
        pathRect?.lineTo(0f, hF)
        pathRect?.close()

        lineStartY = diagonalPoint - offsetStroke
        lineFinishY = hF - offsetStroke
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPath(pathRect, bgPaint)
        canvas.drawLine(0f, lineFinishY, measuredWidth.toFloat(), lineStartY,  linePaint)
    }
}
