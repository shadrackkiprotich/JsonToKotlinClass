package extensions.wu.seal

import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.ui.*
import javax.swing.JPanel

object PropertySuffixSupport : Extension() {

    private const val suffixKeyEnable = "wu.seal.property_suffix_enable"
    private const val suffixKey = "wu.seal.property_suffix"
    override fun createUI(): JPanel {

        val prefixJField = jTextInput(getConfig(suffixKey), getConfig(suffixKeyEnable).toBoolean()) {
            addFocusLostListener {
                if (getConfig(suffixKeyEnable).toBoolean()) {
                    setConfig(suffixKey, text)
                }
            }
            document = NamingConventionDocument(80)
        }

        return jHorizontalLinearLayout {
            jCheckBox("Suffix append after every property: ", getConfig(suffixKeyEnable).toBoolean()) {
                addActionListener {
                    setConfig(suffixKeyEnable, isSelected.toString())
                    prefixJField.isEnabled = isSelected
                }
            }
            add(prefixJField)
        }
    }


    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {
        return if (getConfig(suffixKeyEnable).toBoolean() && getConfig(suffixKey).isNotEmpty()) {
            val originProperties = kotlinDataClass.properties
            val newProperties = originProperties.map {
                val suffix = getConfig(suffixKey)
                if (it.name.isNotEmpty()) {
                    val newName = it.name + suffix.first().toUpperCase() + suffix.substring(1)
                    it.copy(name = newName)
                } else it
            }
            kotlinDataClass.copy(properties = newProperties)
        } else {
            kotlinDataClass
        }
    }
}