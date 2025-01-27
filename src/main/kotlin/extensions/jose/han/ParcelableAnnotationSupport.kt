package extensions.jose.han

import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.Annotation
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.ui.checkBox
import wu.seal.jsontokotlin.ui.horizontalLinearLayout
import wu.seal.jsontokotlin.ui.link
import javax.swing.JPanel

/**
 *  @author jose.han
 *  @Date 2019/7/27Ø
 */
object ParcelableAnnotationSupport : Extension() {

    const val configKey = "jose.han.add_parcelable_annotatioin_enable"

    override fun createUI(): JPanel {
        return horizontalLinearLayout {
            checkBox("Enable Parcelable Support ", getConfig(configKey).toBoolean()) { isSelectedAfterClick ->
                setConfig(configKey, isSelectedAfterClick.toString())
            }()
            link("May Need Some Config", "https://github.com/wuseal/JsonToKotlinClass/blob/master/parceable_support_tip.md")()
            fillSpace()
        }
    }

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {
        return if (getConfig(configKey).toBoolean()) {

            val classAnnotationString1 = "@SuppressLint(\"ParcelCreator\")"
            val classAnnotationString2 = "@Parcelize"

            val classAnnotation1 = Annotation.fromAnnotationString(classAnnotationString1)
            val classAnnotation2 = Annotation.fromAnnotationString(classAnnotationString2)

            return kotlinDataClass.copy(annotations = listOf(classAnnotation1, classAnnotation2), parentClassTemplate = "Parcelable")
        } else {
            kotlinDataClass
        }
    }

    override fun intercept(originClassImportDeclaration: String): String {

        val classAnnotationImportClassString = "import kotlinx.android.parcel.Parcelize".append("import android.os.Parcelable")

        return if (getConfig(configKey).toBoolean()) {
            originClassImportDeclaration.append(classAnnotationImportClassString)
        } else {
            originClassImportDeclaration
        }
    }
}
