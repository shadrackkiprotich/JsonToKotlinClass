package wu.seal.jsontokotlin.ui

import com.intellij.util.ui.JBDimension
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.TargetJsonConverter
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.ScrollPaneConstants

/**
 *
 * Created by Seal.Wu on 2018/2/7.
 */
/**
 * JSON Converter Annotation Tab View
 */
class AdvancedAnnotationTab(isDoubleBuffered: Boolean) : JPanel(BorderLayout(), isDoubleBuffered) {

    init {

        val customizeAnnotationConfigPanel = jVerticalLinearLayout(addToParent = false) {

            setAlignLeft(true)

            jLabel("Annotation Import Class : ")

            setAlignLeft(false)

            jTextAreaInput(ConfigManager.customAnnotationClassImportdeclarationString) {
                ConfigManager.customAnnotationClassImportdeclarationString = it.text
            }

            setAlignLeft(true)

            jLabel("Class Annotation Format:")

            setAlignLeft(false)

            jTextAreaInput(ConfigManager.customClassAnnotationFormatString) {
                ConfigManager.customClassAnnotationFormatString = it.text
            }

            setAlignLeft(true)

            jLabel("Property Annotation Format:")

            setAlignLeft(false)

            jTextAreaInput(ConfigManager.customPropertyAnnotationFormatString) {
                ConfigManager.customPropertyAnnotationFormatString = it.text
            }

        }

        jScrollPanel(JBDimension(500, 300)) {
            jVerticalLinearLayout {
                add(AnnotationsSelectPanel(true) {
                    customizeAnnotationConfigPanel.isVisible = it
                })
                add(customizeAnnotationConfigPanel)
            }
        }.apply {
            verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS //make sure when open customize annotation panel it's layout view will no change
        }
    }

    /**
     * Target JsonLib ConfigPanel
     */
    class AnnotationsSelectPanel(isDoubleBuffered: Boolean, callBack: (isCustomizeAnnotationSelected: Boolean) -> Unit) : JPanel(BorderLayout(), isDoubleBuffered) {
        init {

            jGridLayout(5,2) {
                jButtonGroup {
                    jRadioButton("None", ConfigManager.targetJsonConverterLib == TargetJsonConverter.None) {
                        addActionListener {
                            ConfigManager.targetJsonConverterLib = TargetJsonConverter.None
                        }
                    }

                    jRadioButton("None (Camel Case)", ConfigManager.targetJsonConverterLib == TargetJsonConverter.NoneWithCamelCase) {
                        addActionListener {
                            ConfigManager.targetJsonConverterLib = TargetJsonConverter.NoneWithCamelCase
                        }
                    }

                    jRadioButton("Gson", ConfigManager.targetJsonConverterLib == TargetJsonConverter.Gson) {
                        addActionListener {
                            ConfigManager.targetJsonConverterLib = TargetJsonConverter.Gson
                        }
                    }

                    jRadioButton("Jackson", ConfigManager.targetJsonConverterLib == TargetJsonConverter.Jackson) {
                        addActionListener {
                            ConfigManager.targetJsonConverterLib = TargetJsonConverter.Jackson
                        }
                    }

                    jRadioButton("Fastjson",      ConfigManager.targetJsonConverterLib == TargetJsonConverter.FastJson) {
                        addActionListener {
                            ConfigManager.targetJsonConverterLib = TargetJsonConverter.FastJson
                        }
                    }

                    jRadioButton("MoShi (Reflect)",   ConfigManager.targetJsonConverterLib == TargetJsonConverter.MoShi ) {
                        addActionListener {
                            ConfigManager.targetJsonConverterLib = TargetJsonConverter.MoShi
                        }
                    }

                    jRadioButton("MoShi (Codegen)",  ConfigManager.targetJsonConverterLib == TargetJsonConverter.MoshiCodeGen) {
                        addActionListener {
                            ConfigManager.targetJsonConverterLib = TargetJsonConverter.MoshiCodeGen
                        }
                    }
                    jRadioButton("LoganSquare",  ConfigManager.targetJsonConverterLib == TargetJsonConverter.LoganSquare) {
                        addActionListener {
                            ConfigManager.targetJsonConverterLib = TargetJsonConverter.LoganSquare
                        }
                    }
                    jRadioButton("kotlinx.serialization", ConfigManager.targetJsonConverterLib == TargetJsonConverter.Serilizable) {
                        addActionListener {
                            ConfigManager.targetJsonConverterLib = TargetJsonConverter.Serilizable
                        }
                    }

                   jRadioButton("Others by customize", ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom) {
                        addActionListener {
                            ConfigManager.targetJsonConverterLib = TargetJsonConverter.Custom
                        }
                        addChangeListener {
                            callBack(isSelected)
                        }
                    }
                    callBack(ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom)
                }
            }
        }
    }
}
