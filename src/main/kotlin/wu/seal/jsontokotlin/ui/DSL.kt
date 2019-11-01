package wu.seal.jsontokotlin.ui

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBUI
import java.awt.*
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.net.URI
import javax.swing.*

fun checkAddView(parent: Any, vararg children: Component) {
    if (parent is Container) {
        for (child in children) {
            parent.add(child)
        }
    }
}


fun checkAddView(parent: Any, children: Component, constraintsInParent: Any?) {
    if (parent is Container) {
        parent.add(children, constraintsInParent)
    }
}

fun ButtonGroup.jRadioButton(text: String, selected: Boolean = false, init: JRadioButton.() -> Unit): JRadioButton {
    val view = JRadioButton(text, selected)
    view.init()
    this.add(view)
    return view
}


fun Any.jHorizontalLinearLayout(init: JHorizontalLinearLayout.() -> Unit): JPanel {
    val jPanel = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.LINE_AXIS)
        componentOrientation = ComponentOrientation.LEFT_TO_RIGHT
        val horizontalBox = JHorizontalLinearLayout()
        horizontalBox.init()
        add(horizontalBox)
    }
    checkAddView(this, jPanel)
    return jPanel
}

fun Container.jButtonGroup(init: ButtonGroup.() -> Unit): ButtonGroup {
    val buttonGroup = ButtonGroup()
    buttonGroup.init()
    checkAddView(this, *(buttonGroup.elements.toList().toTypedArray()))
    return buttonGroup
}

fun Any.jButton(text: String = "", init: JButton.() -> Unit): JButton {
    val jButton = JButton(text)
    jButton.init()
    checkAddView(this, jButton)
    return jButton
}


fun Any.jLabel(text: String, textSize: Float = 13f, init: JLabel.() -> Unit = {}): JLabel {
    val jLabel = JLabel(text).apply {
        font = font.deriveFont(textSize)
    }
    jLabel.init()
    checkAddView(this, jLabel)
    return jLabel
}

fun Any.jTextField(text: String = "", init: JTextField.() -> Unit = {}): JTextField {
    val jTextField = JTextField(text)
    jTextField.init()
    checkAddView(this, jTextField)
    return jTextField
}


fun Any.jCheckBox(text: String, selected: Boolean = false, init: JBCheckBox.() -> Unit): JCheckBox {
    val jCheckBox = JBCheckBox(text, selected)
    jCheckBox.init()
    checkAddView(this, jCheckBox)
    return jCheckBox
}


/**
 * generate a scrollable component
 */
fun Any.jScrollPanel(size: JBDimension, constraintsInParent: Any = BorderLayout.CENTER, content: () -> Component): JBScrollPane {

    val jScrollPanel = JBScrollPane(content()).apply {
        preferredSize = size
        border = null
    }
    checkAddView(this, jScrollPanel, constraintsInParent)
    return jScrollPanel
}


fun Any.jLine(): JSeparator {
    val jLine = JSeparator(SwingConstants.CENTER).apply {
        maximumSize = JBDimension(10000, 10)
        background = Color.GRAY
    }
    checkAddView(this, jLine)
    return jLine
}

fun Any.jVerticalLinearLayout(isAlignLeft: Boolean = false, constraintsInParent: Any? = BorderLayout.CENTER, addToParent: Boolean = true, init: JVerticalLinearLayout.() -> Unit): JVerticalLinearLayout {

    val jVerticalLinearLayout = JVerticalLinearLayout(isAlignLeft)
    val jPanel = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        componentOrientation = ComponentOrientation.LEFT_TO_RIGHT
        jVerticalLinearLayout.init()
        add(jVerticalLinearLayout)
    }
    if (addToParent) {
        checkAddView(this, jPanel, constraintsInParent)
    }
    return jVerticalLinearLayout
}

/**
 * generate multiple lines text input component
 */
