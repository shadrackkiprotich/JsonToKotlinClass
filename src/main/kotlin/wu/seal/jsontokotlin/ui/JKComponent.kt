package wu.seal.jsontokotlin.ui

//interface JKComponentController {
//    var marginTop: Int
//    var marginBottom: Int
//    var alignLeft: Boolean
//
//}
//
//interface JKContainerController {
//    fun beforeAdd(comp: Component?)
//}
//
//class JKLabel: JLabel, JKComponentController {
//    override var alignLeft: Boolean = false
//    override var marginTop: Int = 0
//    override var marginBottom: Int = 0
//
//    constructor(text: String?, icon: Icon?, horizontalAlignment: Int) : super(text, icon, horizontalAlignment)
//    constructor(text: String?, horizontalAlignment: Int) : super(text, horizontalAlignment)
//    constructor(text: String?) : super(text)
//    constructor(image: Icon?, horizontalAlignment: Int) : super(image, horizontalAlignment)
//    constructor(image: Icon?) : super(image)
//    constructor() : super()
//
//}
//
//open class JKBox(axis: Int) : Box(axis),JKComponentController{
//    override var marginTop: Int = 0
//    override var marginBottom: Int = 0
//    override var alignLeft: Boolean = false
//
//}
//
//
//fun Any.jKLabel(text: String, textSize: Float = 13f, init: JKLabel.() -> Unit = {}): JKLabel {
//    val jLabel = JKLabel(text).apply {
//        font = font.deriveFont(textSize)
//    }
//    jLabel.init()
//    checkAddView(this, jLabel)
//    return jLabel
//}
//
//
//fun Any.jKVerticalLinearLayout(constraintsInParent: Any? = BorderLayout.CENTER, addToParent: Boolean = true, init: JKVerticalLinearLayout.() -> Unit): JKVerticalLinearLayout {
//
//    val jVerticalLinearLayout = JKVerticalLinearLayout()
//    val jPanel = JPanel().apply {
//        layout = BoxLayout(this, BoxLayout.Y_AXIS)
//        componentOrientation = ComponentOrientation.LEFT_TO_RIGHT
//        jVerticalLinearLayout.init()
//        add(jVerticalLinearLayout)
//    }
//    if (addToParent) {
//        checkAddView(this, jPanel, constraintsInParent)
//    }
//    return jVerticalLinearLayout
//}
//
//class JKVerticalLinearLayout : Box(BoxLayout.Y_AXIS) , JKComponentController,JKContainerController{
//    override var alignLeft: Boolean = false
//    override var marginTop: Int = 0
//    override var marginBottom: Int = 0
//
//    /**
//     * fill the fixed space for linear layout
//     */
//    private fun addFixedSpace(spaceHeight: Int) {
//        super.add(createVerticalStrut(JBUI.scale(spaceHeight)))
//    }
//
//    override fun addImpl(comp: Component?, constraints: Any?, index: Int) {
//        beforeAdd(comp)
//        super.addImpl(comp, constraints, index)
//    }
//    override fun beforeAdd(comp: Component?) {
//       if (comp is JKComponentController) {
//           if (comp.marginTop != 0) {
//               addFixedSpace(comp.marginTop)
//           }
//           if (comp.alignLeft) {
//               val jPanel = JPanel().apply {
//                   layout = BoxLayout(this, BoxLayout.LINE_AXIS)
//                   componentOrientation = ComponentOrientation.LEFT_TO_RIGHT
//                   val horizontalBox = HorizontalLinearLayoutBox()
//                   horizontalBox.add(comp)
//                   horizontalBox.add(createHorizontalGlue())
//                   add(horizontalBox)
//               }
//               add(jPanel)
//           }
//       }
//    }
//}



