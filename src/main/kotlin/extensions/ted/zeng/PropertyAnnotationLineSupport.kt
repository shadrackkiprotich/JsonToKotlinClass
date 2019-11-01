package extensions.ted.zeng

import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.classscodestruct.Property
import wu.seal.jsontokotlin.ui.jCheckBox
import wu.seal.jsontokotlin.ui.jHorizontalLinearLayout
import javax.swing.JPanel

/**
 * Created by ted on 2019-06-13 18:10.
 */
object PropertyAnnotationLineSupport : Extension() {

    private const val enable = "ted.zeng.property_annotation_in_same_line_enable"

    override fun createUI(): JPanel {
        return jHorizontalLinearLayout {
            jCheckBox("Keep Annotation And Property In Same Line", getConfig(enable).toBoolean()) {
                addActionListener {
                    setConfig(enable, isSelected.toString())
                }
            }
            fillSpace()
        }
    }

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {
        if (getConfig(enable).toBoolean()) {
            kotlinDataClass.properties.forEach(Property::letLastAnnotationStayInSameLine)
        }
        return kotlinDataClass
    }

}