fun Any.jTextAreaInput(initText: String, size: JBDimension = JBDimension(400, 50), enabled: Boolean = true
                       , textLanguageType: LanguageFileType = PlainTextFileType.INSTANCE, onFocusLost: (textAreaInput: Document) -> Unit): JComponent {
    val editorFactory = EditorFactory.getInstance()
    val document = editorFactory.createDocument("").apply {
        setReadOnly(false)
    }
    val editor = editorFactory.createEditor(document, null, textLanguageType, false)
    editor.component.apply {
        isEnabled = enabled
        autoscrolls = true
        preferredSize = size
    }
    editor.contentComponent.addFocusListener(object : FocusListener {
        override fun focusGained(e: FocusEvent?) {
        }

        override fun focusLost(e: FocusEvent?) {
            onFocusLost(editor.document)
        }
    })
    editor.document.setText(initText)
    checkAddView(this, editor.component)
    return editor.component
}


/**
 * generate a link component
 */
fun Any.jLink(text: String, linkURL: String, linkURLColor: String = "#5597EB", maxSize: JBDimension? = null, onclick: () -> Unit = {}): JLabel {
    val jLink = JLabel("<html><a href='$linkURL'><font color=\"$linkURLColor\">$text</font></a></html>").apply {
        if (maxSize != null) {
            maximumSize = maxSize
        }
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                Desktop.getDesktop().browse(URI(linkURL))
                onclick()
            }

            override fun mouseEntered(e: MouseEvent?) {
                cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            }

            override fun mouseExited(e: MouseEvent?) {
                cursor = Cursor.getDefaultCursor()
            }
        })
    }

    checkAddView(this, jLink)
    return jLink
}


/**
 * generate a grid layout component
 */
fun Any.jGridLayout(rows: Int, columns: Int, init: JPanel.() -> Unit = {}): JPanel {
    val jPanel = JPanel().apply {
        layout = GridLayout(rows, columns, 10, 10)
    }
    jPanel.init()
    checkAddView(this, jPanel)
    return jPanel
}


fun Any.jTextInput(initText: String = "", enabled: Boolean = true, init: JTextField.() -> Unit = {}): JTextField {

    val jTextInput = JBTextField().apply {
        text = initText
        maximumSize = JBDimension(10000, 30)
        isEnabled = enabled
    }
    jTextInput.init()
    checkAddView(this, jTextInput)
    return jTextInput
}


fun JTextField.addFocusLostListener(listener: (e: FocusEvent?) -> Unit) {
    addFocusListener(object : FocusListener {
        override fun focusLost(e: FocusEvent?) {
            listener.invoke(e)
        }

        override fun focusGained(e: FocusEvent?) {

        }

    })
}


class JVerticalLinearLayout(private var isAlignLeft: Boolean = false) : Box(BoxLayout.Y_AXIS) {

    /**
     * Space height between lines
     */
    private val lineSpaceHeight = 10

    /**
     * fill the fixed space for linear layout
     */
    fun fixedSpace(spaceHeight: Int) {
        super.add(createVerticalStrut(JBUI.scale(spaceHeight)))
    }

    fun setAlignLeft(alignLeft: Boolean) {
        isAlignLeft = alignLeft
    }

    override fun add(comp: Component?): Component {
        fixedSpace(lineSpaceHeight)
        if (isAlignLeft) {
            val jPanel = JPanel().apply {
                layout = BoxLayout(this, BoxLayout.LINE_AXIS)
                componentOrientation = ComponentOrientation.LEFT_TO_RIGHT
                val horizontalBox = HorizontalLinearLayoutBox()
                horizontalBox.add(comp)
                horizontalBox.add(createHorizontalGlue())
                add(horizontalBox)
            }
            return super.add(jPanel)
        }
        return super.add(comp)
    }
}


class JHorizontalLinearLayout : Box(BoxLayout.X_AXIS) {
    /**
     * fill the remaining space for linear layout,like android empty space with weight value
     */
    fun fillSpace() {
        add(createHorizontalGlue())
    }

    /**
     * fill the fixed space for linear layout
     */
    fun fixedSpace(spaceWidth: Int) {
        add(createHorizontalStrut(JBUI.scale(spaceWidth)))
    }
}





